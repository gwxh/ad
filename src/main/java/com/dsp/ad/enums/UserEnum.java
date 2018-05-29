package com.dsp.ad.enums;

public interface UserEnum {

    enum Status {
        disable(0, "禁用"), enable(1, "启用");

        public final int value;
        public final String text;

        Status(int value, String text) {
            this.value = value;
            this.text = text;
        }
    }
}
