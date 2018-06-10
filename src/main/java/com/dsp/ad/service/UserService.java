package com.dsp.ad.service;

import com.dsp.ad.entity.User;
import com.dsp.ad.entity.ext.ExtPlan;

public interface UserService {

    User selectUserByName(String username);

    void saveUserInfo(User user);

    void createPlan(ExtPlan extPlan);
}
