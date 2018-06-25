package com.dsp.ad.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class PlanLogPrimaryKey implements Serializable {

    private Integer recordTime;
    private Integer planId;

    public PlanLogPrimaryKey() {
    }

    public PlanLogPrimaryKey(Integer recordTime, Integer planId) {

        this.recordTime = recordTime;
        this.planId = planId;
    }

    public Integer getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Integer recordTime) {
        this.recordTime = recordTime;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }
}
