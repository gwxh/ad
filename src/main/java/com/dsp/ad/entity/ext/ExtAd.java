package com.dsp.ad.entity.ext;

import com.dsp.ad.entity.Ad;
import com.dsp.ad.entity.subentity.AdParam;
import org.springframework.web.multipart.MultipartFile;

public class ExtAd {

    private int id;
    private int uid;
    private int planId;
    private String name;
    private int type;
    private String url;
    private AdParam param = new AdParam();
    private int status;
    private ExtPlan plan = new ExtPlan();
    private ExtUser user = new ExtUser();

    /**
     * 显示需要
     */
    private String typeName;

    /**
     * 传参需要
     */
    private MultipartFile imageFile;

    public ExtAd() {
    }

    public ExtAd(Ad ad) {
        this.id = ad.getId();
        this.uid = ad.getUid();
        this.planId = ad.getPid();
        this.name = ad.getName();
        this.type = ad.getType();
        this.url = ad.getUrl();
        this.param = AdParam.fromJson(ad.getParam());
        this.status = ad.getStatus();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return uid;
    }

    public void setUserId(int uid) {
        this.uid = uid;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public AdParam getParam() {
        return param;
    }

    public void setParam(AdParam param) {
        this.param = param;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }

    public ExtPlan getPlan() {
        return plan;
    }

    public void setPlan(ExtPlan plan) {
        this.plan = plan;
    }

    public ExtUser getUser() {
        return user;
    }

    public void setUser(ExtUser user) {
        this.user = user;
    }
}
