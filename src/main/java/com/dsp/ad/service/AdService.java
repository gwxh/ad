package com.dsp.ad.service;

/**
 * @author wanghh
 * @date 2018/12/22 13:41
 */
public interface AdService {

    void saveAdType(int id,String name);

    void deleteAdType(int id);

    void saveAdImgSize(int id,int type,String name);

    void deleteAdImgSize(int id);
}
