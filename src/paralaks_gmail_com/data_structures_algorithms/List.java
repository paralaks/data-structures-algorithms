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

  @Override
  default Iterator<T> iterator() {
    return new ListIterator<>(getHead());
  }
}
