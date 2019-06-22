package com.nqlz.jucstudy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 功能描述:写入并复制
 *
 * @author: MR.zt
 * @date: 2019/6/20 22:05
 */
public class TestCopyOnWriteArrayList {

    public static void main(String[] args) {
        //
        final int clientCount = 5;
        final int totalRequestCount = 10;
        Semaphore semaphore = new Semaphore(clientCount);
        helloThread ht = new helloThread();
        for (int i = 0; i < totalRequestCount; i++) {
            try {
                semaphore.acquire();
                new Thread(ht).start();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                semaphore.release();
            }
        }

    }
}

class helloThread implements Runnable {


//    private static List<String> list = Collections.synchronizedList(new ArrayList<>());

    private static CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

    static {
        list.add("AA");
        list.add("CC");
        list.add("BB");
    }

    @Override
    public void run() {
        list.forEach(item->{
            System.out.println(item);
            list.add("AA");
        });
    }
}