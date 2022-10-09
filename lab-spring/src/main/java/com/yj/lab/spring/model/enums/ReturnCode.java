package com.yj.lab.spring.model.enums;

import com.yj.lab.spring.config.exception.BusinessExceptionAssert;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhangyj21
 * 返回信息 Return Code
 */
@Getter
@AllArgsConstructor
public enum ReturnCode implements BusinessExceptionAssert {

    /**
     * 公共 Return Code
     */
    SUCCESS("0", "成功"),
    FAIL("1", "失败"),
    NULL("401", "对象为空"),

    /**
     * 切面 Return Code
     */
    RISK_CONTROL_BLACK("40001", "操作频繁啦！请30分钟后再来哦！");

    private final String code;
    private final String msg;

}



