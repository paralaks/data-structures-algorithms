package paralaks_gmail_com.data_structures_algorithms;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class LinkedListTest extends ListTest {
  @Override
  @BeforeEach
  void setUp() {
    list = new LinkedList<>();

    assertNull(list.getHead());
    assertNull(list.getTail());
    assertEquals(0, list.size());
  }

  void populateList() {
    list = new LinkedList<>();
    list.add(10);
    list.add(20);
    list.add(30);
  }

  @Test
  void addFirst() {
    list.addFirst(null);
    assertNull(list.getHead());
    assertNull(list.getTail());
    assertEquals(0, list.size());

    list.addFirst(30);
    assertEquals(1, list.size());
    assertNotNull(list.getHead());
    assertEquals(list.getHead(), list.getTail());
    assertEquals(30, list.getHead().value);

    list.addFirst(20);
    assertEquals(2, list.size());
    assertNotNull(list.getHead());
    assertNotNull(list.getTail());
    assertNotEquals(list.getHead(), list.getTail());
    assertEquals(list.getHead().getNext(), list.getTail());
    assertEquals(20, list.getHead().value);
    assertEquals(30, list.getTail().value);

    list.addFirst(10);
    assertEquals(3, list.size());
    assertNotNull(list.getHead());
    assertNotNull(list.getTail());
    assertNotEquals(list.getHead(), list.getTail());
    assertEquals(10, list.getHead().value);
    assertEquals(20, list.getHead().getNext().value);
    assertEquals(30, list.getTail().value);
    assertEquals(list.getHead().getNext().getNext(), list.getTail());
  }

  @Test
  void addLast() {
    list.addLast(null);
    assertEquals(0, list.size());
    assertNull(list.getHead());
    assertNull(list.getTail());

    list.addLast(30);
    assertEquals(1, list.size());
    assertNotNull(list.getHead());
    assertNotNull(list.getTail());
    assertEquals(list.getHead(), list.getTail());
    assertEquals(30, list.getHead().value);

    list.addLast(20);
    assertEquals(2, list.size());
    assertNotNull(list.getHead());
    assertNotNull(list.getTail());
    assertNotEquals(list.getHead(), list.getTail());
    assertEquals(list.getHead().getNext(), list.getTail());
    assertEquals(30, list.getHead().value);
    assertEquals(20, list.getTail().value);

    list.addLast(10);
    assertEquals(3, list.size());
    assertNotNull(list.getHead());
    assertNotNull(list.getTail());
    assertNotEquals(list.getHead(), list.getTail());
    assertEquals(30, list.getHead().value);
    assertEquals(20, list.getHead().getNext().value);
    assertEquals(10, list.getTail().value);
    assertEquals(list.getHead().getNext().getNext(), list.getTail());
  }

  @Test
  void add() {
    list.add(null);
    assertEquals(0, list.size());
    assertNull(list.getHead());
    assertNull(list.getTail());

    list.add(10);
    assertEquals(1, list.size());
    assertEquals(10, list.getHead().value);
    assertEquals(10, list.getTail().value);

    list.add(20);
    assertEquals(2, list.size());
    assertEquals(10, list.getHead().value);
    assertEquals(20, list.getTail().value);

    list.add(30);
    assertEquals(3, list.size());
    assertEquals(10, list.getHead().value);
    assertEquals(30, list.getTail().value);
  }

  @Test
  void peekFirst() {
    assertNull(list.peekFirst());

    list.addFirst(10);
    assertEquals(list.getHead().value, list.peekFirst());

    list.addFirst(20);
    assertEquals(list.getHead().value, list.peekFirst());

    list.addFirst(30);
    assertEquals(list.getHead().value, list.peekFirst());

    list.clear();
    assertNull(list.peekFirst());
  }

  @Test
  void peekLast() {
    assertNull(list.peekLast());

    list.addLast(10);
    assertEquals(list.getTail().value, list.peekLast());

    list.addLast(20);
    assertEquals(list.getTail().value, list.peekLast());

    list.addLast(30);
    assertEquals(list.getTail().value, list.peekLast());

    list.clear();
    assertNull(list.peekFirst());
  }


  @Test
  void peek() {
    assertNull(list.peek());

    list.addLast(10);
    assertEquals(list.getTail().value, list.peek());

    list.addLast(20);
    assertEquals(list.getTail().value, list.peek());

    list.addLast(30);
    assertEquals(list.getTail().value, list.peek());

    list.clear();
    assertNull(list.peekFirst());
  }

  @Test
  void removeFirst() {
    assertNull(list.removeFirst());

    populateList();

    assertEquals(10, list.removeFirst());
    assertEquals(2, list.size());
    assertNotNull(list.getHead());
    assertNotNull(list.getTail());
    assertNotEquals(list.getHead(), list.getTail());
    assertEquals(list.getHead().getNext(), list.getTail());
    assertEquals(20, list.getHead().value);
    assertEquals(30, list.getTail().value);

    assertEquals(20, list.removeFirst());
    assertEquals(1, list.size());
    assertNotNull(list.getHead());
    assertNotNull(list.getTail());
    assertEquals(list.getHead(), list.getTail());
    assertEquals(30, list.getHead().value);

    assertEquals(30, list.removeFirst());
    assertEquals(0, list.size());
    assertNull(list.getHead());
    assertNull(list.getTail());

    assertNull(list.removeFirst());
  }

  @Test
  void removeLast() {
    assertNull(list.removeLast());

    populateList();

    assertEquals(30, list.removeLast());
    assertEquals(2, list.size());
    assertNotNull(list.getHead());
    assertNotNull(list.getTail());
    assertNotEquals(list.getHead(), list.getTail());
    assertEquals(list.getHead().getNext(), list.getTail());
    assertEquals(10, list.getHead().value);
    assertEquals(20, list.getTail().value);

    assertEquals(20, list.removeLast());
    assertEquals(1, list.size());
    assertNotNull(list.getHead());
    assertNotNull(list.getTail());
    assertEquals(list.getHead(), list.getTail());
    assertEquals(10, list.getHead().value);

    assertEquals(10, list.removeLast());
    assertEquals(0, list.size());
    assertNull(list.getHead());
    assertNull(list.getTail());

    assertNull(list.removeLast());
  }

  @Test
  void remove() {
    list.remove();
    assertEquals(0, list.size());
    assertNull(list.getHead());
    assertNull(list.getTail());

    populateList();

    assertEquals(3, list.size());
    assertEquals(10, list.getHead().value);
    assertEquals(30, list.getTail().value);

    list.remove();
    assertEquals(2, list.size());
    assertEquals(10, list.getHead().value);
    assertEquals(20, list.getTail().value);

    list.remove();
    assertEquals(1, list.size());
    assertEquals(list.getHead(), list.getTail());
    assertEquals(10, list.getHead().value);

    list.remove();
    assertEquals(0, list.size());
    assertNull(list.getHead());
    assertNull(list.getTail());
  }

  @Test
  void remove_withValueParameter() {
    list.add(10);
    list.add(20);
    list.add(30);
    list.add(40);
    list.add(50);

    assertNull(list.remove(null));
    assertEquals(5, list.size());

    assertNull(list.remove(99));
    assertEquals(5, list.size());

    assertTrue(list.contains(10));
    assertEquals(10, list.remove(10));
    assertEquals(4, list.size());
    assertFalse(list.contains(10));

    assertTrue(list.contains(50));
    assertEquals(50, list.remove(50));
    assertEquals(3, list.size());
    assertFalse(list.contains(50));

    assertTrue(list.contains(30));
    assertEquals(30, list.remove(30));
    assertEquals(2, list.size());
    assertFalse(list.contains(30));

    assertTrue(list.contains(20));
    assertEquals(20, list.remove(20));
    assertEquals(1, list.size());
    assertFalse(list.contains(20));

    assertTrue(list.contains(40));
    assertEquals(40, list.remove(40));
    assertEquals(0, list.size());
    assertFalse(list.contains(40));

    assertNull(list.getHead());
    assertNull(list.getTail());
  }
}
