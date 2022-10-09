package com.yj.lab.spring.common;

import sun.misc.Signal;
import sun.misc.SignalHandler;

public class SigTests {

    public static void main(String[] args) {

        Signal.handle(new Signal("INT"), new SignalHandler() {
            @Override
            public void handle(Signal signal) {
                System.out.println(signal.getName() + " catched");
            }
        });

        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(123);
        }
    }
}
