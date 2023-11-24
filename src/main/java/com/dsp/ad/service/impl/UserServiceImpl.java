package com.dsp.ad.service.impl;

import cn.hutool.core.stream.CollectorUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.dsp.ad.config.C;
import com.dsp.ad.entity.*;
import com.dsp.ad.entity.dto.UserConsumeLogDTO;
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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
    @Autowired
    private HttpServletResponse response;

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
        plan.setDays(extPlan.getDays());
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
        plan.setDays(extPlan.getDays());
        planRepository.save(plan);
        List<Ad> ads = adRepository.selectAdsByPlan(extPlan.getId());
        for (Ad ad : ads) {
            ExtAd extAd = new ExtAd(ad);
            if (extAd.getStatus() == AdEnum.Status.RUNNING.value) {
                adRepository.updateStatus(extAd.getId(), AdEnum.Status.ENABLE.value);
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
        int startTime = TimeUtil.day(-1);
        int endTime = TimeUtil.day();
        Integer amount = userConsumeLogRepository.selectUserConsumeLogByDay(startTime, endTime, uid, UserConsumeLogEnum.Type.TASK_COST.value);
        if (amount == null) {
            return 0;
        }
        return amount / 100d;
    }

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private PlanLogRepository planLogRepository;

    @Override
    public String selectUserMonthConsumeLogJson(int uid) throws JsonProcessingException {
        int start = TimeUtil.day(-30);
        int end = TimeUtil.day();
        List<ExtAdLog> extAdLogs = planLogRepository.selectUserPlanLogByMonth(start, end, uid);
        Map<Integer, ExtAdLog> logMap = extAdLogs.stream().collect(Collectors.toMap(ExtAdLog::getRecordTime, log -> log, (log1, log2) -> log1));
        while (start < end) {
            ExtAdLog extAdLog = logMap.get(start);
            if (extAdLog == null) {
                ExtAdLog log = new ExtAdLog();
                log.setRecordTime(start);
                log.setCpc(0L);
                log.setExec(0L);
                extAdLogs.add(log);
            }
            start += 24 * 60 * 60;
        }
        extAdLogs.sort(Comparator.comparing(ExtAdLog::getRecordTime));
        return OBJECT_MAPPER.writeValueAsString(extAdLogs);
    }

    @Override
    public double selectUserMonthConsumeAmount(int uid) {
        int startTime = TimeUtil.month();
        int endTime = TimeUtil.month(1);
        Integer amount = userConsumeLogRepository.selectUserConsumeLogByDay(startTime, endTime, uid, UserConsumeLogEnum.Type.TASK_COST.value);
        if (amount == null) {
            return 0;
        }
        return amount / 100d;
    }

    @Override
    public List<ExtAdLog> selectAdConsumeLogs(int uid) {
        long totalExec = 0, totalCpc = 0, totalAmount = 0;

        List<ExtAdLog> extAdLogs = planLogRepository.selectUserPlanLogs(uid);

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
            if (extAdLog.getExec() > 0) {
                extAdLog.setRate(BigDecimal.valueOf(extAdLog.getCpc()).divide(BigDecimal.valueOf(extAdLog.getExec()), 4, RoundingMode.DOWN));
            }
        }
//        ExtAdLog totalLog = new ExtAdLog();
//        totalLog.setDate("汇总");
//        totalLog.setExec(totalExec);
//        totalLog.setCpc(totalCpc);
//        totalLog.setAmount(totalAmount);
//        totalLog.setRate(BigDecimal.valueOf(totalLog.getCpc()).divide(BigDecimal.valueOf(totalLog.getExec()), 2, RoundingMode.DOWN));
//        extAdLogs.add(totalLog);
//        Collections.reverse(extAdLogs);
        return extAdLogs;
    }

    @Override
    public List<ExtConsumeLog> selectUserConsumeLogs(int uid) {
        List<UserConsumeLogEntity> logs = userConsumeLogRepository.findByUid(uid);
        List<ExtConsumeLog> extLogs = new ArrayList<>();
        for (UserConsumeLogEntity log : logs) {
            ExtConsumeLog extLog = new ExtConsumeLog();
            LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(log.getTime()), ZoneId.systemDefault());
            extLog.setDate(TimeUtil.toDate(localDateTime, "YYYY-MM-DD HH:mm:ss"));
            extLog.setTypeName(UserConsumeLogEnum.Type.valueOf(log.getType()).text);
            extLog.setAmount(log.getAmount() / 100d);
            extLog.setNote(log.getNote());
            extLogs.add(extLog);
        }
        return extLogs;
    }

    @Override
    public List<ExtConsumeLog> selectUserRechargeLogs(int uid) {
        List<UserConsumeLogEntity> logs = userConsumeLogRepository.findByUidAndTypeOrderByTimeDesc(uid, UserConsumeLogEnum.Type.RECHARGE.value);
        List<ExtConsumeLog> extLogs = new ArrayList<>();
        for (UserConsumeLogEntity log : logs) {
            ExtConsumeLog extLog = new ExtConsumeLog();
            LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(log.getTime()), ZoneId.systemDefault());
            extLog.setDate(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm")));
            extLog.setTypeName(UserConsumeLogEnum.Type.valueOf(log.getType()).text);
            extLog.setAmount(log.getAmount() / 100d);
            extLog.setNote(log.getNote());
            extLog.setFile(log.getFile());
            extLogs.add(extLog);
        }
        return extLogs;
    }

    @Override
    public void export(int uid) {
        try {
            List<UserConsumeLogDTO> exportList = new ArrayList<>();
            List<ExtAdLog> adLogs = selectAdConsumeLogs(uid);
            for (ExtAdLog adLog : adLogs) {
                UserConsumeLogDTO logDTO = new UserConsumeLogDTO();

                WriteCellData<String> dateCellData = new WriteCellData<>(adLog.getDate());
                dateCellData.setType(CellDataTypeEnum.STRING);
                logDTO.setDate(dateCellData);

                BigDecimal exec = BigDecimal.valueOf(adLog.getExec());
                WriteCellData<BigDecimal> execCellData = new WriteCellData<>(exec);
                execCellData.setType(CellDataTypeEnum.NUMBER);
                logDTO.setExec(execCellData);

                WriteCellData<BigDecimal> cpcCellData = new WriteCellData<>(BigDecimal.valueOf(adLog.getCpc()));
                cpcCellData.setType(CellDataTypeEnum.NUMBER);
                logDTO.setCpc(cpcCellData);

                WriteCellData<BigDecimal> rateCellData = new WriteCellData<>(adLog.getRate());
                rateCellData.setType(CellDataTypeEnum.NUMBER);
                logDTO.setRate(rateCellData);

                WriteCellData<BigDecimal> amountCellData = new WriteCellData<>(BigDecimal.valueOf(adLog.getAmount()).divide(BigDecimal.valueOf(100L), 2, RoundingMode.DOWN));
                amountCellData.setType(CellDataTypeEnum.NUMBER);
                logDTO.setAmount(amountCellData);

                BigDecimal unitPrice = BigDecimal.ZERO;
                if (exec.compareTo(BigDecimal.ZERO) > 0) {
                    unitPrice = BigDecimal.valueOf(adLog.getAmount()).divide(exec, 0, RoundingMode.DOWN).divide(BigDecimal.valueOf(100L), 2, RoundingMode.DOWN);
                }
                WriteCellData<BigDecimal> unitPriceCellData = new WriteCellData<>(unitPrice);
                unitPriceCellData.setType(CellDataTypeEnum.NUMBER);
                logDTO.setUnitPrice(unitPriceCellData);

                exportList.add(logDTO);
            }

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("数据", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            // 这里需要设置不关闭流
            EasyExcel.write(response.getOutputStream(), UserConsumeLogDTO.class).inMemory(true).autoCloseStream(Boolean.FALSE).sheet("数据")
                    .doWrite(exportList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println(TimeUtil.day(-2));
        System.out.println(TimeUtil.day(-3));
        System.out.println(TimeUtil.day(-5));
        System.out.println(TimeUtil.day(-7));
    }
}
