package com.yj.lab.spring.juc;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 同步，锁，等待&唤醒，中断
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class SyncAndLockTests {

    static Object obj = new Object();
    static Lock lock = new ReentrantLock();
    static Condition c1 = lock.newCondition();
    static Condition c2 = lock.newCondition();

    @SneakyThrows
    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
//            test6();
//            test7();
//            test8();
//            test9();
            test10();
        }
//        test6Notify();
//        test7Signal();
//        test8Signal();
    }

    @SneakyThrows
    private static void test10() {
        Thread t = new Thread(() -> {
            lock.lock();
            try {
                // 中断 await
//                c1.await();
                log.info("hello");
                // 中断 自行处理
                while (!Thread.currentThread().isInterrupted()) {

                }
                // 中断 sleep
//                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });
        t.start();
//        Thread.sleep(5000);
        t.interrupt();

    }

    @SneakyThrows
    private static void test9() {
        Thread t = new Thread(() -> {
            lock.lock();
            try {
                log.info("hello");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });
        t.start();
        // 等待t执行完成，主线程再继续
        t.join();
        log.info("end");
    }

    @SneakyThrows
    private static void test8Signal() {
        Thread.sleep(5000);
        lock.lock();
        try {
            c1.signalAll();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            lock.unlock();
        }

        Thread.sleep(5000);
        lock.lock();
        try {
            c2.signalAll();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            lock.unlock();
        }
    }

    private static void test8() {
        Thread t = new Thread(() -> {
            lock.lock();
            try {
                c1.await();
                log.info("hello");
                c2.await();
                log.info("hello2");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });
        t.start();
    }

    @SneakyThrows
    private static void test7Signal() {
        Thread.sleep(5000);
        lock.lock();
        try {
            c1.signalAll();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            lock.unlock();
        }
    }

    private static void test7() {
        Thread t = new Thread(() -> {
            lock.lock();
            try {
                c1.await();
                log.info("hello");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        });
        t.start();
    }

    private static void test6Notify() throws InterruptedException {
        Thread.sleep(2000);
        synchronized (obj) {
            obj.notify();
        }

        Thread.sleep(5000);
        synchronized (obj) {
            obj.notifyAll();
        }
    }

    private static void test6() {
        Thread t = new Thread(() -> {
            synchronized (obj) {
                try {
                    obj.wait();
                    log.info("hello");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    private static void test5() {
        Thread t = new Thread(() -> {

            boolean b = false;

            try {
                b = lock.tryLock(3500, TimeUnit.MILLISECONDS);
                if (b) {
                    log.info("hello");
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (b) {
                    lock.unlock();
                }
            }

        });
        t.start();
    }

    private static void test4() {
        Thread t = new Thread(() -> {

            boolean b = false;
            try {
                b = lock.tryLock();
                if (b) {
                    log.info("hello");
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (b) {
                    lock.unlock();
                }
            }

        });
        t.start();
    }


    private static void test3() {
        Thread t = new Thread(() -> {

            lock.lock();
            try {
                log.info("hello");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        });
        t.start();
    }


    private static void test2() {
        Thread t = new Thread(() -> {

            synchronized (obj) {
                log.info("hello");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
        t.start();
    }

    private static void test1() {
        Thread t = new Thread(() -> {

//            synchronized (obj) {
            log.info("hello");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            }

        });
        t.start();
    }

}




























































