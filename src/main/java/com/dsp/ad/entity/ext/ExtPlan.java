package com.dsp.ad.entity.ext;

import com.dsp.ad.entity.Plan;
import com.dsp.ad.entity.subentity.PlanParam;

public class ExtPlan {

    private int id;
    private String name;
    private double unitPrice;
    private double totalPrice;
    private PlanParam param = new PlanParam();
    private int status;
    private int userId;

    private ExtUser user = new ExtUser();

    public ExtPlan() {
    }

    public ExtPlan(Plan plan) {
        this.id = plan.getId();
        this.name = plan.getName();
        this.unitPrice = plan.getUnitPrice() / 100d;
        this.totalPrice = plan.getTotalPrice() / 100d;
        this.status = plan.getStatus();
        this.param = PlanParam.fromJson(plan.getParam());
        this.userId = plan.getUid();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public PlanParam getParam() {
        return param;
    }

    public void setParam(PlanParam param) {
        this.param = param;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ExtUser getUser() {
        return user;
    }

    public void setUser(ExtUser user) {
        this.user = user;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
