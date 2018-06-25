package com.dsp.ad.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UserConsumeLogPrimaryKey implements Serializable {

    private Integer recordTime;
    private Integer userId;

    public UserConsumeLogPrimaryKey() {
    }

    public UserConsumeLogPrimaryKey(Integer recordTime, Integer userId) {
        this.recordTime = recordTime;
        this.userId = userId;
    }

    public Integer getRecordTime() {

        return recordTime;
    }

    public void setRecordTime(Integer recordTime) {
        this.recordTime = recordTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
