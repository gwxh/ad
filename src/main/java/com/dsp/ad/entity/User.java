package com.dsp.ad.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private int sid;
    @Column(nullable = true)
    private String company;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private int amount;
    @Column(nullable = false)
    private int status;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String qq;
    @Column(nullable = false)
    private String mobile;
    @Column(nullable = false)
    private String note;
    @Column(nullable = false)
    private int loginTime;
    @Column(name = "login_ip", nullable = false)
    private int loginIP;
    @Column(nullable = false)
    private int createTime;
    @Column(nullable = false)
    private int updateTime;


}
