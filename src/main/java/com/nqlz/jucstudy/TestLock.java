package com.nqlz.jucstudy;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 功能描述:
 *
 * @author: MR.zt
 * @date: 2019/6/20 23:35
 */
public class TestLock {

    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        new Thread(ticket, "1号窗口").start();
        new Thread(ticket, "2号窗口").start();
        new Thread(ticket, "3号窗口").start();
        new Thread(ticket, "4号窗口").start();

    }

}

class Ticket implements Runnable {

    private int count = 100;

    private Lock lock = new ReentrantLock();

    @Override
    public void run() {

        try {
            lock.lock();
            while (true) {
                if (count > 0) {
                    try {
                        Thread.sleep(100);
                        System.out.println(Thread.currentThread().getName() + "完成售票，余票为：" + --count);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } finally {
            lock.unlock();
        }
    }
}
