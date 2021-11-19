import org.junit.Before;


import static org.junit.Assert.*;

public class dHeapTester {
    dHeap default_bin_max_heap;

    dHeap fourd_bin_max_heap;

    @Before
    public void setup(){
        default_bin_max_heap = new dHeap();
        fourd_bin_max_heap = new dHeap(4, 10, true );
    }













}