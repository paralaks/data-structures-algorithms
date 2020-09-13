package paralaks_gmail_com.data_structures_algorithms;

public interface Collection<T> extends Iterable<T> {
  default boolean isEmpty() {
    return size() == 0;
  }

  void clear();

  void add(T value);

  T peek();

  boolean contains(T value);

  T remove();

  int size();
}
