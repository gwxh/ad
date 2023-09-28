package com.dsp.ad.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_consume_log")
public class UserConsumeLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private int uid;
    @Column(nullable = false)
    private int type;
    @Column(nullable = false)
    private int amount;
    @Column(nullable = false)
    private int time;
    @Column(nullable = false)
    private String file;
    @Column(nullable = false)
    private String note;
}
