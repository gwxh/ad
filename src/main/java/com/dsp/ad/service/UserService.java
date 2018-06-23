package com.dsp.ad.service;

import com.dsp.ad.entity.User;
import com.dsp.ad.entity.ext.ExtAd;
import com.dsp.ad.entity.ext.ExtPlan;
import com.dsp.ad.enums.AdEnum;
import com.dsp.ad.enums.PlanEnum;

import java.util.List;

public interface UserService {

    User selectUserByName(String username);

    void saveUserInfo(User user);

    void createPlan(User user, ExtPlan extPlan);

    void editPlan(User user, ExtPlan extPlan);

    void updatePlanStatus(User user, int planId, PlanEnum.Status status);

    List<ExtPlan> selectPlans(User user);

    ExtPlan selectPlan(int planId, int userId);

    void createAd(User user, ExtAd extAd);

    void editAd(User user, ExtAd extAd);

    void updateAdStatus(User user, int adId, AdEnum.Status status);

    List<ExtAd> selectAds(User user);

    ExtAd selectAd(int adId, int userId);
}
