package com.company;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    private static final int N = 50000;
    private static final int BOUND = 1000;

    private static void printArray(int[] arr, int n) {
        for (int i = 0; i < n; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {

//        for (int i = 0; i < 1; i++) {

        int[] randomIntsArray = IntStream.generate(() -> new Random().nextInt(BOUND)).limit(N).toArray();
        int[] copy = Arrays.copyOf(randomIntsArray, N);
        int[] copy2 = Arrays.copyOf(randomIntsArray, N);
        List<Integer> unsorted = Arrays.stream(randomIntsArray).boxed().collect(Collectors.toList());

//        printArray(randomIntsArray, N);

//            long startTime2 = System.nanoTime();
//            QuickSortSingle qst2 = new QuickSortSingle();
//            qst2.quickSort(copy, 0, N - 1);
//            long endTime2 = System.nanoTime();
//            long singleThreadTime = endTime2 - startTime2;
////        printArray(copy, N);
//            System.out.println("Single thread time: \t" + singleThreadTime);

//            long startTime = System.nanoTime();
//            QuickSortThreads qst = new QuickSortThreads(randomIntsArray, 0, N - 1);
//            qst.start();
//            long endTime = System.nanoTime();
//            long multiThreadTime = endTime - startTime;
////        printArray(randomIntsArray, N);
//            System.out.println("Multi thread time: \t\t" + multiThreadTime);
//
//            long startTime3 = System.nanoTime();
//
//            QuickSortExecutor qst3 = new QuickSortExecutor();
//            qst3.quickSort(unsorted);
//            qst3.shutDownExecutor();
//
//            long endTime3 = System.nanoTime();
//            long executorThreadTime = endTime3 - startTime3;
////        System.out.println("Result: " + result);
//            System.out.println("Executor thread time: \t" + executorThreadTime);

        long startTime4 = System.nanoTime();

        ForkJoinPool forkJoinPool = new ForkJoinPool(5);
        QuickSortForkJoinPool quickSortForkJoinPool = new QuickSortForkJoinPool(copy2, 0, copy2.length - 1);
//        quickSortForkJoinPool.compute();
        forkJoinPool.invoke(quickSortForkJoinPool);

        long endTime4 = System.nanoTime();
        long fJTime = endTime4 - startTime4;

        System.out.println("Fork Join Time: \t\t" + fJTime);
        System.out.println();
//        }
//        printArray(copy2, N);
    }
}
