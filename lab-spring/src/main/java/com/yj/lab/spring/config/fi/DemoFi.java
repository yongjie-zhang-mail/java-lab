package com.yj.lab.spring.config.fi;

import com.yj.lab.spring.model.vo.request.HomeRequestVo;

/**
 * @author zhangyj21
 */
@FunctionalInterface
public interface DemoFi {

    Boolean doTask(HomeRequestVo homeRequestVo);

}












