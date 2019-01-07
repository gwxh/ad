package com.dsp.ad.controller;

import com.dsp.ad.entity.AdImgSizeEntity;
import com.dsp.ad.entity.PlanAttributeEntity;
import com.dsp.ad.service.SiteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wanghh
 * @date 2018/12/20 22:33
 */
@Controller
@RequestMapping("/site")
public class SiteController {

    @Autowired
    private SiteService siteService;

    @ResponseBody
    @PostMapping("/queryAdImgSizeList")
    public List<AdImgSizeEntity> queryAdImgSizeList(int type) throws JsonProcessingException {
        return siteService.queryAdImgSizeList(type);
    }

    @PostMapping("/saveAdType")
    public String saveAdType(int id, String name) {
        siteService.saveAdType(id, name);
        return PageController.REDIRECT_SETTING;
    }

    @GetMapping("/deleteAdType/{id}")
    public String deleteAdType(@PathVariable int id) {
        siteService.deleteAdType(id);
        return PageController.REDIRECT_SETTING;
    }

    @PostMapping("/saveAdImgSize")
    public String saveAdImgSize(int id, int type, String name) {
        siteService.saveAdImgSize(id, type, name);
        return PageController.REDIRECT_SETTING;
    }

    @GetMapping("/deleteAdImgSize/{id}")
    public String deleteAdImgSize(@PathVariable int id) {
        siteService.deleteAdImgSize(id);
        return PageController.REDIRECT_SETTING;
    }

    @PostMapping("/savePlanAttribute")
    public String savePlanAttribute(PlanAttributeEntity entity) {
        siteService.savePlanAttribute(entity);
        return PageController.REDIRECT_SETTING;
    }

    @GetMapping("/deletePlanAttribute/{id}")
    public String deletePlanAttribute(@PathVariable int id) {
        siteService.deletePlanAttribute(id);
        return PageController.REDIRECT_SETTING;
    }
}
