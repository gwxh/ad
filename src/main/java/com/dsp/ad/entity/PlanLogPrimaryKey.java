package com.dsp.ad.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class PlanLogPrimaryKey implements Serializable {

    private Integer day;
    private Integer pid;

    public PlanLogPrimaryKey(Integer day, Integer pid) {
        this.day = day;
        this.pid = pid;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public PlanLogPrimaryKey(){}
}
