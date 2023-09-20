package com.dsp.ad.entity.ext;

import com.dsp.ad.entity.User;
import lombok.Data;

@Data
public class ExtUser {

    private int id;
    private String company;
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
        this.company = user.getCompany();
        this.username = user.getUsername();
        this.amount = user.getAmount() / 100d;
        this.status = user.getStatus();
        this.email = user.getEmail();
        this.qq = user.getQq();
        this.mobile = user.getMobile();
        this.note = user.getNote();
    }

}
