package com.dsp.ad.config;

public interface LLB {

    //gwxh apiKey 4eb3623669648d37c26e912c92082ec0
    //正式 apiKey 41650dfa2ba262471b92ad56208be07f
    String API_KEY = "41650dfa2ba262471b92ad56208be07f";

    String CREATE_TASK_URL = "http://service.liuliangbao.cn/api/v2/task.mobile.create";

    String VIEW_TASK_URL = "http://service.liuliangbao.cn/api/v2/task.mobile.view";

    String MODIFY_TASK_URL = "http://service.liuliangbao.cn/api/v2/task.mobile.modify";

    String SELECT_TASK_EXEC = "http://service.liuliangbao.cn/api/v2/task.mobile.execBatch";

    String START_TASK_URL = "http://service.liuliangbao.cn/api/v2/service.start";

    String STOP_TASK_URL = "http://service.liuliangbao.cn/api/v2/service.stop";

    int SUCCESS_CODE = 100;
}
