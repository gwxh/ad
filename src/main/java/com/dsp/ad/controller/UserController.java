package com.dsp.ad.controller;

import com.dsp.ad.entity.User;
import com.dsp.ad.entity.ext.ExtAd;
import com.dsp.ad.entity.ext.ExtPlan;
import com.dsp.ad.entity.ext.ExtUser;
import com.dsp.ad.enums.UserEnum;
import com.dsp.ad.service.AdminService;
import com.dsp.ad.service.UserService;
import com.dsp.ad.util.MD5Util;
import com.dsp.ad.util.UploadUtil;
import com.dsp.ad.util.result.LLBResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    PageController pageController;

    @Resource
    UploadUtil uploadUtil;

    @PostMapping("/login")
    public String login(Model model, String username, String password, HttpSession session) {
        if (StringUtils.isEmpty(username)) {
            model.addAttribute("msg", "用户名不能为空");
            return pageController.toLoginPage();
        }
        if (StringUtils.isEmpty(password)) {
            model.addAttribute("msg", "密码不能为空");
            return pageController.toLoginPage();
        }
        User user = userService.selectUserByName(username);
        if (user == null) {
            model.addAttribute("msg", "广告商不存在");
            return pageController.toLoginPage();
        }
        if (!Objects.equals(MD5Util.md5(password), user.getPassword())) {
            model.addAttribute("msg", "密码错误");
            return pageController.toLoginPage();
        }
        if (user.getStatus() != UserEnum.Status.ENABLE.value) {
            model.addAttribute("msg", "此账号已被禁用");
            return pageController.toLoginPage();
        }

        session.setAttribute("user", new ExtUser(user));
        return "redirect:/";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.setAttribute("user", null);
        return PageController.REDIRECT + pageController.toLoginPage();
    }

    @PostMapping("/saveUserInfo")
    public String saveUserInfo(Model model, ExtUser userInfo, @SessionAttribute ExtUser extUser) {
        User user = userService.selectUserByName(extUser.getUsername());
        user.setMobile(userInfo.getMobile());
        user.setEmail(userInfo.getEmail());
        user.setQq(userInfo.getQq());
        userService.saveUserInfo(user);
        return PageController.REDIRECT + pageController.toSettingPage(model, extUser);
    }

    @PostMapping("/editPassword")
    public String editPassword(Model model, @SessionAttribute ExtUser extUser, String oldPassword, String newPassword2) {
        User user = userService.selectUserByName(extUser.getUsername());
        if (!user.getPassword().equals(MD5Util.md5(oldPassword))) {
            model.addAttribute("msg", "旧密码输出错误");
            return pageController.toSettingPage(model, extUser);
        }
        user.setPassword(MD5Util.md5(newPassword2));
        userService.saveUserInfo(user);
        return PageController.REDIRECT + pageController.toSettingPage(model, extUser);
    }

    @PostMapping("/createPlan")
    public String createPlan(ExtPlan plan, Model model, @SessionAttribute ExtUser user) {
        userService.createPlan(user, plan);
        return PageController.REDIRECT + pageController.toPlanPage(model, user);
    }

    @PostMapping("/editPlan")
    public String editPlan(ExtPlan plan, Model model, @SessionAttribute ExtUser user) {
        userService.editPlan(user, plan);
        return PageController.REDIRECT + pageController.toPlanPage(model, user);
    }

    @PostMapping("/createAd")
    public String createAd(ExtAd ad, @SessionAttribute ExtUser user) throws IOException {
        ad.getParam().setImage(uploadUtil.uploadImage(ad.getImageFile()));
        userService.createAd(user, ad);
        return PageController.REDIRECT_USER_AD;
    }

    @PostMapping("/editAd")
    public String editAd(ExtAd ad, @SessionAttribute ExtUser user) throws IOException {
        if (!StringUtils.isEmpty(ad.getImageFile().getOriginalFilename())) {
            ad.getParam().setImage(uploadUtil.uploadImage(ad.getImageFile()));
        }
        userService.editAd(user, ad);
        return PageController.REDIRECT_USER_AD;
    }

    @Autowired
    private AdminService adminService;

    @RequestMapping("/startAd/{adId}")
    public String startAd(RedirectAttributes attributes, @PathVariable int adId) {
        ExtAd ad = adminService.selectAdById(adId);
        if (ad == null) {
            attributes.addFlashAttribute("msg", "广告不存在");
            return PageController.REDIRECT_USER_AD;
        }
        ExtUser user = adminService.selectUserById(ad.getUserId());
        if (user == null) {
            attributes.addFlashAttribute("msg", "广告商不存在");
            return PageController.REDIRECT_USER_AD;
        }
        double price = ad.getPlan().getTotalPrice();
        if (user.getAmount() < price) {
            attributes.addFlashAttribute("msg", "广告商余额不足");
            return PageController.REDIRECT_USER_AD;
        }
        LLBResult result = adminService.startAd(ad);
        if (result == null) {
            attributes.addFlashAttribute("msg", "广告开启失败");
            return PageController.REDIRECT_USER_AD;
        }
        attributes.addFlashAttribute("msg", result.getStatus().getDetail());
        return PageController.REDIRECT_USER_AD;
    }

    @RequestMapping("/stopAd/{adId}")
    public String stopAd(RedirectAttributes attributes, @PathVariable int adId) {
        ExtAd ad = adminService.selectAdById(adId);
        if (ad == null) {
            attributes.addFlashAttribute("msg", "广告不存在");
            return PageController.REDIRECT_USER_AD;
        }
        LLBResult result = adminService.stopAd(ad);
        if (result == null) {
            attributes.addFlashAttribute("msg", "广告开启失败");
            return PageController.REDIRECT_USER_AD;
        }
        attributes.addFlashAttribute("msg", result.getStatus().getDetail());
        return PageController.REDIRECT_USER_AD;
    }
}
