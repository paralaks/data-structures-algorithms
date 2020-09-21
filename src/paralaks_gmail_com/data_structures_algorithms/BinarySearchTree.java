package paralaks_gmail_com.data_structures_algorithms;

public class BinarySearchTree<T extends Comparable<T>> extends BinaryTree<T> {

  public BinarySearchTree() {
    clear();
  }

  @Override
  public boolean add(T value) {
    if (value == null) {
      return false;
    }

    if (root == null) {
      root = new BSTNode<>(value);
      size++;
    } else {
      addNode(root, value);
    }

    return true;
  }

  @Override
  public boolean delete(T value) {
    BSTNode<T> node = deleteNode(value);
    return node != null;
  }

  @Override
  public BSTNode<T> findNode(BSTNode<T> parent, T value) {
    if (parent == null || value == null) {
      return null;
    }

    int compareTo = parent.value.compareTo(value);
    return compareTo == 0
           ? parent
           : compareTo > 0
             ? findNode(parent.left, value)
             : findNode(parent.right, value);
  }

  @Override
  public BSTNode<T> addNode(BSTNode<T> parent, T value) {
    if (parent == null || value == null) {
      return null;
    }

    if (parent.value.compareTo(value) > 0) {
      if (parent.left == null) {
        size++;
        return (parent.left = new BSTNode<>(value, parent));
      } else {
        return addNode(parent.left, value);
      }
    }

    if (parent.right == null) {
      size++;
      return (parent.right = new BSTNode<>(value, parent));
    } else {
      return addNode(parent.right, value);
    }
  }

  @Override
  public BSTNode<T> swapWithBiggestChild(BSTNode<T> parent) {
    if (parent == null || parent.left == null || parent.right == null) {
      return null;
    }

    BSTNode<T> child = parent.left;
    while (child.right != null) {
      child = child.right;
    }

    swapValue(parent, child);

    return child;
  }

  @Override
  public BSTNode<T> deleteNode(T value) {
    BSTNode<T> node;

    if (root == null || value == null || (node = findNode(root, value)) == null) {
      return null;
    }

    BSTNode<T> replacement = null;
    if (node.left != null && node.right != null) {
      node = swapWithBiggestChild(node);
      replacement = node.left;
    } else if (node.left != null) {
      replacement = node.left;
    } else if (node.right != null) {
      replacement = node.right;
    }

    BSTNode<T> parent = node.parent;
    if (parent == null) {
      root = replacement;
    } else {
      if (node == parent.left) {
        parent.left = replacement;
      } else {
        parent.right = replacement;
      }
    }

    if (replacement != null) {
      replacement.parent = parent;
    }

    size--;
    return node;
  }
}
