package com.dsp.ad.service.impl;

import com.dsp.ad.entity.Ad;
import com.dsp.ad.entity.Plan;
import com.dsp.ad.entity.User;
import com.dsp.ad.entity.ext.ExtAd;
import com.dsp.ad.entity.ext.ExtAdLog;
import com.dsp.ad.entity.ext.ExtPlan;
import com.dsp.ad.entity.ext.ExtUser;
import com.dsp.ad.enums.AdEnum;
import com.dsp.ad.enums.PlanEnum;
import com.dsp.ad.repository.AdLogRepository;
import com.dsp.ad.repository.AdRepository;
import com.dsp.ad.repository.PlanRepository;
import com.dsp.ad.repository.UserRepository;
import com.dsp.ad.service.TaskService;
import com.dsp.ad.service.UserService;
import com.dsp.ad.util.TimeUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private AdRepository adRepository;

    @Override
    public User selectUserByName(String username) {
        return userRepository.selectUserByName(username);
    }

    @Override
    public void saveUserInfo(User user) {
        userRepository.save(user);
    }

    @Override
    public void createPlan(ExtUser user, ExtPlan extPlan) {
        Plan plan = new Plan();
        plan.setUserId(user.getId());
        plan.setName(extPlan.getName());
        plan.setUnitPrice((int) (extPlan.getUnitPrice() * 100));
        plan.setTotalPrice((int) (extPlan.getTotalPrice() * 100));
        plan.setParam(extPlan.getParam().toJson());
        plan.setCreateTime(TimeUtil.now());
        plan.setStatus(PlanEnum.Status.CREATE_CHECK.value);
        planRepository.save(plan);
    }

    @Override
    public List<ExtPlan> selectPlans(int userId) {
        List<Plan> plans = planRepository.selectPlans(userId);
        return plans.stream().map(ExtPlan::new).collect(Collectors.toList());
    }

    @Override
    public ExtPlan selectPlan(int planId, int userId) {
        Plan plan = planRepository.selectPlan(planId, userId);
        return new ExtPlan(plan);
    }

    @Override
    public void editPlan(ExtUser user, ExtPlan extPlan) {
        Plan plan = planRepository.selectPlan(extPlan.getId(), user.getId());
        plan.setName(extPlan.getName());
        plan.setUnitPrice((int) (extPlan.getUnitPrice() * 100));
        plan.setTotalPrice((int) (extPlan.getTotalPrice() * 100));
        plan.setParam(extPlan.getParam().toJson());
        plan.setUpdateTime(TimeUtil.now());
        plan.setStatus(PlanEnum.Status.EDIT_CHECK.value);
        planRepository.save(plan);
        List<Ad> ads = adRepository.selectAdsByPlan(extPlan.getId());
        for (Ad ad : ads) {
            ExtAd extAd = new ExtAd(ad);
            if (extAd.getStatus() == AdEnum.Status.RUNNING.value) {
                adRepository.updateStatus(extAd.getId(), AdEnum.Status.ENABLE.value);
                taskService.stopTask(extAd);
            }
        }
    }

    @Override
    public void createAd(ExtUser user, ExtAd extAd) {
        Ad ad = new Ad();
        ad.setUserId(user.getId());
        ad.setPlanId(extAd.getPlanId());
        ad.setName(extAd.getName());
        ad.setType(extAd.getType());
        ad.setUrl(extAd.getUrl());
        ad.setParam(extAd.getParam().toJson());
        ad.setCreateTime(TimeUtil.now());
        ad.setStatus(AdEnum.Status.CREATE_CHECK.value);
        adRepository.save(ad);
    }

    @Autowired
    private TaskService taskService;

    @Override
    public void editAd(ExtUser user, ExtAd extAd) {
        Ad ad = adRepository.selectAd(extAd.getId(), user.getId());
        ad.setName(ad.getName());
        ad.setPlanId(extAd.getPlanId());
        ad.setName(extAd.getName());
        ad.setType(extAd.getType());
        ad.setUrl(extAd.getUrl());
        ad.setParam(extAd.getParam().toJson());
        ad.setUpdateTime(TimeUtil.now());
        ad.setStatus(AdEnum.Status.EDIT_CHECK.value);
        adRepository.save(ad);
        taskService.stopTask(new ExtAd(ad));
    }

    @Override
    public List<ExtAd> selectAds(int userId) {
        List<Ad> ads = adRepository.selectAds(userId);
        return ads.stream().map(this::newExtAd).collect(Collectors.toList());
    }

    @Override
    public ExtAd selectAd(int adId, int userId) {
        Ad ad = adRepository.selectAd(adId, userId);
        return newExtAd(ad);
    }

    private ExtAd newExtAd(Ad ad) {
        ExtAd extAd = new ExtAd(ad);
        ExtPlan extPlan = selectPlan(ad.getPlanId(), ad.getUserId());
        extAd.setPlan(extPlan);
        return extAd;
    }

    @Autowired
    private AdLogRepository adLogRepository;

    @Override
    public double selectUserTodayConsumeAmount(int userId) {
        int day = TimeUtil.day();
        Integer amount = adLogRepository.selectUserAdsLogByDay(day, userId);
        if (amount == null) {
            return 0;
        }
        return amount / 100d;
    }

    @Override
    public double selectUserYesterdayConsumeAmount(int userId) {
        int yesterday = TimeUtil.day(-1);
        Integer amount = adLogRepository.selectUserAdsLogByDay(yesterday, userId);
        if (amount == null) {
            return 0;
        }
        return amount / 100d;
    }

    private int monthConsumeAmount;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public String selectUserMonthConsumeLogJson(int userId) throws JsonProcessingException {
        monthConsumeAmount = 0;
        int month = TimeUtil.month();
        int nextMonth = TimeUtil.month(1);
        List<ExtAdLog> extAdLogs = adLogRepository.selectUserAdsLogByMonth(month, nextMonth, userId);
        for (ExtAdLog extAdLog : extAdLogs) {
            if (extAdLog.getRecordTime() == null) {
                extAdLogs = new ArrayList<>();
                break;
            }
            monthConsumeAmount += extAdLog.getAmount();
        }
        return OBJECT_MAPPER.writeValueAsString(extAdLogs);
    }

    @Override
    public double selectUserMonthConsumeAmount(int userId) {
        return monthConsumeAmount / 100d;
    }

    @Override
    public List<ExtAdLog> selectUserConsumeLogs(int userId) {
        long totalExec = 0, totalCpc = 0, totalAmount = 0;

        List<ExtAdLog> extAdLogs = adLogRepository.selectUserAdsLogs(userId);

        for (ExtAdLog extAdLog : extAdLogs) {
            if (extAdLog.getRecordTime() == null) {
                extAdLogs = new ArrayList<>();
                break;
            }
            LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(extAdLog.getRecordTime()), ZoneId.systemDefault());
            extAdLog.setDate(TimeUtil.toDate(localDateTime, "yyyy-MM-dd"));
            totalExec += extAdLog.getExec();
            totalCpc += extAdLog.getCpc();
            totalAmount += extAdLog.getAmount();
        }
        ExtAdLog totalLog = new ExtAdLog();
        totalLog.setDate("汇总");
        totalLog.setExec(totalExec);
        totalLog.setCpc(totalCpc);
        totalLog.setAmount(totalAmount);
        extAdLogs.add(totalLog);
        Collections.reverse(extAdLogs);
        return extAdLogs;
    }
}
