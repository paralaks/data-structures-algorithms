package paralaks_gmail_com.data_structures_algorithms.graph;

public class AVLTree<T extends Comparable<T>> extends BinarySearchTree<T> {

  public AVLTree() {
    clear();
  }

  @Override
  public boolean add(T value) {
    if (value == null) {
      return false;
    }

    if (root == null) {
      root = new BSTNode<>(value, null);
      size++;
    } else {
      rebalance(addNode(root, value));
    }

    return true;
  }

  @Override
  public boolean delete(T value) {
    BSTNode<T> node = deleteNode(value);
    if (node == null) {
      return false;
    }

    rebalance(node);

    return true;
  }

  void rebalance(BSTNode<T> node) {
    while (node != null) {
      int balanceFactor = height(node.left) - height(node.right);
      if (balanceFactor > 1) {
        if (height(node.left.left) >= height(node.left.right)) {
          rightRotate(node);
        } else {
          leftRightRotate(node);
        }
      } else if (balanceFactor < -1) {
        if (height(node.right.right) >= height(node.right.left)) {
          leftRotate(node);
        } else {
          rightLeftRotate(node);
        }
      }

      node = node.parent;
    }
  }
}
