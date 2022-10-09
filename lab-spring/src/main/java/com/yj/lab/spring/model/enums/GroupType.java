package com.yj.lab.spring.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GroupType {

    /**
     * 人群圈选
     */
    GROUP_CHOOSE(10, "人群圈选"),
    /**
     * 联系人组上传(merge id)
     */
    UPLOAD(11, "联系人组上传(merge id)"),
    /**
     * 纯联系人组上传
     */
    CROWD_UPLOAD(21, "纯联系人组上传");

    private final int code;
    private final String name;


}
