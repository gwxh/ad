package com.dsp.ad.repository;

import com.dsp.ad.entity.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AdRepository extends JpaRepository<Advertisement, Integer> {

    @Query("from Advertisement a where a.status in (1,2) order by a.id desc")
    List<Advertisement> selectAllAds();

    @Query("from Advertisement a where a.status not in (1,2,5) order by a.id desc")
    List<Advertisement> selectAllAuditAds();

    @Query("from Advertisement a where a.userId=?1 and a.status!=5 order by a.id desc")
    List<Advertisement> selectAds(int userId);

    @Query("from Advertisement a where a.id=?1 and a.userId=?2 and a.status!=5")
    Advertisement selectAd(int adId, int userId);

    @Transactional
    @Modifying
    @Query("update Advertisement a set a.status=?2 where a.id=?1")
    void updateStatus(int adId, int status);

    @Query("from Advertisement a where a.planId=?")
    List<Advertisement> selectAdsByPlan(int planId);
}
