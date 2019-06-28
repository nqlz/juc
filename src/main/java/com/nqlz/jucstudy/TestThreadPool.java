package com.nqlz.jucstudy;

import java.util.Random;
import java.util.concurrent.*;

public class TestThreadPool {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(5);
        for (int i = 0; i < 10; i++) {
            ScheduledFuture<Object> schedule = pool.schedule((Callable<Object>) () -> {
                int num = new Random().nextInt(100);
                System.out.println(Thread.currentThread().getName() + ":" + num);
                return num;
            }, 1, TimeUnit.SECONDS);

            System.out.println(schedule.get());
        }
        pool.shutdown();
    }
}
