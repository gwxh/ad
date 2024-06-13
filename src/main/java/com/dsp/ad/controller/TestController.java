package com.dsp.ad.controller;

import com.dsp.ad.schedule.PlatformFlowTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private PlatformFlowTask platformFlowTask;

    @GetMapping("/calcPlatformFlow")
    public String calcPlatformFlow(){
        platformFlowTask.calcPlatformFlow();
        return "success";
    }
}
