package com.yj.lab.spring.model.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhangyj21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("示例Vo")
public class DemoVo {

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("年龄")
    private int age;
    @ApiModelProperty("0:未知,1:男,2:女")
    private int gender;
    @ApiModelProperty("薪水")
    private int salary;

}
