package com.example.scheduling.tasks;

import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Component;

@Component
public class Worker implements Runnable{

    @Override
    public void run() {
        System.out.println("Worker started" + Thread.currentThread().getName());
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       /* int i = 1;
        while(i <= 100) {
            System.out.println("processing");
            i++;
        }*/
        System.out.println("Worker completed" + Thread.currentThread().getName());
    }
}
