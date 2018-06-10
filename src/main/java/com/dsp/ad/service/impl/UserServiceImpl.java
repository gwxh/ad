package com.dsp.ad.service.impl;

import com.dsp.ad.entity.Plan;
import com.dsp.ad.entity.User;
import com.dsp.ad.entity.ext.ExtPlan;
import com.dsp.ad.repository.PlanRepository;
import com.dsp.ad.repository.UserRepository;
import com.dsp.ad.service.UserService;
import com.dsp.ad.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void createPlan(ExtPlan extPlan) {
        Plan plan = new Plan();
        plan.setName(extPlan.getName());
        plan.setUnitPrice((int) (extPlan.getUnitPrice() * 100));
        plan.setTotalPrice((int) (extPlan.getTotalPrice() * 100));
        plan.setParam(extPlan.getParam().toJson());
        plan.setCreateTime(TimeUtil.now());
        plan.setStatus(0);
        planRepository.save(plan);
    }
}
