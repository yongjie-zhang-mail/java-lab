package com.yj.lab.spring.config.threadpool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Configuration
@EnableAsync
public class ThreadPoolConfig {

    @Value("${threadpool.main.core-pool-size}")
    private int mainCoreSize;
    @Value("${threadpool.main.max-pool-size}")
    private int mainMaxSize;
    @Value("${threadpool.main.queue-capacity}")
    private int mainQueueSize;
    @Value("${threadpool.main.thread-name-prefix}")
    private String mainPrefix;

    @Value("${threadpool.second.core-pool-size}")
    private int secondCoreSize;
    @Value("${threadpool.second.max-pool-size}")
    private int secondMaxSize;
    @Value("${threadpool.second.queue-capacity}")
    private int secondQueueSize;
    @Value("${threadpool.second.thread-name-prefix}")
    private String secondPrefix;

    @Primary
    @Bean(name = "mainTp")
    public Executor mainThreadPool() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数，CPU核数
        executor.setCorePoolSize(mainCoreSize);
        // 最大线程数
        executor.setMaxPoolSize(mainMaxSize);
        // 阻塞队列大小
        executor.setQueueCapacity(mainQueueSize);
        // 线程名称前缀
        executor.setThreadNamePrefix(mainPrefix);
        // 拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        // 允许线程的空闲时间60秒：当超过了核心线程出之外的线程在空闲时间到达之后会被销毁
        executor.setKeepAliveSeconds(60);
        // 等待所有任务调度完成在关闭线程池，保证所有的任务被正确处理
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 线程池关闭时等待其他任务的时间，不能无限等待，确保应用最后能被关闭。而不是无限期阻塞
        executor.setAwaitTerminationSeconds(60);

        // 初始化
        executor.initialize();

        return executor;
    }

    @Bean(name = "secondTp")
    public Executor secondThreadPool() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数，CPU核数
        executor.setCorePoolSize(secondCoreSize);
        // 最大线程数
        executor.setMaxPoolSize(secondMaxSize);
        // 阻塞队列大小
        executor.setQueueCapacity(secondQueueSize);
        // 线程名称前缀
        executor.setThreadNamePrefix(secondPrefix);
        // 拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        // 允许线程的空闲时间60秒：当超过了核心线程出之外的线程在空闲时间到达之后会被销毁
        executor.setKeepAliveSeconds(60);
        // 等待所有任务调度完成在关闭线程池，保证所有的任务被正确处理
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 线程池关闭时等待其他任务的时间，不能无限等待，确保应用最后能被关闭。而不是无限期阻塞
        executor.setAwaitTerminationSeconds(60);

        // 初始化
        executor.initialize();

        return executor;

    }


}







