package com.dsp.ad.repository;

import com.dsp.ad.entity.AdImgSizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wanghh
 * @date 2018/12/20 21:53
 */
public interface AdImgSizeRepository extends JpaRepository<AdImgSizeEntity, Integer> {

    List<AdImgSizeEntity> findBySid(int sid);

    List<AdImgSizeEntity> findBySidAndType(int sid, int type);
}
