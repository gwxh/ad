package com.dsp.ad.repository;

import com.dsp.ad.entity.AdLog;
import com.dsp.ad.entity.AdLogPrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdLogRepository extends JpaRepository<AdLog, AdLogPrimaryKey> {
}
