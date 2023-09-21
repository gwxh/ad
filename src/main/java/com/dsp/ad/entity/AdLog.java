package com.dsp.ad.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "ad_log")
public class AdLog {

    @EmbeddedId
    private AdLogPrimaryKey adLogPK;

    @Column(nullable = false)
    private int uid;

    @Column(nullable = false)
    private int exec;
    @Column(nullable = false)
    private int cpc;
    @Column(nullable = false)
    private int amount;

    private BigDecimal rate;
}
