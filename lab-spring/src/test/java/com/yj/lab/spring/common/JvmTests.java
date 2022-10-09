package com.yj.lab.spring.common;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class JvmTests {

    public static void main(String[] args) {

        List<Integer> list = new ArrayList<>();
        int i = 0;
        while (true) {
//            test1();
            list.add(i++);
        }

    }

    private static void test1() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//            System.out.println(123);
        log.info("123");
        Object o = new Object();
    }

}
