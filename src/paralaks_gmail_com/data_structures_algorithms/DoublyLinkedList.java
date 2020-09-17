package paralaks_gmail_com.data_structures_algorithms;

public class DoublyLinkedList<T extends Comparable<T>> implements List<T> {
  public static class Node<T extends Comparable<T>> extends ListNode<T> {
    private Node<T> prev, next;

    public Node(T value) {
      super(value);
      prev = next = null;
    }

    public Node(T value, Node<T> prev, Node<T> next) {
      super(value);
      this.prev = prev;
      this.next = next;
    }

    @Override
    ListNode<T> getNext() {
      return next;
    }
  }

  private Node<T> head, tail;
  private int size;


  public DoublyLinkedList() {
    clear();
  }

  @Override
  public void clear() {
    head = tail = null;
    size = 0;
  }

  @Override
  public void addFirst(T value) {
    if (head == null) {
      head = tail = new Node<>(value);
    } else {
      head = head.prev = new Node<>(value, null, head);
    }

    size++;
  }

  @Override
  public void addLast(T value) {
    if (head == null) {
      addFirst(value);
      return;
    }

    tail = tail.next = new Node<>(value, tail, null);
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

    if (head == tail) {
      head = tail = null;
    } else {
      head = head.next;
      head.prev = null;
    }

    size--;

    return removed;
  }

  @Override
  public T removeLast() {
    if (tail == null) {
      return null;
    }

    if (head == tail) {
      return removeFirst();
    }

    T removed = tail.value;

    tail = tail.prev;
    tail.next = null;
    size--;

    return removed;
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
  public T remove() {
    return removeLast();
  }

  @Override
  public T remove(T value) {
    Node<T> current = head;

    while (current != null) {
      if (current.value.compareTo(value) == 0) {
        if (current == head) {
          return removeFirst();
        }
        if (current == tail) {
          return removeLast();
        }

        current.prev.next = current.next;
        current.next.prev = current.prev;
        size--;

        return current.value;
      }

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
