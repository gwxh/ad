package com.dsp.ad.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class AdLogPrimaryKey implements Serializable {

    private Integer recordTime;
    private Integer adId;

    public AdLogPrimaryKey(Integer recordTime, Integer adId) {
        this.recordTime = recordTime;
        this.adId = adId;
    }

    public AdLogPrimaryKey() {

    }

    public Integer getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Integer recordTime) {
        this.recordTime = recordTime;
    }

    public Integer getAdId() {
        return adId;
    }

    public void setAdId(Integer adId) {
        this.adId = adId;
    }
}
