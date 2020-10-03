package paralaks_gmail_com.data_structures_algorithms.sort;

import java.lang.reflect.Array;

public class MergeSort extends Sort {
  @Override
  @SuppressWarnings("unchecked")
  protected <T extends Comparable<T>> void sortItems(T[] items, int direction) {
    // Unusual way of initializing a generic array.
    T[] tempItems = (T[]) Array.newInstance(items[0].getClass(), items.length);

    mergeSortItems(items, tempItems, direction, 0, items.length - 1);
  }

  protected <T extends Comparable<T>> void mergeSortItems(T[] items, T[] tempItems, int direction, int start, int end) {
    if (start >= end) {
      return;
    }

    // Partition.
    int mid = (start + end) / 2;
    mergeSortItems(items, tempItems, direction, start, mid);
    mergeSortItems(items, tempItems, direction, mid + 1, end);

    // Sort while merging left + right partitions.
    int iLeft = start;
    int iRight = mid + 1;
    int iMerged = start;

    // Sort and merge.
    while (iLeft <= mid && iRight <= end) {
      tempItems[iMerged++] = items[iLeft].compareTo(items[iRight]) * direction <= 0
                             ? items[iLeft++]
                             : items[iRight++];
    }

    // Copy remaining items.
    while (iLeft <= mid) {
      tempItems[iMerged++] = items[iLeft++];
    }

    while (iRight <= end) {
      tempItems[iMerged++] = items[iRight++];
    }

    // Copy sorted slice back to original array.
    System.arraycopy(tempItems, start, items, start, end - start + 1);
  }
}
