package com.company;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

import static com.company.Helpers.partition;

public class QuickSortFJPool extends RecursiveAction {

    private final int[] arr;
    private int low, high;
    private static final int THRESHOLD = 10;
    private final QuickSortSingle quickSortSingle = new QuickSortSingle();

    public QuickSortFJPool(int[] arr, int low, int high) {
        this.arr = arr;
        this.low = low;
        this.high = high;
    }

    @Override
    protected void compute() {
        if (high > (low + THRESHOLD)) {
            int i = partition(arr, low, high); // pivot

            System.out.println(("Processed by "
                    + Thread.currentThread().getName()));

            QuickSortFJPool quickSortLow = new QuickSortFJPool(arr, low, i - 1);
            QuickSortFJPool quickSortHigh = new QuickSortFJPool(arr, i + 1, high);
            ForkJoinTask.invokeAll(quickSortLow, quickSortHigh);
        } else if (high > low) {
            quickSortSingle.quickSort(arr, low, high);
        }
    }
}
