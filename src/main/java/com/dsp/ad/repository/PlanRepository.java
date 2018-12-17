package com.dsp.ad.repository;

import com.dsp.ad.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Integer> {

    @Query("from Plan p where p.status=1 order by p.id desc")
    List<Plan> selectAllPlans();

    @Query("from Plan p where p.status not in (1,4) order by p.id desc")
    List<Plan> selectAllAuditPlans();

    @Query("from Plan p where p.uid=?1 and p.status <>4 order by p.id desc")
    List<Plan> selectPlans(int uid);

    @Query("from Plan p where p.id=?1 and p.uid=?2")
    Plan selectPlan(int pid, int uid);

    @Transactional
    @Modifying
    @Query("update Plan p set p.status=?2 where p.id=?1")
    void updateStatus(int pid, int status);
}
