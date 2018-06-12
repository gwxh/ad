package com.dsp.ad.entity.ext;

import com.dsp.ad.entity.subentity.PlanParam;

public class ExtPlan {

    private int id;
    private String name;
    private double unitPrice;
    private double totalPrice;
    private PlanParam param = new PlanParam();
    private int status;

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
}
