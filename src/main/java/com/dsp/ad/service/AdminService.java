package com.dsp.ad.service;

import com.dsp.ad.entity.Admin;
import com.dsp.ad.entity.ext.ExtAd;
import com.dsp.ad.entity.ext.ExtPlan;
import com.dsp.ad.entity.ext.ExtUser;
import com.dsp.ad.util.result.LLBResult;

import java.util.List;

public interface AdminService {

    Admin selectAdminByName(String username);

    List<ExtUser> selectAllUser();

    ExtUser selectUserById(int userId);

    void userRecharge(ExtUser user, int amount, String note);

    void createUser(ExtUser user);

    void editUser(ExtUser user);

    void disableUser(int userId);

    void enableUser(int userId);

    void deleteUser(int userId);

    List<ExtPlan> selectAllPlans();

    List<ExtPlan> selectAllAuditPlans();

    ExtPlan selectPlanById(int planId);

    void enablePlan(int planId);

    void disablePlan(int planId);

    void deletePlan(int planId);

    List<ExtAd> selectAllAds();

    List<ExtAd> selectAllAuditAds();

    ExtAd selectAdById(int adId);

    LLBResult enableAd(ExtAd extAd);

    void disableAd(ExtAd extAd);

    void deleteAd(ExtAd extAd);

    LLBResult startAd(ExtAd extAd, int start);

    LLBResult stopAd(ExtAd extAd);
}
