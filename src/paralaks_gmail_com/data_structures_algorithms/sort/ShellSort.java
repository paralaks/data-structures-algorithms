package paralaks_gmail_com.data_structures_algorithms.sort;

public class ShellSort extends Sort {
  private final int[] TOKUDAS_SEQUENCE = {10987, 4883, 2170, 964, 428, 190, 84, 37, 16, 7, 3, 1};

  @Override
  protected <T extends Comparable<T>> void sortItems(T[] items, int direction) {
    for (Integer gap : TOKUDAS_SEQUENCE) {
      for (int i = gap; i < items.length; i++) {
        T item = items[i];
        int j = i;

        // Move elements after gap position to the beginning of the array, when gap = 1 do insertion sort.
        while (j >= gap && (item.compareTo(items[j - gap]) * direction < 0)) {
          items[j] = items[(j -= gap)];
        }

        items[j] = item;
      }
    }
  }
}
