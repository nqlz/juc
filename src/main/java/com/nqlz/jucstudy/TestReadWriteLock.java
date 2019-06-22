package com.nqlz.jucstudy;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁
 */
public class TestReadWriteLock {
    public static void main(String[] args) {
        ReadWriteDemo rt = new ReadWriteDemo();

        new Thread(()->{
            rt.set(156);
        },"write:").start();

        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                rt.get();
            }).start();
        }
    }
}

class ReadWriteDemo {

    private int number = 0;
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    //读
    public void get() {
        try {
            lock.readLock().lock();
            System.out.println(Thread.currentThread().getName() + ":--" + number);
        }finally {
            lock.readLock().unlock();
        }

    }

    //写
    public void set(int number) {
        try {
            lock.writeLock().lock();
            System.out.println(Thread.currentThread().getName());
            this.number = number;
        }finally {
            lock.writeLock().unlock();
        }
    }

}