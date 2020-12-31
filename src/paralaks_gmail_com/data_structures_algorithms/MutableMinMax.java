package paralaks_gmail_com.data_structures_algorithms;

public class MutableMinMax<T extends Comparable<T>> {
  T min, max;

  public MutableMinMax(T initialMinValue, T initialMaxValue) {
    this.min = initialMinValue;
    this.max = initialMaxValue;
  }

  public T getMin() {
    return min;
  }

  public T getMax() {
    return max;
  }

  public T min(T v) {
    return (min = min.compareTo(v) < 0 ? min : v);
  }

  public T max(T v) {
    return (max = max.compareTo(v) > 0 ? max : v);
  }

  public void minMax(T v) {
    min = min.compareTo(v) < 0 ? min : v;
    max = max.compareTo(v) > 0 ? max : v;
  }
}
