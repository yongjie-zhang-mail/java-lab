package com.yj.lab.spring.controller;

import com.yj.lab.spring.config.aspect.anno.DemoAnno;
import com.yj.lab.spring.model.vo.request.HomeRequestVo;
import com.yj.lab.spring.model.vo.response.HomeResponseVo;
import com.yj.lab.spring.model.vo.response.common.ResultEntity;
import com.yj.lab.spring.service.biz.HomeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Zhang Yongjie
 */
@Slf4j
@RestController
@RequestMapping("v1/home")
@Api(tags = "Lab-Home-v1")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @GetMapping("get1")
    @ApiOperation("get1")
    public ResultEntity<HomeResponseVo> get(@RequestHeader(required = false, defaultValue = "1") Integer tenantId,
                                            @RequestParam(defaultValue = "1234") String userId) {
        HomeResponseVo responseVo = homeService.get(userId);
        return ResultEntity.getSuccessResult(responseVo);
    }

    @PostMapping("post1")
    @ApiOperation("post1")
    public ResultEntity<String> post(@RequestHeader(required = false, defaultValue = "1") Integer tenantId,
                                     @RequestBody HomeRequestVo homeRequestVo) {
        Boolean result = homeService.post(homeRequestVo);
        return ResultEntity.getSuccessResult(result.toString());
    }

    @DemoAnno(id = 200)
    @PostMapping("post2")
    @ApiOperation("post2")
    public ResultEntity<String> postAsyncGlobal(@RequestHeader(required = false, defaultValue = "1") Integer tenantId) {
        Boolean result = homeService.postAsyncGlobal();
        return ResultEntity.getSuccessResult(result.toString());
    }

    @PostMapping("post3")
    @ApiOperation("post3")
    public ResultEntity<String> postAsyncSome(@RequestHeader(required = false, defaultValue = "1") Integer tenantId,
                                              @RequestBody HomeRequestVo homeRequestVo) {
        Boolean result = homeService.postAsyncSome(homeRequestVo);
        return ResultEntity.getSuccessResult(result.toString());
    }


}

























