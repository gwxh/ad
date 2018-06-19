package com.dsp.ad.controller;

import com.dsp.ad.entity.User;
import com.dsp.ad.entity.ext.ExtAd;
import com.dsp.ad.entity.ext.ExtPlan;
import com.dsp.ad.enums.UserEnum;
import com.dsp.ad.service.UserService;
import com.dsp.ad.util.MD5Util;
import com.dsp.ad.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

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

        session.setAttribute("user", user);
        return "redirect:/";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.setAttribute("user", null);
        return pageController.toLoginPage();
    }

    @PostMapping("/saveUserInfo")
    public String saveUserInfo(Model model, User userInfo, @SessionAttribute User user) {
        user.setMobile(userInfo.getMobile());
        user.setEmail(userInfo.getEmail());
        user.setQq(userInfo.getQq());
        userService.saveUserInfo(user);
        return PageController.REDIRECT + pageController.toSettingPage(model, user);
    }

    @PostMapping("/editPassword")
    public String editPassword(Model model, @SessionAttribute User user, String oldPassword, String newPassword2) {
        if (!user.getPassword().equals(MD5Util.md5(oldPassword))) {
            model.addAttribute("msg", "旧密码输出错误");
            return pageController.toSettingPage(model, user);
        }
        user.setPassword(MD5Util.md5(newPassword2));
        userService.saveUserInfo(user);
        return PageController.REDIRECT + pageController.toSettingPage(model, user);
    }

    @PostMapping("/createPlan")
    public String createPlan(ExtPlan plan, Model model, @SessionAttribute User user) {
        userService.createPlan(user, plan);
        return PageController.REDIRECT + pageController.toPlanPage(model, user);
    }

    @PostMapping("/editPlan")
    public String editPlan(ExtPlan plan, Model model, @SessionAttribute User user) {
        userService.editPlan(user, plan);
        return PageController.REDIRECT + pageController.toPlanPage(model, user);
    }

    @PostMapping("/createAd")
    public String createAd(ExtAd ad, Model model, @SessionAttribute User user) throws IOException {
        ad.getParam().setImage(uploadUtil.uploadImage(ad.getImageFile()));
        userService.createAd(user, ad);
        return PageController.REDIRECT + pageController.toAdPage(model, user);
    }

    @PostMapping("/editAd")
    public String editAd(ExtAd ad, Model model, @SessionAttribute User user) throws IOException {
        if (!StringUtils.isEmpty(ad.getImageFile().getOriginalFilename())) {
            ad.getParam().setImage(uploadUtil.uploadImage(ad.getImageFile()));
        }
        userService.editAd(user, ad);
        return PageController.REDIRECT + pageController.toAdPage(model, user);
    }
}
