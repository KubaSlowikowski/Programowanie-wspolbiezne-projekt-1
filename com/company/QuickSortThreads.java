package com.company;

import static com.company.Helpers.partition;

public class QuickSortThreads extends Thread{
    private int[] arr;
    private int low, high;
    private static int numberOfThreads = Runtime.getRuntime().availableProcessors();
    private static int count = 0;

    QuickSortThreads(int[] arr, int low, int high){
        this.arr = arr;
        this.low = low;
        this.high = high;
    }

    public void run(){
        if (high>low){
            int i = partition(arr,low,high);
            if (count + 1 < numberOfThreads){
                QuickSortThreads quickSortLow = new QuickSortThreads(arr, low, i-1);
                QuickSortThreads quickSortHigh = new QuickSortThreads(arr, i+1, high);
                count += 2;
                quickSortLow.start();
                quickSortHigh.start();
                try{
                    quickSortLow.join();
                    quickSortHigh.join();
                }
                catch (InterruptedException ignored){}
            }
            else if (count < numberOfThreads) {
                QuickSortThreads quickSort  = new QuickSortThreads(arr, low, i-1);
                count += 1;
                quickSort.start();
                sort(arr,i+1,high);
                try{
                    quickSort.join();
                }
                catch (InterruptedException ignored){}
            }
            else{
                sort(arr,low,i-1);
                sort(arr,i+1,high);
            }
        }
    }

    private static void sort(int[] arr, int low, int high){
        if (high>low) {
            int i = partition(arr,low,high);
            sort(arr,low,i-1);
            sort(arr,i+1,high);
        }
    }
}
