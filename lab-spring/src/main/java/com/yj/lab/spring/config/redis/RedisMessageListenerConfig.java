//package com.yj.lab.spring.config.redis;
//
//import lombok.Data;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.Message;
//import org.springframework.data.redis.connection.MessageListener;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.listener.ChannelTopic;
//import org.springframework.data.redis.listener.RedisMessageListenerContainer;
//import org.springframework.data.redis.listener.Topic;
//import org.springframework.stereotype.Component;
//
//import java.nio.charset.StandardCharsets;
//
//@Data
//@Slf4j
//@Configuration
//@EnableAutoConfiguration
//public class RedisMessageListenerConfig {
//
//    @Autowired
//    private RedisConnectionFactory redisConnectionFactory;
//    @Autowired
//    private SampleListener sampleListener;
//
//    /**
//     * 定义 Redis 的监听容器
//     */
//    @Bean
//    public RedisMessageListenerContainer redisMessageListenerContainer() {
//        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//        container.setConnectionFactory(redisConnectionFactory);
//        //添加监听器
//        Topic sampleTopic = new ChannelTopic("sampleTopic");
//        container.addMessageListener(sampleListener, sampleTopic);
//
//        return container;
//    }
//
//    @Component
//    public static class SampleListener implements MessageListener {
//
//        @Override
//        public void onMessage(Message message, byte[] bytes) {
//            String topic = new String(message.getChannel(), StandardCharsets.UTF_8);
//            String messageBody = new String(message.getBody(), StandardCharsets.UTF_8);
//            log.info("topic:{}, messageBody:{}", topic, messageBody);
//            try {
//                // 根据监听到的消息，进行相关逻辑处理
//                // sampleService.execute(messageBody);
//                log.info("do something");
//            } catch (Exception e) {
//                log.error("异常原因:{}", e.getMessage(), e);
//            }
//        }
//
//    }
//
//
//}
