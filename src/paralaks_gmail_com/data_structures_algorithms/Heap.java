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
  private int compareToValue;
  private boolean isMinHeap = true;
  private T initialValueForGenerics;


  public Heap(int initialCapacity, T value) {
    initialValueForGenerics = value;
    capacity = Math.max(2, initialCapacity);
    clear();
    add(initialValueForGenerics);
  }

  public void makeMinHeap(boolean makeMinHeap) {
    if (makeMinHeap == isMinHeap) {
      return;
    }

    T[] copied = Arrays.copyOf(table, size);
    this.isMinHeap = makeMinHeap;
    this.clear();

    for (T val : copied) {
      add(val);
    }
  }

  public boolean isMinHeap() {
    return isMinHeap;
  }

  @Override
  @SuppressWarnings("unchecked")
  public void clear() {
    compareToValue = isMinHeap
                     ? -1
                     : 1; // min-heap smaller.compareTo(bigger) = -1
    table = (T[]) Array.newInstance(initialValueForGenerics.getClass(), capacity);
    size = 0;
  }

  protected void swap(int indexA, int indexB) {
    if (indexA == indexB) {
      return;
    }

    T temp = table[indexA];
    table[indexA] = table[indexB];
    table[indexB] = temp;
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
    return removeAt(0);
  }

  public T remove(T val) {
    return removeAt(indexOf(val));
  }

  protected T removeAt(int index) {
    if (index < 0 || index >= size) {
      return null;
    }

    swap(index, --size);
    trickleDown(index);

    return table[size];
  }

  protected void trickleUp(int child) {
    if (child == 0) {
      return;
    }

    int parent = (child - 1) / 2;
    if (table[child].compareTo(table[parent]) == compareToValue) {
      swap(parent, child);
      trickleUp(parent);
    }
  }

  protected void trickleDown(int parent) {
    if (parent >= size) {
      return;
    }

    int left = 2 * parent + 1;
    int right = left + 1;
    T parentItem = table[parent];
    T leftItem = table[left];
    T rightItem = table[right];


    // Both left and right children exist
    if (left < size && right < size) {
      // Left child is the smallest/biggest of all 3
      if (leftItem.compareTo(parentItem) == compareToValue && leftItem.compareTo(rightItem) == compareToValue) {
        swap(left, parent);
        trickleDown(left);
      }
      // right child is the smallest/biggest of all 3
      else if (rightItem.compareTo(parentItem) == compareToValue && rightItem.compareTo(leftItem) == compareToValue) {
        swap(right, parent);
        trickleDown(right);
      }
      // parent is the smallest/biggest
    }
    // Only left child exist
    else if (left < size) {
      if (leftItem.compareTo(parentItem) == compareToValue) {
        swap(left, parent);
        trickleDown(left);
      }
    }
    // No children
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
