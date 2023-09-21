package com.dsp.ad.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@Table(name="plan_log")
public class PlanLog {

    @EmbeddedId
    private PlanLogPrimaryKey planLogPk;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private boolean complete;

    @Column(nullable = false)
    private int uid;

    @Column(nullable = false)
    private int exec;
    @Column(nullable = false)
    private int cpc;
    @Column(nullable = false)

    private BigDecimal rate;
}
