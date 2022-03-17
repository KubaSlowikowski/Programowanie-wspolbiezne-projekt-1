package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class QuickSortExecutor {

    private final ExecutorService executor;

    public QuickSortExecutor() {
        this.executor = Executors.newCachedThreadPool();
    }

    public List<Integer> quickSort(List<Integer> unsorted) {
        if (unsorted.size() == 1) {
            return unsorted;
        }

        Integer pivot = unsorted.remove(0);

        List<Integer> lower = new ArrayList<>(); // liczby mniejsze od pivota
        List<Integer> greaterOrEqual = new ArrayList<>(); // liczby większe lub równe pivotowi

        for (Integer i : unsorted) {
            if (i < pivot) {
                lower.add(i);
            } else {
                greaterOrEqual.add(i);
            }
        }

        Callable<List<Integer>> lowerCallable =
                lower.isEmpty() ?
                        () -> lower :
                        () -> quickSort(lower);

        Callable<List<Integer>> greaterOrEqualCallable =
                greaterOrEqual.isEmpty() ?
                        () -> greaterOrEqual :
                        () -> quickSort(greaterOrEqual);


        List<Integer> sorted = new ArrayList<>();
        try {
            List<Future<List<Integer>>> futures = executor.invokeAll(List.of(lowerCallable, greaterOrEqualCallable));

            sorted.addAll(futures.get(0).get());
            sorted.add(pivot);
            sorted.addAll(futures.get(1).get());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return sorted;
    }

    public void shutDownExecutor() {
        executor.shutdown();
    }
}
