package com.yj.lab.spring.model.rdb.entity.second;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Zhang Yongjie
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = 123456L;

    @TableId(type = IdType.AUTO)
    private String uid;
    @TableField
    private String name;
    @TableField
    private String userLogo;
    @TableField
    private String gender;
    @TableField
    private Date birthday;
    @TableField
    private String phone;
    @TableField
    private String email;
    @TableField
    private String unionid;
    @TableField
    private String openid;
    @TableField
    private String wxCardId;
    @TableField
    private String wxCardCode;
    @TableField
    private String shopCode;
    @TableField
    private String barcode;
    @TableField
    private String jpushRegId;
    @TableField
    private Date regTime;
    @TableField
    private String regChannel;
    @TableField
    private Integer clientid;
    @TableField
    private Integer tenantId;
    @TableField
    private String statusCode;
    @TableField
    private Integer retryTimes;
    @TableField
    private Date createTime;
    @TableField
    private String createBy;
    @TableField
    private Date updateTime;
    @TableField
    private String updateBy;

}