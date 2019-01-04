package com.dsp.ad.service;

import com.dsp.ad.entity.AdImgSizeEntity;
import com.dsp.ad.entity.AdTypeEntity;
import com.dsp.ad.entity.PlanAttributeEntity;
import com.dsp.ad.entity.ext.ExtAdImgSize;

import java.util.List;

/**
 * @author wanghh
 * @date 2018/12/20 21:54
 */
public interface SiteService {

    List<AdTypeEntity> queryAdTypeList();

    List<ExtAdImgSize> queryAdImgSizeList();

    List<AdImgSizeEntity> queryAdImgSizeList(int type);

    void saveAdType(int id, String name);

    void deleteAdType(int id);

    void saveAdImgSize(int id, int type, String name);

    void deleteAdImgSize(int id);

    List<PlanAttributeEntity> queryPlanAttributes(int sid, int level);

    void savePlanAttribute(PlanAttributeEntity entity);

    void deletePlanAttribute(int id);
}
