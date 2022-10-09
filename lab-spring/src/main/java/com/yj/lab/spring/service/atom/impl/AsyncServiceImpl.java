package com.yj.lab.spring.service.atom.impl;

import com.yj.lab.spring.model.vo.request.HomeRequestVo;
import com.yj.lab.spring.service.atom.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author Zhang Yongjie
 */
@Slf4j
@Service
public class AsyncServiceImpl implements AsyncService {

    @Override
    @Async
    public void asyncGlobal() {
        log.info("异步方法，开始执行");
        long timing = commonTask();
        log.info("异步方法，结束执行，耗时 {} ms", timing);
    }

    @Override
    @Async("secondTp")
    public String doAsync(HomeRequestVo homeRequestVo) {
        log.info("异步方法，开始执行");
        long timing = commonTask();
        log.info("异步方法，结束执行，耗时 {} ms", timing);
        return "success";
    }

    private static long commonTask() {
        long start = System.currentTimeMillis();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        return end - start;
    }

}
