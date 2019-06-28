package com.nqlz.jucstudy;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class TestForkJoin {
    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinCalculate calculate = new ForkJoinCalculate(0L, 100000000L);
        long invoke = forkJoinPool.invoke(calculate);
        System.out.println(invoke);
    }
}

@AllArgsConstructor
@NoArgsConstructor
class ForkJoinCalculate extends RecursiveTask<Long> {

    private long start;
    private long end;
    private static final long THURSHOLD = 10000L;//临界值

    @Override
    protected Long compute() {
        long length = end - start;
        if (length <= THURSHOLD) {
            long sum = 0L;
            for (long i = start; i < end; i++) {
                sum+=i;
            }
            return sum;
        }else {
            long middle = (start +end) / 2;
            ForkJoinCalculate leftTask = new ForkJoinCalculate(start,middle);
            leftTask.fork();
            ForkJoinCalculate rightTask = new ForkJoinCalculate(middle+1,end);
            rightTask.fork();
            return leftTask.join()+rightTask.join();
        }
    }
}