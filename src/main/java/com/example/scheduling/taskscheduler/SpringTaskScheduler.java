package com.example.scheduling.taskscheduler;

import com.example.scheduling.tasks.Worker;
import java.time.Instant;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.LockingTaskExecutor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@EnableAsync
@Component
@ConditionalOnProperty(value = "job1.enabled", matchIfMissing = true, havingValue = "true")
public class SpringTaskScheduler {

    /*@Autowired
    ThreadPoolTaskExecutor springThreadPoolTaskExecutor;

    @Autowired
    ExecutorService javaSingleThreadExecutorService;*/

    @Autowired
    Worker worker;

    @Autowired
    @Qualifier("customLockingTaskExecutor")
    LockingTaskExecutor customLockingTaskExecutor;

    @Value("${jobs.delay:30}")
    int jobDelay;

    /*@Scheduled(fixedDelay = 1000)
    public void scheduleTask() throws InterruptedException {
        System.out.println("Submitting task :" + Thread.currentThread().getName());
         // getting the channel needs to be schedule
        Future<?> future = springThreadPoolTaskExecutor.submit(worker);

       //  while (springThreadPoolTaskExecutor.getActiveCount() != 0) { // can be used if always one active thread
         while (!future.isDone()) {
            System.out.println("still working "); // log in db that channel is in progress, updating
             TimeUnit.SECONDS.sleep(1);
        }

        System.out.println("Task completed:" + Thread.currentThread().getName());
    }*/

    @Async
    @Scheduled(fixedRateString = "${rate:10000}")
    @SchedulerLock(name = "task1", lockAtMostFor = "${lockAtMostFor:120}s")
    public void task() throws InterruptedException {
        log.info("Submitting task for job1 : {}, time: {}", Thread.currentThread().getName(), Instant.now());
        TimeUnit.SECONDS.sleep(jobDelay);
    }
}
