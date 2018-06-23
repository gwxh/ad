package com.dsp.ad.service.impl;

import com.dsp.ad.entity.Admin;
import com.dsp.ad.entity.Advertisement;
import com.dsp.ad.entity.Plan;
import com.dsp.ad.entity.User;
import com.dsp.ad.entity.ext.ExtAd;
import com.dsp.ad.entity.ext.ExtPlan;
import com.dsp.ad.enums.AdEnum;
import com.dsp.ad.enums.PlanEnum;
import com.dsp.ad.enums.UserEnum;
import com.dsp.ad.repository.AdRepository;
import com.dsp.ad.repository.AdminRepository;
import com.dsp.ad.repository.PlanRepository;
import com.dsp.ad.repository.UserRepository;
import com.dsp.ad.service.AdminService;
import com.dsp.ad.util.MD5Util;
import com.dsp.ad.util.TimeUtil;
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

    @Override
    public Admin selectAdminByName(String username) {
        return adminRepository.selectAdminByName(username);
    }

    @Override
    public List<User> selectAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User selectUserById(int userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.orElse(null);
    }

    @Override
    public void createUser(User user) {
        String encryptPwd = MD5Util.md5(user.getPassword());
        int money = user.getMoney() * 100;
        user.setPassword(encryptPwd);
        user.setMoney(money);
        user.setStatus(UserEnum.Status.ENABLE.value);
        user.setCreateTime(TimeUtil.now());
        userRepository.save(user);
    }

    @Override
    public void editUser(User userInfo) {
        User user = selectUserById(userInfo.getId());
        if (user != null) {
            String encryptPwd = MD5Util.md5(user.getPassword());
            user.setPassword(encryptPwd);
            user.setMoney(userInfo.getMoney() * 100);
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
        User user = selectUserById(userId);
        if (user != null) {
            user.setStatus(UserEnum.Status.DISABLE.value);
            userRepository.save(user);
        }
    }

    @Override
    public void enableUser(int userId) {
        User user = selectUserById(userId);
        if (user != null) {
            user.setStatus(UserEnum.Status.ENABLE.value);
            userRepository.save(user);
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

    private Plan selectPlanById(int planId) {
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
        List<Advertisement> ads = adRepository.selectAllAds();
        return ads.stream().map((Advertisement ad) -> {
            ExtAd extAd = new ExtAd(ad);
            extAd.setPlanName(planRepository.selectPlan(extAd.getPlanId(), extAd.getUserId()).getName());
            return extAd;
        }).collect(Collectors.toList());
    }

    @Override
    public List<ExtAd> selectAllAuditAds() {
        List<Advertisement> ads = adRepository.selectAllAuditAds();
        return ads.stream().map((Advertisement ad) -> {
            ExtAd extAd = new ExtAd(ad);
            extAd.setPlanName(planRepository.selectPlan(extAd.getPlanId(), extAd.getUserId()).getName());
            return extAd;
        }).collect(Collectors.toList());
    }

    private Advertisement selectAd(int adId) {
        Optional<Advertisement> ad = adRepository.findById(adId);
        return ad.orElse(null);
    }

    @Override
    public void enableAd(int adId) {
        Advertisement ad = selectAd(adId);
        if (ad != null) {
            ad.setStatus(AdEnum.Status.ENABLE.value);
            adRepository.save(ad);
        }
    }

    @Override
    public void disableAd(int adId) {
        Advertisement ad = selectAd(adId);
        if (ad != null) {
            ad.setStatus(AdEnum.Status.DISABLE.value);
            adRepository.save(ad);
        }
    }

    @Override
    public void deleteAd(int adId) {
        Advertisement ad = selectAd(adId);
        if (ad != null) {
            ad.setStatus(AdEnum.Status.DELETE.value);
            adRepository.save(ad);
        }
    }

    @Override
    public void startAd(int adId) {
        Advertisement ad = selectAd(adId);
        if (ad != null) {
            ad.setStatus(AdEnum.Status.RUNNING.value);
            adRepository.save(ad);
        }
    }

    @Override
    public void stopAd(int adId) {
        enableAd(adId);
    }

}
