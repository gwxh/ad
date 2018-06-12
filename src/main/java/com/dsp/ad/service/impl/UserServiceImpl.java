package com.dsp.ad.service.impl;

import com.dsp.ad.entity.Plan;
import com.dsp.ad.entity.User;
import com.dsp.ad.entity.ext.ExtPlan;
import com.dsp.ad.entity.subentity.PlanParam;
import com.dsp.ad.enums.PlanEnum;
import com.dsp.ad.repository.PlanRepository;
import com.dsp.ad.repository.UserRepository;
import com.dsp.ad.service.UserService;
import com.dsp.ad.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PlanRepository planRepository;

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
        List<ExtPlan> extPlans = new ArrayList<>();
        for (Plan plan : plans) {
            ExtPlan extPlan = new ExtPlan();
            extPlan.setId(plan.getId());
            extPlan.setName(plan.getName());
            extPlan.setUnitPrice(plan.getUnitPrice() / 100d);
            extPlan.setTotalPrice(plan.getTotalPrice() / 100d);
            extPlan.setStatus(plan.getStatus());
            extPlan.setParam(PlanParam.fromJson(plan.getParam()));
            extPlans.add(extPlan);
        }
        return extPlans;
    }

    @Override
    public ExtPlan selectPlan(int planId, int userId) {
        Plan plan = planRepository.selectPlan(planId, userId);
        ExtPlan extPlan = new ExtPlan();
        extPlan.setId(plan.getId());
        extPlan.setName(plan.getName());
        extPlan.setUnitPrice(plan.getUnitPrice() / 100d);
        extPlan.setTotalPrice(plan.getTotalPrice() / 100d);
        extPlan.setStatus(plan.getStatus());
        extPlan.setParam(PlanParam.fromJson(plan.getParam()));
        return extPlan;
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
}
