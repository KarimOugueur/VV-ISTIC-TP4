package fr.istic.vv;
import java.util.Comparator;

import net.jqwik.api.*;
import net.jqwik.engine.providers.IntegerArbitraryProvider;


public class BinaryHeapTest {

    Comparator<Integer> comparator = new Comparator<Integer>() {
        @Override
        public int compare(Integer leftElement, Integer rightElement) {
            return leftElement.compareTo(rightElement);
        }
    };

    @Property
    boolean heapTest(@ForAll("binaryHeap") BinaryHeap<Integer> heap) {
        if (heap.count() == 0) return true;
        Integer min = heap.pop();
        if (comparator.compare(min, min = heap.pop()) > 0) {
            return false;
        }
        return true;
    }

    @Provide
    Arbitrary<BinaryHeap<Integer>> binaryHeap() {
        BinaryHeap<Integer> binaryHeap = new BinaryHeap<Integer>(comparator, new Integer[3]);
        Arbitrary<Integer> ints = Arbitraries.integers().between(1, 2);
        return Arbitraries.integers().between(0, 3).map(i -> {
            ints.forEachValue(val -> binaryHeap.push(i));
            return binaryHeap;
        });
    }

}
