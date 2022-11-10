package com.yj.lab.spring.service.common.impl;

import com.yj.lab.spring.service.common.BizService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author zhangyj21
 */
@Slf4j
@Service
public class BizServiceImpl implements BizService {

    @SneakyThrows
    @Override
    public void bizLogic() {
        TimeUnit.SECONDS.sleep(5);
    }


}
