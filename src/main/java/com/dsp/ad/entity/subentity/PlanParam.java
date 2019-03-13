package com.dsp.ad.entity.subentity;

import com.dsp.ad.config.C;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;

import static com.dsp.ad.config.C.OBJECT_MAPPER;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlanParam {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlanParam.class);

    private int platform;  //平台 0.移动端 1.PC

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    private int system; //系统 0.Android 1.IOS

    public int getSystem() {
        return system;
    }

    public void setSystem(int system) {
        this.system = system;
    }

    private int style; //广告样式

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private int network;

    public int getNetwork() {
        return network;
    }

    public void setNetwork(int network) {
        this.network = network;
    }

    private int operator;

    public int getOperator() {
        return operator;
    }

    public void setOperator(int operator) {
        this.operator = operator;
    }

    private String brand;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    private String app;

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    private String adv;

    public String getAdv() {
        return adv;
    }

    public void setAdv(String adv) {
        this.adv = adv;
    }

    private String xq;

    public String getXq() {
        return xq;
    }

    public void setXq(String xq) {
        this.xq = xq;
    }

    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private int payMethod;


    public int getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(int payMethod) {
        this.payMethod = payMethod;
    }

    /**
     * 展现频率
     */
    private int zxpl;

    public int getZxpl() {
        return zxpl;
    }

    public void setZxpl(int zxpl) {
        this.zxpl = zxpl;
    }

    /**
     * 展现次数
     */
    private int zxcs;

    public int getZxcs() {
        return zxcs;
    }

    public void setZxcs(int zxcs) {
        this.zxcs = zxcs;
    }

    /**
     * 点击次数
     */
    private int djcs;

    public int getDjcs() {
        return djcs;
    }

    public void setDjcs(int djcs) {
        this.djcs = djcs;
    }

    /**
     * 传参需要
     */
    private MultipartFile imageFile;

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }

    private int sex;

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    private String channel;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    /**
     * 投放速率
     * 0.匀速1.加速
     */
    private int speed;
    private String area;
    private String consumption;

    public String toJson() {
        try {
            return C.OBJECT_MAPPER.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return "";
    }

    public static PlanParam fromJson(String json) {
        if (!StringUtils.isEmpty(json)) {
            try {
                return OBJECT_MAPPER.readValue(json, PlanParam.class);
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        return new PlanParam();
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getConsumption() {
        return consumption;
    }

    public void setConsumption(String consumption) {
        this.consumption = consumption;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
