package com.dsp.ad.config;

public interface LLB {

    //gwxh apiKey 4eb3623669648d37c26e912c92082ec0
    //正式 apiKey 41650dfa2ba262471b92ad56208be07f
    String API_KEY = "4eb3623669648d37c26e912c92082ec0";

    String CREATE_TASK_URL = "http://service.liuliangbao.cn/api/v2/task.pc.create";

    String VIEW_TASK_URL = "http://service.liuliangbao.cn/api/v2/task.pc.view";

    String MODIFY_TASK_URL = "http://service.liuliangbao.cn/api/v2/task.pc.modify";

    String SELECT_TASK_EXEC = "http://service.liuliangbao.cn/api/v2/task.pc.execBatch";

    String START_TASK_URL = "http://service.liuliangbao.cn/api/v2/service.start";

    String STOP_TASK_URL = "http://service.liuliangbao.cn/api/v2/service.stop";

    int SUCCESS_CODE = 100;
}
