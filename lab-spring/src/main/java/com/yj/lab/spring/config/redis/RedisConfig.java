//package com.yj.lab.spring.config.redis;
//
//import com.google.common.collect.Sets;
//import lombok.Data;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import redis.clients.jedis.JedisPoolConfig;
//import redis.clients.jedis.JedisSentinelPool;
//
//import java.util.Set;
//
//@Data
//@Slf4j
//@Configuration
//@EnableAutoConfiguration
//public class RedisConfig {
//
//    // 连接信息
//    @Value("${spring.redis.sentinel.master}")
//    private String sentinelMaster;
//    @Value("${spring.redis.sentinel.nodes}")
//    private String sentinelNodes;
//    @Value("${spring.redis.password}")
//    private String password;
//    // 连接池配置
//    @Value("${spring.redis.jedis.pool.max-total}")
//    private int maxTotal;
//    @Value("${spring.redis.jedis.pool.max-idle}")
//    private int maxIdle;
//    @Value("${spring.redis.jedis.pool.min-idle}")
//    private int minIdle;
//    @Value("${spring.redis.jedis.pool.max-wait}")
//    private int maxWait;
//
//    @Value("${spring.redis.jedis.pool.timeout}")
//    private int timeout;
//
//    @Bean
//    public JedisPoolConfig jedisPoolConfig() {
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        // 最大连接数；例如每个请求平均耗时50ms，那么每个连接的QPS=1000ms/50ms=20qps；max-total=10000qps/20qps=500最大连接数
//        jedisPoolConfig.setMaxTotal(maxTotal);
//        // 最大可空闲的连接数；实际的最大连接数；
//        jedisPoolConfig.setMaxIdle(maxIdle);
//        // 最小可空闲的连接数；实际的最小连接数；
//        jedisPoolConfig.setMinIdle(minIdle);
//        // 最大等待时间
//        jedisPoolConfig.setMaxWaitMillis(maxWait);
//
//        //是否在从池中取出连接前进行检验，如果检验失败，则从池中去除连接并尝试取出另一个
////        jedisPoolConfig.setTestOnBorrow(true);
//        //在return给pool时，是否提前进行validate操作
////        jedisPoolConfig.setTestOnReturn(true);
//        //在空闲时检查有效性，默认false
////        jedisPoolConfig.setTestWhileIdle(true);
//
//        //表示一个对象至少停留在idle状态的最短时间，然后才能被idle object evitor扫描并驱逐；
//        //这一项只有在timeBetweenEvictionRunsMillis大于0时才有意义
//        jedisPoolConfig.setMinEvictableIdleTimeMillis(30000);
//        //表示idle object evitor两次扫描之间要sleep的毫秒数
//        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(60000);
//        //表示idle object evitor每次扫描的最多的对象数
//        jedisPoolConfig.setNumTestsPerEvictionRun(10);
//        return jedisPoolConfig;
//    }
//
//    @Bean
//    public JedisSentinelPool sentinelConfiguration(JedisPoolConfig jedisPoolConfig) {
//        Set<String> sentinels = Sets.newHashSet(StringUtils.split(sentinelNodes, ","));
//        log.info("jedisNodeSet -->" + sentinels);
//        // 配置jedis的哨兵sentinel    Protocol.DEFAULT_TIMEOUT
//        JedisSentinelPool jedisSentinelPool = new JedisSentinelPool(sentinelMaster, sentinels,
//                jedisPoolConfig, timeout, password);
//        return jedisSentinelPool;
//    }
//
//}
