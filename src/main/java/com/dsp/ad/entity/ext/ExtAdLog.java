package com.dsp.ad.entity.ext;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExtAdLog {

    @ExcelProperty("日期")
    private String date;
    private Integer recordTime;
    @ExcelProperty("浏览量")
    private Long exec;
    @ExcelProperty("点击量")
    private Long cpc;
    @ExcelProperty("消耗金额")
    private Long amount;
    @ExcelProperty("点击率")
    private BigDecimal rate = BigDecimal.ZERO;

    public ExtAdLog(){}

    public ExtAdLog(Integer recordTime, Long exec, Long cpc, Long amount) {
        this.recordTime = recordTime;
        this.exec = exec;
        this.cpc = cpc;
        this.amount = amount;
    }
}
