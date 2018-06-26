package com.dsp.ad.repository;

import com.dsp.ad.entity.AdLog;
import com.dsp.ad.entity.AdLogPrimaryKey;
import com.dsp.ad.entity.ext.ExtAdLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdLogRepository extends JpaRepository<AdLog, AdLogPrimaryKey> {

    @Query("select sum(a.amount) from AdLog a where a.adLogPK.recordTime = ?1 and a.userId=?2")
    Integer selectUserAdsLogByDay(int day, int userId);

    @Query("select new com.dsp.ad.entity.ext.ExtAdLog(a.adLogPK.recordTime,sum(a.exec),sum(a.cpc),sum(a.amount)) from AdLog a where a.adLogPK.recordTime >= ?1 and a.adLogPK.recordTime<?2 and a.userId=?3")
    List<ExtAdLog> selectUserAdsLogByMonth(int startDay, int endDay, int userId);
}
