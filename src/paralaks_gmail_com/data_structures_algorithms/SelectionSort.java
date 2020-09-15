package paralaks_gmail_com.data_structures_algorithms;

public class SelectionSort extends Sort {
  @Override
  protected <T extends Comparable<T>> void sortItems(T[] items, int direction) {
    for (int i = 0; i < items.length - 1; i++) {
      int itemIndex = i;
      T item = items[itemIndex];

      // Find the smallest/biggest item in the sublist starting from i+1.
      for (int j = i + 1; j < items.length; j++) {
        if (items[j].compareTo(item) * direction < 0) {
          itemIndex = j;
          item = items[itemIndex];
        }
      }
      // Swap smallest/biggest with item at index i.
      swap(items, i, itemIndex);
    }
  }
}
