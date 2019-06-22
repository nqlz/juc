package com.nqlz.jucstudy;

import java.util.concurrent.*;

/**
 * 功能描述:callable
 *
 * @author: MR.zt
 * @date: 2019/6/20 23:02
 */
public class TestCallable {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        callDemo callDemo = new callDemo();

        ExecutorService threadPoolExecutor = new ThreadPoolExecutor(100, 100, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));

        Future<Object> submit = threadPoolExecutor.submit(callDemo);

        Integer result = (Integer) submit.get();

        System.out.println(result);

        threadPoolExecutor.shutdown();
    }
}

class callDemo implements Callable<Object> {

    @Override
    public Object call() throws Exception {
        int sum = 0;
        for (int i = 0; i < 100; i++) {
            sum += i;
        }
        return sum;
    }


}
