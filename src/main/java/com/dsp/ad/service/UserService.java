package com.dsp.ad.service;

import com.dsp.ad.entity.User;
import com.dsp.ad.entity.ext.ExtAd;
import com.dsp.ad.entity.ext.ExtPlan;
import com.dsp.ad.entity.ext.ExtUser;

import java.util.List;

public interface UserService {

    User selectUserByName(String username);

    void saveUserInfo(User user);

    void createPlan(ExtUser user, ExtPlan extPlan);

    void editPlan(ExtUser user, ExtPlan extPlan);

    List<ExtPlan> selectPlans(int userId);

    ExtPlan selectPlan(int planId, int userId);

    void createAd(ExtUser user, ExtAd extAd);

    void editAd(ExtUser user, ExtAd extAd);

    List<ExtAd> selectAds(int userId);

    ExtAd selectAd(int adId, int userId);
}
