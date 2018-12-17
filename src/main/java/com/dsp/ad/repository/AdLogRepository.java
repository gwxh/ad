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

    @Query("select sum(a.amount) from AdLog a where a.adLogPK.day = ?1 and a.uid=?2")
    Integer selectUserAdsLogByDay(int day, int uid);

    @Query("select new com.dsp.ad.entity.ext.ExtAdLog(a.adLogPK.day,sum(a.exec),sum(a.cpc),sum(a.amount)) from AdLog a where a.adLogPK.day >= ?1 and a.adLogPK.day<?2 and a.uid=?3 group by a.adLogPK.day")
    List<ExtAdLog> selectUserAdsLogByMonth(int startDay, int endDay, int uid);

    @Query("select new com.dsp.ad.entity.ext.ExtAdLog(a.adLogPK.day,sum(a.exec),sum(a.cpc),sum(a.amount)) from AdLog a where a.uid=?1 group by a.adLogPK.day order by a.adLogPK.day desc")
    List<ExtAdLog> selectUserAdsLogs(int uid);
}
