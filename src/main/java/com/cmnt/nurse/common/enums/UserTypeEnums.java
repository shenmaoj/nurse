package com.cmnt.nurse.common.enums;

import com.cmnt.nurse.common.exception.BusinessException;

/**
 * 用户类型枚举
 * Created by shenmj on 2019/1/7.
 */
public enum UserTypeEnums {
    NURSE("护士", "01"),
    BASE("基地", "02"),
    HOSPITAL("医院", "03"),
    LEARN("学会", "04"),
    EXPERTS("专家", "05"),
    SYSTEM("系统管理员", "99");

    UserTypeEnums(String value, String key) {
        this.value = value;
        this.key = key;
    }

    private String value;

    private String key;

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
        for (UserTypeEnums typeEnums : UserTypeEnums.values()) {
            if (typeEnums.key.equals(key)) {
                return typeEnums.value;
            }
        }
        throw new BusinessException("90000", key + " Out of range(01,02,03,04,05,99) ");
    }
}
