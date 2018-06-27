package com.dsp.ad.entity.ext;

public class ExtAdLog {

    private String date;
    private Integer recordTime;
    private Long exec;
    private Long cpc;
    private Long amount;

    public ExtAdLog() {
    }

    public ExtAdLog(Integer recordTime, Long exec, Long cpc, Long amount) {
        this.recordTime = recordTime;
        this.exec = exec;
        this.cpc = cpc;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Integer recordTime) {
        this.recordTime = recordTime;
    }

    public Long getExec() {
        return exec;
    }

    public void setExec(Long exec) {
        this.exec = exec;
    }

    public Long getCpc() {
        return cpc;
    }

    public void setCpc(Long cpc) {
        this.cpc = cpc;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
