package com.example.scheduling.taskscheduler;

import com.example.scheduling.tasks.Worker;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.LockConfiguration;
import net.javacrumbs.shedlock.core.LockingTaskExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@EnableAsync
@Component
@ConditionalOnProperty(value = "job2.enabled", havingValue = "true")
public class SpringTaskSchedulerCustomShedLock {

    @Autowired
    Worker worker;

    @Autowired
    @Qualifier("customLockingTaskExecutor")
    LockingTaskExecutor customLockingTaskExecutor;

    @Value("${lockAtMostFor:120}")
    int lockAtMostFor;

    @Value("${job2.random-lock.enabled:true}")
    boolean randomLockNameEnabled;

    @Value("${jobs.delay:30}")
    int jobDelay;

    @Async
    @Scheduled(fixedRateString = "${rate:10000}")
    public void task() {
        String lockName = randomLockNameEnabled ? UUID.randomUUID().toString() : "job2";
        customLockingTaskExecutor.executeWithLock(
            (Runnable) () -> {
                log.info("Submitting task for job2 : {}, lockName {}, time: {}",
                    Thread.currentThread().getName(), lockName, Instant.now());
                try {
                    TimeUnit.SECONDS.sleep(jobDelay);
                } catch (InterruptedException e) {
                    log.error("", e);
                    Thread.currentThread().interrupt();
                }
            },
            new LockConfiguration(Instant.now(),
                lockName, Duration.ofSeconds(lockAtMostFor), Duration.ZERO));
    }
}
