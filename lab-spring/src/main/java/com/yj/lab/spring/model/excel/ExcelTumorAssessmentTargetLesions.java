package com.yj.lab.spring.model.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 基础数据类.这里的排序和excel里面的排序一致
 *
 * @author Zhang Yongjie
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExcelTumorAssessmentTargetLesions {
    /**
     * 用名字去匹配，这里需要注意，如果名字重复，会导致只有一个字段读取到数据
     */

    @ExcelProperty("受试者筛选号")
    private String screenNumber;

    @ExcelProperty("eCRF 名称")
    private String ecrf;

    @ExcelProperty("是否进行病灶评估？")
    private String isAssess;

    @ExcelProperty("评估周期")
    private Integer assessmentCycles;

    @ExcelProperty("病灶序号")
    private Integer lesionsNumber;

    @ExcelProperty("病灶描述")
    private String lesionsDescription;

    @ExcelProperty("类型")
    private String lesionsTypes;

    @ExcelProperty("检测日期")
    @DateTimeFormat("yyyy/MM/dd")
    private String examinationDate;

    @ExcelProperty("检测方法")
    private String examinationMethod;

    @ExcelProperty("非淋病灶为最大直径；淋为最小径")
    private BigDecimal lesionsMaximumDiameter;

    @ExcelProperty("病灶最大直径总和")
    private BigDecimal targetLesionsTotalMaximumDiameters;


}
