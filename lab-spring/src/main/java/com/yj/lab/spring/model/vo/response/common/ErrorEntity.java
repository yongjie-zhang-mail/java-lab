package com.yj.lab.spring.model.vo.response.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhangyj21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorEntity<T> {

    private String code;
    private String msg;
    private T data;

}
