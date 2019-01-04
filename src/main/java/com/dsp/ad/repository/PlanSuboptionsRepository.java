package com.dsp.ad.repository;

import com.dsp.ad.entity.PlanAttributeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author wanghh
 * @date 2019/1/3 21:17
 */
public interface PlanSuboptionsRepository extends JpaRepository<PlanAttributeEntity, Integer> {
}
