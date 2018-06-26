package com.dsp.ad.schedule;

import com.dsp.ad.entity.*;
import com.dsp.ad.entity.ext.ExtAd;
import com.dsp.ad.entity.ext.ExtPlan;
import com.dsp.ad.entity.ext.ExtUser;
import com.dsp.ad.enums.AdEnum;
import com.dsp.ad.enums.TaskEnum;
import com.dsp.ad.repository.*;
import com.dsp.ad.service.AdminService;
import com.dsp.ad.service.TaskService;
import com.dsp.ad.util.TimeUtil;
import com.dsp.ad.util.result.ExecResult;
import com.dsp.ad.util.result.LLBExecResult;
import com.dsp.ad.util.result.LLBResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ScheduledTask {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTask.class);

    @Autowired
    private AdminService adminService;

    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private AdRepository adRepository;
    @Autowired
    private UserConsumeLogRepository userConsumeLogRepository;
    @Autowired
    private PlanLogRepository planLogRepository;
    @Autowired
    private AdLogRepository adLogRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskService taskService;

    @Scheduled(cron = "59 * * * * ?")
    private void calcUserConsume() {
        Map<Integer, List<ExtAd>> userAdsMap = new HashMap<>();
        int today = TimeUtil.day();
        System.out.println("开始执行");
        List<Ad> ads = adRepository.selectAdsByStartStatus();
        System.out.println(ads.size() + "条任务");
        for (Ad ad : ads) {
            ExtAd extAd = new ExtAd(planRepository, ad);
            needStopTask(extAd);

            List<ExtAd> extAds = userAdsMap.get(ad.getId());
            if (extAds == null) {
                extAds = new ArrayList<>();
                userAdsMap.put(ad.getUserId(), extAds);
            }
            extAds.add(extAd);
        }
        System.out.println(userAdsMap.size() + "个用户");
        for (Map.Entry<Integer, List<ExtAd>> entry : userAdsMap.entrySet()) {
            Integer userId = entry.getKey();
            ExtUser extUser = adminService.selectUserById(userId);
            if (extUser.getAmount() <= 0) {
                continue;
            }
            int userAmount = (int) (extUser.getAmount() * 100);

            List<ExtAd> extAds = entry.getValue();
            int userAdsConsume = 0;
            boolean noMoney = false;
            for (ExtAd extAd : extAds) {
                if (noMoney) {
                    break;
                }
                LLBExecResult execResult = taskService.selectTaskExec(extAd);
                if (execResult == null) {
                    continue;
                }
                if (execResult.getResult() == null || execResult.getResult().isEmpty()) {
                    continue;
                }
                ExecResult todayExecResult = execResult.getResult().get(0);
                if (todayExecResult == null) {
                    continue;
                }

                AdLogPrimaryKey adLogPK = new AdLogPrimaryKey(extAd.getId(), today);
                Optional<AdLog> optionalAdLog = adLogRepository.findById(adLogPK);
                AdLog adLog = optionalAdLog.orElseGet(AdLog::new);
                int adTotalExec = todayExecResult.getToday();
                int exec = adTotalExec - adLog.getExec();

                ExtPlan plan = extAd.getPlan();
                int realConsumeAmount = (int) (exec * plan.getUnitPrice() * 100);

                PlanLogPrimaryKey planLogPK = new PlanLogPrimaryKey(plan.getId(), today);
                Optional<PlanLog> optionalPlanLog = planLogRepository.findById(planLogPK);
                int planTotalPrice = (int) (plan.getTotalPrice() * 100);
                PlanLog planLog = optionalPlanLog.orElseGet(PlanLog::new);
                if (planLog.isComplete()) {
                    taskService.stopTask(extAd);
                    adRepository.updateStatus(extAd.getId(), AdEnum.Status.ENABLE.value);
                    continue;
                }
                int prevPlanAmount = planLog.getAmount();
                int planAmount = prevPlanAmount + realConsumeAmount;
                if (planAmount >= planTotalPrice) {
                    realConsumeAmount = planAmount - planTotalPrice;
                    planAmount = planTotalPrice;
                    planLog.setComplete(true);
                }
                planLog.setPlanLogPk(planLogPK);
                planLog.setAmount(planAmount);
                planLogRepository.save(planLog);

                if (userAmount < realConsumeAmount) {
                    realConsumeAmount = userAmount;
                    noMoney = true;
                }

                int adAmount = adLog.getAmount() + realConsumeAmount;
                Random rand = new Random();
                int randPv = rand.nextInt(6) + 1;
                int cpc = adLog.getCpc() + exec * randPv;
                adLog.setAdLogPK(adLogPK);
                adLog.setUserId(userId);
                adLog.setExec(todayExecResult.getToday());
                adLog.setCpc(cpc);
                adLog.setAmount(adAmount);
                adLogRepository.save(adLog);

                userAdsConsume += realConsumeAmount;
            }
            System.out.println("用户" + userId + "花费" + userAdsConsume / 100 + "元");
            userRepository.consume(userId, userAdsConsume);
            UserConsumeLogPrimaryKey userConsumeLogPK = new UserConsumeLogPrimaryKey(userId, today);
            Optional<UserConsumeLog> optionalConsumeLog = userConsumeLogRepository.findById(userConsumeLogPK);
            UserConsumeLog consumeLog = optionalConsumeLog.orElseGet(UserConsumeLog::new);
            int userConsume = consumeLog.getAmount() + userAdsConsume;
            consumeLog.setUserConsumeLogPK(userConsumeLogPK);
            consumeLog.setAmount(userConsume);
            userConsumeLogRepository.save(consumeLog);
        }
    }

    private void needStopTask(ExtAd extAd) {
        LLBResult result = taskService.viewTask(extAd);
        if (result != null && result.getTask() != null) {
            if (result.getTask().getStatus() == TaskEnum.Status.TASK_STOP.value) {
                extAd.setStatus(AdEnum.Status.ENABLE.value);
                adRepository.updateStatus(extAd.getId(), extAd.getStatus());
            }
        }
    }
}
