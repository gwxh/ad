package com.dsp.ad.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class AdLogPrimaryKey implements Serializable {

    private Integer day;
    private Integer aid;

    public AdLogPrimaryKey(Integer day, Integer aid) {
        this.day = day;
        this.aid = aid;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public AdLogPrimaryKey(){

    }
}
