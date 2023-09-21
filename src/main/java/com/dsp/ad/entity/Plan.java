package com.dsp.ad.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int sid;
    private int uid;
    private String name;
    private int unitPrice;
    private int totalPrice;
    private int days;
    private String param;
    private int status;
    private int createTime;
    private int updateTime;
}
