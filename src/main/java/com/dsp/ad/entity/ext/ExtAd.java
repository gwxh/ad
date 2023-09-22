package com.dsp.ad.entity.ext;

import com.dsp.ad.entity.Ad;
import com.dsp.ad.entity.subentity.AdParam;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
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
}
