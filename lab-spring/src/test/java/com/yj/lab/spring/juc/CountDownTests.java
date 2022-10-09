package com.yj.lab.spring.juc;

import com.alibaba.fastjson.JSON;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Phaser;

/**
 * 计数器，循环屏障，阶段器
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class CountDownTests {

    public static void main(String[] args) {

//        test1();
//        test2();
//        getPhaserInfo();
        test4();

    }

    @SneakyThrows
    private static void test4() {

        Phaser p = new Phaser();
        log.info(JSON.toJSONString(p));
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(() -> {
                try {
                    p.register();
                    Thread.sleep((long) (Math.random() * 3000));
                    log.info(Thread.currentThread().getName() + "玩家准备就绪");
//                    p.arriveAndAwaitAdvance();
                    p.arrive();
                    log.info(JSON.toJSONString(p));
                    log.info(Thread.currentThread().getName() + "选择英雄");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            t.start();
        }
        p.awaitAdvance(p.getPhase());
        log.info(p.getPhase() + "号房间游戏开始");
//        p.awaitAdvance(p.getPhase());
//        log.info(p.getPhase() + "号房间游戏开始");

    }

    @SneakyThrows
    private static void test3() {
        Phaser p = new Phaser(5);
        log.info(JSON.toJSONString(p));
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(() -> {
                try {
                    Thread.sleep((long) (Math.random() * 3000));
                    log.info(Thread.currentThread().getName() + "玩家准备就绪");
//                    p.arriveAndAwaitAdvance();
                    p.arrive();
                    log.info(JSON.toJSONString(p));
                    log.info(Thread.currentThread().getName() + "选择英雄");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            t.start();
        }
        p.awaitAdvance(p.getPhase());
        log.info(p.getPhase() + "号房间游戏开始");
        p.awaitAdvance(p.getPhase());
        log.info(p.getPhase() + "号房间游戏开始");

    }

    private static void getPhaserInfo() {
        Phaser p = new Phaser(5);
        String s = JSON.toJSONString(p);
//        String s = p.toString();
        log.info(s);
    }

    @SneakyThrows
    private static void test2() {
        CyclicBarrier cb = new CyclicBarrier(5);
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(() -> {
                try {
                    Thread.sleep((long) (Math.random() * 3000));
                    log.info(Thread.currentThread().getName() + "玩家准备就绪");
                    cb.await();
                    log.info(Thread.currentThread().getName() + "选择英雄");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            t.start();
        }

//        log.info("游戏开始");
    }

    @SneakyThrows
    private static void test1() {
        CountDownLatch cdl = new CountDownLatch(5);
        for (int i = 0; i < 5; i++) {
            Thread t = new Thread(() -> {
                try {
                    Thread.sleep((long) (Math.random() * 3000));
                    log.info(Thread.currentThread().getName() + "玩家准备就绪");
                    cdl.countDown();
                    log.info(Thread.currentThread().getName() + "选择英雄");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            t.start();
        }
        cdl.await();
        log.info("游戏开始");
    }


}
