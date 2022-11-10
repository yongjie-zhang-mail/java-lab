package com.yj.lab.spring.service.common.impl;

import com.yj.lab.spring.config.common.MyBeanContext;
import com.yj.lab.spring.service.common.BizService;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

/**
 * @author zhangyj21
 */
@Slf4j
public class RunnableTask implements Runnable {

    private CountDownLatch cdl;

    public RunnableTask(CountDownLatch cdl) {
        this.cdl = cdl;
    }

    @Override
    public void run() {
        log.info(Thread.currentThread().getName());
        BizService bizService = MyBeanContext.getApplicationContext().getBean(BizService.class);
        bizService.bizLogic();
        cdl.countDown();
        long count = cdl.getCount();
        log.info("count: {}", count);
    }


}
