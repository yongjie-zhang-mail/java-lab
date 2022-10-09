package com.yj.lab.spring.model.vo.request;

import com.yj.lab.spring.model.vo.es.NameValueAndChildren;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("时间维度")
public class TimeDimensionVo {

    @ApiModelProperty(value = "开始时间")
    private NameValueAndChildren startTime;

    @ApiModelProperty(value = "结束时间")
    private NameValueAndChildren endTime;
}
