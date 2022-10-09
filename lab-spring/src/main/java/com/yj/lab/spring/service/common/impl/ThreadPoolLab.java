package com.yj.lab.spring.service.common.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.yj.lab.spring.service.common.impl.CommonLab.commonTask;

/**
 * @author Zhang Yongjie
 */
@Slf4j
public class ThreadPoolLab {

    // 构造线程池
    static ThreadPoolExecutor tp = new ThreadPoolExecutor(8, 16, 60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(512),
            new ThreadFactory() {
                private final AtomicInteger t = new AtomicInteger(1);

                @Override
                public Thread newThread(@NotNull Runnable runnable) {
                    return new Thread(runnable, "myTp-" + t.getAndIncrement());
                }
            }, new ThreadPoolExecutor.CallerRunsPolicy());

    public static void main(String[] args) {
//        testRunnable();
//        testCallable();
        testInvokeAll();
    }

    private static void testInvokeAll() {
        List<Callable<String>> tasks = new ArrayList<>();
        List<Future<String>> futures = null;
        List<String> results = new ArrayList<>();

        // 构建 Callable<T> taskList
        for (int i = 0; i < 100; i++) {
            tasks.add(new CallableTask(i));
        }
        // 将taskList 提交到线程池
        try {
            futures = tp.invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 获取task返回结果
        if (CollectionUtils.isNotEmpty(futures)) {
            boolean b = futures.stream().allMatch(Future::isDone);

            if (b) {
                for (Future<String> f : futures) {
                    String result;
                    try {
                        result = f.get();
                        results.add(result);
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        // 输出返回结果
        for (String r : results) {
            log.info(r);
        }

        log.info("main thread");
    }

    private static void testCallable() {
        // 构建任务
        // 第1种实现方法，函数式接口，Callable<T>
        Callable<String> task = CommonLab::callableTask;
        // 第2种实现方法，匿名类
        Callable<String> tempTask1 = new Callable<String>() {
            @Override
            public String call() throws Exception {
                long l = commonTask();
                return String.format("time cost %s", l);
            }
        };
        // 第3种实现方法，Lambda
        Callable<String> tempTask2 = () -> {
            long l = commonTask();
            return String.format("time cost %s", l);
        };
        // 将任务提交到线程池，submit Callable<T>；返回 return Future<T>
        Future<String> future = tp.submit(task);
        // 主线程获取 线程池中线程 执行任务后 返回的结果
        try {
            String result = future.get();
            log.info(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private static String callableTask() throws Exception {
//        long timing = commonTask();
//        LocalDateTime localDateTime = LocalDateTime.now();
//        log.info("current time: {}, task cost: {} ms", localDateTime, timing);
//        return String.valueOf(timing);
//    }

    private static void testRunnable() {
        // 构建函数式接口，Runnable，使用Lambda方式；作为任务；
        Runnable task = CommonLab::runnableTask;
        // 提交任务到线程池执行
        tp.execute(task);
    }


//    private static long commonTask() {
//        long start = System.currentTimeMillis();
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        long end = System.currentTimeMillis();
//        return end - start;
//    }


}
