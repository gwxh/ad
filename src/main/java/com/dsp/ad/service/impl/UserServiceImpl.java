package com.dsp.ad.service.impl;

import com.dsp.ad.entity.Advertisement;
import com.dsp.ad.entity.Plan;
import com.dsp.ad.entity.User;
import com.dsp.ad.entity.ext.ExtAd;
import com.dsp.ad.entity.ext.ExtPlan;
import com.dsp.ad.enums.AdEnum;
import com.dsp.ad.enums.PlanEnum;
import com.dsp.ad.repository.AdRepository;
import com.dsp.ad.repository.PlanRepository;
import com.dsp.ad.repository.UserRepository;
import com.dsp.ad.service.UserService;
import com.dsp.ad.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void createPlan(User user, ExtPlan extPlan) {
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
    public List<ExtPlan> selectPlans(User user) {
        List<Plan> plans = planRepository.selectPlans(user.getId());
        return plans.stream().map(ExtPlan::new).collect(Collectors.toList());
    }

    @Override
    public ExtPlan selectPlan(int planId, int userId) {
        Plan plan = planRepository.selectPlan(planId, userId);
        return new ExtPlan(plan);
    }

    @Override
    public void editPlan(User user, ExtPlan extPlan) {
        Plan plan = planRepository.selectPlan(extPlan.getId(), user.getId());
        plan.setName(extPlan.getName());
        plan.setUnitPrice((int) (extPlan.getUnitPrice() * 100));
        plan.setTotalPrice((int) (extPlan.getTotalPrice() * 100));
        plan.setParam(extPlan.getParam().toJson());
        plan.setUpdateTime(TimeUtil.now());
        plan.setStatus(PlanEnum.Status.EDIT_CHECK.value);
        planRepository.save(plan);
    }

    @Override
    public void updatePlanStatus(User user, int planId, PlanEnum.Status status) {
        planRepository.updateStatus(planId, user.getId(), status.value, TimeUtil.now());
    }

    @Override
    public void createAd(User user, ExtAd extAd) {
        Advertisement ad = new Advertisement();
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

    @Override
    public void editAd(User user, ExtAd extAd) {
        Advertisement ad = adRepository.selectAd(extAd.getId(), user.getId());
        ad.setName(ad.getName());
        ad.setPlanId(extAd.getPlanId());
        ad.setName(extAd.getName());
        ad.setType(extAd.getType());
        ad.setUrl(extAd.getUrl());
        ad.setParam(extAd.getParam().toJson());
        ad.setUpdateTime(TimeUtil.now());
        ad.setStatus(AdEnum.Status.EDIT_CHECK.value);
        adRepository.save(ad);
    }

    @Override
    public void updateAdStatus(User user, int adId, AdEnum.Status status) {
        adRepository.updateStatus(adId, status.value);
    }

    @Override
    public List<ExtAd> selectAds(User user) {
        List<Advertisement> ads = adRepository.selectAds(user.getId());
        return ads.stream().map(ad -> new ExtAd(planRepository, ad)).collect(Collectors.toList());
    }

    @Override
    public ExtAd selectAd(int adId, int userId) {
        Advertisement ad = adRepository.selectAd(adId, userId);
        return new ExtAd(planRepository, ad);
    }
}
