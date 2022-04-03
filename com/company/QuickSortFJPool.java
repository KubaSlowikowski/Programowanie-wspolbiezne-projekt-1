package com.company;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

import static com.company.Helpers.partition;

public class QuickSortFJPool extends RecursiveAction {

    private final ForkJoinPool pool; //niepotrzebne
    private final int[] arr;
    private int low, high;

    public QuickSortFJPool(ForkJoinPool pool, int[] arr, int low, int high) {
        this.pool = pool;
        this.arr = arr;
        this.low = low;
        this.high = high;
    }

    @Override
    protected void compute() {
        if (high > low) {
            int i = partition(arr, low, high);
            QuickSortFJPool quickSortLow = new QuickSortFJPool(pool, arr, low, i - 1);
            QuickSortFJPool quickSortHigh = new QuickSortFJPool(pool, arr, i + 1, high);
            ForkJoinTask.invokeAll(quickSortLow, quickSortHigh);
        }
    }
}
