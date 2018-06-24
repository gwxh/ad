package com.dsp.ad.service.impl;

import com.dsp.ad.config.LLB;
import com.dsp.ad.entity.Task;
import com.dsp.ad.entity.ext.ExtAd;
import com.dsp.ad.repository.TaskRepository;
import com.dsp.ad.service.TaskService;
import com.dsp.ad.util.HttpUtil;
import com.dsp.ad.util.result.LLBResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import static com.dsp.ad.config.LLB.SUCCESS_CODE;

@Service
public class TaskServiceImpl implements TaskService {

    private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public LLBResult createTask(ExtAd ad) {
        Map<String, String> map = new TreeMap<>();
        map.put("apiKey", LLB.API_KEY);
        map.put("url", ad.getUrl());
        int plan = calcPlan(ad.getPlan().getUnitPrice(), ad.getPlan().getTotalPrice());
        String planStr = String.valueOf(plan);
        map.put("plan", planStr);
        String name = ad.getPlan().getName() + "-" + ad.getName() + "-" + ad.getId();
        map.put("name", name);
        map.put("pv", "6");
        map.put("source", "[{\"type\": 2,\"rate\": 100}]");
        map.put("fastCurve", "true");
        map.put("areas", ad.getPlan().getParam().getArea());
        //todo ua未传
        String result = HttpUtil.post(LLB.CREATE_TASK_URL, map);
        LLBResult llbResult = null;
        try {
            llbResult = OBJECT_MAPPER.readValue(result, LLBResult.class);
            Task task = new Task();
            task.setAdId(ad.getId());
            task.setPlan(plan);
            task.setResult(llbResult.getStatus().getDetail());
            if (llbResult.getStatus().getCode() == SUCCESS_CODE) {
                task.setTaskId(llbResult.getResult().getTaskId());
                llbResult.setSuccess(true);
            }
            taskRepository.save(task);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return llbResult;
    }

    private int calcPlan(double unitPrice, double totalPrice) {
        return (int) (totalPrice / unitPrice);
    }
}
