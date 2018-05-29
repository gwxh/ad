package com.dsp.ad.controller;

import com.dsp.ad.entity.User;
import com.dsp.ad.enums.UserEnum;
import com.dsp.ad.service.UserService;
import com.dsp.ad.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    PageController pageController;

    @PostMapping("/login")
    public String login(Model model, @RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) {
        if (StringUtils.isEmpty(username)) {
            model.addAttribute("msg", "用户名不能为空");
            return pageController.login();
        }
        if (StringUtils.isEmpty(password)) {
            model.addAttribute("msg", "密码不能为空");
            return pageController.login();
        }
        User user = userService.selectUserByName(username);
        if (user == null) {
            model.addAttribute("msg", "广告商不存在");
            return pageController.login();
        }
        if (!Objects.equals(MD5Util.md5(password), user.getPassword())) {
            model.addAttribute("msg", "密码错误");
            return pageController.login();
        }
        if (user.getStatus() != UserEnum.Status.enable.value) {
            model.addAttribute("msg", "此账号已被禁用");
            return pageController.login();
        }

        session.setAttribute("user", user);
        return "redirect:/";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.setAttribute("user", null);
        return pageController.login();
    }
}
