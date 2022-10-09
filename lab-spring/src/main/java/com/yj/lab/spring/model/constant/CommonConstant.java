package com.yj.lab.spring.model.constant;

/**
 * @author zhangyj21
 * 通用 Constant
 */
public class CommonConstant {

    /**
     * 切面使用到的常量
     */
    public static class Aspect {

        public static final String TOKEN = "token";

        public static final String USER_ID = "userid";

    }


    /**
     * Redis Key
     */
    public static class Redis {

        /**
         * 用户黑名单 key
         */
        public static final String BLACK_USER_KEY = "black_user:";
        /**
         * 黑名单过期时间, 30分钟
         */
        public static final int BLACK_KEY_TIME = 60 * 30;

        /**
         * 风控Key - 用户
         */
        public static final String USER_LIMIT_KEY = "user_limit:";

    }


}














