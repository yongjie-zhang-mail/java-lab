package com.yj.lab.spring;

import com.yj.lab.spring.model.enums.ReturnCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zhang Yongjie
 */

@SpringBootTest
class LabApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void test1() {

        Object o = new Object();
        List<Object> list = new ArrayList<>();

        Assertions.assertNotNull(o, "对象为空");

        Assert.isTrue(true, "值为 false");

        Assert.notNull(o, "对象为空");

        Assert.notEmpty(list, "列表为空");

        org.junit.Assert.assertNotNull("对象为空", o);

        ReturnCode.FAIL.assertTrue(false, "aa", "bb", "cc");


    }


}


















