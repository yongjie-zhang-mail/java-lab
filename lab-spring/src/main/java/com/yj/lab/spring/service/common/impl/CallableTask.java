package com.yj.lab.spring.service.common.impl;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.Callable;

/**
 * @author Zhang Yongjie
 * 构建任务
 * 第0种实现方法，构造类，实现函数式接口 Callable<T>
 * 正统实现函数式接口的方式，功能最为强大，可有自有字段
 */
@Slf4j
public class CallableTask implements Callable<String> {
    protected final int id;

    public CallableTask(int id) {
        this.id = id;
    }

    @Override
    public String call() throws Exception {
        log.info("start CallableTask: {}", id);
        int i = new Random().nextInt(10);
        Thread.sleep(i);
        log.info("end CallableTask: {}", id);
        return String.valueOf(i);
    }
}
