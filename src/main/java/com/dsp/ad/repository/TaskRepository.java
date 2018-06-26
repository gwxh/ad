package com.dsp.ad.repository;

import com.dsp.ad.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    @Transactional
    @Modifying
    @Query("update Task t set t.status=?2 where t.adId=?1")
    int updateStatus(int adId, int status);
}
