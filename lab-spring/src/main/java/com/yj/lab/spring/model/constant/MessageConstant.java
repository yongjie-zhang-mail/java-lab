package com.yj.lab.spring.model.constant;

public class MessageConstant {

    public static final String CODE_SUCCESS = "0";
    public static final String MESSAGE_SUCCESS = "success";

    public static final String CODE_ERROR = "1";
    public static final String MESSAGE_ERROR = "fail";

    /**
     * 第三方公共信息类
     */
    public static class ThirdMessageConstant {

        /**
         * 成功-字符串型，第三方接口返回 对应status_code & code
         */
        public static final String CODE_SUCCESS_STR_CODE = "200";

        /**
         * 成功-字符串型，第三方接口返回 对应ret
         */
        public static final String CODE_SUCCESS_STR_RET_CODE = "0";
        /**
         * 成功-int型，第三方接口返回
         */
        public static final int CODE_SUCCESS_RET_INT = 0;

        /**
         * 成功-整型，对应 restemplate
         */
        public static final int CODE_SUCCESS_INT = 200;

        /**
         * 成功-字符串型，第三方接口返回
         */
        public static final String CODE_SUCCESS_STR_MSG = "success";

        /**
         * 自定义第三方统一返回code
         */
        public static final String CODE_ERROR_STR_CODE = "500";
    }

}
