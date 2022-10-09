//package com.yj.lab.spring.util.redis;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.util.SerializationUtils;
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisSentinelPool;
//import redis.clients.jedis.exceptions.JedisConnectionException;
//import redis.clients.jedis.params.SetParams;
//
//import java.util.*;
//
///**
// * @author Zhang Yongjie
// */
//@Component
//@Slf4j
//public class RedisUtil {
//
//    private static final Long RELEASE_SUCCESS = 1L;
//    private static final String LOCK_SUCCESS = "OK";
//    private static final String SET_IF_NOT_EXIST = "NX";
//    private static final String SET_WITH_EXPIRE_TIME = "EX";
//    private static final String RELEASE_LOCK_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
//    // 获取锁，默认等待时间，ms
//    private static final Long WAIT_TIME = 500L;
//    String CHARSET = "UTF-8";
//    @Autowired
//    private JedisSentinelPool jedisSentinelPool;
//
//    /**
//     * 同步获取Jedis实例
//     *
//     * @param dbIndex 对应库位置，如果为null，则取默认为0
//     * @return Jedis
//     */
//    public Jedis getJedis(String dbIndex) {
//
//        Jedis jedis = null;
//        try {
//            if (jedisSentinelPool != null) {
//
//                int timeoutCount = 0;
//
//                while (true) {
//                    try {
//                        jedis = jedisSentinelPool.getResource();
//                        break;
//                    } catch (Exception e) {
//                        if (e instanceof JedisConnectionException) {
//                            // 记录下获取多少次才获得jedis连接，并发很多的时候可能池里的连接不够而导致获取连接失败，所以这里循环获取
//                            timeoutCount++;
//                        } else {
//                            log.error("jedis连接失败，失败原因：{}", e.getMessage(), e);
//                        }
//                        if (timeoutCount > 20) {
//                            throw new Exception("jedis 获取异常，连接池不足！");
//                        }
//                    }
//                }
//
//                if (StringUtils.isNotBlank(dbIndex)) {
//                    jedis.select(Integer.parseInt(dbIndex));
//                } else {
//                    jedis.select(0);
//                }
//            }
//        } catch (Exception e) {
//            log.error("同步获取Jedis实例失败:{}", e, e);
//            assert jedis != null;
//            jedis.close();
//        }
//
//        return jedis;
//    }
//
//    /**
//     * 设置值
//     *
//     * @param key
//     * @param value
//     * @param dbIndex
//     * @return -5：Jedis实例获取失败<br/>OK：操作成功<br/>null：操作失败
//     */
//    public String set(String key, String value, String dbIndex) {
//        Jedis jedis = getJedis(dbIndex);
//        if (jedis == null) {
//            return JedisStatus.FAIL_STRING;
//        }
//
//        String result = null;
//        try {
//            result = jedis.set(key, value);
//        } catch (Exception e) {
//            log.error("设置值失败：" + e.getMessage(), e);
//        } finally {
//            jedis.close();
//        }
//
//        return result;
//    }
//
//    /**
//     * 设置值
//     *
//     * @param expire 过期时间，单位：秒
//     * @return -5：Jedis实例获取失败<br/>OK：操作成功<br/>null：操作失败
//     * @deprecated ========注：非原子方法，以后不再使用，可能会导致事务问题 @see {@link #setex(String, int, String, String)}
//     */
//    @Deprecated
//    public String set(String key, String value, int expire, String dbIndex) {
//        Jedis jedis = getJedis(dbIndex);
//        if (jedis == null) {
//            return JedisStatus.FAIL_STRING;
//        }
//
//        String result = null;
//        try {
//            result = jedis.set(key, value);
//            jedis.expire(key, expire);
//        } catch (Exception e) {
//            log.error("设置值失败：" + e.getMessage(), e);
//        } finally {
//            jedis.close();
//        }
//
//        return result;
//    }
//
//    /**
//     * 获取值
//     *
//     * @param key
//     * @param dbIndex
//     * @return
//     */
//    public String get(String key, String dbIndex) {
//        Jedis jedis = getJedis(dbIndex);
//        if (jedis == null) {
//            return JedisStatus.FAIL_STRING;
//        }
//
//        String result = null;
//        try {
//            result = jedis.get(key);
//        } catch (Exception e) {
//            log.error("获取值失败：" + e.getMessage(), e);
//        } finally {
//            jedis.close();
//        }
//
//        return result;
//    }
//
//    /**
//     * 获取key的过期时间
//     *
//     * @param key
//     * @param dbIndex
//     * @return
//     */
//    public long ttl(String key, String dbIndex) {
//        Jedis jedis = getJedis(dbIndex);
//        if (jedis == null) {
//            return JedisStatus.FAIL_LONG;
//        }
//
//        long result = 0L;
//        try {
//            result = jedis.ttl(key);
//        } catch (Exception e) {
//            log.error("获取key={}的过期时间失败，失败原因：{}", key, e);
//        } finally {
//            jedis.close();
//        }
//        return result;
//    }
//
//    /**
//     * 设置key的过期时间
//     *
//     * @param key
//     * @param dbIndex
//     * @return
//     */
//    public long expire(String key, int seconds, String dbIndex) {
//        Jedis jedis = getJedis(dbIndex);
//        if (jedis == null) {
//            return JedisStatus.FAIL_LONG;
//        }
//
//        long result = 0L;
//        try {
//            result = jedis.expire(key, seconds);
//        } catch (Exception e) {
//            log.error(String.format("设置key=%s的过期时间失败：" + e.getMessage(), key), e);
//        } finally {
//            jedis.close();
//        }
//
//        return result;
//    }
//
//    /**
//     * 判断key是否存在
//     *
//     * @param key
//     * @param dbIndex
//     * @return
//     */
//    public boolean exists(String key, String dbIndex) {
//        Jedis jedis = getJedis(dbIndex);
//        if (jedis == null) {
//            log.warn("Jedis实例获取为空");
//            return false;
//        }
//
//        boolean result = false;
//        try {
//            result = jedis.exists(key);
//        } catch (Exception e) {
//            log.error(String.format("判断key=%s是否存在失败：" + e.getMessage(), key), e);
//        } finally {
//            jedis.close();
//        }
//
//        return result;
//    }
//
//    /**
//     * 删除key
//     *
//     * @param dbIndex
//     * @param keys
//     * @return -5：Jedis实例获取失败，1：成功，0：失败
//     */
//    public long del(String dbIndex, String... keys) {
//        Jedis jedis = getJedis(dbIndex);
//        if (jedis == null) {
//            return JedisStatus.FAIL_LONG;
//        }
//
//        long result = JedisStatus.FAIL_LONG;
//        try {
//            result = jedis.del(keys);
//        } catch (Exception e) {
//            log.error(String.format("删除key=%s失败：" + e.getMessage(), keys), e);
//        } finally {
//            jedis.close();
//        }
//
//        return result;
//    }
//
//    /**
//     * set if not exists，若key已存在，则setnx不做任何操作
//     *
//     * @param key
//     * @param value   key已存在，1：key赋值成功
//     * @param dbIndex
//     * @return
//     */
//    public long setnx(String key, String value, String dbIndex) {
//        long result = JedisStatus.FAIL_LONG;
//
//        Jedis jedis = getJedis(dbIndex);
//
//        try (jedis) {
//            if (jedis == null) {
//                return result;
//            }
//            result = jedis.setnx(key, value);
//        } catch (Exception e) {
//            log.error("设置值失败：" + e.getMessage(), e);
//        }
//
//        return result;
//    }
//
//    /**
//     * set if not exists，若key已存在，则setnx不做任何操作
//     *
//     * @param key
//     * @param value   key已存在，1：key赋值成功
//     * @param expire  过期时间，单位：秒
//     * @param dbIndex
//     * @return
//     */
//    public long setnx(String key, String value, int expire, String dbIndex) {
//        long result = JedisStatus.FAIL_LONG;
//
//        Jedis jedis = getJedis(dbIndex);
//
//        try (jedis) {
//            if (jedis == null) {
//                return result;
//            }
//            result = jedis.setnx(key, value);
//            jedis.expire(key, expire);
//        } catch (Exception e) {
//            log.error("设置值失败：" + e.getMessage(), e);
//        }
//
//        return result;
//    }
//
//    /**
//     * 在列表key的头部插入元素
//     *
//     * @param key
//     * @param dbIndex
//     * @param values  -5：Jedis实例获取失败，>0：返回操作成功的条数，0：失败
//     * @return
//     */
//    public long lpush(String key, String dbIndex, String... values) {
//        long result = JedisStatus.FAIL_LONG;
//
//        Jedis jedis = getJedis(dbIndex);
//
//        try (jedis) {
//            if (jedis == null) {
//                return result;
//            }
//            result = jedis.lpush(key, values);
//        } catch (Exception e) {
//            log.error("在列表key的头部插入元素失败：" + e.getMessage(), e);
//        }
//
//        return result;
//    }
//
//    /**
//     * 在列表key的尾部插入元素
//     *
//     * @param key
//     * @param values  -5：Jedis实例获取失败，>0：返回操作成功的条数，0：失败
//     * @param dbIndex
//     * @return
//     */
//    public long rpush(String key, String dbIndex, String... values) {
//        long result = JedisStatus.FAIL_LONG;
//
//        Jedis jedis = getJedis(dbIndex);
//
//        try (jedis) {
//            if (jedis == null) {
//                return result;
//            }
//            result = jedis.rpush(key, values);
//        } catch (Exception e) {
//            log.error("在列表key的尾部插入元素失败：" + e.getMessage(), e);
//        }
//
//        return result;
//    }
//
//    /**
//     * 在列表key的首部弹出元素
//     *
//     * @param key
//     * @param dbIndex
//     * @return
//     */
//    public String lpop(String key, String dbIndex) {
//
//        String result = JedisStatus.FAIL_STRING;
//
//        Jedis jedis = getJedis(dbIndex);
//
//        try (jedis) {
//            if (jedis == null) {
//                return result;
//            }
//            result = jedis.lpop(key);
//        } catch (Exception e) {
//            log.error("在列表key的首部弹出元素失败：" + e.getMessage(), e);
//        }
//
//        return result;
//    }
//
//    /**
//     * 在列表key的尾部弹出元素
//     *
//     * @param key
//     * @param dbIndex
//     * @return
//     */
//    public String rpop(String key, String dbIndex) {
//
//        String result = JedisStatus.FAIL_STRING;
//
//        Jedis jedis = getJedis(dbIndex);
//
//        try (jedis) {
//            if (jedis == null) {
//                return result;
//            }
//            result = jedis.rpop(key);
//        } catch (Exception e) {
//            log.error("在列表key的尾部弹出元素失败：" + e.getMessage(), e);
//        }
//
//        return result;
//    }
//
//    /**
//     * 返回存储在key列表的特定元素
//     *
//     * @param key
//     * @param start   开始索引，索引从0开始，0表示第一个元素，1表示第二个元素
//     * @param end     结束索引，-1表示最后一个元素，-2表示倒数第二个元素
//     * @param dbIndex
//     * @return redis client获取失败返回null
//     */
//    public List<String> lrange(String key, long start, long end, String dbIndex) {
//        Jedis jedis = getJedis(dbIndex);
//        if (jedis == null) {
//            return null;
//        }
//
//        List<String> result = null;
//        try {
//            result = jedis.lrange(key, start, end);
//        } catch (Exception e) {
//            log.error("查询列表元素失败：" + e.getMessage(), e);
//        } finally {
//            jedis.close();
//        }
//
//        return result;
//    }
//
//    /**
//     * 获取列表长度
//     *
//     * @param key     -5：Jedis实例获取失败
//     * @param dbIndex
//     * @return
//     */
//    public long llen(String key, String dbIndex) {
//        Jedis jedis = getJedis(dbIndex);
//        if (jedis == null) {
//            return JedisStatus.FAIL_LONG;
//        }
//
//        long result = 0;
//        try {
//            result = jedis.llen(key);
//        } catch (Exception e) {
//            log.error("获取列表长度失败：" + e.getMessage(), e);
//        } finally {
//            jedis.close();
//        }
//
//        return result;
//    }
//
//    /**
//     * 移除等于value的元素<br/><br/>
//     * 当count>0时，从表头开始查找，移除count个；<br/>
//     * 当count=0时，从表头开始查找，移除所有等于value的；<br/>
//     * 当count<0时，从表尾开始查找，移除count个
//     *
//     * @param key
//     * @param count
//     * @param value
//     * @param dbIndex
//     * @return -5:Jedis实例获取失败
//     */
//    public long lrem(String key, long count, String value, String dbIndex) {
//        Jedis jedis = getJedis(dbIndex);
//        if (jedis == null) {
//            return JedisStatus.FAIL_LONG;
//        }
//
//        long result = 0;
//        try {
//            result = jedis.lrem(key, count, value);
//        } catch (Exception e) {
//            log.error("获取列表长度失败：" + e.getMessage(), e);
//        } finally {
//            jedis.close();
//        }
//
//        return result;
//    }
//
//    /**
//     * 对列表进行修剪
//     *
//     * @param key
//     * @param start
//     * @param end
//     * @param dbIndex
//     * @return -5：Jedis实例获取失败，OK：命令执行成功
//     */
//    public String ltrim(String key, long start, long end, String dbIndex) {
//        Jedis jedis = getJedis(dbIndex);
//        if (jedis == null) {
//            return JedisStatus.FAIL_STRING;
//        }
//
//        String result = "";
//        try {
//            result = jedis.ltrim(key, start, end);
//        } catch (Exception e) {
//            log.error("获取列表长度失败：" + e.getMessage(), e);
//        } finally {
//            jedis.close();
//        }
//
//        return result;
//    }
//
//    /**
//     * 设置对象
//     *
//     * @param key
//     * @param obj
//     * @param dbIndex
//     * @return
//     */
//    public <T> String setObject(String key, T obj, String dbIndex) {
//        Jedis jedis = getJedis(dbIndex);
//        if (jedis == null) {
//            return JedisStatus.FAIL_STRING;
//        }
//
//        String result = null;
//        try {
//            byte[] data = SerializationUtils.serialize(obj);
//            result = jedis.set(key.getBytes(), data);
//        } catch (Exception e) {
//            log.error("设置对象失败：" + e.getMessage(), e);
//        } finally {
//            jedis.close();
//        }
//
//        return result;
//    }
//
//    /**
//     * 获取List缓存对象
//     *
//     * @param key
//     * @param start
//     * @param end
//     * @return List<T> 返回类型
//     *
//     */
////    public <T> List<T> range(String key, long start, long end, Class<T> clazz){
////        List<String> dataList = lrange(key, start, end);
////        if(CollectionUtils.isEmpty(dataList)){
////            return new ArrayList<T>();
////        }
////        return JavaJsonConvert.json2List(dataList.toString(), clazz);
////    }
//
//    /**
//     * 获取对象
//     *
//     * @param key
//     * @param dbIndex
//     * @return
//     */
//    @SuppressWarnings("unchecked")
//    public <T> T getObject(String key, String dbIndex) {
//        Jedis jedis = getJedis(dbIndex);
//        if (jedis == null) {
//            return null;
//        }
//
//        T result = null;
//        try {
//            byte[] data = jedis.get(key.getBytes());
//            if (data != null && data.length > 0) {
//                result = (T) SerializationUtils.deserialize(data);
//            }
//        } catch (Exception e) {
//            log.error("获取对象失败：" + e.getMessage(), e);
//        } finally {
//            jedis.close();
//        }
//
//        return result;
//    }
//
//    /**
//     * 缓存Map赋值
//     *
//     * @param key
//     * @param field
//     * @param value
//     * @param dbIndex
//     * @return -5：Jedis实例获取失败
//     */
//    public long hset(String key, String field, String value, String dbIndex) {
//        Jedis jedis = getJedis(dbIndex);
//        if (jedis == null) {
//            return JedisStatus.FAIL_LONG;
//        }
//
//        long result = 0L;
//        try {
//            result = jedis.hset(key, field, value);
//        } catch (Exception e) {
//            log.error("缓存Map赋值失败：" + e.getMessage(), e);
//        } finally {
//            jedis.close();
//        }
//
//        return result;
//    }
//
//    /**
//     * hash存储加过期时间
//     *
//     * @param key
//     * @param field
//     * @param value
//     * @param expire
//     * @param dbIndex
//     * @return
//     */
//    public long hsetex(String key, String field, String value, int expire, String dbIndex) {
//        Jedis jedis = getJedis(dbIndex);
//        if (jedis == null) {
//            return JedisStatus.FAIL_LONG;
//        }
//
//        long result = 0L;
//        try {
//            result = jedis.hset(key, field, value);
//            jedis.expire(key, expire);
//        } catch (Exception e) {
//            log.error("缓存Map赋值失败：" + e.getMessage(), e);
//        } finally {
//            jedis.close();
//        }
//
//        return result;
//    }
//
//    /**
//     * 获取缓存的Map值
//     *
//     * @param key
//     * @param dbIndex
//     * @return
//     */
//    public String hget(String key, String field, String dbIndex) {
//        Jedis jedis = getJedis(dbIndex);
//        if (jedis == null) {
//            return null;
//        }
//
//        String result = null;
//        try {
//            result = jedis.hget(key, field);
//        } catch (Exception e) {
//            log.error("获取缓存的Map值失败：" + e.getMessage(), e);
//        } finally {
//            jedis.close();
//        }
//
//        return result;
//    }
//
//    /**
//     * 获取map所有的字段和值
//     *
//     * @param key
//     * @param dbIndex
//     * @return
//     */
//    public Map<String, String> hgetAll(String key, String dbIndex) {
//        Map<String, String> map = new HashMap<String, String>();
//
//        Jedis jedis = getJedis(dbIndex);
//
//        try (jedis) {
//            if (jedis == null) {
//                log.warn("Jedis实例获取为空");
//                return map;
//            }
//            map = jedis.hgetAll(key);
//        } catch (Exception e) {
//            log.error("获取map所有的字段和值失败：" + e.getMessage(), e);
//        }
//
//        return map;
//    }
//
//    /**
//     * 查看哈希表 key 中，指定的field字段是否存在。
//     *
//     * @param key
//     * @param dbIndex
//     * @param field
//     * @return
//     */
//    public Boolean hexists(String key, String field, String dbIndex) {
//        Jedis jedis = getJedis(dbIndex);
//
//        try (jedis) {
//            if (jedis == null) {
//                log.warn("Jedis实例获取为空");
//                return null;
//            }
//            return jedis.hexists(key, field);
//        } catch (Exception e) {
//            log.error("查看哈希表field字段是否存在失败：" + e.getMessage(), e);
//        }
//
//        return null;
//    }
//
//    /**
//     * 获取所有哈希表中的字段
//     *
//     * @param key
//     * @param dbIndex
//     * @return
//     */
//    public Set<String> hkeys(String key, String dbIndex) {
//        Set<String> set = new HashSet<String>();
//        Jedis jedis = getJedis(dbIndex);
//
//        try (jedis) {
//            if (jedis == null) {
//                log.warn("Jedis实例获取为空");
//                return set;
//            }
//            return jedis.hkeys(key);
//        } catch (Exception e) {
//            log.error("获取所有哈希表中的字段失败：" + e.getMessage(), e);
//        }
//
//        return null;
//    }
//
//    /**
//     * 获取所有哈希表中的值
//     *
//     * @param key
//     * @param dbIndex
//     * @return
//     */
//    public List<String> hvals(String key, String dbIndex) {
//        List<String> list = new ArrayList<>();
//        Jedis jedis = getJedis(dbIndex);
//
//        try (jedis) {
//            if (jedis == null) {
//                log.warn("Jedis实例获取为空");
//                return list;
//            }
//            return jedis.hvals(key);
//        } catch (Exception e) {
//            log.error("获取所有哈希表中的值失败：" + e.getMessage(), e);
//        }
//
//        return null;
//    }
//
//    /**
//     * 从哈希表 key 中删除指定的field
//     *
//     * @param key
//     * @param dbIndex
//     * @return
//     */
//    public long hdel(String key, String dbIndex, String... fields) {
//        Jedis jedis = getJedis(dbIndex);
//
//        try (jedis) {
//            if (jedis == null) {
//                log.warn("Jedis实例获取为空");
//                return JedisStatus.FAIL_LONG;
//            }
//            return jedis.hdel(key, fields);
//        } catch (Exception e) {
//            log.error("map删除指定的field失败：" + e.getMessage(), e);
//        }
//
//        return 0;
//    }
//
//    /**
//     * 根据正则获取key
//     *
//     * @param pattern
//     * @param dbIndex
//     * @return
//     */
//    public Set<String> keys(String pattern, String dbIndex) {
//        Set<String> keyList = new HashSet<>();
//        Jedis jedis = getJedis(dbIndex);
//
//        try (jedis) {
//            if (jedis == null) {
//                log.warn("Jedis实例获取为空");
//                return keyList;
//            }
//            keyList = jedis.keys(pattern);
//        } catch (Exception e) {
//            log.error("操作keys失败：" + e.getMessage(), e);
//        }
//
//        return keyList;
//    }
//
//    public long incrBy(String key, long number, String dbIndex) {
//        try (Jedis jedis = getJedis(dbIndex)) {
//            long len = jedis.incrBy(key.getBytes(CHARSET), number);
//            return len;
//        } catch (Exception e) {
//            log.error("操作keys失败：" + e.getMessage(), e);
//        }
//        return 0;
//    }
//
//    public long decrBy(String key, long number, String dbIndex) {
//        try (Jedis jedis = getJedis(dbIndex)) {
//            long len = jedis.decrBy(key.getBytes(CHARSET), number);
//            return len;
//        } catch (Exception e) {
//            log.error("操作keys失败：" + e.getMessage(), e);
//        }
//        return 0;
//    }
//
//    /**
//     * 设置string key的标准原子方法
//     */
//    public void setex(String key, int seconds, String value, String dbIndex) {
//        try (Jedis jedis = getJedis(dbIndex)) {
//            jedis.setex(key.getBytes(CHARSET), seconds, value.getBytes(CHARSET));
//        } catch (Exception e) {
//            log.error("操作keys失败：" + e.getMessage(), e);
//        }
//    }
//
//    /**
//     * 加分布式锁简化方法 lockValue简单默认为lockKey 获取锁可等待时长，赋予默认值，500ms Redis存储数据库，默认使用并发锁数据
//     *
//     * @param lockKey  加锁Key
//     * @param lockTime 加锁时长（s,一定要大于业务执行时间）
//     * @return 是否加锁成功
//     */
//    public Boolean tryLock(String lockKey, long lockTime) {
//        return tryLock(lockKey, lockKey, lockTime, RedisDbIndex.LOCK_DATA_DB);
//    }
//
//    /**
//     * 释放分布式锁简化方法
//     * del操作唯一标识，使用默认加锁时的lockValue == lockKey
//     * Redis存储数据库，默认使用并发锁数据
//     *
//     * @param lockKey 加锁Key
//     * @return 释放锁是否成功
//     */
//    public Boolean releaseLock(String lockKey) {
//        return releaseLock(lockKey, lockKey, RedisDbIndex.LOCK_DATA_DB);
//    }
//
//    /**
//     * 该加锁方法仅针对单实例及可容错集群 Redis 可实现分布式加锁
//     * 对于 Redis 集群则无法使用
//     * <p>
//     * 支持重复，线程安全
//     *
//     * @param lockKey   加锁Key
//     * @param lockValue 加锁Value（可以和lockKey相同）
//     * @param lockTime  加锁时长（s,一定要大于业务执行时间）
//     * @param waitTime  可等待时长（ms,轮询获取锁的时间）
//     * @param dbIndex   redis数据库位置
//     * @return 是否加锁成功
//     */
//    public Boolean tryLock(String lockKey, String lockValue, long lockTime, long waitTime, String dbIndex) {
//
//        Jedis jedis = getJedis(dbIndex);
//
//        try (jedis) {
//            if (jedis == null) {
//                log.warn("Jedis实例获取为空");
//                return false;
//            }
//            long millisTime = System.currentTimeMillis();
//            // 轮询时间内获取锁
//            while (System.currentTimeMillis() - millisTime < waitTime) {
//                SetParams setParams = new SetParams();
//                setParams.nx();
//                setParams.ex((int) lockTime);
////                String result = jedis.set(lockKey, lockValue, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, lockTime);
//                String result = jedis.set(lockKey, lockValue, setParams);
//                if (LOCK_SUCCESS.equals(result)) {
//                    return true;
//                }
//                // 短暂休眠，避免可能的活锁
//                Thread.sleep(3);
//            }
//        } catch (Exception e) {
//            log.error("redis获取锁失败，失败原因：{}", e.getMessage(), e);
//        }
//        return false;
//    }
//
//    /**
//     * 不支持锁等待和重试，拿不到锁立即返回
//     *
//     * @param lockKey   key
//     * @param lockValue value
//     * @param lockTime  过期时间
//     * @param dbIndex   库
//     * @return
//     * @author zangchuanlei
//     */
//    public Boolean tryLock(String lockKey, String lockValue, long lockTime, String dbIndex) {
//        log.info("开始抢占分布式锁");
//        Jedis jedis = getJedis(dbIndex);
//
//        try (jedis) {
//            if (jedis == null) {
//                log.warn("Jedis实例获取为空");
//                return false;
//            }
//            SetParams setParams = new SetParams();
//            setParams.nx();
//            setParams.ex((int) lockTime);
//            String result = jedis.set(lockKey, lockValue, setParams);
//            if (LOCK_SUCCESS.equals(result)) {
//                log.info("抢占成功");
//                return true;
//            } else {
//                log.info("抢占失败");
//                return false;
//            }
//        } catch (Exception e) {
//            log.error("redis获取锁失败，失败原因：{}", e.getMessage(), e);
//        }
//        return false;
//    }
//
//
//    /**
//     * 与 tryLock 相对应，用作释放锁
//     *
//     * @param lockKey   加锁键
//     * @param lockValue 加锁客户端唯一标识(使用和加锁时同样的lockValue)
//     * @param dbIndex   redis数据库位置
//     * @return
//     */
//    public Boolean releaseLock(String lockKey, String lockValue, String dbIndex) {
//
//        Jedis jedis = getJedis(dbIndex);
//
//        try (jedis) {
//            if (jedis == null) {
//                log.warn("Jedis实例获取为空");
//                return Boolean.FALSE;
//            }
//            Object result = jedis.eval(RELEASE_LOCK_SCRIPT, Collections.singletonList(lockKey),
//                    Collections.singletonList(lockValue));
//            if (RELEASE_SUCCESS.equals(result)) {
//                return Boolean.TRUE;
//            }
//            return Boolean.FALSE;
//        } catch (Exception e) {
//            log.error("redis释放锁失败，失败原因：{}", e.getMessage(), e);
//        }
//        return Boolean.FALSE;
//    }
//
//    /**
//     * 订阅
//     *
//     * @param channel 信道
//     * @param message 消息
//     * @param dbIndex db位置
//     */
//    public void publish(String channel, String message, String dbIndex) {
//        try (Jedis jedis = getJedis(dbIndex)) {
//            jedis.publish(channel, message);
//        } catch (Exception e) {
//            log.error("订阅消息失败：{}", e.getMessage(), e);
//        }
//    }
//
//    /**
//     * redis分库规则
//     */
//    public static class RedisDbIndex {
//        //TODO: 自行重新定义 Redis DB Index
//        /**
//         * token数据
//         */
//        public static final String TOKEN_DATA_DB = "0";
//        /**
//         * 业务数据（各种Entity实体数据）
//         */
//        public static final String BUSINESS_DATA_DB = "1";
//        /**
//         * 并发锁数据
//         */
//        public static final String LOCK_DATA_DB = "2";
//        /**
//         * 风控key（user limit,ip limit及black key等）
//         */
//        public static final String RISK_LIMIT_DB = "3";
//        /**
//         * 埋点数据统计
//         */
//        public static final String BURIED_POINT_LIMIT_DB = "4";
//        /**
//         * 发布订阅
//         */
//        public static final String PUB_SUB_DB = "5";
//        /**
//         * 活动数据（包括大转盘等）
//         */
//        public static final String ACTIVITY_DATA_DB = "8";
//        /**
//         * 校验数据（包括短信验证码等信息）
//         */
//        public static final String CHECK_DATA_DB = "9";
//        /**
//         * 存放配置码表
//         */
//        public static final String SETTINGS_DB = "11";
//
//        /**
//         * 行为验证码数据
//         */
//        public static final String CAPTCHA_DATA_DB = "12";
//
//        /**
//         * 产品洞察
//         */
//        public static final String ISTAR_DATA_DB = "15";
//    }
//
//    /**
//     * 过期时间
//     */
//    public static class ExpireTime {
//        /**
//         * 5分钟 = 60 * 5 = 300 秒
//         */
//        public static final int FIVE_MINUTE = 300;
//        /**
//         * 30分钟 = 60 * 30 = 1800 秒
//         */
//        public static final int THIRTY_MINUTE = 1800;
//        /**
//         * 60分钟 = 60 * 60 = 3600 秒
//         */
//        public static final int SIXTY_MINUTE = 3600;
//        /**
//         * 1天 = 60 * 60 * 24 = 86400 秒
//         */
//        public static final int DAY = 86400;
//        /**
//         * 7天 = 60 * 60 * 24 * 7 = 604800 秒
//         */
//        public static final int SEVEN_DAY = 604800;
//        /**
//         * 1月 = 60 * 60 * 24 * 30 = 2592000 秒
//         */
//        public static final int MONTH = 2592000;
//
//    }
//
//    /**
//     * redis订阅信道
//     */
//    public static class RedisPubSubChannel {
//
//        /**
//         * 用户设备数据merge
//         */
//        public static final String USER_DEVICE_MERGE = "user_device_merge";
//        /**
//         * 延保信息新增
//         */
//        public static final String USER_DEVICE_GUARANTEE = "user_device_guarantee";
//        /**
//         * 用户profile信息更新
//         */
//        public static final String USER_PROFILE_UPDATE = "user_profile_update";
//
//    }
//
//    /**
//     * Jedis实例获取返回码
//     */
//    public static class JedisStatus {
//
//        /**
//         * Jedis实例获取失败
//         */
//        public static final long FAIL_LONG = -5L;
//        /**
//         * Jedis实例获取失败
//         */
//        public static final int FAIL_INT = -5;
//        /**
//         * Jedis实例获取失败
//         */
//        public static final String FAIL_STRING = "-5";
//    }
//}
