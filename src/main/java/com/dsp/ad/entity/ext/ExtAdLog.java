package com.dsp.ad.entity.ext;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExtAdLog {

    private String date;
    private Integer recordTime;
    private Long exec;
    private Long cpc;
    private Long amount;
    private BigDecimal rate;

    public ExtAdLog() {
    }

    public ExtAdLog(Integer recordTime, Long exec, Long cpc, Long amount) {
        this.recordTime = recordTime;
        this.exec = exec;
        this.cpc = cpc;
        this.amount = amount;
    }
}
