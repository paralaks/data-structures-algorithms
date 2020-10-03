package paralaks_gmail_com.data_structures_algorithms.graph;

public class BSTNode<T extends Comparable<T>> implements Comparable<BSTNode<T>> {
  T value;
  BSTNode<T> parent;
  BSTNode<T> left, right;
  boolean isRed = true; // Red-black tree node insertions are red by default.

  public BSTNode(T value) {
    this.value = value;
    parent = left = right = null;
  }

  public BSTNode(T value, BSTNode<T> parent) {
    this.value = value;
    this.parent = parent;
    left = right = null;
  }

  public BSTNode(T value, BSTNode<T> parent, boolean isRed) {
    this.value = value;
    this.parent = parent;
    this.isRed = isRed;
    left = right = null;
  }

  public BSTNode<T> getParent() {
    return parent;
  }

  public void setValue(T value) {
    this.value = value;
  }

  public T getValue() {
    return value;
  }

  public void setLeft(BSTNode<T> left) {
    this.left = left;
  }

  public BSTNode<T> getLeft() {
    return left;
  }

  public void setRight(BSTNode<T> right) {
    this.right = right;
  }

  public BSTNode<T> getRight() {
    return right;
  }

  public void setRed(boolean red) {
    isRed = red;
  }

  public boolean isRed() {
    return isRed;
  }

  @Override
  public int compareTo(BSTNode<T> o) {
    return this.value.compareTo(o.value);
  }
}
