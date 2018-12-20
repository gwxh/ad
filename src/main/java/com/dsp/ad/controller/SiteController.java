package com.dsp.ad.controller;

import com.dsp.ad.entity.AdImgSizeEntity;
import com.dsp.ad.service.SiteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
