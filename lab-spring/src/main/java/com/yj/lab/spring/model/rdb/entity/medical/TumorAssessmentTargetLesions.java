package com.yj.lab.spring.model.rdb.entity.medical;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author Zhang Yongjie
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("tumor_assessment_target_lesions")
public class TumorAssessmentTargetLesions {


    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField
    private String screenNumber;

    @TableField
    private String ecrf;

    @TableField
    private String isAssess;

    @TableField
    private Integer assessmentCycles;

    @TableField
    private Integer lesionsNumber;

    @TableField
    private String lesionsDescription;

    @TableField
    private String lesionsTypes;

    @TableField
    private String examinationDate;

    @TableField
    private String examinationMethod;

    @TableField
    private BigDecimal lesionsMaximumDiameter;

    @TableField
    private BigDecimal targetLesionsTotalMaximumDiameters;

    /**
     * 是否正确(0:否,1:是)
     */
    @TableField
    private String result;

    /**
     * 疗效
     */
    @TableField
    private String efficacy;

}
