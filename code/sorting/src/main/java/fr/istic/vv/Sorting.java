package fr.istic.vv;

import java.util.Comparator;

/**
 * Sorting class with bubblesort, quicksort and mergesort
 */
public class Sorting {

    /**
     * Bubble sort
     *
     * @param array      : the array to sort
     * @param comparator : object comparator
     * @param <T>        : type of the object
     * @return a sorted array
     */
    public static <T> T[] bubblesort(T[] array, Comparator<T> comparator) {
        for (int i = array.length; i > 0; i--) {
            for (int j = 0; j < i - 1; j++) {
                changeElements(array, j, j + 1, comparator);
            }
        }
        return array;
    }

    /**
     * Quick sort of an array
     *
     * @param array      : array to sort
     * @param comparator : object comparator
     * @param <T>        : type of the object
     * @return return a sorted array
     */
    public static <T> T[] quicksort(T[] array, Comparator<T> comparator) {
        return quicksort(array, 0, array.length - 1, comparator);
    }

    /**
     * Quick sort of an array of object
     *
     * @param array      : the array to sort
     * @param begin      : the beginning of the array
     * @param end        : the end of the array
     * @param comparator : object comparator
     * @param <T>        : type of the object
     * @return return a sorted array
     */
    private static <T> T[] quicksort(T[] array, int begin, int end, Comparator<T> comparator) {
        if (begin < end) {
            int partition = partition(array, begin, end, comparator);
            quicksort(array, begin, partition - 1, comparator);
            quicksort(array, partition + 1, end, comparator);
        }
        return array;
    }

    /**
     * Create a partition in the array with a pivot
     *
     * @param array      : array to sort
     * @param begin      : biginning of the array
     * @param end        : end of the array
     * @param comparator : object comparator
     * @param <T>        : object type
     * @return a sorted array
     */
    private static <T> int partition(T[] array, int begin, int end, Comparator<T> comparator) {
        T pivot = array[end];
        int i = begin - 1;

        for (int j = begin; j < end; j++) {
            if (comparator.compare(pivot, array[j]) > 0) {
                i++;
                T temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        T temp = array[i + 1];
        array[i + 1] = array[end];
        array[end] = temp;
        return i + 1;
    }

    /**
     * Change an element at the position posElem1 to the position posElem2 & vice versa depending on the values of each object
     *
     * @param array      : array
     * @param posElem1   : position of element 1
     * @param posElem2   : position of element 2
     * @param comparator : object comparator
     * @param <T>        : type of the object
     */
    public static <T> void changeElements(T[] array, int posElem1, int posElem2, Comparator<T> comparator) {
        int comparison = comparator.compare(array[posElem1], array[posElem2]);
        if (comparison > 0) {
            T temp = array[posElem1];
            array[posElem1] = array[posElem2];
            array[posElem2] = temp;
        }
    }

    /**
     * Merge sort of an array
     *
     * @param array      : the array to sort
     * @param comparator : object comparator
     * @param <T>        : type of object
     * @return a sorted array
     */
    public static <T> T[] mergesort(T[] array, Comparator<T> comparator) {
        int sizeArray = array.length;
        if (sizeArray > 0) {
            mergesort(array, 0, sizeArray - 1, comparator);
        }
        return null;
    }

    /**
     * Merge sort of an array
     *
     * @param array      : the array to sort
     * @param begin      : beginning of the array
     * @param end        : en of the array
     * @param comparator : object comparator
     * @param <T>        : type of object
     * @return a sorted array
     */
    private static <T> T[] mergesort(T[] array, int begin, int end, Comparator<T> comparator) {
        if (begin != end) {
            int middle = (begin + end) / 2;
            mergesort(array, begin, middle, comparator);
            mergesort(array, middle + 1, end, comparator);
            mergeAll(array, begin, middle, end, comparator);
        }
        return array;
    }

    /**
     * Merge the arrays
     *
     * @param array      : array to sort
     * @param begin      : beginning of the array
     * @param middle     : middle of the array
     * @param end        : end of the array
     * @param comparator : the object comparator
     * @param <T>        type of the object
     */
    private static <T> void mergeAll(T[] array, int begin, int middle, int end, Comparator<T> comparator) {
        int sizeArray1 = middle - begin + 1;
        int sizeArray2 = end - middle;

        T leftArray[] = (T[]) new Object[sizeArray1];
        T rightArray[] = (T[]) new Object[sizeArray2];

        // Initialize the values with the values of the 2 arrays
        for (int i = 0; i < sizeArray1; i++)
            leftArray[i] = array[begin + i];
        for (int j = 0; j < sizeArray2; j++)
            rightArray[j] = array[middle + 1 + j];


        int i, j, k;
        i = 0;
        j = 0;
        k = begin;

        // Walk into the 2 arrays and order the values
        while (i < sizeArray1 && j < sizeArray2) {
            if (comparator.compare(leftArray[i], rightArray[j]) < 0) {
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
            }
            k++;
        }

        // Copy all element from the left Array into the array to return
        while (i < sizeArray1) {
            array[k] = leftArray[i];
            i++;
            k++;
        }

        // Copy all element from the right Array into the array to return
        while (j < sizeArray2) {
            array[k] = rightArray[j];
            j++;
            k++;
        }
    }

}
