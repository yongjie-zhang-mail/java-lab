package com.yj.lab.spring.juc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.*;

/**
 * 线程池
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class TpeTests {

    @SneakyThrows
    public static void main(String[] args) {
        ExecutorService e1 = Executors.newCachedThreadPool();
        ExecutorService e2 = Executors.newFixedThreadPool(10);
        ExecutorService e3 = Executors.newSingleThreadExecutor();

        ThreadPoolExecutor e = new ThreadPoolExecutor(10, 20, 10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10));
        // TP执行任务
//        tpe01(e1);

        // 不同TP执行任务
//        tpe02(e1);
//        tpe02(e2);
//        tpe02(e3);

        // ThreadPoolExecutor，线程数超过最大值后，新任务不再能执行了；
//        tpeReject(e);

        // 测试 CachedThreadPool
//        testCachedTP(e1);

        // 测试 FixedThreadPool
//        testFixedTp(e2);


    }

    private static void testFixedTp(ExecutorService e2) throws InterruptedException {
        tpe02(e2);
        Thread.sleep(13000);
        tpe03(e2);
    }

    private static void testCachedTP(ExecutorService e1) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            e1.execute(new TpeTask(i));
        }
        // 线程池中的线程，过期后都已销毁
        Thread.sleep(70000);

        for (int i = 10; i < 20; i++) {
            e1.execute(new TpeTask(i));
        }
    }

    private static void tpeReject(ThreadPoolExecutor e) throws InterruptedException {
        tpe02(e);
        Thread.sleep(5000);
        tpe03(e);
    }

    private static void tpe03(ExecutorService e) {
        for (int i = 101; i < 200; i++) {
            e.execute(new TpeTask(i));
        }
    }

    private static void tpe02(ExecutorService e) {
        for (int i = 0; i < 100; i++) {
            e.execute(new TpeTask(i));
        }
    }

    private static void tpe01(ExecutorService e) {
        e.execute(new TpeTask(1));
    }

    @Data
    @AllArgsConstructor
    static class TpeTask implements Runnable {
        private int i;

        @SneakyThrows
        @Override
        public void run() {
            log.info(Thread.currentThread().getName() + "----" + i);
            Thread.sleep(1000);
        }
    }
}
