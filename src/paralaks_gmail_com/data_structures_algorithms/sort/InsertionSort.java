package paralaks_gmail_com.data_structures_algorithms.sort;

public class InsertionSort extends Sort {
  @Override
  protected <T extends Comparable<T>> void sortItems(T[] items, DIRECTION direction) {
    int d = direction == DIRECTION.DESCENDING ? -1 : 1;
    for (int i = 1; i < items.length; i++) {
      T item = items[i];
      int j = i;

      while (j > 0 && (item.compareTo(items[j - 1]) * d < 0)) {
        items[j] = items[--j];
      }

      items[j] = item;
    }
  }
}
