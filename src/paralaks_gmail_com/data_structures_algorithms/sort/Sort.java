package paralaks_gmail_com.data_structures_algorithms.sort;

public abstract class Sort {
  public enum DIRECTION {
    ASCENDING,
    DESCENDING
  }

  /**
   * @param items     Array of objects to be sorted. Should implement Comparable interface.
   * @param direction Positive: ascending; negative: descending; 0 ascending.
   */
  public <T extends Comparable<T>> void sort(T[] items, DIRECTION direction) {
    if (items == null || items.length == 0) {
      return;
    }

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

  abstract protected <T extends Comparable<T>> void sortItems(T[] items, DIRECTION direction);
}
