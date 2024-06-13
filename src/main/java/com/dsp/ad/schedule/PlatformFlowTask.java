package com.dsp.ad.schedule;

import com.dsp.ad.entity.PlatformFlow;
import com.dsp.ad.enums.PlatformFlowEnum;
import com.dsp.ad.repository.PlatformFlowRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Slf4j
@Component
public class PlatformFlowTask {

    @Autowired
    private PlatformFlowRepository platformFlowRepository;

    @Scheduled(cron = "0 0,8,16 * * * ?")
    public void calcPlatformFlow() {
        Random random = new Random();
        BigDecimal totalPV = BigDecimal.ZERO;
        BigDecimal targetTotal = new BigDecimal("10000");

        List<PlatformFlow> platformFlowList = new ArrayList<>();
        for (PlatformFlowEnum platformFlowEnum : PlatformFlowEnum.values()) {
            BigDecimal min = platformFlowEnum.pv.multiply(new BigDecimal("0.8"));
            BigDecimal max = platformFlowEnum.pv.multiply(new BigDecimal("1.2"));
            BigDecimal randomPV = min.add(new BigDecimal(random.nextDouble()).multiply(max.subtract(min))).setScale(2, RoundingMode.HALF_UP);
            PlatformFlow platformFlow = new PlatformFlow();
            platformFlow.setName(platformFlowEnum.text);
            platformFlow.setPv(randomPV);
            platformFlowList.add(platformFlow);
            totalPV = totalPV.add(randomPV);
        }

        BigDecimal correctionFactor = targetTotal.divide(totalPV, RoundingMode.HALF_UP);
        totalPV = BigDecimal.ZERO;
        for (PlatformFlow platformFlow : platformFlowList) {
            BigDecimal pv = platformFlow.getPv().multiply(correctionFactor).setScale(2, RoundingMode.HALF_UP);
            platformFlow.setPv(pv);
            totalPV = totalPV.add(pv);
        }

        for (PlatformFlow platformFlow : platformFlowList) {
            BigDecimal rate = platformFlow.getPv().multiply(new BigDecimal("100")).divide(totalPV, 2, RoundingMode.HALF_UP);
            platformFlow.setRate(rate);
            log.info("{} 流量:{},占比: {}", platformFlow.getName(), platformFlow.getPv(), rate);
        }
        log.info("totalPV:{}", totalPV);
        platformFlowRepository.deleteAll();
        platformFlowRepository.saveAll(platformFlowList);
    }
}
