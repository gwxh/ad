package com.dsp.ad.controller;

import com.dsp.ad.entity.Admin;
import com.dsp.ad.entity.User;
import com.dsp.ad.service.AdminService;
import com.dsp.ad.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
@RequestMapping("/mgr")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    PageController pageController;

    @PostMapping("/login")
    public String login(Model model, @RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) {
        if (StringUtils.isEmpty(username)) {
            model.addAttribute("msg", "用户名不能为空");
            return pageController.toMgrLoginPage();
        }
        if (StringUtils.isEmpty(password)) {
            model.addAttribute("msg", "密码不能为空");
            return pageController.toMgrLoginPage();
        }
        Admin admin = adminService.selectAdminByName(username);
        if (admin == null) {
            model.addAttribute("msg", "管理员不存在");
            return pageController.toMgrLoginPage();
        }
        if (!Objects.equals(MD5Util.md5(password), admin.getPassword())) {
            model.addAttribute("msg", "密码错误");
            return pageController.toMgrLoginPage();
        }

        session.setAttribute("admin", admin);
        return "redirect:/mgr/";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.setAttribute("admin", null);
        return PageController.REDIRECT + pageController.toMgrLoginPage();
    }

    @PostMapping("/createUser")
    public String createUser(Model model, User user) {
        adminService.createUser(user);
        return PageController.REDIRECT + pageController.toMgrIndexPage(model);
    }

    @PostMapping("/editUser")
    public String editUser(Model model, User userInfo) {
        adminService.editUser(userInfo);
        return PageController.REDIRECT + pageController.toMgrIndexPage(model);
    }

    @RequestMapping("/disableUser/{userId}")
    public String disableUser(Model model, @PathVariable int userId) {
        adminService.disableUser(userId);
        return PageController.REDIRECT + pageController.toMgrIndexPage(model);
    }

    @RequestMapping("/enableUser/{userId}")
    public String enableUser(Model model, @PathVariable int userId) {
        adminService.enableUser(userId);
        return PageController.REDIRECT + pageController.toMgrIndexPage(model);
    }

    @RequestMapping("/enablePlan/{planId}")
    public String enablePlan(Model model, @PathVariable int planId) {
        adminService.enablePlan(planId);
        return PageController.REDIRECT + pageController.toMgrAuditPlansPage(model);
    }

    @RequestMapping("/disablePlan/{planId}")
    public String disablePlan(Model model, @PathVariable int planId) {
        adminService.disablePlan(planId);
        return PageController.REDIRECT + pageController.toMgrPlansPage(model);
    }

    @RequestMapping("/deletePlan/{planId}")
    public String deletePlan(Model model, @PathVariable int planId) {
        adminService.deletePlan(planId);
        return PageController.REDIRECT + pageController.toMgrPlansPage(model);
    }

    @RequestMapping("/enableAd/{adId}")
    public String enableAd(Model model, @PathVariable int adId) {
        adminService.enableAd(adId);
        return PageController.REDIRECT + pageController.toMgrAuditAdsPage(model);
    }

    @RequestMapping("/disableAd/{adId}")
    public String disableAd(Model model, @PathVariable int adId) {
        adminService.disableAd(adId);
        return PageController.REDIRECT + pageController.toMgrAdsPage(model);
    }

    @RequestMapping("/deleteAd/{adId}")
    public String deleteAd(Model model, @PathVariable int adId) {
        adminService.deleteAd(adId);
        return PageController.REDIRECT + pageController.toMgrAdsPage(model);
    }

    @RequestMapping("/startAd/{adId}")
    public String startAd(Model model, @PathVariable int adId) {
        adminService.startAd(adId);
        return PageController.REDIRECT + pageController.toMgrAdsPage(model);
    }

    @RequestMapping("/stopAd/{adId}")
    public String stopAd(Model model, @PathVariable int adId) {
        adminService.stopAd(adId);
        return PageController.REDIRECT + pageController.toMgrAdsPage(model);
    }
}
