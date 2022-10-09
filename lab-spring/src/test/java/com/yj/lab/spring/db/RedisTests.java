//package com.yj.lab.spring.db;
//
//import com.alibaba.fastjson.JSON;
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.yj.lab.spring.model.rdb.entity.primary.ModularExtractGroup;
//import com.yj.lab.spring.model.rdb.mapper.primary.ModularExtractGroupMapper;
//import com.yj.lab.spring.util.redis.RedisUtil;
//import lombok.Data;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Random;
//import java.util.stream.Collectors;
//
//@Slf4j
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class RedisTests {
//
//    private static final String REDIS_GROUP_KEY = "modular_extract_group:";
//    private static final String REDIS_MODULAR_ID = "2796";
//    private static final Integer INTEGER_MODULAR_ID = 2796;
//    private static final String REDIS_GROUP_LIST_KEY = "group_list";
//
//    @Autowired
//    private RedisUtil redisUtil;
//    @Autowired
//    private ModularExtractGroupMapper mapper;
//
//    private static GroupVo map(ModularExtractGroup group) {
//
//        GroupVo groupVo = new GroupVo();
//        BeanUtils.copyProperties(group, groupVo);
//        groupVo.setName(group.getNameCn());
//        return groupVo;
//    }
//
//    @Test
//    public void testRedisQueryList() {
//
//        List<ModularExtractGroup> groups = getRedisGroups();
//
//        List<GroupVo> groupVos = groups.stream().map(RedisTests::map).collect(Collectors.toList());
//        log.info("Groups is {}", groupVos);
//        log.info("Groups is {}", JSON.toJSONString(groupVos));
//
//    }
//
//    private List<ModularExtractGroup> getRedisGroups() {
//        List<ModularExtractGroup> groups;
//
//        String key = REDIS_GROUP_LIST_KEY;
//        String value = redisUtil.get(key, RedisUtil.RedisDbIndex.BUSINESS_DATA_DB);
//        if (StringUtils.isBlank(value)) {
//            // 查询RDB
//            groups = getDbGroups();
//            // 存入Redis缓存
//            redisUtil.setex(key, RedisUtil.ExpireTime.THIRTY_MINUTE, JSON.toJSONString(groups), RedisUtil.RedisDbIndex.BUSINESS_DATA_DB);
//        } else {
//            groups = JSON.parseArray(value, ModularExtractGroup.class);
//        }
//        return groups;
//    }
//
//    private List<ModularExtractGroup> getDbGroups() {
//        List<ModularExtractGroup> groups;
//        LambdaQueryWrapper<ModularExtractGroup> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(ModularExtractGroup::getGroupType, 10);
//        wrapper.eq(ModularExtractGroup::getOperateStatus, 2);
//        wrapper.orderByDesc(ModularExtractGroup::getCreateTime);
//        wrapper.in(ModularExtractGroup::getId, Arrays.asList(2632, 2631, 2630));
//        groups = mapper.selectList(wrapper);
//        return groups;
//    }
//
//    @Test
//    public void testRedisUpdate() {
//
//        String key = REDIS_GROUP_KEY.concat(REDIS_MODULAR_ID);
//        queryGroupVo(key);
//        updateRedisGroup(key);
//        queryGroupVo(key);
//
//    }
//
//    private void queryGroupVo(String key) {
//        ModularExtractGroup group;
//        group = getRedisGroup(key);
//        GroupVo groupVo = map(group);
//        log.info("Groups is {}", JSON.toJSONString(groupVo));
//    }
//
//    private void updateRedisGroup(String key) {
//        LambdaQueryWrapper<ModularExtractGroup> updateWrapper = new LambdaQueryWrapper<>();
//        updateWrapper.eq(ModularExtractGroup::getId, INTEGER_MODULAR_ID);
//        ModularExtractGroup updateEntity = new ModularExtractGroup();
//        updateEntity.setNameCn(String.valueOf(new Random().nextInt()));
//        int update = mapper.update(updateEntity, updateWrapper);
//        if (update > 0) {
//            redisUtil.del(RedisUtil.RedisDbIndex.BUSINESS_DATA_DB, key);
//        }
//    }
//
//    private ModularExtractGroup getRedisGroup(String key) {
//        ModularExtractGroup group;
//        String value = redisUtil.get(key, RedisUtil.RedisDbIndex.BUSINESS_DATA_DB);
//        if (StringUtils.isBlank(value)) {
//            LambdaQueryWrapper<ModularExtractGroup> wrapper = new LambdaQueryWrapper<>();
//            wrapper.eq(ModularExtractGroup::getId, INTEGER_MODULAR_ID);
//            group = mapper.selectOne(wrapper);
//            redisUtil.setex(key, RedisUtil.ExpireTime.THIRTY_MINUTE, JSON.toJSONString(group), RedisUtil.RedisDbIndex.BUSINESS_DATA_DB);
//        } else {
//            group = JSON.parseObject(value, ModularExtractGroup.class);
//        }
//        return group;
//    }
//
//    @Data
//    public static class GroupVo {
//        private Integer id;
//        private String name;
//        private Integer groupId;
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
