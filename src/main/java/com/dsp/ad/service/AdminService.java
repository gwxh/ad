package com.dsp.ad.service;

import com.dsp.ad.entity.Admin;
import com.dsp.ad.entity.User;
import com.dsp.ad.entity.ext.ExtAd;
import com.dsp.ad.entity.ext.ExtPlan;

import java.util.List;

public interface AdminService {

    Admin selectAdminByName(String username);

    List<User> selectAllUser();

    User selectUserById(int userId);

    void createUser(User user);

    void editUser(User user);

    void disableUser(int userId);

    void enableUser(int userId);

    List<ExtPlan> selectAllPlans();

    List<ExtPlan> selectAllAuditPlans();

    void enablePlan(int planId);

    void disablePlan(int planId);

    void deletePlan(int planId);

    List<ExtAd> selectAllAds();

    List<ExtAd> selectAllAuditAds();

    void enableAd(int adId);

    void disableAd(int adId);

    void deleteAd(int adId);

    void startAd(int adId);

    void stopAd(int adId);
}
