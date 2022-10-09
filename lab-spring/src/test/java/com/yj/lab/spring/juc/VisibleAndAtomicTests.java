package com.yj.lab.spring.juc;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * 可见性和原子性
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class VisibleAndAtomicTests {

    /**
     * synchronized
     */
    static int numAtomic3 = 0;
    /**
     * atomic
     */
    static AtomicInteger numAtomic2 = new AtomicInteger(0);
    static volatile int numAtomic1 = 0;
    /**
     * volatile
     */
    static volatile boolean flag = true;
    static AtomicInteger num2 = new AtomicInteger(0);
    static int num = 0;

    public static void main(String[] args) {
//        testSync1();
//        nonSync1();
//        sync3();
//        volatile1();
//        nonsafeAtomic1();
//        safeAtomic1();
        safeAtomic2();
    }

    @SneakyThrows
    private static void safeAtomic2() {

        for (int i = 0; i < 10000; i++) {
            Thread t1 = new Thread(() -> {
                synchronized (VisibleAndAtomicTests.class) {
                    numAtomic3++;
                }
            });
            t1.start();

            Thread t2 = new Thread(() -> {
                synchronized (VisibleAndAtomicTests.class) {
                    numAtomic3++;
                }
            });
            t2.start();
        }

        Thread.sleep(1000);
        log.info(String.valueOf(numAtomic3));

    }

    @SneakyThrows
    private static void safeAtomic1() {
        for (int i = 0; i < 10000; i++) {
            Thread t1 = new Thread(() -> {
                numAtomic2.getAndAdd(1);
            });
            t1.start();

            Thread t2 = new Thread(() -> {
                numAtomic2.getAndAdd(1);
            });
            t2.start();
        }

        Thread.sleep(1000);
        log.info(String.valueOf(numAtomic2));
    }

    @SneakyThrows
    private static void nonsafeAtomic1() {

        for (int i = 0; i < 10000; i++) {
            Thread t1 = new Thread(() -> {
                numAtomic1++;
            });
            t1.start();

            Thread t2 = new Thread(() -> {
                numAtomic1++;
            });
            t2.start();
        }

        Thread.sleep(1000);
        log.info(String.valueOf(numAtomic1));
    }

    @SneakyThrows
    private static void volatile1() {
        log.info(String.valueOf(flag));
        Thread t = new Thread(() -> {
            while (flag) {
//                log.info(String.valueOf(flag));
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        });
        t.start();
        Thread.sleep(5000);
        flag = false;
        log.info(String.valueOf(flag));

    }

    private static void sync3() {
        for (int i = 0; i < 3; i++) {
            Thread t = new Thread(() -> {
                while (num2.get() < 1000) {
                    log.info("num: " + num2.incrementAndGet());
                }
            });
            t.start();
        }
    }

    private static void nonSync1() {
        for (int i = 0; i < 3; i++) {
            Thread t = new Thread(() -> {
                while (num < 1000) {
                    log.info("num:" + num++);
                }
            });
            t.start();
        }
    }

    private static void sync2() {
        for (int i = 0; i < 3; i++) {
            Thread t = new Thread(() -> {
                synchronized (VisibleAndAtomicTests.class) {
                    while (num < 1000) {
                        log.info("num:" + num++);
                    }
                }
            });
            t.start();
        }
    }

    private static void testSync1() {
        TestSync testSync = new TestSync();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                testSync.test();
            }
        });

        Thread t2 = new Thread(testSync::test);

        t1.start();
        t2.start();
    }

    public static class TestSync {
        private int num = 0;

        public void test() {
            for (int i = 0; i < 1000; i++) {
                synchronized (this) {
                    log.info("thread:" + Thread.currentThread().getId() + ", num:" + num++);
                }
            }
        }
    }


}
