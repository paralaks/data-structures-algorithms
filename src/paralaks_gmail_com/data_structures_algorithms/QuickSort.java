package paralaks_gmail_com.data_structures_algorithms;

public class QuickSort extends Sort {
  @Override
  protected <T extends Comparable<T>> void sortItems(T[] items, int direction) {
    quickSortItems(items, direction, 0, items.length - 1);
  }

  protected <T extends Comparable<T>> void quickSortItems(T[] items, int direction, int start, int end) {
    if (start >= end) {
      return;
    }

    T pivot = items[end];
    int iPartition = start;

    for (int i = start; i < end; i++) {
      if (items[i].compareTo(pivot) * direction <= 0) {
        swap(items, i, iPartition++);
      }
    }
    swap(items, iPartition, end);

    quickSortItems(items, direction, start, iPartition - 1);
    quickSortItems(items, direction, iPartition + 1, end);
  }
}
