package com.yj.lab.spring.juc;

import com.yj.lab.spring.service.common.impl.ThreadPoolLab;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author zhangyj21
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class TpeTests2 {

    @Resource
    ThreadPoolLab threadPoolLab;

    @Test
    public void testTpeTaskService() {

        log.info("testTpeTaskService 开始执行");
        long start = System.currentTimeMillis();

        threadPoolLab.testServiceRef();

        long end = System.currentTimeMillis();
        log.info("testTpeTaskService 执行完成, time：{}", (end - start));

    }
}
