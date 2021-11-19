/*
 * Name: Kevin Morales-Nguyen
 * PID:  A17186624
 */

/**
 * This class provides sorting by using a heap
 */
public class HeapSort {

    /**
     * This method takes an array and sorts it using a heap
     */
    public static void heapSort(int[] arr, int start, int end) {
        dHeap sorted = new dHeap(2,10,false);
        for(int i = start; i <= end;i++){
            sorted.add(arr[i]);
            System.out.print(arr[i] + " ");

        }

        System.out.println();

        for(int l = start; l <= end;l++){
            arr[l] = (int)sorted.remove();
            System.out.print(arr[l] + " ");

        }
        System.out.println();


    }
}
