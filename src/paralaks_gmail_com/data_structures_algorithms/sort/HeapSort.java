package paralaks_gmail_com.data_structures_algorithms.sort;

import paralaks_gmail_com.data_structures_algorithms.Heap;

public class HeapSort extends Sort {
  @Override
  protected <T extends Comparable<T>> void sortItems(T[] items, DIRECTION direction) {
    Heap<T> heap = new Heap<>(items, direction == DIRECTION.DESCENDING);

    for (int i = 0; i < items.length; i++) {
      heap.remove();
    }

    System.arraycopy(heap.getTable(), 0, items, 0, items.length);
  }
}
