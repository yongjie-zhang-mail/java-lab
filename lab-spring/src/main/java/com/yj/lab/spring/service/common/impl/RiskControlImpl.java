//package com.yj.lab.spring.service.common.impl;
//
//import com.yj.lab.spring.config.aspect.anno.DemoAnno;
//import com.yj.lab.spring.model.constant.CommonConstant;
//import com.yj.lab.spring.model.enums.ReturnCode;
//import com.yj.lab.spring.service.common.RiskControl;
//import com.yj.lab.spring.util.redis.RedisUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@Slf4j
//@Service
//public class RiskControlImpl implements RiskControl {
//
//    @Value("${black-list.count}")
//    private int blackCount;
//    @Value("${black-list.time}")
//    private int blackTime;
//
//    @Autowired
//    private RedisUtil redisUtil;
//
//    @Override
//    public void commonRiskControl(HttpServletRequest request, HttpServletResponse response, DemoAnno demoAnno) {
//
//        long start = System.currentTimeMillis();
//
//        // 获取参数
//        // header, token
//        String token = request.getHeader("token");
//        // 获取注解配置参数
//        int id = demoAnno.id();
//        // 获取cookie
//        Cookie[] cookies = request.getCookies();
//        // 获取parameter
//        String userid = request.getParameter("userid");
//
//        // 设置Cookie
//        Cookie cookie = new Cookie("token", "token-value");
//        cookie.setMaxAge(60 * 60);
//        cookie.setPath("/");
//        response.addCookie(cookie);
//
//        // 计时
//        log.info("风控 - 用户黑名单耗时: {} ms", System.currentTimeMillis() - start);
//
//    }
//
//    @Override
//    public void blackList(HttpServletRequest request) {
//        long start = System.currentTimeMillis();
//        // parameter
//        String userid = request.getParameter("userid");
//        // 风控 - 黑名单
//        String blackKey = CommonConstant.Redis.BLACK_USER_KEY.concat(userid);
//        boolean exists = redisUtil.exists(blackKey, RedisUtil.RedisDbIndex.RISK_LIMIT_DB);
//        // 若在黑名单里存在，则抛出异常
//        ReturnCode.RISK_CONTROL_BLACK.assertFalse(exists);
//        // 用户限制Key，+URI
//        String requestUri = request.getRequestURI();
//        String userLimitKey = CommonConstant.Redis.USER_LIMIT_KEY.concat(userid).concat(":").concat(requestUri);
//        // +1
//        long count = redisUtil.incrBy(userLimitKey, 1, RedisUtil.RedisDbIndex.RISK_LIMIT_DB);
//        // 第一次设置过期时间，时间窗口
//        if (count == 1) {
//            redisUtil.expire(userLimitKey, blackTime, RedisUtil.RedisDbIndex.RISK_LIMIT_DB);
//        }
//        // 若单位时间访问次数 > 黑名单次数设置，则加入黑名单；30分钟后自动解锁（失效）
//        if (count > blackCount) {
//            redisUtil.setex(blackKey, CommonConstant.Redis.BLACK_KEY_TIME, String.valueOf(count), RedisUtil.RedisDbIndex.RISK_LIMIT_DB);
//            log.warn("用户：{}访问url：{}超过限制，已加入黑名单，过期时间：{}", userid, requestUri, CommonConstant.Redis.BLACK_KEY_TIME);
//        }
//        // 计时
//        log.info("风控 - 用户黑名单耗时: {} ms", System.currentTimeMillis() - start);
//    }
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
