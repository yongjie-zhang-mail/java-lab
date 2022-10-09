package com.yj.lab.spring.model.vo.request;

import com.yj.lab.spring.model.vo.es.NameValueAndChildren;
import com.yj.lab.spring.model.vo.es.NameValueAndParentName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Objects;

@Data
@ApiModel("查询人群数量入参")
public class LabelSelectionConditionVo {
    private static final long serialVersionUID = -5386568553839971063L;
    @ApiModelProperty(value = "时间维度")
    private TimeDimensionVo timeDimension;

    @ApiModelProperty(value = "用户维度")
    private NameValueAndChildren userDimension;

    @ApiModelProperty(value = "产品维度")
    private List<NameValueAndParentName> productDimension;

    @ApiModelProperty(value = "产品大类")
    private NameValueAndChildren goodsCategory;

    private String name;//人群模板名称

    private Integer userCount;//人群数量

    private String createUid;//创建人

    private int buyCount;//购买人数

    private int useCount;//使用人数

    private int buyAndUseCount;//购买且使用人数

    public boolean isEmpty() {
        return CollectionUtils.isEmpty(productDimension) && Objects.isNull(goodsCategory);
    }
}
