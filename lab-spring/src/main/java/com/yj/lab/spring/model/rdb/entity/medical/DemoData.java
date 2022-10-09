package com.yj.lab.spring.model.rdb.entity.medical;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 基础数据类.这里的排序和excel里面的排序一致
 *
 * @author yongjie
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("demo_data")
public class DemoData {
    /**
     * 用名字去匹配，这里需要注意，如果名字重复，会导致只有一个字段读取到数据
     */

    @TableField
    @ExcelProperty("数字标题")
    private Double doubleData;

    @TableField
    @ExcelProperty("字符串标题")
    private String stringData;

    @TableField
    @ExcelProperty("日期标题")
    private Date dateData;


}
