package com.company;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class Main {

    private static final int N = 10000000;

    private static void printArray(int[] arr, int n) {
        for (int i = 0; i< n; i++){
            System.out.print(arr[i]+" ");
        }
        System.out.println();
    }

    public static void main(String[] args) {

        int [] randomIntsArray = IntStream.generate(() -> new Random().nextInt(1000000)).limit(N).toArray();
        int [] copy = Arrays.copyOf(randomIntsArray, N);

        // printArray(randomIntsArray, N);
        long startTime = System.nanoTime();
        QuickSortThreads qst = new QuickSortThreads(randomIntsArray, 0 , N - 1);
        qst.start();
        long endTime = System.nanoTime();
        long multiThreadTime = endTime - startTime;
        //printArray(randomIntsArray, N);
        System.out.println("Multi thread time: "+multiThreadTime);

        long startTime2 = System.nanoTime();
        QuickSortSingle qst2 = new QuickSortSingle();
        qst2.quickSort(copy,0, N - 1);
        long endTime2 = System.nanoTime();
        long singleThreadTime = endTime2 - startTime2;
        //printArray(copy, N);
        System.out.println("Single thread time: "+singleThreadTime);
	   
    }
}
