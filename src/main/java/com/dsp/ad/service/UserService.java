package com.dsp.ad.service;

import com.dsp.ad.entity.User;
import com.dsp.ad.entity.ext.ExtAd;
import com.dsp.ad.entity.ext.ExtAdLog;
import com.dsp.ad.entity.ext.ExtPlan;
import com.dsp.ad.entity.ext.ExtUser;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface UserService {

    User selectUserByName(String username);

    void saveUserInfo(User user);

    int createPlan(ExtUser user, ExtPlan extPlan);

    void editPlan(ExtUser user, ExtPlan extPlan);

    List<ExtPlan> selectPlans(int userId);

    ExtPlan selectPlan(int planId, int userId);

    void createAd(ExtUser user, ExtAd extAd);

    void editAd(ExtUser user, ExtAd extAd);

    List<ExtAd> selectAds(int userId);

    ExtAd selectAd(int adId, int userId);

    double selectUserTodayConsumeAmount(int userId);

    double selectUserYesterdayConsumeAmount(int userId);

    String  selectUserMonthConsumeLogJson(int userId) throws JsonProcessingException;

    double selectUserMonthConsumeAmount(int userId);

    List<ExtAdLog> selectUserConsumeLogs(int userId);
}
