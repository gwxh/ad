package com.dsp.ad.service.impl;

import com.dsp.ad.config.C;
import com.dsp.ad.entity.AdImgSizeEntity;
import com.dsp.ad.entity.AdTypeEntity;
import com.dsp.ad.entity.PlanAttributeEntity;
import com.dsp.ad.entity.ext.ExtAdImgSize;
import com.dsp.ad.repository.AdImgSizeRepository;
import com.dsp.ad.repository.AdTypeRepository;
import com.dsp.ad.repository.PlanAttributeRepository;
import com.dsp.ad.repository.PlanSuboptionsRepository;
import com.dsp.ad.service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<ExtAdImgSize> queryAdImgSizeList() {
        final List<AdImgSizeEntity> list = adImgSizeRepository.findBySid(C.SID);
        return list.stream().map(entity -> {
            ExtAdImgSize size = new ExtAdImgSize();
            size.setId(entity.getId());
            final String name = adTypeRepository.findById(entity.getType()).orElse(new AdTypeEntity()).getName();
            size.setTypeName(name);
            size.setImgSize(entity.getName());
            return size;
        }).collect(Collectors.toList());
    }

    @Override
    public List<AdImgSizeEntity> queryAdImgSizeList(int type) {
        return adImgSizeRepository.findBySidAndType(C.SID, type);
    }

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

    @Autowired
    private PlanAttributeRepository planAttributeRepository;

    @Autowired
    private PlanSuboptionsRepository planSuboptionsRepository;

    @Override
    public List<PlanAttributeEntity> queryPlanAttributes(int sid, int level) {
        return planAttributeRepository.findBySidAndLevel(sid, level);
    }

    @Override
    public void savePlanAttribute(PlanAttributeEntity entity) {
        planAttributeRepository.save(entity);
    }

    @Override
    public void deletePlanAttribute(int id) {
        planAttributeRepository.deleteById(id);
    }

}
