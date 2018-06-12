package com.dsp.ad.controller;

import com.dsp.ad.entity.User;
import com.dsp.ad.entity.ext.ExtPlan;
import com.dsp.ad.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Controller
public class PageController {

    public static final String REDIRECT = "redirect:";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(PageController.class);

    @Autowired
    private UserService userService;

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
    public String toCreateAd() {
        return "create_ad";
    }
}
