package com.dsp.ad.controller;

import com.dsp.ad.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class PageController {

    public static final String REDIRECT = "redirect:";

    @RequestMapping("/user/")
    public String toIndexPage() {
        return "index";
    }

    @RequestMapping("/user/login")
    public String toLoginPage(){
        return "login";
    }

    @RequestMapping("/user/plan")
    public String toPlanPage() {
        return "plan";
    }

    @RequestMapping("/user/ad")
    public String toAdPage() {
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
    public String toCreatePlanPage() {
        return "create_plan";
    }

    @RequestMapping("/user/createAd")
    public String toCreateAd() {
        return "create_ad";
    }
}
