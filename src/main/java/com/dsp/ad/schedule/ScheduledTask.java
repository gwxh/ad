package com.dsp.ad.schedule;

import cn.hutool.core.util.RandomUtil;
import com.dsp.ad.config.C;
import com.dsp.ad.entity.*;
import com.dsp.ad.entity.ext.ExtAd;
import com.dsp.ad.entity.ext.ExtPlan;
import com.dsp.ad.entity.ext.ExtUser;
import com.dsp.ad.enums.UserConsumeLogEnum;
import com.dsp.ad.repository.*;
import com.dsp.ad.service.AdminService;
import com.dsp.ad.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Component
public class ScheduledTask {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTask.class);

    @Autowired
    private AdminService adminService;

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


    @Scheduled(cron = "0 0 0 * * ?")
    public void calcUserConsume() {
        int today = TimeUtil.day();
        List<Ad> ads = adRepository.selectAdsByStartStatus(C.SID);
        if (!ads.isEmpty()) {
            log.info("获取到{}条任务", ads.size());
        }
        Map<Integer, List<ExtAd>> userAdsMap = initUserAdsMap(ads);

        for (Map.Entry<Integer, List<ExtAd>> entry : userAdsMap.entrySet()) {
            Integer uid = entry.getKey();
            ExtUser extUser = adminService.selectUserById(uid);
            if (extUser.getAmount() <= 0) {
                log.info("用户<{}>没钱了!", uid);
                continue;
            }
            int userAmount = (int) (extUser.getAmount() * 100);
            log.info("用户<{}>余额：{}元", uid, extUser.getAmount());

            List<ExtAd> extAds = entry.getValue();
            int userAdsConsume = 0;
            for (ExtAd extAd : extAds) {
                ExtPlan plan = extAd.getPlan();

                int adId = extAd.getId();
                BigDecimal total = BigDecimal.valueOf(plan.getTotalPrice()).divide(BigDecimal.valueOf(plan.getUnitPrice()), 0, RoundingMode.DOWN);
                int totalPlanCount = Integer.parseInt(total.toString());
                int avgPlanCount = totalPlanCount / plan.getDays();
                int maxPlanCount = avgPlanCount * 2;
                int randomPlanCount = RandomUtil.randomInt(0, maxPlanCount);
                Integer exec = planLogRepository.sumExec(adId);
                if (exec == null) {
                    exec = 0;
                }
                int difference = totalPlanCount - exec;
                if (difference < randomPlanCount) {
                    randomPlanCount = difference;
                }
                PlanLogPrimaryKey planLogPK = new PlanLogPrimaryKey(today, plan.getId());
                PlanLog planLog = new PlanLog();
                planLog.setUid(uid);
                planLog.setExec(randomPlanCount);
                BigDecimal rate = RandomUtil.randomBigDecimal(BigDecimal.TEN, new BigDecimal(30)).divide(BigDecimal.valueOf(100), 2, RoundingMode.DOWN);
                planLog.setRate(rate);
                int cpc = BigDecimal.valueOf(randomPlanCount).multiply(rate).intValue();
                planLog.setCpc(cpc);
                int consumeAmount = (int) (randomPlanCount * plan.getUnitPrice() * 100);
                planLog.setPlanLogPk(planLogPK);
                planLog.setAmount(consumeAmount);
                planLogRepository.save(planLog);

                userAdsConsume += consumeAmount;
            }
            if (userAdsConsume > userAmount) {
                log.info("用户<{}>实际消费了{}了元", uid, userAdsConsume / 100d);
                userAdsConsume = userAmount;
                log.info("由于余额不足，扣除用户<{}>所有余额:<{}>元", uid, userAdsConsume / 100d);
            }
            if (userAdsConsume > 0) {
                userRepository.consume(uid, userAdsConsume);
                UserConsumeLogEntity consumeLog = new UserConsumeLogEntity();
                consumeLog.setUid(uid);
                consumeLog.setType(UserConsumeLogEnum.Type.TASK_COST.value);
                consumeLog.setAmount(-userAdsConsume);
                consumeLog.setTime(TimeUtil.now());
                consumeLog.setNote("");
                userConsumeLogRepository.save(consumeLog);
            }
        }
    }

    private Map<Integer, List<ExtAd>> initUserAdsMap(List<Ad> ads) {
        Map<Integer, List<ExtAd>> userAdsMap = new HashMap<>();
        Set<Integer> plans = new HashSet<>();
        for (Ad ad : ads) {
            ExtAd extAd = new ExtAd(ad);
            int pid = ad.getPid();
            if (plans.contains(pid)) {
                continue;
            }
            plans.add(pid);
            ExtPlan extPlan = adminService.selectPlanById(pid);
            extAd.setPlan(extPlan);
            int uid = ad.getUid();
            List<ExtAd> extAds = userAdsMap.get(uid);
            if (extAds == null) {
                extAds = new ArrayList<>();
            }
            userAdsMap.put(uid, extAds);
            extAds.add(extAd);
        }
        if (!userAdsMap.isEmpty()) {
            log.info("共{}个用户", userAdsMap.size());
        }
        return userAdsMap;
    }
}
