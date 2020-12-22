package paralaks_gmail_com.data_structures_algorithms;


import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

public class Heap<T extends Comparable<T>> implements Collection<T> {
  static class HeapIterator<T extends Comparable<T>> implements Iterator<T> {
    int index;
    int size;
    T[] table;

    HeapIterator(T[] table, int size) {
      this.index = 0;
      this.table = table;
      this.size = size;
    }

    @Override
    public boolean hasNext() {
      return index < size;
    }

    @Override
    public T next() {
      if (hasNext()) {
        return table[index++];
      }

      return null;
    }
  }


  private T[] table;
  private int size;
  private int capacity;
  private boolean isMinHeap;
  private Comparator<T> comparator;

  @SuppressWarnings("unchecked")
  protected void initialize(int initialCapacity, boolean isMin, T[] items, T value, Comparator<T> c) {
    if ((items == null || items.length == 0) && value == null) {
      throw new RuntimeException("Bad input. Initial value or array is required to initialize heap.");
    }

    T firstElement;
    if (items == null) {
      size = 0;
      capacity = Math.max(16, initialCapacity);
      firstElement = value;
    } else {
      size = items.length;
      capacity = Math.max(16, items.length);
      firstElement = items[0];
    }

    isMinHeap = isMin;
    table = (T[]) Array.newInstance(firstElement.getClass(), capacity);
    comparator = c != null ? c : Comparable::compareTo;

    if (items == null) {
      add(firstElement);
    } else {
      // Copy elements and heapify.
      System.arraycopy(items, 0, table, 0, size);
      heapify();
    }
  }

  public Heap(int initialCapacity, T value) {
    initialize(initialCapacity, true, null, value, null);
  }

  public Heap(int initialCapacity, T value, Comparator<T> c) {
    initialize(initialCapacity, true, null, value, c);
  }

  public Heap(T[] items, boolean isMinHeap) {
    initialize(0, isMinHeap, items, null, null);
  }

  public Heap(T[] items, boolean isMinHeap, Comparator<T> c) {
    initialize(0, isMinHeap, items, null, c);
  }

  public T[] getTable() {
    return table;
  }

  public void makeMinHeap(boolean makeMinHeap) {
    if (makeMinHeap == isMinHeap) {
      return;
    }

    isMinHeap = makeMinHeap;
    heapify();
  }

  public boolean isMinHeap() {
    return isMinHeap;
  }

  @Override
  public void clear() {
    size = 0;
  }

  protected void swap(int i, int j) {
    if (i == j) {
      return;
    }

    T temp = table[i];
    table[i] = table[j];
    table[j] = temp;
  }

  @Override
  public T peek() {
    return size > -1
           ? table[0]
           : null;
  }

  @Override
  public boolean contains(T value) {
    return indexOf(value) != -1;
  }

  protected void resize() {
    capacity = 2 * capacity;
    table = Arrays.copyOf(table, capacity);
  }

  @Override
  public void add(T value) {
    if (value == null) {
      return;
    }

    if (size >= capacity) {
      resize();
    }

    table[size] = value;
    trickleUp(size++);
  }

  protected int indexOf(T value) {
    for (int i = 0; i < size; i++) {
      if (comparator.compare(table[i], value) == 0) {
        return i;
      }
    }
    return -1;
  }

  @Override
  public T remove() {
    if (size == 0) {
      return null;
    }

    swap(0, --size);
    trickleDown(0);

    return table[size];
  }

  protected void trickleUp(int child) {
    if (child == 0) {
      return;
    }

    int parent = (child - 1) / 2;
    int childParentCompareTo = comparator.compare(table[child], table[parent]);
    if (isMinHeap && childParentCompareTo < 0 || !isMinHeap && childParentCompareTo > 0) {
      swap(parent, child);
      trickleUp(parent);
    }
  }

  protected void swapAndTrickleDown(int parent, int child) {
    swap(parent, child);
    trickleDown(child);
  }

  protected void trickleDown(int parent) {
    if (parent >= size) {
      return;
    }

    int left = 2 * parent + 1;
    if (left >= size) { // No children.
      return;
    }

    int right = left + 1;
    int leftParentCompareTo = comparator.compare(table[left], table[parent]);

    if (right >= size) {
      if (isMinHeap && leftParentCompareTo < 0 || !isMinHeap && leftParentCompareTo > 0) {
        // Only left child exists. Compare it with parent.
        swapAndTrickleDown(parent, left);
      }
    } else {
      // Both left and right children exist. Find smallest/biggest among parent-left-right then trickleDown as necessary.
      int leftRightCompareTo = comparator.compare(table[left], table[right]);
      int rightParentCompareTo = comparator.compare(table[right], table[parent]);

      if (isMinHeap) {
        if (leftParentCompareTo < 0 && leftRightCompareTo < 0) {
          swapAndTrickleDown(parent, left);
        } else if (rightParentCompareTo < 0 && leftRightCompareTo > 0
            || leftRightCompareTo == 0 && leftParentCompareTo < 0) {
          swapAndTrickleDown(parent, right);
        }
      } else {
        if (leftParentCompareTo > 0 && leftRightCompareTo > 0) {
          swapAndTrickleDown(parent, left);
        } else if (rightParentCompareTo > 0 && leftRightCompareTo < 0
            || leftRightCompareTo == 0 && leftParentCompareTo > 0) {
          swapAndTrickleDown(parent, right);
        }
      }
    }
  }

  public void heapify() {
    for (int i = size - 1; i >= 0; i--) {
      trickleDown(i);
    }
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public Iterator<T> iterator() {
    return new HeapIterator<>(table, size);
  }

  @Override
  public String toString() {
    return asString();
  }
}
