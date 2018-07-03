package com.dsp.ad.entity.subentity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlanParam {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(PlanParam.class);

    /**
     * 投放速率
     * 0.匀速1.加速
     */
    private int speed;
    private String device;
    private String area;
    private String sex;
    private String age;
    private String income;
    private String consumption;
    private String car;
    private String marry;
    private String child;
    private String quality;

    /**
     * 媒体选择
     * 0.随机 1.定向选择
     */
    private int media;
    private String m1;
    private String m2;
    private String m3;
    private String m4;
    private String m5;
    private String m6;
    private String m7;
    private String m8;
    private String m9;
    private String m10;
    private String m11;
    private String m12;
    private String m13;
    private String m14;
    private String m15;
    private String m16;
    private String m17;
    private String m18;
    private String m19;
    private String m20;
    private String m21;
    private String m22;
    private String m23;
    private String m24;
    private String m25;

    private String d1;
    private String d2;
    private String d3;
    private String d4;
    private String d5;
    private String d6;
    private String d7;


    public String toJson() {
        try {
            return OBJECT_MAPPER.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return "";
    }

    public static PlanParam fromJson(String json) {
        if (!StringUtils.isEmpty(json)) {
            try {
                return OBJECT_MAPPER.readValue(json, PlanParam.class);
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        return new PlanParam();
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getConsumption() {
        return consumption;
    }

    public void setConsumption(String consumption) {
        this.consumption = consumption;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getMarry() {
        return marry;
    }

    public void setMarry(String marry) {
        this.marry = marry;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getMedia() {
        return media;
    }

    public void setMedia(int media) {
        this.media = media;
    }

    public String getM1() {
        return m1;
    }

    public void setM1(String m1) {
        this.m1 = m1;
    }

    public String getM2() {
        return m2;
    }

    public void setM2(String m2) {
        this.m2 = m2;
    }

    public String getM3() {
        return m3;
    }

    public void setM3(String m3) {
        this.m3 = m3;
    }

    public String getM4() {
        return m4;
    }

    public void setM4(String m4) {
        this.m4 = m4;
    }

    public String getM5() {
        return m5;
    }

    public void setM5(String m5) {
        this.m5 = m5;
    }

    public String getM6() {
        return m6;
    }

    public void setM6(String m6) {
        this.m6 = m6;
    }

    public String getM7() {
        return m7;
    }

    public void setM7(String m7) {
        this.m7 = m7;
    }

    public String getM8() {
        return m8;
    }

    public void setM8(String m8) {
        this.m8 = m8;
    }

    public String getM9() {
        return m9;
    }

    public void setM9(String m9) {
        this.m9 = m9;
    }

    public String getM10() {
        return m10;
    }

    public void setM10(String m10) {
        this.m10 = m10;
    }

    public String getM11() {
        return m11;
    }

    public void setM11(String m11) {
        this.m11 = m11;
    }

    public String getM12() {
        return m12;
    }

    public void setM12(String m12) {
        this.m12 = m12;
    }

    public String getM13() {
        return m13;
    }

    public void setM13(String m13) {
        this.m13 = m13;
    }

    public String getM14() {
        return m14;
    }

    public void setM14(String m14) {
        this.m14 = m14;
    }

    public String getM15() {
        return m15;
    }

    public void setM15(String m15) {
        this.m15 = m15;
    }

    public String getM16() {
        return m16;
    }

    public void setM16(String m16) {
        this.m16 = m16;
    }

    public String getM17() {
        return m17;
    }

    public void setM17(String m17) {
        this.m17 = m17;
    }

    public String getM18() {
        return m18;
    }

    public void setM18(String m18) {
        this.m18 = m18;
    }

    public String getM19() {
        return m19;
    }

    public void setM19(String m19) {
        this.m19 = m19;
    }

    public String getM20() {
        return m20;
    }

    public void setM20(String m20) {
        this.m20 = m20;
    }

    public String getM21() {
        return m21;
    }

    public void setM21(String m21) {
        this.m21 = m21;
    }

    public String getM22() {
        return m22;
    }

    public void setM22(String m22) {
        this.m22 = m22;
    }

    public String getM23() {
        return m23;
    }

    public void setM23(String m23) {
        this.m23 = m23;
    }

    public String getM24() {
        return m24;
    }

    public void setM24(String m24) {
        this.m24 = m24;
    }

    public String getM25() {
        return m25;
    }

    public void setM25(String m25) {
        this.m25 = m25;
    }

    public String getD1() {
        return d1;
    }

    public void setD1(String d1) {
        this.d1 = d1;
    }

    public String getD2() {
        return d2;
    }

    public void setD2(String d2) {
        this.d2 = d2;
    }

    public String getD3() {
        return d3;
    }

    public void setD3(String d3) {
        this.d3 = d3;
    }

    public String getD4() {
        return d4;
    }

    public void setD4(String d4) {
        this.d4 = d4;
    }

    public String getD5() {
        return d5;
    }

    public void setD5(String d5) {
        this.d5 = d5;
    }

    public String getD6() {
        return d6;
    }

    public void setD6(String d6) {
        this.d6 = d6;
    }

    public String getD7() {
        return d7;
    }

    public void setD7(String d7) {
        this.d7 = d7;
    }
}
