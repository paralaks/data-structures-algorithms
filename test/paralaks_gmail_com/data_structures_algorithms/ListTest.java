package paralaks_gmail_com.data_structures_algorithms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/*
  Tests default methods of  List  interface extending and providing minimum required functionality.
 */
public class ListTest {
  List<Integer> list = null;

  @BeforeEach
  void setUp() {
    list = new FakeList<>();
  }

  // Minimal list implementation.
  static class FakeList<T> implements List<T> {
    static class FakeListNode<T> extends ListNode<T> {
      FakeListNode<T> next;

      public FakeListNode(T value) {
        super(value);
      }

      @Override
      ListNode<T> getNext() {
        return next;
      }
    }

    FakeListNode<T> head, tail;
    int size;

    public FakeList() {
      clear();
    }

    @Override
    public void addFirst(T value) {
      throw new RuntimeException("Methods must be implemented.");
    }

    @Override
    public void addLast(T value) {
      FakeListNode<T> node = new FakeListNode<>(value);

      if (head == null) {
        head = tail = node;
      } else {
        tail.next = node;
        tail = node;
      }

      size++;
    }

    @Override
    public T remove(T value) {
      throw new RuntimeException("Methods must be implemented.");
    }

    @Override
    public T peekFirst() {
      throw new RuntimeException("Methods must be implemented.");
    }

    @Override
    public T peekLast() {
      throw new RuntimeException("Methods must be implemented.");
    }

    @Override
    public T removeFirst() {
      throw new RuntimeException("Methods must be implemented.");
    }

    @Override
    public T removeLast() {
      throw new RuntimeException("Methods must be implemented.");
    }

    @Override
    public ListNode<T> getHead() {
      return head;
    }

    @Override
    public ListNode<T> getTail() {
      return tail;
    }

    @Override
    public void clear() {
      head = tail = null;
      size = 0;
    }

    @Override
    public void add(T value) {
      addLast(value);
    }

    @Override
    public T peek() {
      return null;
    }

    @Override
    public T remove() {
      throw new RuntimeException("Methods must be implemented.");
    }

    @Override
    public int size() {
      return size;
    }
  }

  @Test
  void contains() {
    assertFalse(list.contains(null));
    assertFalse(list.contains(100));

    list.add(10);
    assertTrue(list.contains(10));

    list.add(30);
    assertTrue(list.contains(30));

    assertFalse(list.contains(100));
  }

  @Test
  void asString() {
    assertEquals("[]", list.asString());

    final int printLimit = 25;

    for (int i = 0; i <= printLimit + 1; i++) {
      list.clear();
      StringBuilder expected = new StringBuilder("[");

      for (int j = 0; j <= i; j++) {
        list.add(j);

        if (list.size() <= printLimit) {
          expected.append(j).append(',');
        }
      }

      if (list.size() > printLimit) {
        expected.replace(expected.length() - 1, expected.length(), "...,");
      }

      expected.replace(expected.length() - 1, expected.length(), "]");

      assertEquals(expected.toString(), list.asString());
    }
  }
}
