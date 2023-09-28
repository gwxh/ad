package com.dsp.ad.repository;

import com.dsp.ad.entity.PlanLog;
import com.dsp.ad.entity.PlanLogPrimaryKey;
import com.dsp.ad.entity.ext.ExtAdLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanLogRepository extends JpaRepository<PlanLog, PlanLogPrimaryKey> {


    @Query("select sum(a.exec) from PlanLog a where a.planLogPk.pid = ?1")
    Integer sumExec(int pid);

    @Query("select new com.dsp.ad.entity.ext.ExtAdLog(a.planLogPk.day,sum(a.exec),sum(a.cpc),sum(a.amount)) from PlanLog a where a.uid=?1 group by a.planLogPk.day order by a.planLogPk.day desc")
    List<ExtAdLog> selectUserPlanLogs(int uid);

    @Query("select new com.dsp.ad.entity.ext.ExtAdLog(a.planLogPk.day,sum(a.exec),sum(a.cpc),sum(a.amount)) from PlanLog a where a.planLogPk.day >= ?1 and a.planLogPk.day<?2 and a.uid=?3 group by a.planLogPk.day")
    List<ExtAdLog> selectUserPlanLogByMonth(int startDay, int endDay, int uid);
}
