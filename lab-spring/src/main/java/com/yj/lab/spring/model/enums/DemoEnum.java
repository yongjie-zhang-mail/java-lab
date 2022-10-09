package com.yj.lab.spring.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhangyj21
 */
@Getter
@AllArgsConstructor
public enum DemoEnum {
    /**
     * 未知
     */
    UNKNOWN(0, "未知"),
    /**
     * 男
     */
    MALE(1, "男"),
    /**
     * 女
     */
    FEMALE(1, "女");

    private final int code;
    private final String msg;

}
