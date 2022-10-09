package com.yj.lab.spring.service.common.impl;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.function.Function;

/**
 * @author Zhang Yongjie
 */
@Slf4j
public class CommonLab {

    /**
     * 函数式接口 Function<T, R>
     * T - 入参 inpuT
     * R - 出参 Result
     */
    public static void testTemp() {
        Function<Integer, String> ft = CommonLab::functionTask;
    }

    public static String functionTask(Integer inputInteger) {
        long timing = commonTask();
        return String.format("input integer: %s, timing: %s", inputInteger, timing);
    }

    public static String callableTask() throws Exception {
        long timing = commonTask();
        LocalDateTime localDateTime = LocalDateTime.now();
        log.info("current time: {}, task cost: {} ms", localDateTime, timing);
        return String.valueOf(timing);
    }

    public static void runnableTask() {
        long timing = commonTask();
        log.info("time cost: {}", timing);
    }

    public static long commonTask() {
        long start = System.currentTimeMillis();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        return end - start;
    }

    private static void testLocalDateTime() {
        // 2021-08-20T11:11:03.705735500
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
        LocalTime localTime = LocalTime.now();
        System.out.println(localTime);
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate);
    }

    private static void testTimeSpan() {
        LocalDateTime localDateTime = LocalDateTime.now();
        long start = System.currentTimeMillis();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        long timeCost = end - start;
        String result = String.format("current time: %s, task cost: %s ms", localDateTime, timeCost);
        System.out.println(result);
    }


}
