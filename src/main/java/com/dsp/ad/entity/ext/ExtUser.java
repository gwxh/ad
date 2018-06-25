package com.dsp.ad.entity.ext;

import com.dsp.ad.entity.User;

public class ExtUser {

    private int id;
    private String username;
    private double amount;
    private int status;
    private String email;
    private String qq;
    private String mobile;
    private String note;

    private String password;

    public ExtUser() {
    }

    public ExtUser(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.amount = user.getAmount() / 100d;
        this.status = user.getStatus();
        this.email = user.getEmail();
        this.qq = user.getQq();
        this.mobile = user.getMobile();
        this.note = user.getNote();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
