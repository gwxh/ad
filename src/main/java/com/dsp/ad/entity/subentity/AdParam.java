package com.dsp.ad.entity.subentity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AdParam {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(AdParam.class);

    private int type;
    private String image;
    private int imgSize;
    @Deprecated
    private String size;

    public static AdParam fromJson(String json) {
        if (!StringUtils.isEmpty(json)) {
            try {
                return OBJECT_MAPPER.readValue(json, AdParam.class);
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        return new AdParam();
    }

    public String toJson() {
        try {
            return OBJECT_MAPPER.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return "";
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getImgSize() {
        return imgSize;
    }

    public void setImgSize(int imgSize) {
        this.imgSize = imgSize;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
