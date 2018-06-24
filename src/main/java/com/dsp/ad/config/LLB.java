package com.dsp.ad.config;

public interface LLB {

    String API_KEY = "4eb3623669648d37c26e912c92082ec0";

    String CREATE_TASK_URL = "http://service.liuliangbao.cn/api/v2/task.pc.create";

    String VIEW_TASK_URL = "http://service.liuliangbao.cn/api/v2/task.pc.view";

    String MODIFY_TASK_URL = "http://service.liuliangbao.cn/api/v2/task.pc.modify";

    String TASK_EXEC_STATUS_URL = "http://service.liuliangbao.cn/api/v2/task.pc.execBatch";

    String START_TASK_URL = "http://service.liuliangbao.cn/api/v2/service.start";

    String STOP_TASK_URL = "http://service.liuliangbao.cn/api/v2/service.stop";

    int SUCCESS_CODE = 100;
}
