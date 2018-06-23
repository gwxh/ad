package com.dsp.ad.controller;

import com.dsp.ad.entity.User;
import com.dsp.ad.entity.ext.ExtAd;
import com.dsp.ad.entity.ext.ExtPlan;
import com.dsp.ad.enums.AdEnum;
import com.dsp.ad.service.AdminService;
import com.dsp.ad.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.io.FileNotFoundException;
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

    @RequestMapping("/user/")
    public String toIndexPage() {
        return "index";
    }

    @RequestMapping("/user/login")
    public String toLoginPage() {
        return "login";
    }

    @RequestMapping("/user/plan")
    public String toPlanPage(Model model, @SessionAttribute User user) {
        List<ExtPlan> extPlans = userService.selectPlans(user);
        model.addAttribute("plans", extPlans);
        return "plan";
    }

    @RequestMapping("/user/ad")
    public String toAdPage(Model model, @SessionAttribute User user) {
        List<ExtAd> extAds = userService.selectAds(user);
        model.addAttribute("ads", extAds);
        return "ad";
    }

    @RequestMapping("/user/data")
    public String toDataPage() {
        return "data";
    }

    @RequestMapping("/user/setting")
    public String toSettingPage(Model model, @SessionAttribute User user) {
        model.addAttribute("user", user);
        return "setting";
    }

    @RequestMapping("/user/createPlan")
    public String toCreatePlanPage(Model model) {
        model.addAttribute("plan", new ExtPlan());
        return "create_plan";
    }

    @RequestMapping("/user/editPlan/{planId}")
    public String toEditPlanPage(Model model, @PathVariable int planId, @SessionAttribute User user) {
        ExtPlan extPlan = userService.selectPlan(planId, user.getId());
        if (extPlan == null) {
            return REDIRECT + toPlanPage(model, user);
        }
        model.addAttribute("plan", extPlan);
        return "create_plan";
    }

    @RequestMapping("/user/createAd")
    public String toCreateAd(Model model, @SessionAttribute User user) {
        initAdPage(model, user);
        model.addAttribute("ad", new ExtAd());
        return "create_ad";
    }

    @RequestMapping("/user/editAd/{adId}")
    public String toEditAdPage(Model model, @PathVariable int adId, @SessionAttribute User user) throws FileNotFoundException {
        ExtAd extAd = userService.selectAd(adId, user.getId());
        if (extAd == null) {
            return REDIRECT + toPlanPage(model, user);
        }
        initAdPage(model, user);
        model.addAttribute("ad", extAd);
        return "create_ad";
    }

    private void initAdPage(Model model, User user) {
        List<ExtPlan> extPlans = userService.selectPlans(user);
        model.addAttribute("plans", extPlans);
        Map<Integer, String> types = new HashMap<>();
        for (AdEnum.Type type : AdEnum.Type.values()) {
            types.put(type.value, type.text);
        }
        model.addAttribute("types", types);
    }


    @RequestMapping({"/mgr/", "/mgr/index"})
    public String toMgrIndexPage(Model model) {
        List<User> users = adminService.selectAllUser();
        model.addAttribute("users", users);
        model.addAttribute("user", new User());
        return "/mgr/index";
    }

    @RequestMapping("/mgr/login")
    public String toMgrLoginPage() {
        return "/mgr/login";
    }

    @RequestMapping("/mgr/editUser/{userId}")
    public String toMgrEditUserPage(Model model, @PathVariable int userId) {
        User user = adminService.selectUserById(userId);
        model.addAttribute("user", user);
        return "/mgr/edit_user";
    }

    @RequestMapping("/mgr/plans")
    public String toMgrPlansPage(Model model) {
        List<ExtPlan> extPlans = adminService.selectAllPlans();
        model.addAttribute("plans", extPlans);
        return "/mgr/plans";
    }

    @RequestMapping("/mgr/audit_plans")
    public String toMgrAuditPlansPage(Model model){
        List<ExtPlan> extPlans = adminService.selectAllAuditPlans();
        model.addAttribute("plans", extPlans);
        return "/mgr/audit_plans";
    }

    @RequestMapping("/mgr/ads")
    public String toMgrAdsPage(Model model) {
        List<ExtAd> extAds = adminService.selectAllAds();
        model.addAttribute("ads", extAds);
        return "/mgr/ads";
    }

    @RequestMapping("/mgr/audit_ads")
    public String toMgrAuditAdsPage(Model model){
        List<ExtAd> extAds = adminService.selectAllAuditAds();
        model.addAttribute("ads", extAds);
        return "/mgr/audit_ads";
    }
}
