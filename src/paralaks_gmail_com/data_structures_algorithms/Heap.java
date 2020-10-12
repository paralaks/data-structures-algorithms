package paralaks_gmail_com.data_structures_algorithms;


import java.lang.reflect.Array;
import java.util.Arrays;
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
  private final T firstElement; // Helper to initialize generics array.

  @SuppressWarnings("unchecked")
  public Heap(int initialCapacity, T value) {
    capacity = Math.max(16, initialCapacity);
    firstElement = value;
    isMinHeap = true;
    table = (T[]) Array.newInstance(firstElement.getClass(), capacity);
    size = 0;
    add(firstElement);
  }

  @SuppressWarnings("unchecked")
  public Heap(T[] items, boolean isMinHeap) {
    if (items == null || items.length == 0) {
      throw new RuntimeException("Bad input array. Unable to initialize heap.");
    }

    capacity = Math.max(16, items.length);
    firstElement = items[0];
    this.isMinHeap = isMinHeap;
    table = (T[]) Array.newInstance(firstElement.getClass(), capacity);

    // Copy elements and heapify.
    size = items.length;
    System.arraycopy(items, 0, table, 0, size);
    heapify();
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
      if (table[i].compareTo(value) == 0) {
        return i;
      }
    }
    return -1;
  }

  @Override
  public T remove() {
    int index = 0;
    swap(index, --size);
    trickleDown(index);

    return table[size];
  }

  protected void trickleUp(int child) {
    if (child == 0) {
      return;
    }

    int parent = (child - 1) / 2;
    int childParentCompareTo = table[child].compareTo(table[parent]);
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
    int leftParentCompareTo = table[left].compareTo(table[parent]);

    if (right >= size) {
      if (isMinHeap && leftParentCompareTo < 0 || !isMinHeap && leftParentCompareTo > 0) {
        // Only left child exists. Compare it with parent.
        swapAndTrickleDown(parent, left);
      }
    } else {
      // Both left and right children exist. Find smallest/biggest among parent-left-right then trickleDown as necessary.
      int leftRightCompareTo = table[left].compareTo(table[right]);
      int rightParentCompareTo = table[right].compareTo(table[parent]);

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
