package paralaks_gmail_com.data_structures_algorithms;

abstract class Sort {
  /**
   * @param items     Array of objects to be sorted. Should implement Comparable interface.
   * @param direction Positive: ascending; negative: descending; 0 ascending.
   */
  public <T extends Comparable<T>> void sort(T[] items, int direction) {
    if (items == null || items.length <= 1) {
      return;
    }

    direction = direction >= 0 ? 1 : -1;

    sortItems(items, direction);
  }

  public <T> void swap(T[] items, int i, int j) {
    if (i == j) {
      return;
    }

    T temp = items[i];
    items[i] = items[j];
    items[j] = temp;
  }

  abstract protected <T extends Comparable<T>> void sortItems(T[] items, int direction);
}
