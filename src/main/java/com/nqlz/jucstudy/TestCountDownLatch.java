package com.nqlz.jucstudy;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

/**
 * 功能描述:闭锁
 *
 * @author: MR.zt
 * @date: 2019/6/20 22:35
 */
public class TestCountDownLatch {

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(10);
        Semaphore semaphore = new Semaphore(10);
        LatchDemo latchDemo = new LatchDemo(latch,semaphore);
        long start = System.currentTimeMillis();

        for (int i = 0; i < 10; i++) {
            new Thread(latchDemo).start();
        }

        latch.await();
        long end = System.currentTimeMillis();

        System.out.println("耗费时间为：" + (end - start));

    }
}

class LatchDemo implements Runnable {

    private CountDownLatch latch;
    private Semaphore semaphore;

    public LatchDemo(CountDownLatch latch,Semaphore phore) {
        this.latch = latch;
        this.semaphore = phore;
    }

    @Override
    public void run() {
        try {
            semaphore.acquire();
            for (int i = 0; i < 5000; i++) {
                if (i % 2 == 0) {
                    System.out.println(i);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
            latch.countDown();
        }

    }


}