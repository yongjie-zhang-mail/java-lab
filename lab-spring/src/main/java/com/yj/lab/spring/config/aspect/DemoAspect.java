//package com.yj.lab.spring.config.aspect;
//
//import com.yj.lab.spring.config.aspect.anno.DemoAnno;
//import com.yj.lab.spring.service.common.RiskControl;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@Slf4j
//@Aspect
//@Component
//public class DemoAspect {
//
//    @Autowired
//    private RiskControl riskControl;
//
//    @Before("@annotation(demoAnno)")
//    public void demoCheck(JoinPoint jp, DemoAnno demoAnno) {
//
//        ServletRequestAttributes sras = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        assert sras != null;
//        HttpServletRequest request = sras.getRequest();
//        HttpServletResponse response = sras.getResponse();
//
//        riskControl.blackList(request);
//
//
//    }
//
//
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
