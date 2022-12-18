package paralaks_gmail_com.data_structures_algorithms.sort;

public class QuickSort extends Sort {
  @Override
  protected <T extends Comparable<T>> void sortItems(T[] items, DIRECTION direction) {
    int d = direction == DIRECTION.DESCENDING ? -1 : 1;
    quickSortItems(items, d, 0, items.length - 1);
  }

  protected <T extends Comparable<T>> void quickSortItems(T[] items, int direction, int start, int end) {
    if (start >= end) {
      return;
    }

    // Hoare partition scheme.
    T pivot = items[(start + end) / 2];
    int i = start;
    int j = end;

    while (true) {
      while (items[i].compareTo(pivot) * direction < 0) {
        i++;
      }

      while (items[j].compareTo(pivot) * direction > 0) {
        j--;
      }

      if (i >= j) {
        break;
      }

      swap(items, i++, j--);
    }

    quickSortItems(items, direction, start, j);
    quickSortItems(items, direction, j + 1, end);
  }
}
