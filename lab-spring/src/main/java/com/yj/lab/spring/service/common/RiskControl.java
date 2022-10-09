package com.yj.lab.spring.service.common;

import com.yj.lab.spring.config.aspect.anno.DemoAnno;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface RiskControl {

    void commonRiskControl(HttpServletRequest request, HttpServletResponse response, DemoAnno demoAnno);

    void blackList(HttpServletRequest request);
}
