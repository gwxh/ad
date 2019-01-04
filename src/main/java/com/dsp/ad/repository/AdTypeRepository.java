package com.dsp.ad.repository;

import com.dsp.ad.entity.AdTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wanghh
 * @date 2018/12/20 21:48
 */
public interface AdTypeRepository extends JpaRepository<AdTypeEntity, Integer> {

    List<AdTypeEntity> findBySid(int sid);
}
