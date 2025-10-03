package day_14.Arrays;

import org.junit.runner.manipulation.Sorter;
import sun.misc.Unsafe;

public class yuanma {
    //Arrays中Sort方法中的调用的普通串行排序
    static void sort(Sorter sorter, int[] a, int bits, int low, int high) {
        while (true) {
            int end = high - 1, size = high - low;
            /*
             * Run mixed insertion sort on small non-leftmost parts.
             */
            if (size < MAX_MIXED_INSERTION_SORT_SIZE + bits && (bits & 1) > 0) {
                sort(int.class, a, Unsafe.ARRAY_INT_BASE_OFFSET, low, high, DualPivotQuicksort::mixedInsertionSort);
                return;
            }

            /*
             * Invoke insertion sort on small leftmost part.
             */
            if (size < MAX_INSERTION_SORT_SIZE) {
                sort(int.class, a, Unsafe.ARRAY_INT_BASE_OFFSET, low, high, DualPivotQuicksort::insertionSort);
                return;
            }

            /*
             * Check if the whole array or large non-leftmost
             * parts are nearly sorted and then merge runs.
             */
            if ((bits == 0 || size > MIN_TRY_MERGE_SIZE && (bits & 1) > 0)
                    && tryMergeRuns(sorter, a, low, size)) {
                return;
            }

            /*
             * Switch to heap sort if execution
             * time is becoming quadratic.
             */
            if ((bits += DELTA) > MAX_RECURSION_DEPTH) {
                heapSort(a, low, high);
                return;
            }

            /*
             * Use an inexpensive approximation of the golden ratio
             * to select five sample elements and determine pivots.
             */
            int step = (size >> 3) * 3 + 3;

            /*
             * Five elements around (and including) the central element
             * will be used for pivot selection as described below. The
             * unequal choice of spacing these elements was empirically
             * determined to work well on a wide variety of inputs.
             */
            int e1 = low + step;
            int e5 = end - step;
            int e3 = (e1 + e5) >>> 1;
            int e2 = (e1 + e3) >>> 1;
            int e4 = (e3 + e5) >>> 1;
            int a3 = a[e3];

            /*
             * Sort these elements in place by the combination
             * of 4-element sorting network and insertion sort.
             *
             *    5 ------o-----------o------------
             *            |           |
             *    4 ------|-----o-----o-----o------
             *            |     |           |
             *    2 ------o-----|-----o-----o------
             *                  |     |
             *    1 ------------o-----o------------
             */
            if (a[e5] < a[e2]) { int t = a[e5]; a[e5] = a[e2]; a[e2] = t; }
            if (a[e4] < a[e1]) { int t = a[e4]; a[e4] = a[e1]; a[e1] = t; }
            if (a[e5] < a[e4]) { int t = a[e5]; a[e5] = a[e4]; a[e4] = t; }
            if (a[e2] < a[e1]) { int t = a[e2]; a[e2] = a[e1]; a[e1] = t; }
            if (a[e4] < a[e2]) { int t = a[e4]; a[e4] = a[e2]; a[e2] = t; }

            if (a3 < a[e2]) {
                if (a3 < a[e1]) {
                    a[e3] = a[e2]; a[e2] = a[e1]; a[e1] = a3;
                } else {
                    a[e3] = a[e2]; a[e2] = a3;
                }
            } else if (a3 > a[e4]) {
                if (a3 > a[e5]) {
                    a[e3] = a[e4]; a[e4] = a[e5]; a[e5] = a3;
                } else {
                    a[e3] = a[e4]; a[e4] = a3;
                }
            }

            // Pointers
            int lower; // The index of the last element of the left part
            int upper; // The index of the first element of the right part

            /*
             * Partitioning with 2 pivots in case of different elements.
             */
            if (a[e1] < a[e2] && a[e2] < a[e3] && a[e3] < a[e4] && a[e4] < a[e5]) {
                /*
                 * Use the first and fifth of the five sorted elements as
                 * the pivots. These values are inexpensive approximation
                 * of tertiles. Note, that pivot1 < pivot2.
                 */
                int[] pivotIndices = partition(int.class, a, Unsafe.ARRAY_INT_BASE_OFFSET, low, high, e1, e5, DualPivotQuicksort::partitionDualPivot);
                lower = pivotIndices[0];
                upper = pivotIndices[1];



                /*
                 * Sort non-left parts recursively (possibly in parallel),
                 * excluding known pivots.
                 */
                if (size > MIN_PARALLEL_SORT_SIZE && sorter != null) {
                    sorter.forkSorter(bits | 1, lower + 1, upper);
                    sorter.forkSorter(bits | 1, upper + 1, high);
                } else {
                    sort(sorter, a, bits | 1, lower + 1, upper);
                    sort(sorter, a, bits | 1, upper + 1, high);
                }

            } else { // Use single pivot in case of many equal elements

                /*
                 * Use the third of the five sorted elements as the pivot.
                 * This value is inexpensive approximation of the median.
                 */
                int[] pivotIndices = partition(int.class, a, Unsafe.ARRAY_INT_BASE_OFFSET, low, high, e3, e3, DualPivotQuicksort::partitionSinglePivot);
                lower = pivotIndices[0];
                upper = pivotIndices[1];
                /*
                 * Sort the right part (possibly in parallel), excluding
                 * known pivot. All elements from the central part are
                 * equal and therefore already sorted.
                 */
                if (size > MIN_PARALLEL_SORT_SIZE && sorter != null) {
                    sorter.forkSorter(bits | 1, upper, high);
                } else {
                    sort(sorter, a, bits | 1, upper, high);
                }
            }
            high = lower; // Iterate along the left part
        }
    }
}
