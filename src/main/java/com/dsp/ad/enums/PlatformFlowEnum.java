package com.dsp.ad.enums;

import com.dsp.ad.entity.PlatformFlow;
import com.dsp.ad.entity.ext.ExtPlatformFlow;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Slf4j
@AllArgsConstructor
public enum PlatformFlowEnum {
    GONG_JU("工具", new BigDecimal("1000"), new BigDecimal("10")),
    YUE_DU("阅读", new BigDecimal("850"), new BigDecimal("8.5")),
    SHI_TING("视听", new BigDecimal("1500"), new BigDecimal("15")),
    YOU_XI("游戏", new BigDecimal("1200"), new BigDecimal("12")),
    SHE_JIAO("社交", new BigDecimal("1100"), new BigDecimal("11")),
    GOU_WU("购物", new BigDecimal("1300"), new BigDecimal("13")),
    JIAN_KANG("健康", new BigDecimal("400"), new BigDecimal("4")),
    LV_XING("旅行", new BigDecimal("300"), new BigDecimal("3")),
    XIN_WEN("新闻", new BigDecimal("1600"), new BigDecimal("16")),
    MU_YING("母婴", new BigDecimal("100"), new BigDecimal("1")),
    QI_CHE("汽车", new BigDecimal("60"), new BigDecimal("0.6")),
    ZHU_FANG("住房", new BigDecimal("40"), new BigDecimal("0.4")),
    LI_CAI("理财", new BigDecimal("120"), new BigDecimal("1.2")),
    JIAO_YU("教育", new BigDecimal("40"), new BigDecimal("0.4")),
    GONG_ZUO("工作", new BigDecimal("30"), new BigDecimal("0.3")),
    TOU_ZI("投资金额", new BigDecimal("50"), new BigDecimal("0.5")),
    QI_TA("其他", new BigDecimal("310"), new BigDecimal("3.1")),
    //
    ;
    public final String text;
    public final BigDecimal pv;
    public final BigDecimal rate;

//    public static void main(String[] args) {
//        BigDecimal total = BigDecimal.ZERO;
//        BigDecimal totalPV = BigDecimal.ZERO;
//        for (PlatformFlowEnum platformFlowEnum : PlatformFlowEnum.values()) {
//            total = total.add(platformFlowEnum.rate);
//            totalPV = totalPV.add(platformFlowEnum.pv);
//        }
//        System.out.println(total);
//        System.out.println(totalPV);
//    }

    public static void main(String[] args) {
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
    }
}