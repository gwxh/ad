package com.dsp.ad.repository;

import com.dsp.ad.entity.PlanLog;
import com.dsp.ad.entity.PlanLogPrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanLogRepository extends JpaRepository<PlanLog, PlanLogPrimaryKey> {
}
