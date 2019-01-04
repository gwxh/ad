package com.dsp.ad.controller;

import com.dsp.ad.config.C;
import com.dsp.ad.entity.AdTypeEntity;
import com.dsp.ad.entity.PlanAttributeEntity;
import com.dsp.ad.entity.User;
import com.dsp.ad.entity.ext.*;
import com.dsp.ad.enums.AdEnum;
import com.dsp.ad.service.AdminService;
import com.dsp.ad.service.SiteService;
import com.dsp.ad.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PageController {

    public static final String REDIRECT = "redirect:";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(PageController.class);

    @Value("${upload-image-path}")
    private String uploadImagePath;

    @Autowired
    private UserService userService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private SiteService siteService;

    @RequestMapping("/")
    public String index(Model model, @SessionAttribute ExtUser user) throws JsonProcessingException {
        int userId = user.getId();
        user = adminService.selectUserById(userId);
        double todayConsumeAmount = userService.selectUserTodayConsumeAmount(userId);
        double yesterdayConsumeAmount = userService.selectUserYesterdayConsumeAmount(userId);
        String logJson = userService.selectUserMonthConsumeLogJson(userId);
        double monthConsumeAmount = userService.selectUserMonthConsumeAmount(userId);
        model.addAttribute("userAmount", user.getAmount());
        model.addAttribute("todayConsumeAmount", todayConsumeAmount);
        model.addAttribute("yesterdayConsumeAmount", yesterdayConsumeAmount);
        model.addAttribute("logJson", logJson);
        model.addAttribute("monthConsumeAmount", monthConsumeAmount);
        return "index";
    }

    @RequestMapping("/user/login")
    public String toLoginPage() {
        return "login";
    }

    @RequestMapping("/user/plan")
    public String toPlanPage(Model model, @SessionAttribute ExtUser user) {
        List<ExtPlan> extPlans = userService.selectPlans(user.getId());
        model.addAttribute("plans", extPlans);
        return "plan";
    }

    public static final String USER_AD = "/user/ad";
    public static final String REDIRECT_USER_AD = REDIRECT + USER_AD;

    @RequestMapping("/user/ad")
    public String toAdPage(Model model, @SessionAttribute ExtUser user, @ModelAttribute("msg") String msg) {
        List<ExtAd> extAds = userService.selectAds(user.getId());
        model.addAttribute("ads", extAds);
        model.addAttribute("msg", msg);
        return "ad";
    }

    @RequestMapping("/user/data")
    public String toDataPage(Model model, @SessionAttribute ExtUser user) {
        List<ExtAdLog> logs = userService.selectAdConsumeLogs(user.getId());
        model.addAttribute("logs", logs);
        return "data";
    }

    @RequestMapping("/user/consumeLog")
    public String toConsumeLogPage(Model model, @SessionAttribute ExtUser user) {
        List<ExtConsumeLog> logs = userService.selectUserConsumeLogs(user.getId());
        model.addAttribute("logs", logs);
        return "consume_log";
    }

    @RequestMapping("/user/setting")
    public String toSettingPage(Model model, @SessionAttribute ExtUser user) {
        model.addAttribute("user", user);
        return "setting";
    }

    @RequestMapping("/user/createPlan")
    public String toCreatePlanPage(Model model) {
        model.addAttribute("plan", new ExtPlan());
        return "create_plan";
    }

    @RequestMapping("/user/editPlan/{planId}")
    public String toEditPlanPage(Model model, @PathVariable int planId, @SessionAttribute ExtUser user) {
        ExtPlan extPlan = userService.selectPlan(planId, user.getId());
        if (extPlan == null) {
            return REDIRECT + toPlanPage(model, user);
        }
        model.addAttribute("plan", extPlan);
        return "create_plan";
    }

    @RequestMapping("/user/createAd/{planId}")
    public String toCreateAd(Model model, @SessionAttribute ExtUser user, @PathVariable int planId) {
        initAdPage(model, user);
        ExtAd ad = new ExtAd();
        if (planId > 0) {
            ad.setPlanId(planId);
        }
        model.addAttribute("ad", ad);
        return "create_ad";
    }

    @RequestMapping("/user/editAd/{adId}")
    public String toEditAdPage(Model model, @PathVariable int adId, @SessionAttribute ExtUser user) {
        ExtAd extAd = userService.selectAd(adId, user.getId());
        if (extAd == null) {
            return REDIRECT + toPlanPage(model, user);
        }
        initAdPage(model, user);
        model.addAttribute("ad", extAd);
        return "create_ad";
    }

    private void initAdPage(Model model, ExtUser user) {
        List<ExtPlan> extPlans = userService.selectPlans(user.getId());
        model.addAttribute("plans", extPlans);
        Map<Integer, String> types = new HashMap<>();
        for (AdEnum.Type type : AdEnum.Type.values()) {
            types.put(type.value, type.text);
        }
        model.addAttribute("types", types);
        final List<AdTypeEntity> adTypeList = siteService.queryAdTypeList();
        model.addAttribute("adTypeList", adTypeList);
    }


    // //////////////////////////// 后台 ////////////////////////////////////


    private final static String MGR_INDEX = "/mgr/index";
    public final static String REDIRECT_MGR_INDEX = REDIRECT + MGR_INDEX;

    @RequestMapping({"/mgr/", "/mgr/index"})
    public String toMgrIndexPage(Model model) {
        List<ExtUser> users = adminService.selectAllUser();
        model.addAttribute("users", users);
        model.addAttribute("user", new User());
        return "mgr/index";
    }

    private final static String MGR_LOGIN = "/mgr/login";
    public final static String REDIRECT_MGR_LOGIN = REDIRECT + MGR_LOGIN;

    @RequestMapping("/mgr/login")
    public String toMgrLoginPage() {
        return "mgr/login";
    }

    @RequestMapping("/mgr/editUser/{userId}")
    public String toMgrEditUserPage(Model model, @PathVariable int userId) {
        ExtUser user = adminService.selectUserById(userId);
        model.addAttribute("user", user);
        return "mgr/edit_user";
    }

    private final static String MGR_PLANS = "/mgr/plans";
    public final static String REDIRECT_MGR_PLANS = REDIRECT + MGR_PLANS;

    @RequestMapping("/mgr/plans")
    public String toMgrPlansPage(Model model) {
        List<ExtPlan> extPlans = adminService.selectAllPlans();
        model.addAttribute("plans", extPlans);
        return "mgr/plans";
    }

    private final static String MGR_AUDIT_PLANS = "/mgr/audit_plans";
    public final static String REDIRECT_MGR_AUDIT_PLANS = REDIRECT + MGR_AUDIT_PLANS;

    @RequestMapping("/mgr/audit_plans")
    public String toMgrAuditPlansPage(Model model) {
        List<ExtPlan> extPlans = adminService.selectAllAuditPlans();
        model.addAttribute("plans", extPlans);
        return "mgr/audit_plans";
    }

    public static final String MGR_ADS = "/mgr/ads";
    public static final String REDIRECT_MGR_ADS = REDIRECT + MGR_ADS;

    @RequestMapping("/mgr/ads")
    public String toMgrAdsPage(Model model, @ModelAttribute("msg") String msg) {
        List<ExtAd> extAds = adminService.selectAllAds();
        model.addAttribute("ads", extAds);
        model.addAttribute("msg", msg);
        return "mgr/ads";
    }

    public static final String MGR_AUDIT_ADS = "/mgr/audit_ads";
    public static final String REDIRECT_AUDIT_ADS = REDIRECT + MGR_AUDIT_ADS;

    @RequestMapping("/mgr/audit_ads")
    public String toMgrAuditAdsPage(Model model, @ModelAttribute("msg") String msg) {
        List<ExtAd> extAds = adminService.selectAllAuditAds();
        model.addAttribute("ads", extAds);
        model.addAttribute("msg", msg);
        return "mgr/audit_ads";
    }

    public static final String MGR_SETTING = "/mgr/setting";
    public static final String REDIRECT_SETTING = REDIRECT + MGR_SETTING;

    @RequestMapping("/mgr/setting")
    public String toMgrSettingPage(Model model) {
        final List<AdTypeEntity> adTypeList = siteService.queryAdTypeList();
        model.addAttribute("adTypeList", adTypeList);
        final List<ExtAdImgSize> adImgSizeList = siteService.queryAdImgSizeList();
        model.addAttribute("adImgSizeList", adImgSizeList);
        final List<PlanAttributeEntity> planAttributeList = siteService.queryPlanAttributes(C.SID, 1);
        model.addAttribute("planAttributeList", planAttributeList);
        return "mgr/setting";
    }
}
