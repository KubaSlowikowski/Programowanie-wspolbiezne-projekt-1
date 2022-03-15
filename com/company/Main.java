package com.company;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Main {

//    private static final int N = 10000000;
    private static final int N = 20;

    private static void printArray(int[] arr, int n) {
        for (int i = 0; i< n; i++){
            System.out.print(arr[i]+" ");
        }
        System.out.println();
    }

    public static void main(String[] args) {

        int [] randomIntsArray = IntStream.generate(() -> new Random().nextInt(50)).limit(N).toArray();
        int [] copy = Arrays.copyOf(randomIntsArray, N);

        System.out.println(Arrays.toString(randomIntsArray));
//         printArray(randomIntsArray, N);
//        long startTime = System.nanoTime();
//        QuickSortThreads qst = new QuickSortThreads(randomIntsArray, 0 , N - 1);
//        qst.start();
//        long endTime = System.nanoTime();
//        long multiThreadTime = endTime - startTime;
        //printArray(randomIntsArray, N);
//        System.out.println("Multi thread time: "+multiThreadTime);

//        long startTime2 = System.nanoTime();
//        QuickSortSingle qst2 = new QuickSortSingle();
//        qst2.quickSort(copy,0, N - 1);
//        long endTime2 = System.nanoTime();
//        long singleThreadTime = endTime2 - startTime2;
        //printArray(copy, N);
//        System.out.println("Single thread time: "+singleThreadTime);

        long startTime3 = System.nanoTime();
        QuickSortExecutor qst3 = new QuickSortExecutor();
        LinkedList<Integer> unsorted = new LinkedList<>(Arrays.stream(randomIntsArray).boxed().toList());
        List<Integer> result = qst3.quickSort(unsorted);
        long endTime3 = System.nanoTime();
        long executorThreadTime = endTime3 - startTime3;
        System.out.println(result);
        System.out.println("Executor thread time: "+executorThreadTime);

    }
}
