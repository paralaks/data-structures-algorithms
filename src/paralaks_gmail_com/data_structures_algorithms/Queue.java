package paralaks_gmail_com.data_structures_algorithms;

import java.util.Iterator;

public class Queue<T> implements Collection<T> {
  private final LinkedList<T> list = new LinkedList<>();

  public void enqueue(T val) {
    list.addLast(val);
  }

  public T dequeue() {
    return list.removeFirst();
  }

  @Override
  public void add(T value) {
    enqueue(value);
  }

  @Override
  public T remove() {
    return dequeue();
  }

  @Override
  public int size() {
    return list.size();
  }

  @Override
  public void clear() {
    list.clear();
  }

  @Override
  public T peek() {
    return list.peekFirst();
  }

  @Override
  public boolean contains(T value) {
    return list.contains(value);
  }

  @Override
  public Iterator<T> iterator() {
    return list.iterator();
  }
}
