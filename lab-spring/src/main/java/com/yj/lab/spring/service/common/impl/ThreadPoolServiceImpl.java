package com.yj.lab.spring.service.common.impl;

import com.yj.lab.spring.service.common.ThreadPoolService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author Zhang Yongjie
 */
@Slf4j
@Service
public class ThreadPoolServiceImpl implements ThreadPoolService {

    @Resource(name = "secondTp")
    private ThreadPoolTaskExecutor secondTp;

    @Override
    public void testTp() {
//        testTpteSubmit();
        testTpteInvokeAll();

    }

    @SneakyThrows
    private void testServiceRef() {

        log.info("开始");

        // 获取tpe
        ThreadPoolExecutor tp2 = secondTp.getThreadPoolExecutor();

        CountDownLatch cdl = new CountDownLatch(10);

        for (int i = 0; i < 10; i++) {
            Runnable task = new RunnableTask(cdl);
            secondTp.execute(task);
            TimeUnit.SECONDS.sleep(3);
        }

        cdl.await();

        log.info("完成");

    }

    private void testTpteInvokeAll() {
        // 获取tpe
        ThreadPoolExecutor tp2 = secondTp.getThreadPoolExecutor();
        // 实现Callable的实体类 List
        List<Callable<String>> tasks = new ArrayList<>();
        // 执行返回的Future结果
        List<Future<String>> futures = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            tasks.add(new CallableTask(i));
        }
        // 线程池批量提交 Callable 任务
        try {
            futures = tp2.invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 阻塞，获取返回结果
        boolean done = futures.stream().allMatch(Future::isDone);
        if (done) {
            String s;
            for (Future<String> f : futures) {
                try {
                    s = f.get();
                    log.info("result is: {}", s);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void testTpteSubmit() {
        Future<String> f = secondTp.submit(CommonLab::callableTask);
        String result = null;
        try {
            result = f.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        log.info("result is: {}", result);
    }


}
