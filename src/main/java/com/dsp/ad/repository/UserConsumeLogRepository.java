package com.dsp.ad.repository;

import com.dsp.ad.entity.UserConsumeLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserConsumeLogRepository extends JpaRepository<UserConsumeLogEntity, Integer> {

    List<UserConsumeLogEntity> findByUid(int uid);

    @Query("select sum(amount) from UserConsumeLogEntity where time>=?1  and time<=?2 and uid=?3 and type = ?4")
    Integer selectUserConsumeLogByDay(int startTime, int endTime, int uid, int type);
}
