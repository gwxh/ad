package com.dsp.ad.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class PageController {

    @RequestMapping("/user/")
    public String index() {
        return "index";
    }

    @RequestMapping("/user/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/user/plan")
    public String plan() {
        return "plan";
    }

    @RequestMapping("/user/ad")
    public String ad() {
        return "ad";
    }

    @RequestMapping("/user/data")
    public String data() {
        return "data";
    }

    @RequestMapping("/user/setting")
    public String setting(Model model, HttpSession session) {
        model.addAttribute("user", session.getAttribute("user"));
        return "setting";
    }

    @RequestMapping("/user/createPlan")
    public String createPlan() {
        return "create_plan";
    }

    @RequestMapping("/user/createAd")
    public String createAd() {
        return "create_ad";
    }
}
