package com.company;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ParallelStreamExample {

    public static void main(String[] args) {
        Random random = new Random();
        List<Double> list = random.doubles(1_000).boxed().collect(Collectors.toList());
        var doer = new HeavyDoer();
        var time1 = System.currentTimeMillis();
        list.stream().map(x -> x*2).filter(x -> x > 1).map(doer::doHeavyWork).forEach(x1 -> System.out.println("sequential: " + x1 + " " + Thread.currentThread().getName()));
        var time2 = System.currentTimeMillis();
        list.parallelStream().map(x -> x*2).filter(x -> x > 1).map(doer::doHeavyWork).forEach(x1 -> System.out.println("parallel: " + x1 + " " + Thread.currentThread().getName()));
        var time3 = System.currentTimeMillis();

        System.out.println("normal: " + (time2 - time1) + "ms, parallel:  " + (time3 - time2) + "ms");
    }

    public static class HeavyDoer {
        Double doHeavyWork(Double d) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return d;
        }
    }
}
