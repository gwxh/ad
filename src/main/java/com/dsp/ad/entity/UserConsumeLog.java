package com.dsp.ad.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user_consume_log")
public class UserConsumeLog {

    @EmbeddedId
    private UserConsumeLogPrimaryKey userConsumeLogPK;

    @Column
    private int amount;

    public UserConsumeLogPrimaryKey getUserConsumeLogPK() {
        return userConsumeLogPK;
    }

    public void setUserConsumeLogPK(UserConsumeLogPrimaryKey userConsumeLogPK) {
        this.userConsumeLogPK = userConsumeLogPK;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
