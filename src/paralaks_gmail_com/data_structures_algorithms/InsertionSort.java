package paralaks_gmail_com.data_structures_algorithms;

public class InsertionSort extends Sort {
  @Override
  protected <T extends Comparable<T>> void sortItems(T[] items, int direction) {
    for (int i = 1; i < items.length; i++) {
      T item = items[i];
      int j = i;

      while (j > 0 && (item.compareTo(items[j - 1]) * direction < 0)) {
        items[j] = items[--j];
      }

      items[j] = item;
    }
  }
}
