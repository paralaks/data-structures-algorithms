package paralaks_gmail_com.data_structures_algorithms.graph;

public class BSTNode<T extends Comparable<T>> implements Comparable<BSTNode<T>> {
  T value;
  BSTNode<T> parent;
  BSTNode<T> left, right;
  boolean isRed = true; // Red-black tree node insertions are red by default.

  BSTNode(T value) {
    this.value = value;
    parent = left = right = null;
  }

  BSTNode(T value, BSTNode<T> parent) {
    this.value = value;
    this.parent = parent;
    left = right = null;
  }

  BSTNode(T value, BSTNode<T> parent, boolean isRed) {
    this.value = value;
    this.parent = parent;
    this.isRed = isRed;
    left = right = null;
  }

  BSTNode<T> getLeft() {
    return left;
  }

  BSTNode<T> getRight() {
    return right;
  }

  @Override
  public int compareTo(BSTNode<T> o) {
    return this.value.compareTo(o.value);
  }
}
