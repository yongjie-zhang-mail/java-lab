package com.yj.lab.spring.juc;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class ThreadLocalTests {

    static ThreadLocal<Integer> tlI = new ThreadLocal<>();

    public static void main(String[] args) {

        test1();

    }

    @SneakyThrows
    private static void test1() {

        Thread t1 = new Thread(() -> {
            log.info(String.valueOf(tlI.get()));
            tlI.set(0);
            log.info(String.valueOf(tlI.get()));
            tlI.remove();
            log.info(String.valueOf(tlI.get()));
        });

        Thread t2 = new Thread(() -> {
            log.info(String.valueOf(tlI.get()));
            tlI.set(1);
            log.info(String.valueOf(tlI.get()));
            tlI.remove();
            log.info(String.valueOf(tlI.get()));
        });

        t1.start();
        t1.join();
        t2.start();

    }


}



















