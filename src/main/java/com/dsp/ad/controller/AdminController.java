package com.dsp.ad.controller;

import com.dsp.ad.entity.Admin;
import com.dsp.ad.entity.ext.ExtAd;
import com.dsp.ad.entity.ext.ExtUser;
import com.dsp.ad.enums.AdEnum;
import com.dsp.ad.enums.PlanEnum;
import com.dsp.ad.service.AdminService;
import com.dsp.ad.util.MD5Util;
import com.dsp.ad.util.result.LLBResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String createUser(Model model, ExtUser user) {
        adminService.createUser(user);
        return PageController.REDIRECT + pageController.toMgrIndexPage(model);
    }

    @PostMapping("/editUser")
    public String editUser(Model model, ExtUser userInfo) {
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
    public String enableAd(RedirectAttributes attributes, @PathVariable int adId) {
        ExtAd ad = adminService.selectAdById(adId);
        if (ad == null) {
            attributes.addFlashAttribute("msg", "广告不存在");
            return PageController.REDIRECT_AUDIT_ADS;
        }
        if (ad.getStatus() == AdEnum.Status.ENABLE.value || ad.getStatus() == AdEnum.Status.RUNNING.value) {
            attributes.addFlashAttribute("msg", "广告已激活");
            return PageController.REDIRECT_AUDIT_ADS;
        }
        if (ad.getPlan().getStatus() != PlanEnum.Status.ENABLE.value) {
            attributes.addFlashAttribute("msg", "广告计划没有被激活");
            return PageController.REDIRECT_AUDIT_ADS;
        }
        LLBResult result = adminService.enableAd(ad);
        if (result == null) {
            attributes.addFlashAttribute("msg", "广告激活失败");
            return PageController.REDIRECT_AUDIT_ADS;
        }
        attributes.addFlashAttribute("msg", result.getStatus().getDetail());
        return PageController.REDIRECT_AUDIT_ADS;
    }

    @RequestMapping("/disableAd/{adId}")
    public String disableAd(RedirectAttributes attributes, @PathVariable int adId) {
        ExtAd ad = adminService.selectAdById(adId);
        if (ad == null) {
            attributes.addFlashAttribute("msg", "广告不存在");
            return PageController.REDIRECT_AUDIT_ADS;
        }
        if (ad.getStatus() == AdEnum.Status.RUNNING.value) {
            attributes.addFlashAttribute("msg", "广告任务正在运行");
            return PageController.REDIRECT_AUDIT_ADS;
        }
        adminService.disableAd(ad);
        return PageController.REDIRECT_MGR_ADS;
    }

    @RequestMapping("/deleteAd/{adId}")
    public String deleteAd(RedirectAttributes attributes, @PathVariable int adId) {
        ExtAd ad = adminService.selectAdById(adId);
        if (ad == null) {
            attributes.addFlashAttribute("msg", "广告不存在");
            return PageController.REDIRECT_AUDIT_ADS;
        }
        if (ad.getStatus() == AdEnum.Status.RUNNING.value) {
            attributes.addFlashAttribute("msg", "广告任务正在运行");
            return PageController.REDIRECT_AUDIT_ADS;
        }
        adminService.deleteAd(ad);
        return PageController.REDIRECT_MGR_ADS;
    }

    @RequestMapping("/startAd/{adId}")
    public String startAd(RedirectAttributes attributes, @PathVariable int adId) {
        ExtAd ad = adminService.selectAdById(adId);
        if (ad == null) {
            attributes.addFlashAttribute("msg", "广告不存在");
            return PageController.REDIRECT_MGR_ADS;
        }
        ExtUser user = adminService.selectUserById(ad.getUserId());
        if (user == null) {
            attributes.addFlashAttribute("msg", "广告商不存在");
            return PageController.REDIRECT_MGR_ADS;
        }
        double price = ad.getPlan().getTotalPrice();
        if (user.getMoney() < price) {
            attributes.addFlashAttribute("msg", "广告商余额不足");
            return PageController.REDIRECT_MGR_ADS;
        }
        LLBResult result = adminService.startAd(ad);
        if (result == null) {
            attributes.addFlashAttribute("msg", "广告开启失败");
            return PageController.REDIRECT_MGR_ADS;
        }
        attributes.addFlashAttribute("msg", result.getStatus().getDetail());
        return PageController.REDIRECT_MGR_ADS;
    }

    @RequestMapping("/stopAd/{adId}")
    public String stopAd(RedirectAttributes attributes, @PathVariable int adId) {
        ExtAd ad = adminService.selectAdById(adId);
        if (ad == null) {
            attributes.addFlashAttribute("msg", "广告不存在");
            return PageController.REDIRECT_AUDIT_ADS;
        }
        LLBResult result = adminService.stopAd(ad);
        if (result == null) {
            attributes.addFlashAttribute("msg", "广告开启失败");
            return PageController.REDIRECT_AUDIT_ADS;
        }
        attributes.addFlashAttribute("msg", result.getStatus().getDetail());
        return PageController.REDIRECT_MGR_ADS;
    }
}
