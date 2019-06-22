package com.nqlz.jucstudy;

/**
 * 判断打印的是“one”或“two”
 * 1、、 非静态方法锁为this
 * 2、静态方法的锁为class实例
 * 3、某一个时刻内，只有一个线程持有锁，无论几个方法
 */
public class TestThread8Monitor {
    public static void main(String[] args) {
        Number number = new Number();
        Number number2 = new Number();
        new Thread(()->{
            number.getOne();
        }).start();
        new Thread(()->{
            number2.getTwo();
        }).start();
        /*new Thread(()->{
            number.getThree();
        }).start();*/
    }
}
class Number {
    public static synchronized void getOne()  {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("one");
    }

    public  static synchronized void getTwo(){
        System.out.println("two");
    }

    public synchronized void getThree(){
        System.out.println("three");
    }
}

