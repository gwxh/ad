package com.dsp.ad.service.impl;

import com.dsp.ad.config.C;
import com.dsp.ad.entity.AdImgSizeEntity;
import com.dsp.ad.entity.AdTypeEntity;
import com.dsp.ad.repository.AdImgSizeRepository;
import com.dsp.ad.repository.AdTypeRepository;
import com.dsp.ad.service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wanghh
 * @date 2018/12/20 21:58
 */
@Service
public class SiteServiceImpl implements SiteService {

    @Autowired
    private AdTypeRepository adTypeRepository;
    @Autowired
    private AdImgSizeRepository adImgSizeRepository;

    @Override
    public List<AdTypeEntity> queryAdTypeList() {
        return adTypeRepository.findBySid(C.SID);
    }

    @Override
    public List<AdImgSizeEntity> queryAdImgSizeList(int type) {
        return adImgSizeRepository.findBySidAndType(C.SID, type);
    }

}
