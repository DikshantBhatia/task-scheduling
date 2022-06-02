package com.example.scheduling.taskscheduler;

import com.example.scheduling.tasks.Worker;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class SpringTaskScheduler {

    @Autowired
    ThreadPoolTaskExecutor springThreadPoolTaskExecutor;

    @Autowired
    ExecutorService javaSingleThreadExecutorService;

    @Autowired
    Worker worker;

    @Scheduled(fixedDelay = 1000)
    public void scheduleTask() throws InterruptedException {
        System.out.println("Submitting task :" + Thread.currentThread().getName());

        Future<?> future = springThreadPoolTaskExecutor.submit(worker);

       //  while (springThreadPoolTaskExecutor.getActiveCount() != 0) { // can be used if always one active thread
         while (!future.isDone()) {
            System.out.println("still working ");
             TimeUnit.SECONDS.sleep(1);
        }

        System.out.println("Task completed:" + Thread.currentThread().getName());
    }


}
