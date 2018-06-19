package com.dsp.ad.repository;

import com.dsp.ad.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Integer> {

    @Query("from Plan p where p.userId=?1 order by p.createTime desc")
    List<Plan> selectPlans(int userId);

    @Query("from Plan p where p.id=?1 and p.userId=?2 and p.status!=3")
    Plan selectPlan(int planId, int userId);

    @Modifying
    @Query("update Plan p set p.status=?3,p.updateTime=?4 where p.id=?1 and p.userId=?2")
    int updateStatus(int planId, int userId, int status, int updateTime);
}
