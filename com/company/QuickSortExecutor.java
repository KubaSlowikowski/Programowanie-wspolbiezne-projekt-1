package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class QuickSortExecutor {

    private final ExecutorService executor;

    public QuickSortExecutor() {
        this.executor = Executors.newFixedThreadPool(1);
    }

    public List<Integer> quickSort(List<Integer> unsorted) {
        System.out.println(Thread.currentThread().getId() + " unsorted " + unsorted);

        if (unsorted.size() == 1) return unsorted;

        Integer pivot = unsorted.remove(0);
        System.out.println(Thread.currentThread().getId() + " pivot " + pivot);
        List<Integer> lower = new ArrayList<>();
        List<Integer> greaterOrEqual = new ArrayList<>();

        for (Integer i : unsorted) {
            if (i < pivot) {
                lower.add(i);
            } else {
                greaterOrEqual.add(i);
            }
        }
        System.out.println(Thread.currentThread().getId() + " lower " + lower);
        System.out.println(Thread.currentThread().getId() + " greater " + greaterOrEqual);

//        List<Integer> lowerSorted = null;
//        List<Integer> upperOrEqualSorted = null;
//
//        if (lower.isEmpty()) {
//            lowerSorted = lower;
//        } else {
//
//        }

        Callable<List<Integer>> lowerCallable = lower.isEmpty() ? () -> lower : () -> quickSort(lower);
        Callable<List<Integer>> greaterOrEqualCallable = greaterOrEqual.isEmpty() ? () -> greaterOrEqual : () -> quickSort(greaterOrEqual);

//        Future<List<Integer>> lowerSortedFuture = lower.isEmpty() ? executor.submit(() -> lower) : executor.submit(() -> quickSort(lower));
//        Future<List<Integer>> upperOrEqualSortedFuture = greaterOrEqual.isEmpty() ? executor.submit(() -> greaterOrEqual) : executor.submit( () -> quickSort(greaterOrEqual));

        List<Integer> result = new ArrayList<>();
        try {
            List<Future<List<Integer>>> futures = executor.invokeAll(List.of(lowerCallable, greaterOrEqualCallable), 200, TimeUnit.MILLISECONDS);
            result.addAll(futures.get(0).get());
            result.add(pivot);
            result.addAll(futures.get(1).get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
//            result = lowerSortedFuture.get(2000, TimeUnit.MILLISECONDS);
//            result.add(pivot);
//            List<Integer> upperOrEqualSorted = upperOrEqualSortedFuture.get(2000, TimeUnit.MILLISECONDS);
//            result.addAll(upperOrEqualSorted);

        return result;
    }

    private Future<List<Integer>> quickSortFuture(List<Integer> unsorted) {
        return executor.submit(() -> List.of(1));
    }

}
