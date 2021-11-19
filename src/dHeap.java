/*
 * Name: Kevin Morales-Nguyen
 * PID:  A17186624
 */

import java.util.*;

/**
 * This class implements a min/max heap by using an array.
 * 
 * @param <T> Generic type
 */
public class dHeap<T extends Comparable<? super T>> implements dHeapInterface<T> {

    private static final int DEFAULT_SIZE = 6;
    private T[] heap; // heap array
    private int d; // branching factor
    private int nelems; // number of elements
    private boolean isMaxHeap; // boolean to indicate whether heap is max or min

    /**
     * Initializes a binary max heap with capacity = 6
     */
    @SuppressWarnings("unchecked")
    public dHeap() {
        this.heap = (T[]) new Comparable[DEFAULT_SIZE];
        this.d = 2;
        this.nelems = 0;
        this.isMaxHeap = true;
    }

    /**
     * Initializes a binary max heap with a given initial capacity.
     *
     * @param heapSize The initial capacity of the heap.
     */
    @SuppressWarnings("unchecked")
    public dHeap(int heapSize) {
        this.heap = (T[]) new Comparable[heapSize];
        this.d = 2;
        this.nelems = 0;
        this.isMaxHeap = true;
    }

    /**
     * Initializes a d-ary heap (with a given value for d), with a given initial
     * capacity.
     *
     * @param d         The number of child nodes each node in the heap should have.
     * @param heapSize  The initial capacity of the heap.
     * @param isMaxHeap indicates whether the heap should be max or min
     * @throws IllegalArgumentException if d is less than one.
     */
    @SuppressWarnings("unchecked")
    public dHeap(int d, int heapSize, boolean isMaxHeap) throws IllegalArgumentException {
        this.heap = (T[]) new Comparable[heapSize];
        this.d = d;
        this.nelems = 0;
        this.isMaxHeap = isMaxHeap;
    }

    /**
     * This method returns the number of elements in the heap
     * @return number of elements in heap
     */
    @Override
    public int size() {
        return this.nelems;
    }

    /**
     * This method adds the new element to the array and then calls
     * the bubbleUp helper method to make sure it is in the right place
     * @param data data to insert
     * @throws NullPointerException if data is null
     */
    @Override
    public void add(T data) throws NullPointerException {
        //check if array is full, then resize
        if(data == null){
            throw new NullPointerException();
        }

        if ((nelems + 1) == this.heap.length ) {
            this.resize();
        }

        //add new data to end of array
        this.heap[this.nelems++] = data;
        //bubbleUp newly added element
        bubbleUp(this.nelems - 1);
    }

    /**
     * This method removes the root of the array and returns the element
     * @return element at root of array
     * @throws NoSuchElementException if tree is empty
     */
    @Override
    public T remove() throws NoSuchElementException {
        if (nelems == 0){
            throw new NullPointerException();
        }

        T keyItem = this.heap[0]; // grab root
        heap[0] = heap[nelems - 1]; //swap root with last element
        nelems--; // decrement
        trickleDown(0); // call trickle down helper method to place
        //swapper element if correct place
        return keyItem;
    }

    /**
     * This mehtod clears the array by setting it to a new
     * array and reseting nelems
     */
    @SuppressWarnings("unchecked")
    @Override
    public void clear() {
        this.heap = (T[]) new Comparable[DEFAULT_SIZE];
        this.nelems = 0;
    }

    public T element() throws NoSuchElementException {
        if(nelems == 0){
            throw new NoSuchElementException();
        }
        return this.heap[0];
    }

    /**
     * This helper method resizes the array by doubling its length and copying
     * existing elements
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        T[] new_array = (T[]) new Comparable[this.heap.length * 2];
        for(int i = 0; i < this.heap.length;i++){
            new_array[i] = this.heap[i];
        }
        this.heap = new_array;
    }

    /**
     * This helper method returns the parent index,
     * by using a simple formula
     * @param index index of child
     * @return index of parent
     */
    private int parent(int index) {

        if(nelems == 0 || nelems == 1){
            return 0;
        }

        return (int) Math.floor(( index - 1) / d) ;
    }


    /**
     * This method implements bubbleUp functionality which places the newly added
     * element into the correct spot to mantain heap rules
     * @param index index of element to potentially bubble up
     */
    private void bubbleUp(int index) {
        if(this.isMaxHeap) {
            T bubble_elem = heap[index]; // copy into temp element
            //keep swapping up with parent until at root node or element is not larger than parent
            while (index > 0 && bubble_elem.compareTo(heap[this.parent(index)]) > 0) {
                heap[index] = heap[this.parent(index)]; // parent < bubble_elem, so swap parent
                index = this.parent(index); //new index to potentially swap is parent
            }
            heap[index] = bubble_elem; // set parent element to bubbleup element
        }
        else{ // same thing but for min-heap
            T bubble_elem = heap[index];
            while (index > 0 && bubble_elem.compareTo(heap[this.parent(index)]) < 0) {
                heap[index] = heap[this.parent(index)];
                index = this.parent(index);
            }
            heap[index] = bubble_elem;
        }
    }

    /**
     * This helper method implements trickle down which helps
     * the newly removed elemt swap find the right place
     * @param index
     */
    private void trickleDown(int index) {
        int child;
        T trickle_elem = heap[index]; // copy into temp elemtn

        if(this.isMaxHeap) {
            while (kth_child(index, 1) < nelems) { //
                child = min_child(index);
                if (heap[child].compareTo(trickle_elem) > 0)
                    heap[index] = heap[child];
                else
                    break;
                index = child;
            }
            heap[index] = trickle_elem;
        }
        else{
            while (kth_child(index, 1) < nelems) {
                child = min_child(index);
                if (heap[child].compareTo(trickle_elem) < 0)
                    heap[index] = heap[child];
                else
                    break;
                index = child;
            }
            heap[index] = trickle_elem;
        }
    }

    /**
     * This helper method helps find the min/max child to swap with the trickle
     * down element
     * @param i
     * @param k
     * @return index of element
     */
    private int kth_child(int i, int k)
    {
        return d * i + k;
    }

    /**
     * This method finds the index of the min/max child to swap with the trickle
     * down element
     * @param index
     * @return index of optimal child
     */
    private int min_child(int index)
    {
        if(this.isMaxHeap) {
            int bestChild = kth_child(index, 1); // make a variable to compare against
            int k = 2; // will compare kth = 2 to d+1
            int pos = kth_child(index, k); // grab second child
            while ((k <= d+1) && (pos < nelems)) { // while in subtree and not out of bounds
                if (heap[pos].compareTo(heap[bestChild]) > 0) { // find largest child > trickle
                    bestChild = pos;
                }

                pos = kth_child(index, k++); // increment to next child

            }
            return bestChild; // return index of optimal min/max
        }
        else{ // same but for min
            int bestChild = kth_child(index, 1);
            int k = 2;
            int pos = kth_child(index, k);
            while ((k <= d) && (pos < nelems)) {
                if (heap[pos].compareTo(heap[bestChild]) < 0)
                    bestChild = pos;
                pos = kth_child(index, k++);
            }
            return bestChild;

        }

    }

}
