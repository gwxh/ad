package com.dsp.ad.repository;

import com.dsp.ad.entity.Ad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AdRepository extends JpaRepository<Ad, Integer> {

    @Query("from Ad a where a.sid=?1 and a.status in (1,2) order by a.id desc")
    List<Ad> selectAllAds(int sid);

    @Query("from Ad a where a.sid=?1 and a.status not in (1,2,5) order by a.id desc")
    List<Ad> selectAllAuditAds(int sid);

    @Query("from Ad a where a.uid=?1 and a.status<>5 order by a.id desc")
    List<Ad> selectAds(int uid);

    @Query("from Ad a where a.id=?1 and a.uid=?2 and a.status<>5")
    Ad selectAd(int aid, int uid);

    @Transactional
    @Modifying
    @Query("update Ad a set a.status=?2 where a.id=?1")
    void updateStatus(int aid, int status);

    @Query("from Ad a where a.pid=?1")
    List<Ad> selectAdsByPlan(int pid);

    @Query("from Ad a where a.sid=?1 and a.status =2")
    List<Ad> selectAdsByStartStatus(int sid);
}
