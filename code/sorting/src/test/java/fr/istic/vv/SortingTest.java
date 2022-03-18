package fr.istic.vv;

import net.jqwik.api.*;

import java.util.Comparator;
import java.util.stream.IntStream;

/**
 * Sorting test
 */
public class SortingTest {

    private Comparator comparator;

    // Tests if the arrays are sorted after each sort
    @Property
    boolean arrayIsSorted(@ForAll("arrayIntegersGenerator") Integer[] array) {

        Comparator<Integer> comparator = (integer, t1) -> {
            if (integer > t1) return 1;
            else if (integer < t1) return -1;
            return 0;
        };
        Integer[] duplicateArray = array.clone();
        Integer[] duplicateArray2 = array.clone();

        boolean isSortedAfterBubbleSort = executeSort(array, "Bubble Sort", comparator);
        boolean isSortedAfterMergeSort = executeSort(duplicateArray, "Merge Sort", comparator);
        boolean isSortedAfterQuickSort = executeSort(duplicateArray2, "Quick Sort", comparator);
        return isSortedAfterBubbleSort && isSortedAfterMergeSort && isSortedAfterQuickSort;
    }

    /**
     * Execute a sort to an array
     *
     * @param array      : array to sort
     * @param typeSort   : type of sort
     * @param comparator : object comparator
     * @return if the array is sorted after the call of functions
     */
    boolean executeSort(Integer[] array, String typeSort, Comparator comparator) {
        displayArray("\n\nBefore sorting ...", typeSort, array);
        boolean isSorted = false;
        switch (typeSort) {
            case "Bubble Sort":
                isSorted = checkIsSortedObjectArrayForCustomSort(Sorting.bubblesort(array, comparator), comparator);
                break;
            case "Merge Sort":
                isSorted = checkIsSortedObjectArrayForCustomSort(Sorting.mergesort(array, comparator), comparator);
                break;
            case "Quick Sort":
                isSorted = checkIsSortedObjectArrayForCustomSort(Sorting.quicksort(array, comparator), comparator);
                break;
            default:
                break;
        }

        displayArray("\nAfter sorting ...", typeSort, array);
        return isSorted;
    }

    /**
     * Generate an array of integers randomly
     *
     * @return an array of integers
     */
    @Provide
    public Arbitrary<Integer[]> arrayIntegersGenerator() {
        Arbitrary<Integer> integerArbitrary = Arbitraries.integers().between(-100, 100);
        return integerArbitrary
                .array(Integer[].class).ofMinSize(1).ofMaxSize(30);
    }

    /**
     * Check if the array is sorted
     *
     * @param array      : array potentially sorted
     * @param comparator : comparator of object
     * @return true if it is sorted
     */
    public boolean checkIsSortedObjectArrayForCustomSort(
            Integer[] array, Comparator<Integer> comparator) {
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator should not be null.");
        }
        if (array == null || array.length <= 1) {
            return true;
        }
        return IntStream.range(0, array.length - 1)
                .noneMatch(i -> comparator.compare(array[i], array[i + 1]) > 0);
    }

    /**
     * Method to display the array like [ 1, 2, 5, 6, 7 ]
     *
     * @param when     : before or after sorted
     * @param typeSort : type of sort
     * @param array    : the array to display
     */
    private void displayArray(String when, String typeSort, Integer[] array) {
        int i = 0;
        StringBuilder sb = new StringBuilder();
        sb.append(when).append(" for ").append(typeSort).append("\n");
        sb.append("[");
        for (Integer integer : array) {
            if (i != array.length - 1) {
                sb.append(" ").append(integer).append(",");
            } else {
                sb.append(" ").append(integer);
            }
            i++;
        }
        System.out.println(sb + "]");
    }

}
