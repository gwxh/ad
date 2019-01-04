package com.dsp.ad.repository;

import com.dsp.ad.entity.PlanAttributeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wanghh
 * @date 2019/1/3 21:11
 */
public interface PlanAttributeRepository extends JpaRepository<PlanAttributeEntity, Integer> {

    List<PlanAttributeEntity> findBySidAndLevel(int sid, int level);
}
