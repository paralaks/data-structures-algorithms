package paralaks_gmail_com.data_structures_algorithms;

import java.util.Iterator;

public class Stack<T extends Comparable<T>> implements Collection<T> {
  private final LinkedList<T> list = new LinkedList<>();

  public void push(T value) {
    list.addLast(value);
  }

  public T pop() {
    return list.removeLast();
  }

  @Override
  public void clear() {
    list.clear();
  }

  @Override
  public void add(T value) {
    push(value);
  }

  @Override
  public T peek() {
    return list.peekLast();
  }

  @Override
  public boolean contains(T value) {
    return list.contains(value);
  }

  @Override
  public T remove() {
    return pop();
  }

  @Override
  public int size() {
    return list.size();
  }

  @Override
  public Iterator<T> iterator() {
    return list.iterator();
  }
}
