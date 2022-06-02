package com.example.scheduling;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableScheduling
@Configuration
public class SpringSchedulingConfig {


    @Bean
    public ThreadPoolTaskExecutor springThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(2);
       // threadPoolTaskExecutor.setMaxPoolSize(1);
        //threadPoolTaskExecutor.setAwaitTerminationSeconds(60);
        return threadPoolTaskExecutor;
    }

    @Bean
    public ExecutorService javaSingleThreadedExecutor() {
        return  Executors.newSingleThreadExecutor();
    }

}
