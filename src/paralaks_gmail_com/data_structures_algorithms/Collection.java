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
