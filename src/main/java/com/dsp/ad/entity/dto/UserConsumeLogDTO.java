package com.dsp.ad.entity.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.data.WriteCellData;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wanghh
 * @since 2023-09-25
 */
@Data
public class UserConsumeLogDTO {

    @ExcelProperty("日期")
    private WriteCellData<String> date;
    @ExcelProperty("浏览量")
    private WriteCellData<BigDecimal> exec;
    @ExcelProperty("点击量")
    private WriteCellData<BigDecimal> cpc;
    @ExcelProperty("点击率")
    private WriteCellData<BigDecimal> rate;
    @ExcelProperty("消耗金额")
    private WriteCellData<BigDecimal> amount;

}
