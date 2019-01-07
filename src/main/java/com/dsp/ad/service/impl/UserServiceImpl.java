package com.dsp.ad.service.impl;

import com.dsp.ad.config.C;
import com.dsp.ad.entity.*;
import com.dsp.ad.entity.ext.*;
import com.dsp.ad.enums.AdEnum;
import com.dsp.ad.enums.PlanEnum;
import com.dsp.ad.enums.UserConsumeLogEnum;
import com.dsp.ad.repository.*;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private AdRepository adRepository;
    @Autowired
    private AdTypeRepository adTypeRepository;
    @Autowired
    private UserConsumeLogRepository userConsumeLogRepository;

    @Override
    public User selectUserByName(String username) {
        return userRepository.selectUserByName(C.SID, username);
    }

    @Override
    public void saveUserInfo(User user) {
        userRepository.save(user);
    }

    @Override
    public int createPlan(ExtUser user, ExtPlan extPlan) {
        Plan plan = new Plan();
        plan.setSid(C.SID);
        plan.setUid(user.getId());
        plan.setName(extPlan.getName());
        plan.setUnitPrice((int) (extPlan.getUnitPrice() * 100));
        plan.setTotalPrice((int) (extPlan.getTotalPrice() * 100));
        plan.setParam(extPlan.getParam().toJson());
        plan.setCreateTime(TimeUtil.now());
        plan.setStatus(PlanEnum.Status.CREATE_CHECK.value);
        planRepository.save(plan);
        return plan.getId();
    }

    @Override
    public List<ExtPlan> selectPlans(int uid) {
        List<Plan> plans = planRepository.selectPlans(uid);
        return plans.stream().map(ExtPlan::new).collect(Collectors.toList());
    }

    @Override
    public Plan findPlanById(int planId, int uid) {
        return planRepository.selectPlan(planId, uid);
    }

    @Override
    public ExtPlan selectPlan(int planId, int uid) {
        Plan plan = planRepository.selectPlan(planId, uid);
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
        ad.setSid(C.SID);
        ad.setUid(user.getId());
        ad.setPid(extAd.getPlanId());
        ad.setName(extAd.getName());
        ad.setType(extAd.getType());
        ad.setUrl(extAd.getUrl());
        ad.setParam(extAd.getParam().toJson());
        ad.setCreateTime(TimeUtil.now());
        ad.setStatus(AdEnum.Status.CREATE_CHECK.value);
        ad.setType(extAd.getType());
        adRepository.save(ad);
    }

    @Autowired
    private TaskService taskService;

    @Override
    public void editAd(ExtUser user, ExtAd extAd) {
        Ad ad = adRepository.selectAd(extAd.getId(), user.getId());
        ad.setName(ad.getName());
        ad.setPid(extAd.getPlanId());
        ad.setName(extAd.getName());
        ad.setType(extAd.getType());
        ad.setUrl(extAd.getUrl());
        ad.setParam(extAd.getParam().toJson());
        ad.setUpdateTime(TimeUtil.now());
        ad.setType(extAd.getType());
        ad.setStatus(AdEnum.Status.EDIT_CHECK.value);
        adRepository.save(ad);
        taskService.stopTask(new ExtAd(ad));
    }

    @Override
    public List<ExtAd> selectAds(int uid) {
        List<Ad> ads = adRepository.selectAds(uid);
        return ads.stream().map(this::newExtAd).collect(Collectors.toList());
    }

    @Override
    public ExtAd selectAd(int adId, int uid) {
        Ad ad = adRepository.selectAd(adId, uid);
        return newExtAd(ad);
    }

    private ExtAd newExtAd(Ad ad) {
        ExtAd extAd = new ExtAd(ad);
        Optional<AdTypeEntity> optional = adTypeRepository.findById(extAd.getType());
        optional.ifPresent(adTypeEntity -> extAd.setTypeName(adTypeEntity.getName()));
        ExtPlan extPlan = selectPlan(ad.getPid(), ad.getUid());
        extAd.setPlan(extPlan);
        return extAd;
    }

    @Override
    public double selectUserTodayConsumeAmount(int uid) {
        int startTime = TimeUtil.day();
        int endTime = TimeUtil.day(1);
        Integer amount = userConsumeLogRepository.selectUserConsumeLogByDay(startTime, endTime, uid, UserConsumeLogEnum.Type.TASK_COST.value);
        if (amount == null) {
            return 0;
        }
        return amount / 100d;
    }

    @Override
    public double selectUserYesterdayConsumeAmount(int uid) {
        int startTime = TimeUtil.month();
        int endTime = TimeUtil.month(1);
        Integer amount = userConsumeLogRepository.selectUserConsumeLogByDay(startTime, endTime, uid, UserConsumeLogEnum.Type.TASK_COST.value);
        if (amount == null) {
            return 0;
        }
        return amount / 100d;
    }

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private AdLogRepository adLogRepository;

    @Override
    public String selectUserMonthConsumeLogJson(int uid) throws JsonProcessingException {
        int month = TimeUtil.month();
        int nextMonth = TimeUtil.month(1);
        List<ExtAdLog> extAdLogs = adLogRepository.selectUserAdsLogByMonth(month, nextMonth, uid);
        return OBJECT_MAPPER.writeValueAsString(extAdLogs);
    }

    @Override
    public double selectUserMonthConsumeAmount(int uid) {
        int startTime = TimeUtil.day(-1);
        int endTime = TimeUtil.day();
        Integer amount = userConsumeLogRepository.selectUserConsumeLogByDay(startTime, endTime, uid, UserConsumeLogEnum.Type.TASK_COST.value);
        if (amount == null) {
            return 0;
        }
        return amount / 100d;
    }

    @Override
    public List<ExtAdLog> selectAdConsumeLogs(int uid) {
        long totalExec = 0, totalCpc = 0, totalAmount = 0;

        List<ExtAdLog> extAdLogs = adLogRepository.selectUserAdsLogs(uid);

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

    @Override
    public List<ExtConsumeLog> selectUserConsumeLogs(int uid) {
        List<UserConsumeLogEntity> logs = userConsumeLogRepository.findByUid(uid);
        List<ExtConsumeLog> extLogs = new ArrayList<>();
        for (UserConsumeLogEntity log : logs) {
            ExtConsumeLog extLog = new ExtConsumeLog();
            LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(log.getTime()), ZoneId.systemDefault());
            extLog.setDate(TimeUtil.toDate(localDateTime, "YYYY-MM-DD HH:mm:SS"));
            extLog.setTypeName(UserConsumeLogEnum.Type.valueOf(log.getType()).text);
            extLog.setAmount(log.getAmount() / 100d);
            extLog.setNote(log.getNote());
            extLogs.add(extLog);
        }
        return extLogs;
    }
}
