package com.company;

import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

import static com.company.Helpers.partition;

public class QuickSortForkJoinPool extends RecursiveAction {

    private static final int THRESHOLD = 100;

    private final int[] arr;
    private int low, high;
    private final QuickSortSingle quickSortSingle = new QuickSortSingle();

    public QuickSortForkJoinPool(int[] arr, int low, int high) {
        this.arr = arr;
        this.low = low;
        this.high = high;
    }

    @Override
    protected void compute() {
        if (high > (low + THRESHOLD)) {
            int i = partition(arr, low, high); // pivot

            System.out.println(("Processed by " + Thread.currentThread().getName()));

            QuickSortForkJoinPool quickSortLow = new QuickSortForkJoinPool(arr, low, i - 1);
            QuickSortForkJoinPool quickSortHigh = new QuickSortForkJoinPool(arr, i + 1, high);

            invokeAll(quickSortLow, quickSortHigh);
        } else if (high > low) {
            quickSortSingle.quickSort(arr, low, high);
        }
    }
}
