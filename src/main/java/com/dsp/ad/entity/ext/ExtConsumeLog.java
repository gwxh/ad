package com.dsp.ad.entity.ext;

import lombok.Data;

/**
 * @author wanghh
 * @date 2018/11/26 23:02
 */
@Data
public class ExtConsumeLog {

    private String date;
    private Integer time;
    private String typeName;
    private double amount;
    private String note;
    private String file;

}
