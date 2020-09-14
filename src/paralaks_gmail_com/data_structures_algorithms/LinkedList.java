package paralaks_gmail_com.data_structures_algorithms;

public class LinkedList<T> implements List<T> {
  public static class Node<T> extends ListNode<T> {
    Node<T> next;

    Node(T value) {
      super(value);
      this.next = null;
    }

    Node(T value, Node<T> next) {
      super(value);
      this.next = next;
    }

    @Override
    Node<T> getNext() {
      return next;
    }
  }

  private Node<T> head, tail;
  private int size;

  public LinkedList() {
    clear();
  }

  @Override
  public Node<T> getHead() {
    return head;
  }

  @Override
  public Node<T> getTail() {
    return tail;
  }

  @Override
  public void clear() {
    head = tail = null;
    size = 0;
  }

  @Override
  public void addFirst(T value) {
    if (value == null) {
      return;
    }

    if (head == null) {
      head = tail = new Node<>(value);
    } else {
      head = new Node<>(value, head);
    }

    size++;
  }

  @Override
  public void addLast(T value) {
    if (value == null) {
      return;
    }

    // Empty list.
    if (head == null) {
      addFirst(value);
      return;
    }

    tail = tail.next = new Node<>(value);
    size++;
  }

  @Override
  public void add(T value) {
    addLast(value);
  }

  @Override
  public T peekFirst() {
    return head == null
           ? null
           : head.value;
  }

  @Override
  public T peekLast() {
    return tail == null
           ? null
           : tail.value;
  }

  @Override
  public T peek() {
    return peekLast();
  }

  @Override
  public T removeFirst() {
    if (head == null) {
      return null;
    }

    T removed = head.value;
    head = head.next;
    size--;

    if (head == null) {
      tail = null;
    }

    return removed;
  }

  @Override
  public T removeLast() {
    if (tail == null) {
      return null;
    }

    // Single element.
    if (head == tail) {
      return removeFirst();
    }

    // Find node before tail and remove tail.
    Node<T> prev = head;
    while (prev.next != tail) {
      prev = prev.next;
    }

    T removed = tail.value;
    tail = prev;
    tail.next = null;
    size--;

    return removed;
  }

  @Override
  public T remove() {
    return removeLast();
  }

  @Override
  @SuppressWarnings("unchecked")
  public T remove(T value) {
    if (value == null) {
      return null;
    }

    Node<T> prev = null, current = head;

    while (current != null) {
      if (((Comparable<T>) current.value).compareTo(value) == 0) {
        if (current == head) {
          return removeFirst();
        }

        if (current == tail) {
          return removeLast();
        }

        prev.next = current.next;
        size--;

        return current.value;
      }

      prev = current;
      current = current.next;
    }

    return null;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public String toString() {
    return asString();
  }
}
