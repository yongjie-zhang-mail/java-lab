package com.yj.lab.spring.model.rdb.entity.primary;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Zhang Yongjie
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("modular_extract_group")
public class ModularExtractGroup implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -4317663327718423168L;

    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 创建人
     */
    @TableField(value = "create_uid")
    private String createUid;

    /**
     *
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 群组中文名
     */
    @TableField(value = "name_cn")
    private String nameCn;

    /**
     * 群组英文名
     */
    @TableField(value = "name_en")
    private String nameEn;

    /**
     * 用户数量
     */
    @TableField(value = "user_count")
    private Integer userCount;

    /**
     * 0:等待审核,1: 审核成功,2:等待验证,3:等待发送4:发送成功5:审核未通过,6:修改
     */
    private Integer status;

    /**
     * 语言
     */
    private String language;

    /**
     * 类型 1 手动新建 2固定人群 3文件上传 4地图圈选
     */
    @TableField(value = "group_type")
    private Integer groupType;

    /**
     * 主要用于summary记录生成后的时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 导出类型 1 画像 2 广告投放
     */
    @TableField(value = "export_type")
    private Integer exportType;

    /**
     * 导出进度 0 未开始 1 es查询 2 Hbase查询 3 保存 4 成功
     */
    @TableField(value = "export_value")
    private Integer exportValue;

    /**
     * 匹配到的数量最大的人群类别编号
     */
    @TableField(value = "max_tag")
    private String maxTag;

    /**
     * json化条件  用于定时任务跑人群画像
     */
    @TableField(value = "modular_condition")
    private String modularCondition;

    /**
     * 保存手动新建拉通的活动
     */
    @TableField(value = "modular_campaign")
    private String modularCampaign;

    /**
     * 联系人
     */
    @TableField(value = "group_id")
    private Integer groupId;
    @TableField(value = "phone_count")
    private Integer phoneCount;
    @TableField(value = "email_count")
    private Integer emailCount;
    @TableField(exist = false)
    private Integer snCount;
    @TableField(exist = false)
    private Integer imeiCount;

    @TableField(exist = false)
    private Integer wechatidCount;

    /**
     * 画像类型 0 GUCP 1 CDP 2 会员中心
     */
    private Integer type;

    /**
     * 画像权限 1 只在CDP人群选择下看到该画像 2 在CDP人群选择和产品画像下都可以看到该画像
     */
    @TableField(value = "portrait_authority")
    private Integer portraitAuthority;

    /**
     * 是否公开画像 0 不公开 1 公开
     */
    @TableField(value = "open_portrait")
    private Integer openPortrait;

    /**
     * 页面显示画像的模块
     */
    @TableField(value = "modules")
    private String modulesAsString;

    /**
     * 处理进度0未处理，1处理中，2处理完成
     */
    @TableField(value = "operate_status")
    private Integer operateStatus;

    /**
     *
     */
    @TableField(value = "push_type")
    private String pushType;

    /**
     *
     */
    @TableField(value = "member_detail_id")
    private Long memberDetailId;


    /**
     * 是否是短信需求创建的 0不是 1是
     */
    @TableField(value = "is_require")
    private Integer isRequire;
    @TableField(exist = false)
    private Integer open;
    @TableField(exist = false)
    private List<String> itCodes;
    @TableField(exist = false)
    private List<String> modules = Lists.newArrayList(); // 用户群的模块
    /**
     * 模板ID
     */
    @TableField(value = "template_id")
    private Integer templateId;

    public ModularExtractGroup(int id, int status) {
        this.id = id;
        this.status = status;
    }

    public String getName(String language) {
        return getNameCn();
    }

}
