package com.dsp.ad.controller;

import com.dsp.ad.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author wanghh
 * @date 2018/12/22 13:38
 */
@Controller
@RequestMapping("/ad")
public class AdController {

    @Autowired
    private AdService adService;

    @PostMapping("/saveAdType")
    public String saveAdType(int id, String name) {
        adService.saveAdType(id, name);
        return PageController.REDIRECT_SETTING;
    }

    @GetMapping("/deleteAdType/{id}")
    public String deleteAdType(@PathVariable int id) {
        adService.deleteAdType(id);
        return PageController.REDIRECT_SETTING;
    }

    @PostMapping("/saveAdImgSize")
    public String saveAdImgSize(int id, int type, String name) {
        adService.saveAdImgSize(id, type, name);
        return PageController.REDIRECT_SETTING;
    }

    @GetMapping("/deleteAdImgSize/{id}")
    public String deleteAdImgSize(@PathVariable int id) {
        adService.deleteAdImgSize(id);
        return PageController.REDIRECT_SETTING;
    }
}
