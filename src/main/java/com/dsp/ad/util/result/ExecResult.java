package com.dsp.ad.util.result;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExecResult {

    private int day;
    private int plan;
    private int today;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getPlan() {
        return plan;
    }

    public void setPlan(int plan) {
        this.plan = plan;
    }

    public int getToday() {
        return today;
    }

    public void setToday(int today) {
        this.today = today;
    }
}
