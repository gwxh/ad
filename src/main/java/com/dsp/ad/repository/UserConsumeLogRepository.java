package com.dsp.ad.repository;

import com.dsp.ad.entity.UserConsumeLog;
import com.dsp.ad.entity.UserConsumeLogPrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserConsumeLogRepository extends JpaRepository<UserConsumeLog, UserConsumeLogPrimaryKey> {

}
