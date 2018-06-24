package com.dsp.ad.service.impl;

import com.dsp.ad.config.LLB;
import com.dsp.ad.entity.Task;
import com.dsp.ad.entity.ext.ExtAd;
import com.dsp.ad.repository.TaskRepository;
import com.dsp.ad.service.TaskService;
import com.dsp.ad.util.HttpUtil;
import com.dsp.ad.util.TimeUtil;
import com.dsp.ad.util.result.LLBResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
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
        Map<String, String> taskInfoMap = taskInfoMap(ad);
        String result = HttpUtil.post(LLB.CREATE_TASK_URL, taskInfoMap);
        return saveTask(ad, result, taskInfoMap);
    }

    @Override
    public LLBResult modifyTask(ExtAd ad) {
        Map<String, String> taskInfoMap = taskInfoMap(ad);
        taskInfoMap.put("taskId", getTaskId(ad));
        String result = HttpUtil.post(LLB.MODIFY_TASK_URL, taskInfoMap);
        return saveTask(ad, result, taskInfoMap);
    }

    @Override
    public LLBResult startTask(ExtAd ad) {
        Map<String, String> taskInfoMap = new TreeMap<>();
        taskInfoMap.put("apiKey", LLB.API_KEY);
        taskInfoMap.put("taskId", getTaskId(ad));
        taskInfoMap.put("taskType", "1");
        int plan = calcPlan(ad.getPlan().getUnitPrice(), ad.getPlan().getTotalPrice());
        int stopTime = TimeUtil.now() + calcDuration(plan);
        taskInfoMap.put("stopTime", String.valueOf(stopTime));
        String result = HttpUtil.post(LLB.START_TASK_URL, taskInfoMap);
        return getResult(result);
    }

    @Override
    public LLBResult stopTask(ExtAd ad) {
        Map<String, String> taskInfoMap = new TreeMap<>();
        taskInfoMap.put("apiKey", LLB.API_KEY);
        taskInfoMap.put("taskId", getTaskId(ad));
        taskInfoMap.put("taskType", "1");
        String result = HttpUtil.post(LLB.STOP_TASK_URL, taskInfoMap);
        return getResult(result);
    }

    private String getTaskId(ExtAd ad) {
        Optional<Task> taskOptional = taskRepository.findById(ad.getId());
        if (!taskOptional.isPresent()) {
            return null;
        }
        Task task = taskOptional.get();
        return String.valueOf(task.getTaskId());
    }

    private LLBResult getResult(String result) {
        LLBResult llbResult = null;
        try {
            llbResult = OBJECT_MAPPER.readValue(result, LLBResult.class);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return llbResult;
    }

    private LLBResult saveTask(ExtAd ad, String result, Map<String, String> taskInfoMap) {
        LLBResult llbResult = getResult(result);
        if (llbResult != null) {
            Task task = new Task();
            task.setAdId(ad.getId());
            int plan = Integer.parseInt(taskInfoMap.get("plan"));
            task.setPlan(plan);
            task.setResult(llbResult.getStatus().getDetail());
            if (llbResult.getStatus().getCode() == SUCCESS_CODE) {
                task.setTaskId(llbResult.getResult().getTaskId());
                llbResult.setSuccess(true);
            }
            taskRepository.save(task);
        }
        return llbResult;
    }

    private Map<String, String> taskInfoMap(ExtAd ad) {
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
        return map;
    }

    private int calcPlan(double unitPrice, double totalPrice) {
        return (int) (totalPrice / unitPrice);
    }

    private int calcDuration(int plan) {
        final int hourDuration = 3600;
        int duration;
        if (plan <= 100) {
            duration = hourDuration;
        } else if (plan <= 1000) {
            duration = 2 * hourDuration;
        } else if (plan <= 5000) {
            duration = 3 * hourDuration;
        } else if (plan <= 10000) {
            duration = 4 * hourDuration;
        } else if (plan <= 15000) {
            duration = 5 * hourDuration;
        } else {
            duration = 6 * hourDuration;
        }
        return duration;
    }
}
