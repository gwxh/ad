package com.dsp.ad.entity.ext;

import com.dsp.ad.entity.Plan;
import com.dsp.ad.entity.subentity.PlanParam;
import lombok.Data;

@Data
public class ExtPlan {

    private int id;
    private String name;
    private double unitPrice;
    private double totalPrice;
    private PlanParam param = new PlanParam();
    private int status;
    private int uid;
    private int days;

    private ExtUser user = new ExtUser();

    public ExtPlan() {
    }

    public ExtPlan(Plan plan) {
        this.id = plan.getId();
        this.name = plan.getName();
        this.unitPrice = plan.getUnitPrice() / 100d;
        this.totalPrice = plan.getTotalPrice() / 100d;
        this.days = plan.getDays();
        this.status = plan.getStatus();
        this.param = PlanParam.fromJson(plan.getParam());
        this.uid = plan.getUid();
    }

}
