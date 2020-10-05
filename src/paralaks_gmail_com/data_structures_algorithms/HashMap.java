package paralaks_gmail_com.data_structures_algorithms;

public class HashMap<K extends Comparable<K>, V extends Comparable<V>> implements Map<K, V> {
  public final float LOAD_FACTOR = 0.75f;

  private LinkedList<Entry<K, V>>[] table;
  private int listSize = 10;
  private int tableSize;
  private int size;

  public HashMap(int initialCapacity) {
    tableSize = Math.max(initialCapacity, 10);
    clear();
  }

  @Override
  @SuppressWarnings("unchecked")
  public void clear() {
    table = (LinkedList<Entry<K, V>>[]) new LinkedList[tableSize];
    for (int i = 0; i < tableSize; i++) {
      table[i] = new LinkedList<>();
    }
    size = 0;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public float currentLoadFactor() {
    return 1.0f * size / (tableSize * listSize);
  }

  @Override
  @SuppressWarnings("unchecked")
  public void resize() {
    int newTableSize = 2 * tableSize;
    LinkedList<Entry<K, V>>[] newTable = (LinkedList<Entry<K, V>>[]) new LinkedList[newTableSize];
    for (int i = 0; i < newTableSize; i++) {
      newTable[i] = new LinkedList<>();
    }

    for (LinkedList<Entry<K, V>> list : table) {
      for (Entry<K, V> entry : list) {
        newTable[hashIndex(entry.key, newTableSize)].add(entry);
      }
    }

    tableSize = newTableSize;
    table = newTable;
  }

  @Override
  public V put(K key, V value) {
    if (currentLoadFactor() >= LOAD_FACTOR) {
      resize();
    }

    Entry<K, V> entry = new Entry<>(key, value);
    LinkedList<Entry<K, V>> list = table[hashIndex(key, tableSize)];
    Entry<K, V> removed = list.remove(entry);
    list.addFirst(entry);

    if (removed != null) {
      return removed.value;
    }

    size++;
    return value;
  }

  @Override
  public V get(K key) {
    for (Entry<K, V> entry : table[hashIndex(key, tableSize)]) {
      if (entry.key.compareTo(key) == 0) {
        return entry.value;
      }
    }

    return null;
  }

  @Override
  public V remove(K key) {
    Entry<K, V> removed = table[hashIndex(key, tableSize)].remove(new Entry<>(key, null));
    if (removed != null) {
      size--;
      return removed.value;
    }
    return null;
  }

  @Override
  public boolean containsKey(K key) {
    Entry<K, V> tempKeyEntry = new Entry<>(key, null);
    for (Entry<K, V> entry : table[hashIndex(key, tableSize)]) {
      if (entry.compareTo(tempKeyEntry) == 0) {
        return true;
      }
    }

    return false;
  }

  @Override
  public boolean containsValue(V value) {
    for (LinkedList<Entry<K, V>> list : table) {
      for (Entry<K, V> entry : list) {
        if ((value == null && entry.value == null) ||
            (value != null && entry.value != null && entry.value.compareTo(value) == 0)) {
          return true;
        }
      }
    }

    return false;
  }

  @Override
  public List<K> keys() {
    LinkedList<K> tempList = new LinkedList<>();
    for (LinkedList<Entry<K, V>> list : table) {
      for (Entry<K, V> entry : list) {
        tempList.add(entry.key);
      }
    }

    return tempList;
  }

  @Override
  public List<V> values() {
    LinkedList<V> tempList = new LinkedList<>();
    for (LinkedList<Entry<K, V>> list : table) {
      for (Entry<K, V> entry : list) {
        tempList.add(entry.value);
      }
    }

    return tempList;
  }

  @Override
  public String toString() {
    final int limit = 25;

    StringBuilder output = new StringBuilder(size * 5);
    int counter = 1;
    output.append("[");


    for (K key : keys()) {
      output.append(key).append(":").append(get(key));

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
