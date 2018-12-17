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
        int today = TimeUtil.day();
        List<Ad> ads = adRepository.selectAdsByStartStatus();
        if (!ads.isEmpty()) {
            log.info("获取到{}条任务", ads.size());
        }
        Map<Integer, List<ExtAd>> userAdsMap = initUserAdsMap(ads);

        for (Map.Entry<Integer, List<ExtAd>> entry : userAdsMap.entrySet()) {
            Integer userId = entry.getKey();
            ExtUser extUser = adminService.selectUserById(userId);
            if (extUser.getAmount() <= 0) {
                log.info("用户<{}>没钱了!", userId);
                continue;
            }
            int userAmount = (int) (extUser.getAmount() * 100);
            log.info("用户<{}>余额：{}元", userId, extUser.getAmount());

            List<ExtAd> extAds = entry.getValue();
            int userAdsConsume = 0;
            boolean noMoney = false;
            for (ExtAd extAd : extAds) {
                if (noMoney) {
                    log.info("用户<{}>没钱了!", userId);
                    break;
                }
                LLBExecResult execResult = taskService.selectTaskExec(extAd);
                if (execResult == null) {
                    log.info("任务数据查不到");
                    continue;
                }
                if (execResult.getResult() == null || execResult.getResult().isEmpty()) {
                    log.info("任务数据查不到");
                    continue;
                }
                ExecResult todayExecResult = execResult.getResult().get(0);
                if (todayExecResult == null) {
                    log.info("任务数据查不到");
                    continue;
                }

                ExtPlan plan = extAd.getPlan();
                PlanLogPrimaryKey planLogPK = new PlanLogPrimaryKey(today, plan.getId());
                Optional<PlanLog> optionalPlanLog = planLogRepository.findById(planLogPK);
                PlanLog planLog = optionalPlanLog.orElseGet(PlanLog::new);
                int adId = extAd.getId();
                if (planLog.isComplete()) {
                    log.info("计划<{}>完成,停止任务执行", plan.getId());
                    taskService.stopTask(extAd);
                    adRepository.updateStatus(adId, AdEnum.Status.ENABLE.value);
                    continue;
                }

                AdLogPrimaryKey adLogPK = new AdLogPrimaryKey(today, adId);
                Optional<AdLog> optionalAdLog = adLogRepository.findById(adLogPK);
                AdLog adLog = optionalAdLog.orElseGet(AdLog::new);
                log.info("广告<{}>执行总量：{}，上一次执行量：{}", adId, todayExecResult.getToday(), adLog.getCpc());
                int adTotalExec = todayExecResult.getToday();
                int realExec = adTotalExec - adLog.getCpc();
                if (realExec <= 0) {
                    continue;
                }

                log.info("广告<{}>当前单价为：{}元", plan.getId(), plan.getUnitPrice() / 100d);
                int realConsumeAmount = (int) (realExec * plan.getUnitPrice() * 100);

                int planAmount = planLog.getAmount() + realConsumeAmount;
                int planTotalPrice = (int) (plan.getTotalPrice() * 100);
                if (planAmount >= planTotalPrice) {
                    realConsumeAmount = planTotalPrice - planLog.getAmount();
                    if (realConsumeAmount <= 0) {
                        continue;
                    }
                    planAmount = planTotalPrice;
                    planLog.setComplete(true);
                }
                log.info("广告<{}>本次花费：{}元", adId, realConsumeAmount / 100d);
                planLog.setPlanLogPk(planLogPK);
                planLog.setAmount(planAmount);
                planLogRepository.save(planLog);

                if (realConsumeAmount > userAmount) {
                    realConsumeAmount = userAmount;
                    noMoney = true;
                    log.info("用户<{}>余额已用完", userId);
                }

                int adAmount = adLog.getAmount() + realConsumeAmount;
                Random rand = new Random();
                int randPv = rand.nextInt(6) + 1;
                int exec = adLog.getCpc() + realExec * randPv;
                adLog.setAdLogPK(adLogPK);
                adLog.setUid(userId);
                adLog.setExec(exec);
                adLog.setCpc(todayExecResult.getToday());
                adLog.setAmount(adAmount);
                adLogRepository.save(adLog);

                userAdsConsume += realConsumeAmount;
            }
            if (userAdsConsume > userAmount) {
                log.info("用户<{}>实际消费了{}了元", userId, userAdsConsume / 100d);
                userAdsConsume = userAmount;
                log.info("由于余额不足，扣除用户<{}>所有余额:<{}>元", userId, userAdsConsume / 100d);
            }
            if (userAdsConsume > 0) {
                log.info("用户<{}>消费了{}元", userId, userAdsConsume / 100d);
            }
            userRepository.consume(userId, userAdsConsume);
            UserConsumeLogPrimaryKey userConsumeLogPK = new UserConsumeLogPrimaryKey(today, userId);
            Optional<UserConsumeLog> optionalConsumeLog = userConsumeLogRepository.findById(userConsumeLogPK);
            UserConsumeLog consumeLog = optionalConsumeLog.orElseGet(UserConsumeLog::new);
            int userConsume = consumeLog.getAmount() + userAdsConsume;
            consumeLog.setUserConsumeLogPK(userConsumeLogPK);
            consumeLog.setAmount(userConsume);
            userConsumeLogRepository.save(consumeLog);
        }
    }

    private Map<Integer, List<ExtAd>> initUserAdsMap(List<Ad> ads) {
        Map<Integer, List<ExtAd>> userAdsMap = new HashMap<>();
        for (Ad ad : ads) {
            ExtAd extAd = new ExtAd(ad);
            needStopTask(extAd);
            ExtPlan extPlan = adminService.selectPlanById(ad.getPlanId());
            extAd.setPlan(extPlan);
            List<ExtAd> extAds = userAdsMap.get(ad.getUserId());
            if (extAds == null) {
                extAds = new ArrayList<>();
            }
            userAdsMap.put(ad.getUserId(), extAds);
            extAds.add(extAd);
        }
        if (!userAdsMap.isEmpty()) {
            log.info("共{}个用户", userAdsMap.size());
        }
        return userAdsMap;
    }

    private void needStopTask(ExtAd extAd) {
        LLBResult result = taskService.viewTask(extAd);
        if (result != null && result.getResult() != null) {
            if (result.getResult().getStatus() == TaskEnum.Status.TASK_STOP.value) {
                extAd.setStatus(AdEnum.Status.ENABLE.value);
                adRepository.updateStatus(extAd.getId(), extAd.getStatus());
            }
        }
    }
}
