package com.dsp.ad.service.impl;

import com.dsp.ad.entity.Ad;
import com.dsp.ad.entity.Admin;
import com.dsp.ad.entity.Plan;
import com.dsp.ad.entity.User;
import com.dsp.ad.entity.ext.ExtAd;
import com.dsp.ad.entity.ext.ExtPlan;
import com.dsp.ad.entity.ext.ExtUser;
import com.dsp.ad.enums.AdEnum;
import com.dsp.ad.enums.PlanEnum;
import com.dsp.ad.enums.UserEnum;
import com.dsp.ad.repository.AdRepository;
import com.dsp.ad.repository.AdminRepository;
import com.dsp.ad.repository.PlanRepository;
import com.dsp.ad.repository.UserRepository;
import com.dsp.ad.service.AdminService;
import com.dsp.ad.service.TaskService;
import com.dsp.ad.util.MD5Util;
import com.dsp.ad.util.TimeUtil;
import com.dsp.ad.util.result.LLBResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private AdRepository adRepository;

    @Autowired
    private TaskService taskService;

    @Override
    public Admin selectAdminByName(String username) {
        return adminRepository.selectAdminByName(username);
    }

    @Override
    public List<ExtUser> selectAllUser() {
        List<User> users = userRepository.findAll();
        return users.stream().map(ExtUser::new).collect(Collectors.toList());
    }

    @Override
    public ExtUser selectUserById(int userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.map(ExtUser::new).orElse(null);
    }

    @Override
    public void createUser(ExtUser userInfo) {
        User user = new User();
        String encryptPwd = MD5Util.md5(userInfo.getPassword());
        int amount = (int) (userInfo.getAmount() * 100);
        user.setPassword(encryptPwd);
        user.setAmount(amount);
        user.setStatus(UserEnum.Status.ENABLE.value);
        user.setCreateTime(TimeUtil.now());
        user.setNote(userInfo.getNote());
        user.setMobile(userInfo.getMobile());
        user.setEmail(userInfo.getEmail());
        user.setQq(userInfo.getQq());
        userRepository.save(user);
    }

    @Override
    public void editUser(ExtUser userInfo) {
        Optional<User> userOptional = userRepository.findById(userInfo.getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String encryptPwd = MD5Util.md5(userInfo.getPassword());
            user.setPassword(encryptPwd);
            int amount = (int) (userInfo.getAmount() * 100);
            user.setAmount(amount);
            user.setNote(userInfo.getNote());
            user.setMobile(userInfo.getMobile());
            user.setEmail(userInfo.getEmail());
            user.setQq(userInfo.getQq());
            user.setUpdateTime(TimeUtil.now());
            userRepository.save(user);
        }
    }

    @Override
    public void disableUser(int userId) {
        ExtUser user = selectUserById(userId);
        if (user != null) {
            userRepository.updateStatus(userId, UserEnum.Status.DISABLE.value);
        }
    }

    @Override
    public void enableUser(int userId) {
        ExtUser user = selectUserById(userId);
        if (user != null) {
            userRepository.updateStatus(userId, UserEnum.Status.ENABLE.value);
        }
    }

    @Override
    public List<ExtPlan> selectAllPlans() {
        List<Plan> plans = planRepository.selectAllPlans();
        return plans.stream().map(ExtPlan::new).collect(Collectors.toList());
    }

    @Override
    public List<ExtPlan> selectAllAuditPlans() {
        List<Plan> plans = planRepository.selectAllAuditPlans();
        return plans.stream().map(ExtPlan::new).collect(Collectors.toList());
    }

    @Override
    public Plan selectPlanById(int planId) {
        Optional<Plan> plan = planRepository.findById(planId);
        return plan.orElse(null);
    }

    @Override
    public void enablePlan(int planId) {
        Plan plan = selectPlanById(planId);
        if (plan != null) {
            plan.setStatus(PlanEnum.Status.ENABLE.value);
            planRepository.save(plan);
        }
    }

    @Override
    public void disablePlan(int planId) {
        Plan plan = selectPlanById(planId);
        if (plan != null) {
            plan.setStatus(PlanEnum.Status.DISABLE.value);
            planRepository.save(plan);
        }
    }

    @Override
    public void deletePlan(int planId) {
        Plan plan = selectPlanById(planId);
        if (plan != null) {
            plan.setStatus(PlanEnum.Status.DELETE.value);
            planRepository.save(plan);
        }
    }

    @Override
    public List<ExtAd> selectAllAds() {
        List<Ad> ads = adRepository.selectAllAds();
        return ads.stream().map(ad -> new ExtAd(planRepository, ad)).collect(Collectors.toList());
    }

    @Override
    public List<ExtAd> selectAllAuditAds() {
        List<Ad> ads = adRepository.selectAllAuditAds();
        return ads.stream().map(ad -> new ExtAd(planRepository, ad)).collect(Collectors.toList());
    }

    @Override
    public ExtAd selectAdById(int adId) {
        Optional<Ad> adOptional = adRepository.findById(adId);
        return adOptional.map(advertisement -> new ExtAd(planRepository, advertisement)).orElse(null);
    }

    @Override
    public LLBResult enableAd(ExtAd extAd) {
        LLBResult result = null;
        switch (AdEnum.Status.valueOf(extAd.getStatus())) {
            case CREATE_CHECK:
                result = taskService.createTask(extAd);
                if (result.isSuccess()) {
                    adRepository.updateStatus(extAd.getId(), AdEnum.Status.ENABLE.value);
                }
                break;
            case EDIT_CHECK:
                result = taskService.modifyTask(extAd);
                if (result.isSuccess()) {
                    adRepository.updateStatus(extAd.getId(), AdEnum.Status.ENABLE.value);
                }
                break;
            case DISABLE:
                adRepository.updateStatus(extAd.getId(), AdEnum.Status.ENABLE.value);
                result = new LLBResult();
                result.getStatus().setDetail("操作成功");
                break;
        }
        return result;
    }

    @Override
    public void disableAd(ExtAd extAd) {
        adRepository.updateStatus(extAd.getId(), AdEnum.Status.DISABLE.value);
    }

    @Override
    public void deleteAd(ExtAd extAd) {
        adRepository.updateStatus(extAd.getId(), AdEnum.Status.DELETE.value);
    }

    @Override
    public LLBResult startAd(ExtAd extAd) {
        LLBResult result = taskService.startTask(extAd);
        if (result.isSuccess()) {
            adRepository.updateStatus(extAd.getId(), AdEnum.Status.RUNNING.value);
        }
        return result;
    }

    @Override
    public LLBResult stopAd(ExtAd extAd) {
        LLBResult result = taskService.stopTask(extAd);
        if (result.isSuccess()) {
            adRepository.updateStatus(extAd.getId(), AdEnum.Status.ENABLE.value);
        }
        return result;
    }

}
