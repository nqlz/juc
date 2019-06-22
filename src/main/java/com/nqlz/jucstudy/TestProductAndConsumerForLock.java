package com.nqlz.jucstudy;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 功能描述:
 *
 * @author: MR.zt
 * @date: 2019/6/21 0:08
 */
public class TestProductAndConsumerForLock {
    public static void main(String[] args) {

        ClerkLock clerk = new ClerkLock();

        ProductorLock productor = new ProductorLock(clerk);
        ConsumerLock consumer = new ConsumerLock(clerk);

        new Thread(productor, "生产者 A ").start();
        new Thread(consumer, "消费者 B ").start();

        new Thread(productor, "生产者 C ").start();
        new Thread(consumer, "消费者 D ").start();
    }
}

class ClerkLock {

    private int product = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    /**
     * 进货
     */
    public  void get() {
       try{
           lock.lock();
           //使用while避免徐佳佳唤醒
           while (product >= 1) {
               System.out.println("仓库已满。。。");
               try {
                   condition.await();
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
           System.out.println(Thread.currentThread().getName() + ":进货" + ++product);
           condition.signalAll();
       }finally {
           lock.unlock();
       }

    }

    /**
     * 卖货
     */
    public  void sale() {
        try {
            lock.lock();
            while (product <= 0) {
                System.out.println("缺货啦。。。");
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + ":卖货---" + --product);
            condition.signalAll();
        }finally {
            lock.unlock();
        }

    }

}

//生产者
class ProductorLock implements Runnable {

    private ClerkLock clerk;

    public ProductorLock(ClerkLock clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {

        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {

            }
            clerk.get();
        }

    }
}

class ConsumerLock implements Runnable {

    private ClerkLock clerk;

    public ConsumerLock(ClerkLock clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {

        for (int i = 0; i < 20; i++) {
            clerk.sale();
        }
    }


}