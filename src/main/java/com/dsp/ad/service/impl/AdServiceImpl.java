package com.dsp.ad.service.impl;

import com.dsp.ad.config.C;
import com.dsp.ad.entity.AdImgSizeEntity;
import com.dsp.ad.entity.AdTypeEntity;
import com.dsp.ad.repository.AdImgSizeRepository;
import com.dsp.ad.repository.AdTypeRepository;
import com.dsp.ad.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wanghh
 * @date 2018/12/22 13:41
 */
@Service
public class AdServiceImpl implements AdService {

    @Autowired
    private AdTypeRepository adTypeRepository;
    @Autowired
    private AdImgSizeRepository adImgSizeRepository;

    @Override
    public void saveAdType(int id, String name) {
        final AdTypeEntity entity = new AdTypeEntity();
        entity.setId(id);
        entity.setName(name);
        entity.setSid(C.SID);
        adTypeRepository.save(entity);
    }

    @Override
    public void deleteAdType(int id) {
        adTypeRepository.deleteById(id);
    }

    @Override
    public void saveAdImgSize(int id, int type, String name) {
        final AdImgSizeEntity entity = new AdImgSizeEntity();
        entity.setId(id);
        entity.setType(type);
        entity.setName(name);
        entity.setSid(C.SID);
        adImgSizeRepository.save(entity);
    }

    @Override
    public void deleteAdImgSize(int id) {
        adImgSizeRepository.deleteById(id);
    }
}
