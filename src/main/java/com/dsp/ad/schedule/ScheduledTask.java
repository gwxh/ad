package com.dsp.ad.schedule;

import com.dsp.ad.entity.Ad;
import com.dsp.ad.entity.UserConsumeLog;
import com.dsp.ad.entity.UserConsumeLogPrimaryKey;
import com.dsp.ad.entity.ext.ExtAd;
import com.dsp.ad.entity.ext.ExtPlan;
import com.dsp.ad.entity.ext.ExtUser;
import com.dsp.ad.enums.AdEnum;
import com.dsp.ad.enums.TaskEnum;
import com.dsp.ad.repository.*;
import com.dsp.ad.service.AdminService;
import com.dsp.ad.service.TaskService;
import com.dsp.ad.util.TimeUtil;
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
        log.info("统计任务开始执行");
        List<Ad> ads = adRepository.selectAdsByStartStatus();
        log.info("获取到{}条任务", ads.size());
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
        log.info("共{}个用户", userAdsMap.size());
        for (Map.Entry<Integer, List<ExtAd>> entry : userAdsMap.entrySet()) {
            Integer userId = entry.getKey();
            ExtUser extUser = adminService.selectUserById(userId);
            if (extUser.getAmount() <= 0) {
                log.info("用户<{}>没钱了!", userId);
                continue;
            }
            int userAmount = (int) (extUser.getAmount() * 100);

            List<ExtAd> extAds = entry.getValue();
            int userAdsConsume = 0;
            boolean noMoney = false;
            for (ExtAd extAd : extAds)
            log.info("用户<{}>消费了{}元", userId, userAdsConsume / 100d);
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
