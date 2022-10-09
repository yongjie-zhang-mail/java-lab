//package com.yj.lab.spring.db;
//
//import com.alibaba.fastjson.JSON;
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.yj.lab.spring.model.enums.GroupOperateStatus;
//import com.yj.lab.spring.model.enums.GroupType;
//import com.yj.lab.spring.model.rdb.entity.primary.ModularExtractGroup;
//import com.yj.lab.spring.model.rdb.entity.second.User;
//import com.yj.lab.spring.model.rdb.mapper.primary.ModularExtractGroupMapper;
//import com.yj.lab.spring.model.rdb.mapper.second.UserMapper;
//import com.yj.lab.spring.model.vo.response.common.PageInfo;
//import com.yj.lab.spring.service.atom.impl.ModularExtractGroupServiceImpl;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.List;
//
//@Slf4j
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class MpTests {
//
//    @Autowired
//    private ModularExtractGroupMapper mapper;
//    @Autowired
//    private ModularExtractGroupServiceImpl service;
//    @Autowired
//    private UserMapper userMapper;
//
//    @Test
//    public void testSelect() {
//        Integer modularId = 2626;
//        ModularExtractGroup modularExtractGroup = mapper.selectById(modularId);
//        log.info(JSON.toJSONString(modularExtractGroup));
//    }
//
//    @Test
//    public void testWrapper() {
//        LambdaQueryWrapper<ModularExtractGroup> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(ModularExtractGroup::getGroupType, GroupType.GROUP_CHOOSE.getCode());
//        wrapper.eq(ModularExtractGroup::getOperateStatus, GroupOperateStatus.DONE.getCode());
//        wrapper.orderByDesc(ModularExtractGroup::getCreateTime);
//        List<ModularExtractGroup> groups = mapper.selectList(wrapper);
////        List<ModularExtractGroup> groups2 = service.list(wrapper);
//    }
//
//    @Test
//    public void testPage() {
//        LambdaQueryWrapper<ModularExtractGroup> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(ModularExtractGroup::getGroupType, GroupType.GROUP_CHOOSE.getCode());
//        wrapper.eq(ModularExtractGroup::getOperateStatus, GroupOperateStatus.DONE.getCode());
//
//        Page<ModularExtractGroup> page = new Page<>(1, 2);
//        Page<ModularExtractGroup> pageGroups = mapper.selectPage(page, wrapper);
//    }
//
//    @Test
//    public void testPage2() {
//
//        long pageNum = 3;
//        long pageSize = 10;
//        IPage<ModularExtractGroup> pageCondition = new Page<>(pageNum, pageSize);
//
//        LambdaQueryWrapper<ModularExtractGroup> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(ModularExtractGroup::getGroupType, GroupType.GROUP_CHOOSE.getCode());
//        wrapper.eq(ModularExtractGroup::getOperateStatus, GroupOperateStatus.DONE.getCode());
//        wrapper.orderByDesc(ModularExtractGroup::getCreateTime);
//
//        IPage<ModularExtractGroup> list = mapper.selectPage(pageCondition, wrapper);
//        PageInfo<ModularExtractGroup> result = new PageInfo<>(list);
//
//    }
//
//    /**
//     * SELECT * FROM `user` WHERE tenant_id = 25 ORDER BY create_time DESC LIMIT 20, 10
//     */
//    @Test
//    public void testSecondPage() {
//
//        long pageNum = 3;
//        long pageSize = 10;
//        IPage<User> pageCondition = new Page<>(pageNum, pageSize);
//
//        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(User::getTenantId, 25);
//        wrapper.orderByDesc(User::getCreateTime);
//
//        IPage<User> list = userMapper.selectPage(pageCondition, wrapper);
//        PageInfo<User> result = new PageInfo<>(list);
//
//
//    }
//
//
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
