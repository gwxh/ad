package com.dsp.ad.service;

import com.dsp.ad.entity.ext.ExtAd;
import com.dsp.ad.util.result.LLBResult;

public interface TaskService {

    LLBResult createTask(ExtAd ad);

    LLBResult modifyTask(ExtAd ad);

    LLBResult viewTask(ExtAd ad);

    LLBResult startTask(ExtAd ad);

    LLBResult stopTask(ExtAd ad);
}
