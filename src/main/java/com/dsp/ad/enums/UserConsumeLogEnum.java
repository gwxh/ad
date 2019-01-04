package com.dsp.ad.enums;

/**
 * @author wanghh
 * @date 2018/11/26 22:42
 */
public interface UserConsumeLogEnum {

    enum Type {
        UNKNOWN(0, "未知"),
        RECHARGE(1, "充值"),
        TASK_COST(2, "广告消耗")
        //
        ;

        public final int value;
        public final String text;

        Type(int value, String text) {
            this.value = value;
            this.text = text;
        }

       public static Type valueOf(int value) {
            for (Type type : Type.values()) {
                if (type.value == value) {
                    return type;
                }
            }
            return UNKNOWN;
        }
    }
}
