package com.dsp.ad.enums;

public interface PlanEnum {

    enum Status {
        CREATE_CHECK(0, "新建待审"),
        ENABLE(1, "启用"),
        EDIT_CHECK(2,"修改待审"),
        DISABLE(3,"禁用"),
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
