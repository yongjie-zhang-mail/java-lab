package com.yj.lab.spring.model.vo.response.common;

import com.yj.lab.spring.model.constant.MessageConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "ResultEntity", description = "返回结果")
public class ResultEntity<T> {
    @ApiModelProperty(value = "返回状态码(0:成功,1:失败,其它)", example = "0")
    private String code;
    @ApiModelProperty(value = "返回信息", example = "success")
    private String msg;
    @ApiModelProperty(value = "返回数据对象")
    private T data;

    public static <T> ResultEntity<T> getSuccessResult(T data) {
        return new ResultEntity<T>(MessageConstant.CODE_SUCCESS, MessageConstant.MESSAGE_SUCCESS, data);
    }

    public static <T> ResultEntity<T> getSuccessResult() {
        return new ResultEntity<T>(MessageConstant.CODE_SUCCESS, MessageConstant.MESSAGE_SUCCESS, null);
    }

    public static <T> ResultEntity<T> getResult(String code, String message) {
        return new ResultEntity<T>(code, message, null);
    }

}
