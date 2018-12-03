package com.dsp.ad.controller;

import com.dsp.ad.entity.Admin;
import com.dsp.ad.entity.User;
import com.dsp.ad.entity.ext.ExtAd;
import com.dsp.ad.entity.ext.ExtPlan;
import com.dsp.ad.entity.ext.ExtUser;
import com.dsp.ad.enums.AdEnum;
import com.dsp.ad.enums.PlanEnum;
import com.dsp.ad.service.AdminService;
import com.dsp.ad.service.UserService;
import com.dsp.ad.util.MD5Util;
import com.dsp.ad.util.TimeUtil;
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
    private UserService userService;

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
        return PageController.REDIRECT_MGR_LOGIN;
    }

    @PostMapping("/createUser")
    public String createUser(ExtUser user, RedirectAttributes attributes) {
        User u = userService.selectUserByName(user.getUsername());
        if (u != null) {
            attributes.addFlashAttribute("msg", "广告商名称不允许重复！");
            return PageController.REDIRECT_MGR_INDEX;
        }
        adminService.createUser(user);
        return PageController.REDIRECT_MGR_INDEX;
    }

    @PostMapping("/userRecharge")
    public String userRecharge(int uid, int amount, String note, RedirectAttributes attributes) {
        ExtUser u = adminService.selectUserById(uid);
        if (u == null) {
            attributes.addFlashAttribute("msg", "广告商不存在！");
            return PageController.REDIRECT_MGR_INDEX;
        }
        adminService.userRecharge(u, amount * 100, note);
        return PageController.REDIRECT_MGR_INDEX;
    }

    @PostMapping("/editUser")
    public String editUser(ExtUser userInfo) {
        adminService.editUser(userInfo);
        return PageController.REDIRECT_MGR_INDEX;
    }

    @RequestMapping("/disableUser/{userId}")
    public String disableUser(@PathVariable int userId) {
        adminService.disableUser(userId);
        return PageController.REDIRECT_MGR_INDEX;
    }

    @RequestMapping("/enableUser/{userId}")
    public String enableUser(@PathVariable int userId) {
        adminService.enableUser(userId);
        return PageController.REDIRECT_MGR_INDEX;
    }

    @RequestMapping("/deleteUser/{userId}")
    public String deleteUser(@PathVariable int userId) {
        adminService.deleteUser(userId);
        return PageController.REDIRECT_MGR_INDEX;
    }

    @RequestMapping("/enablePlan/{planId}")
    public String enablePlan(@PathVariable int planId) {
        adminService.enablePlan(planId);
        return PageController.REDIRECT_MGR_AUDIT_PLANS;
    }

    @RequestMapping("/disablePlan/{planId}")
    public String disablePlan(@PathVariable int planId) {
        adminService.disablePlan(planId);
        return PageController.REDIRECT_MGR_PLANS;
    }

    @RequestMapping("/deletePlan/{planId}")
    public String deletePlan(@PathVariable int planId) {
        adminService.deletePlan(planId);
        return PageController.REDIRECT_MGR_PLANS;
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

    @RequestMapping("/startAd")
    public String startAd(RedirectAttributes attributes, int aid, String startTime) {
        int start = TimeUtil.getTimestamp(startTime.split(":"));
        ExtAd ad = adminService.selectAdById(aid);
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
        if (user.getAmount() < price) {
            attributes.addFlashAttribute("msg", "广告商余额不足");
            return PageController.REDIRECT_MGR_ADS;
        }
        LLBResult result = adminService.startAd(ad, start);
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

    @RequestMapping("/toUser/{userId}")
    public String toUser(HttpSession session, @PathVariable int userId) {
        ExtUser user = adminService.selectUserById(userId);
        if (user == null) {
            return PageController.REDIRECT_MGR_INDEX;
        }
        session.setAttribute("user", user);
        return PageController.REDIRECT + "/";
    }

    @RequestMapping("/toPlan/{planId}")
    public String toPlan(HttpSession session, @PathVariable int planId) {
        ExtPlan plan = adminService.selectPlanById(planId);
        if (plan == null) {
            return PageController.REDIRECT_MGR_INDEX;
        }
        ExtUser user = adminService.selectUserById(plan.getUserId());
        if (user == null) {
            return PageController.REDIRECT_MGR_INDEX;
        }
        session.setAttribute("user", user);
        return PageController.REDIRECT + "/user/editPlan/" + planId;
    }

    @RequestMapping("/toAd/{adId}")
    public String toAd(HttpSession session, @PathVariable int adId) {
        ExtAd ad = adminService.selectAdById(adId);
        if (ad == null) {
            return PageController.REDIRECT_MGR_INDEX;
        }
        ExtUser user = adminService.selectUserById(ad.getUserId());
        if (user == null) {
            return PageController.REDIRECT_MGR_INDEX;
        }
        session.setAttribute("user", user);
        return PageController.REDIRECT + "/user/editAd/" + adId;
    }
}
