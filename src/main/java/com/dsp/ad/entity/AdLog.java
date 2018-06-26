package com.dsp.ad.entity;

import javax.persistence.*;

@Entity
@Table(name="ad_log")
public class AdLog {

    @EmbeddedId
    private AdLogPrimaryKey adLogPK;

    @Column
    private int userId;

    @Column(nullable = false)
    private int exec;
    @Column(nullable = false)
    private int cpc;
    @Column(nullable = false)
    private int amount;

    public AdLogPrimaryKey getAdLogPK() {
        return adLogPK;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setAdLogPK(AdLogPrimaryKey adLogPK) {
        this.adLogPK = adLogPK;
    }

    public int getExec() {
        return exec;
    }

    public void setExec(int exec) {
        this.exec = exec;
    }

    public int getCpc() {
        return cpc;
    }

    public void setCpc(int cpc) {
        this.cpc = cpc;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
