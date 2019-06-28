package com.nqlz.jucstudy;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程交替
 * 开启一个线程，线程Id分别为A,B,C,每个线程将自己的ID打印十遍，输出结果按顺序显示。
 */
public class TestAlternate {

    public static void main(String[] args) throws InterruptedException {
        AlternateDemo ad = new AlternateDemo();
        new Thread(() -> {
            for (int i = 1; i < 21; i++) {
                ad.loopA( i);
            }
        }, "线程A").start();
        new Thread(() -> {
            for (int i = 1; i < 21; i++) {
                ad.loopB( i);
            }
        }, "线程B").start();
        new Thread(() -> {
            for (int i = 1; i < 21; i++) {
                ad.loopC(i);
                System.out.println("---------------------------------");
            }
        }, "线程C").start();

    }
}

class AlternateDemo {
    private int number = 1;
    private Lock lock = new ReentrantLock();
    private Condition conditionA = lock.newCondition();
    private Condition conditionB = lock.newCondition();
    private Condition conditionC = lock.newCondition();

    public void loopA(int totalLoop) {
        lock.lock();
        try {
            //判断
            if (number != 1) {
                conditionA.await();
            }
            //打印
            for (int i = 1; i <= 1; i++) {
                System.out.println(Thread.currentThread().getName() + ":\t" + i + "\t" + totalLoop);
            }
            //唤醒
            number = 2;
            conditionB.signal();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void loopB(int totalLoop) {
        lock.lock();
        try {
            //判断
            if (number != 2) {
                conditionB.await();
            }
            //打印
            for (int i = 1; i <= 1; i++) {
                System.out.println(Thread.currentThread().getName() + ":\t" + i + "\t" + totalLoop);
            }
            //唤醒
            number = 3;
            conditionC.signal();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void loopC(int totalLoop) {
        lock.lock();
        try {
            //判断
            if (number != 3) {
                conditionC.await();
            }
            //打印
            for (int i = 1; i <= 1; i++) {
                System.out.println(Thread.currentThread().getName() + ":\t" + i + "\t" + totalLoop);
            }
            //唤醒
            number = 1;
            conditionA.signal();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}