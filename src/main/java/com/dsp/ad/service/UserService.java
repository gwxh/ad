package com.dsp.ad.service;

import com.dsp.ad.entity.User;
import com.dsp.ad.entity.ext.ExtAd;
import com.dsp.ad.entity.ext.ExtPlan;

import java.util.List;

public interface UserService {

    User selectUserByName(String username);

    void saveUserInfo(User user);

    void createPlan(User user, ExtPlan extPlan);

    List<ExtPlan> selectPlans(User user);

    ExtPlan selectPlan(int planId, int userId);

    void editPlan(User user, ExtPlan extPlan);

    void createAd(User user, ExtAd ad);
}
