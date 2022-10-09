package com.yj.lab.spring.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GroupOperateStatus {

    /**
     * 未处理
     */
    NOT_START(0, "未处理"),
    /**
     * 处理中
     */
    DOING(1, "处理中"),
    /**
     * 处理完成
     */
    DONE(2, "处理完成");

    private final int code;
    private final String name;


}
