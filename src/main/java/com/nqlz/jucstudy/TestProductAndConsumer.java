package com.nqlz.jucstudy;

/**
 * 功能描述:同步代码块
 *
 * @author: MR.zt
 * @date: 2019/6/21 0:08
 */
public class TestProductAndConsumer {
    public static void main(String[] args) {

        Clerk clerk = new Clerk();

        Productor productor = new Productor(clerk);
        Consumer consumer = new Consumer(clerk);

        new Thread(productor, "生产者 A ").start();
        new Thread(consumer, "消费者 B ").start();

        new Thread(productor, "生产者 C ").start();
        new Thread(consumer, "消费者 D ").start();


    }
}

class Clerk {

    private int product = 0;

    /**
     * 进货
     */
    public synchronized void get() {
        //使用while避免徐佳佳唤醒
        while (product >= 1) {
            System.out.println("仓库已满。。。");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + ":进货" + ++product);
        notifyAll();

    }

    /**
     * 卖货
     */
    public synchronized void sale() {
        while (product <= 0) {
            System.out.println("缺货啦。。。");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + ":卖货---" + --product);
        notifyAll();

    }

}

//生产者
class Productor implements Runnable {

    private Clerk clerk = new Clerk();

    public Productor(Clerk clerk) {
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

class Consumer implements Runnable {

    private Clerk clerk = new Clerk();

    public Consumer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {

        for (int i = 0; i < 20; i++) {
            clerk.sale();
        }
    }


}