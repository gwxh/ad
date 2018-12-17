package com.dsp.ad.entity;

import javax.persistence.*;

@Entity
public class Task {

    @Id
    private int aid;
    @Column(nullable = false)
    private long tid;
    @Column(nullable = false)
    private int plan;
    @Column(nullable = false)
    private int status;
    @Column(nullable = false)
    private String result;

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public long getTid() {
        return tid;
    }

    public void setTid(long tid) {
        this.tid = tid;
    }

    public int getPlan() {
        return plan;
    }

    public void setPlan(int plan) {
        this.plan = plan;
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
