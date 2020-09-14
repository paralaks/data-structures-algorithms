package paralaks_gmail_com.data_structures_algorithms;

import java.util.Iterator;

public interface List<T> extends Collection<T> {
  abstract class ListNode<T> {
    T value;

    public ListNode(T value) {
      this.value = value;
    }

    abstract ListNode<T> getNext();
  }

  class ListIterator<T> implements Iterator<T> {
    ListNode<T> current;

    ListIterator(ListNode<T> head) {
      current = head;
    }

    @Override
    public boolean hasNext() {
      return current != null;
    }

    @Override
    public T next() {
      if (hasNext()) {
        T value = current.value;
        current = current.getNext();

        return value;
      }

      return null;
    }
  }

  void addFirst(T value);

  void addLast(T value);

  T remove(T value);

  T peekFirst();

  T peekLast();

  T removeFirst();

  T removeLast();

  ListNode<T> getHead();

  ListNode<T> getTail();

  @Override
  default Iterator<T> iterator() {
    return new ListIterator<>(getHead());
  }

  @Override
  @SuppressWarnings("unchecked")
  default boolean contains(T value) {
    ListNode<T> head = getHead();

    if (value == null || head == null) {
      return false;
    }

    ListNode<T> current = head;
    while (current != null) {
      if (((Comparable<T>) current.value).compareTo(value) == 0) {
        return true;
      }

      current = current.getNext();
    }

    return false;
  }


  default String asString() {
    final int limit = 25;
    int size = size();

    StringBuilder output = new StringBuilder(size * 5);
    int counter = 1;
    output.append("[");

    for (T value : this) {
      output.append(value);

      if (counter == limit) {
        if (counter < size) {
          output.append("...");
        }
        break;
      }

      if (counter < size) {
        output.append(",");
      }

      counter++;
    }
    output.append("]");

    return output.toString();
  }
}
