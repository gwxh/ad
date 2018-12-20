package com.dsp.ad.service;

import com.dsp.ad.entity.AdImgSizeEntity;
import com.dsp.ad.entity.AdTypeEntity;

import java.util.List;

/**
 * @author wanghh
 * @date 2018/12/20 21:54
 */
public interface SiteService {

    List<AdTypeEntity> queryAdTypeList();

    List<AdImgSizeEntity> queryAdImgSizeList(int type);
}
