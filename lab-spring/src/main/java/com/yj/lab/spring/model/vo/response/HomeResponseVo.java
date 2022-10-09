package com.yj.lab.spring.model.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("HomeResponseVo出参")
public class HomeResponseVo {

    @ApiModelProperty(value = "姓名", required = true)
    private String name;

    @ApiModelProperty(value = "性别(0:不限,1:男,2:女)", example = "0", required = true)
    private Integer gender;

    @ApiModelProperty(value = "手机号", required = true)
    private String phone;

    @ApiModelProperty(value = "出生日期")
    private Date birthday;

}