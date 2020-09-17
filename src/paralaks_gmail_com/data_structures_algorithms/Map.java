package paralaks_gmail_com.data_structures_algorithms;

public interface Map<K extends Comparable<K>, V extends Comparable<V>> {
  class Entry<K extends Comparable<K>, V extends Comparable<V>> implements Comparable<Entry<K, V>> {
    K key;
    V value;

    Entry(K key, V value) {
      this.key = key;
      this.value = value;
    }

    @Override
    public int compareTo(Entry<K, V> other) {
      return key == null ? (other.key == null ? 0 : -1) : ((Comparable<K>) key).compareTo(other.key);
    }
  }

  void clear();

  void resize();

  int size();

  float currentLoadFactor();

  V put(K key, V value);

  V get(K key);

  V remove(K key);

  List<K> keys();

  List<V> values();

  boolean containsKey(K key);

  boolean containsValue(V value);

  default boolean isEmpty() {
    return size() == 0;
  }

  default int hashIndex(K key, int tableSize) {
    return key == null ? 0 : (key.hashCode() & 0x7fffffff) % tableSize;
  }
}
