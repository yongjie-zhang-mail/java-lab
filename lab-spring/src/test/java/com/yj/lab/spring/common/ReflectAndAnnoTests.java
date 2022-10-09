package com.yj.lab.spring.common;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class ReflectAndAnnoTests {

    public static void main(String[] args) {
        Person p = new Person();
        p.age = 22;

        test1(p);

    }

    @SneakyThrows
    private static void test1(Object obj) {

        Class<?> c = obj.getClass();

        Field ageField = c.getDeclaredField("age");
        int age = ageField.getInt(obj);
        log.info(String.valueOf(age));
        MyAnno anno = ageField.getDeclaredAnnotation(MyAnno.class);
        String name = anno.name();
        log.info(name);

        Method m1 = c.getDeclaredMethod("sayHello");
        m1.invoke(obj);

        Method m3 = c.getDeclaredMethod("sayHello2", String.class);
        Object result = m3.invoke(obj, "aa");
        log.info(result.toString());

        boolean b = c.isAnnotationPresent(MyAnno.class);
        log.info(String.valueOf(b));


    }

    @MyAnno
    static class Person {

        @MyAnno(name = "cc")
        int age;

        @MyAnno(name = "bb")
        public void sayHello() {
            log.info("hello");
        }

        public Boolean sayHello2(String name) {
            log.info("hello, " + name);
            return true;
        }

    }
}

