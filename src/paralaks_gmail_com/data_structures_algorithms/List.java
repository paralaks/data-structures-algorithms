package paralaks_gmail_com.data_structures_algorithms;

import java.util.Iterator;

public interface List<T extends Comparable<T>> extends Collection<T> {
  abstract class ListNode<T extends Comparable<T>> {
    T value;

    public ListNode(T value) {
      this.value = value;
    }

    public T getValue() {
      return value;
    }

    abstract ListNode<T> getNext();
  }

  class ListIterator<T extends Comparable<T>> implements Iterator<T> {
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
  default boolean contains(T value) {
    ListNode<T> head = getHead();

    if (value == null || head == null) {
      return false;
    }

    ListNode<T> current = head;
    while (current != null) {
      if (current.value.compareTo(value) == 0) {
        return true;
      }

      current = current.getNext();
    }

    return false;
  }
}
