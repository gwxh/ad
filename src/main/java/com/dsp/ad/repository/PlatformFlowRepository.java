package com.dsp.ad.repository;

import com.dsp.ad.entity.PlatformFlow;
import com.dsp.ad.entity.ext.ExtPlatformFlow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlatformFlowRepository extends JpaRepository<PlatformFlow, Integer> {

    @Query("select new com.dsp.ad.entity.ext.ExtPlatformFlow(p.name,p.pv,p.rate) from PlatformFlow p")
    List<ExtPlatformFlow> selectPlatformFlowList();
}
