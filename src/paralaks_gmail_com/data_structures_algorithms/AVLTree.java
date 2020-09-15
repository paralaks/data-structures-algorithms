package paralaks_gmail_com.data_structures_algorithms;

public class AVLTree<T> extends BinarySearchTree<T> {

  public AVLTree() {
    clear();
  }

  @Override
  public boolean add(T value) {
    if (value == null) {
      return false;
    }

    if (root == null) {
      root = new TreeNode<>(value, null);
      size++;
    } else {
      rebalance(addNode(root, value));
    }

    return true;
  }

  @Override
  public boolean delete(T value) {
    TreeNode<T> node = deleteNode(value);
    if (node == null) {
      return false;
    }

    rebalance(node);

    return true;
  }


  void rebalance(TreeNode<T> node) {
    if (node == null) {
      return;
    }

    TreeNode<T> parent;
    TreeNode<T> rotated;

    while (node != null) {
      parent = node.parent;
      rotated = null;

      int balanceFactor = height(node.left) - height(node.right);
      if (balanceFactor > 1) {
        rotated = height(node.left.left) >= height(node.left.right)
                  ? rightRotate(node)
                  : leftRightRotate(node);
      } else if (balanceFactor < -1) {
        rotated = height(node.right.right) >= height(node.right.left)
                  ? leftRotate(node)
                  : rightLeftRotate(node);
      }

      if (parent == null && rotated != null) {
        root = rotated;
      }

      node = parent;
    }
  }
}
