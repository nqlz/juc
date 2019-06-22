package com.nqlz.jucstudy;

import lombok.Data;

/**
 * 功能描述:
 *
 * @author: MR.zt
 * @date: 2019/6/20 20:11
 */
public class TestVolatile {


    public static void main(String[] args) {
        demo target = new demo();
        new Thread(target).start();

        while (true) {
            if (target.isFlag()) {
                System.out.println("结束：------------------------");
                break;
            }
        }
    }


}

@Data
class demo implements Runnable {

    private volatile boolean flag = false;

    @Override
    public void run() {
        try {
            Thread.sleep(200);
            flag = true;
            System.out.println("Flag:是" + isFlag());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}