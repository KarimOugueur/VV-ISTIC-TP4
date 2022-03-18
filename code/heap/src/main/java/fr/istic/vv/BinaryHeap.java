package fr.istic.vv;

import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * Class BinaryHeap 
 */

class BinaryHeap<T> {

    private int heapSize;
    private T[] heap;
    private Comparator<T> comparator;

  /**
     * Constructor
     *
     * @param comparator : comparator
     * @param heap       : heap array
     */
    public BinaryHeap(Comparator<T> comparator, T[] heap) {
        heapSize = 0;
        this.comparator = comparator;
        this.heap = heap;

    }

      /**
     * Get minimum from the Heap
     * @returns and removes the minimum object in the heap.
     * If the heap is empty it throws a NotSuchElementException
     */
    public T pop() {
        if(isEmpty()) {throw new NoSuchElementException("Heap is empty");}

        return delete(0);
    }

    /**
     *
     * get the minimum object
     * @returns the minimum object but it does not remove it from the BinaryHeap
     */
    public T peek() {
        if(isEmpty()) {throw new NoSuchElementException("Heap is empty");}
        return heap[0];
    }

    /**
     * adds an element to the BinaryHeap
     * @param element: element to push in the heap 
     */
    public void push(T element) {
        if (isFull()) {throw new NoSuchElementException("Heap is full");}

        heap[heapSize++] = element;
        heapifyUp(heapSize - 1);
    }

    /**
     *  Count the number of elements in the BinaryHeap
     * @returns the number of elements in the BinaryHeap
     */
    public int count() {
        return this.heap.length;
    }

    /**
     * Function heapifUp
     *This function is used when we insert a new element to a heap.
     * When inserting a new element, we add it at the bottom of the heap tree,
     * and move up the tree while comparing to the current parent element
     * and swapping if needed.
     **/
    private void heapifyUp(int childIndex) {

        T tmp = heap[childIndex];
        while (childIndex > 0 && comparator.compare(tmp, heap[parent(childIndex)]) == -1) {
            heap[childIndex] = heap[parent(childIndex)];
            childIndex = parent(childIndex);
        }
        heap[childIndex] = tmp;
    }

    /**
     * Function to remove element at an index
     * Remove element and call HeapifyDown function
     * @param index of element to remove
     * @return element removed
     */
    public T delete(int index) {
        if (isEmpty()) {throw new NoSuchElementException("BinaryHeap is empty");}
        T keyItem = heap[index];
        heap[index] = heap[heapSize - 1];
        heapSize--;
        heapifyDown(index);
        return keyItem;
    }

    /**
     * Function HeapifyDown
     * This function is used when we remove the top element from a heap.
     * Removal of an element is done by swapping the top element with the last element at the bottom of the tree,
     * removing the last element, and then heapfying the new top element down to maintain the heap property.
     * @param index
     */
    private void heapifyDown(int index) {
        int child;
        T tmp = heap[index];
        while (kthChild(index, 1) < heapSize) {
            child = minimumChild(index);
            if (comparator.compare(heap[child],tmp) <0) {
                heap[index] = heap[child];
            } else {
                break;
            }
            index = child;
        }
        heap[index] = tmp;
    }

    /**
     * Allows to get
     * @param index
     * @return
     */
    private int minimumChild(int index) {
        int minchildIndex = kthChild(index, 1);
        int k = 2;
        int position = kthChild(index, k);
        while (k <= 2 && position < heapSize) {
            if (comparator.compare(heap[position],heap[minchildIndex])==-1) {
                minchildIndex = position;
            }
            position = kthChild(index, k++);
        }
        return minchildIndex;
    }

    /**
     * Check if BinaryHeap is empty
     * @return
     */
    public boolean isEmpty() {
        return heapSize == 0;
    }

    /**
     * check if binaryHeap is full
     * @return Boolean
     */
    public boolean isFull() {
        return heapSize == heap.length;
    }

    /**
     * @param index
     * @return index parent of index
     */
    private int parent(int index) {
        return (index - 1) / 2;
    }

    /**
     * Function to get index of k th child of i
     * @param index child
     * @param k k=1 left child k=2 right child
     * @return index of k th child of index
     */
    private int kthChild(int index, int k) {
        return 2 * index + k;
    }
}