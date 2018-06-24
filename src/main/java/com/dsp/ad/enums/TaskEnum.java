package com.dsp.ad.enums;

public interface TaskEnum {

    enum Status {
        TASK_STOP(0, "任务停止中"),
        TASK_START(1, "任务开始中"),
        //
        ;

        public final int value;
        public final String text;

        Status(int value, String text) {
            this.value = value;
            this.text = text;
        }
    }
}
