package com.cmnt.nurse.common.enums;

import com.cmnt.nurse.common.exception.BusinessException;

/**
 * 用户状态枚举
 * Created by shenmj on 2019/1/7.
 */
public enum UserStatusEnum {

    INVALID("无效", "0"),
    VALID("有效", "1"),
    FREEZE("冻结", "2");
    private String value;

    private String key;

    UserStatusEnum(String value, String key) {
        this.value = value;
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    /**
     * 通过key获取value值
     *
     * @param key
     * @return
     */
    public static String getValueOfKey(String key) {
        for (UserStatusEnum statusEnum : UserStatusEnum.values()) {
            if (statusEnum.key.equals(key)) {
                return statusEnum.value;
            }
        }
        throw new BusinessException("90000", key + " Out of range(0,1,2) ");
    }
}
