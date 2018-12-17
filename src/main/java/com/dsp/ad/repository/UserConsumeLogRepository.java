package com.dsp.ad.repository;

import com.dsp.ad.entity.UserConsumeLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserConsumeLogRepository extends JpaRepository<UserConsumeLog, Integer> {

    List<UserConsumeLog> findByUid(int uid);
}
