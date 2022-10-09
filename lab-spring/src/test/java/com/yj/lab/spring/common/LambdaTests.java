package com.yj.lab.spring.common;

import com.yj.lab.spring.config.fi.DemoFi;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zhangyj21
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class LambdaTests {

    @Test
    public void test1() {

        DemoFi demoFi = (a) -> {
            Boolean result = true;
            return result;
        };


    }


}
