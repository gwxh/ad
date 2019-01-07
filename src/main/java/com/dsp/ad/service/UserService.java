package com.dsp.ad.service;

import com.dsp.ad.entity.Plan;
import com.dsp.ad.entity.User;
import com.dsp.ad.entity.ext.*;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface UserService {

    User selectUserByName(String username);

    void saveUserInfo(User user);

    int createPlan(ExtUser user, ExtPlan extPlan);

    void editPlan(ExtUser user, ExtPlan extPlan);

    List<ExtPlan> selectPlans(int uid);

    Plan findPlanById(int planId, int uid);

    ExtPlan selectPlan(int planId, int uid);

    void createAd(ExtUser user, ExtAd extAd);

    void editAd(ExtUser user, ExtAd extAd);

    List<ExtAd> selectAds(int uid);

    ExtAd selectAd(int adId, int uid);

    double selectUserTodayConsumeAmount(int uid);

    double selectUserYesterdayConsumeAmount(int uid);

    String selectUserMonthConsumeLogJson(int uid) throws JsonProcessingException;

    double selectUserMonthConsumeAmount(int uid);

    List<ExtAdLog> selectAdConsumeLogs(int uid);

    List<ExtConsumeLog> selectUserConsumeLogs(int uid);
}
