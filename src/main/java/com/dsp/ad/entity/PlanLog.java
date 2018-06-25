package com.dsp.ad.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="plan_log")
public class PlanLog {

    @EmbeddedId
    private PlanLogPrimaryKey planLogPk;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private boolean complete;

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public PlanLogPrimaryKey getPlanLogPk() {

        return planLogPk;
    }

    public void setPlanLogPk(PlanLogPrimaryKey planLogPk) {
        this.planLogPk = planLogPk;
    }
}
