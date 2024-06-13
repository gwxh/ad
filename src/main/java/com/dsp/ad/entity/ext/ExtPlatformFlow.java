package com.dsp.ad.entity.ext;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ExtPlatformFlow {

    private String name;
    private BigDecimal pv;
    private BigDecimal rate;
}
