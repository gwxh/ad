package com.dsp.ad.enums;

public interface UserEnum {

    enum Status {
        DISABLE(0, "禁用"), ENABLE(1, "启用"), DELETE(2, "删除");

        public final int value;
        public final String text;

        Status(int value, String text) {
            this.value = value;
            this.text = text;
        }
    }
}
