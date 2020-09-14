package paralaks_gmail_com.data_structures_algorithms;


import java.util.Arrays;
import java.util.Iterator;

public class Heap<T> implements Collection<T> {
  static class HeapIterator<T> implements Iterator<T> {
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


  public Heap(int initialCapacity) {
    capacity = Math.max(2, initialCapacity);
    clear();
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
    table = (T[]) new Object[capacity];
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

  @SuppressWarnings("unchecked")
  protected int indexOf(T value) {
    for (int i = 0; i < size; i++) {
      if (((Comparable<T>) table[i]).compareTo(value) == 0) {
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

  @SuppressWarnings("unchecked")
  protected void trickleUp(int child) {
    if (child == 0) {
      return;
    }

    int parent = (child - 1) / 2;
    if (((Comparable<T>) table[child]).compareTo(table[parent]) == compareToValue) {
      swap(parent, child);
      trickleUp(parent);
    }
  }

  @SuppressWarnings("unchecked")
  protected void trickleDown(int parent) {
    if (parent >= size) {
      return;
    }

    int left = 2 * parent + 1;
    int right = left + 1;

    // Both left and right children exist
    if (left < size && right < size) {
      Comparable<T> leftComp = ((Comparable<T>) table[left]);
      Comparable<T> rightComp = ((Comparable<T>) table[right]);

      // Left child is the smallest/biggest of all 3
      if (leftComp.compareTo(table[parent]) == compareToValue && leftComp.compareTo(table[right]) == compareToValue) {
        swap(left, parent);
        trickleDown(left);
      }
      // right child is the smallest/biggest of all 3
      else if (rightComp.compareTo(table[parent]) == compareToValue && rightComp.compareTo(table[left]) == compareToValue) {
        swap(right, parent);
        trickleDown(right);
      }
      // parent is the smallest/biggest
    }
    // Only left child exist
    else if (left < size) {
      if (((Comparable<T>) table[left]).compareTo(table[parent]) == compareToValue) {
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
    final int limit = 25;

    StringBuilder output = new StringBuilder(capacity);
    int counter = 1;
    output.append("[");

    for (T value : this) {
      output.append(value);

      if (counter < size) {
        output.append(counter == limit
                      ? "..."
                      : ",");
      }

      counter++;
    }
    output.append("]");

    return output.toString();
  }
}
