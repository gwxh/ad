package com.dsp.ad.repository;

import com.dsp.ad.entity.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdRepository extends JpaRepository<Advertisement, Integer> {

    @Query("from Advertisement a where a.userId=?1 and a.status!=5 order by a.createTime desc")
    List<Advertisement> selectAds(int userId);

    @Query("from Advertisement a where a.id=?1 and a.userId=?2 and a.status!=5")
    Advertisement selectAd(int adId, int userId);

    @Modifying
    @Query("update Advertisement a set a.status=?3,a.updateTime=?4 where a.id=?1 and a.userId=?2")
    int updateStatus(int adId, int userId, int status, int updateTime);
}
