package com.dsp.ad.entity;

import javax.persistence.*;

@Entity
public class Task {

    @Id
    private int adId;
    @Column(nullable = false)
    private long taskId;
    @Column(nullable = false)
    private int plan;
    @Column(nullable = false)
    private int status;
    @Column(nullable = false)
    private String result;

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public int getPlan() {
        return plan;
    }

    public void setPlan(int plan) {
        this.plan = plan;
    }

    public int getAdId() {
        return adId;
    }

    public void setAdId(int adId) {
        this.adId = adId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
