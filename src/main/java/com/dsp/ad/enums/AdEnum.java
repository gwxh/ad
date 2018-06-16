package com.dsp.ad.enums;

public interface AdEnum {

    enum Status {
        CREATE_CHECK(0, "新建待审"),
        ENABLE(1, "停止"),
        RUNNING(2, "开启"),
        EDIT_CHECK(3, "修改待审"),
        DISABLE(4, "禁用"),
        //
        ;

        public final int value;
        public final String text;

        Status(int value, String text) {
            this.value = value;
            this.text = text;
        }
    }

    enum Type {
        GROUP_IMAGE(1, "组图广告"),
        BIG_IMAGE(2, "大图广告"),
        LOWER_RIGHT_CORNER(3, "右下角"),
        MOBILE_SCREEN(4, "移动插屏"),
        MOBILE_BANNER(5, "移动Banner"),
        APP_SCREEN(6, "APP开屏"),
        //
        ;

        public final int value;
        public final String text;

        Type(int value, String text) {
            this.value = value;
            this.text = text;
        }
    }
}
