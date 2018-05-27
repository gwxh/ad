package com.dsp.ad.controller;

import com.dsp.ad.entity.Admin;
import com.dsp.ad.service.AdminService;
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
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public String login(Model model, @RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) {
        if (StringUtils.isEmpty(username)) {
            model.addAttribute("msg", "用户名不能为空");
            return loginPage();
        }
        if (StringUtils.isEmpty(password)) {
            model.addAttribute("msg", "密码不能为空");
            return loginPage();
        }
        Admin admin = adminService.selectAdminByName(username);
        if (admin == null) {
            model.addAttribute("msg", "管理员不存在");
            return loginPage();
        }
        if (!Objects.equals(MD5Util.md5(password), admin.getPassword())) {
            model.addAttribute("msg", "密码错误");
            return loginPage();
        }

        session.setAttribute("admin", admin);
        return "index";
    }

    private String loginPage(){
        return "login";
    }
}
