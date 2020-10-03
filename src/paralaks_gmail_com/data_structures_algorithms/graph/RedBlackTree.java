package paralaks_gmail_com.data_structures_algorithms.graph;

public class RedBlackTree<T extends Comparable<T>> extends BinarySearchTree<T> {
  @Override
  public boolean add(T value) {
    if (value == null) {
      return false;
    }

    if (root == null) {
      root = new BSTNode<>(value);
      root.isRed = false;
      size++;
    } else {
      rebalanceInsertion(addNode(root, value));
    }

    return true;
  }

  void rebalanceInsertion(BSTNode<T> node) {
    if (node.parent == null) { // Root node, just color flip.
      node.isRed = false;
    } else if (node.parent.isRed) { // Node has both parent and grandparent. Red parent; violation.
      BSTNode<T> parent = node.parent;
      BSTNode<T> grandParent = parent.parent;
      BSTNode<T> aunt = parent != grandParent.left ? grandParent.left : grandParent.right;

      if (aunt != null && aunt.isRed) {
        // Red aunt: color flip is enough.
        parent.isRed = aunt.isRed = false;
        grandParent.isRed = true;
        rebalanceInsertion(grandParent); // Color flip might result in both parent + grandparent being red: keep checking.
      } else {
        // Black aunt, rotation is required.
        if (parent == grandParent.left) {
          if (node == parent.left) {
            parent = rightRotate(grandParent);
          } else {
            parent = leftRightRotate(grandParent);
          }
        } else {
          if (node == parent.right) {
            parent = leftRotate(grandParent);
          } else {
            parent = rightLeftRotate(grandParent);
          }
        }
        parent.isRed = false; // Fix colors after rotation.
        parent.left.isRed = parent.right.isRed = true;
      }
    }
  }

  @Override
  public boolean delete(T value) {
    BSTNode<T> node;

    if (root == null || value == null || (node = findNode(root, value)) == null) {
      return false;
    }

    // Implementation is based on https://github.com/alenachang/red-black-deletion-steps/
    BSTNode<T> child = null, replacement = null, deleted = node;
    if (node.left != null && node.right != null) {
      replacement = node = swapWithBiggestChild(node);
      child = node.left;
    } else if (node.left != null) {
      replacement = child = node.left;
    } else if (node.right != null) {
      replacement = child = node.right;
    }

    // Attach a nullNode when needed to simplify rebalancing.
    BSTNode<T> nullNode = new BSTNode<>(null, null, false);
    if (child == null) {
      child = nullNode;
    }

    BSTNode<T> parent = node.parent;
    if (parent == null) {
      root = child;
    } else {
      if (node == parent.left) {
        parent.left = child;
      } else {
        parent.right = child;
      }
    }
    child.parent = parent;

    rebalanceDeletion(deleted, replacement, child);

    // Detach nullNode.
    if (child == nullNode) {
      if (root == child) {
        root = null;
      } else {
        if (child.parent.left == child) {
          child.parent.left = null;
        } else {
          child.parent.right = null;
        }
        child.parent = null;
      }
    }

    size--;
    return true;
  }

  void rebalanceDeletion(BSTNode<T> deleted, BSTNode<T> replacement, BSTNode<T> x) {
    if (deleted.isRed && (replacement == null || replacement.isRed)) {
      return;
    }

    if (!deleted.isRed && (replacement != null && replacement.isRed)) {
      replacement.isRed = false;
      return;
    }

    BSTNode<T> w = getSibling(x);
    handleCases(x, w);
  }

  void handleCases(BSTNode<T> x, BSTNode<T> w) {
    if (x.isRed) {
      handleCase0(x);
    } else if (w != null) { // x is black.
      if (w.isRed) {
        handleCase1(x, w);
      } else { // w is black.
        if ((w.left == null || !w.left.isRed) && (w.right == null || !w.right.isRed)) {
          handleCase2(x, w);
        } else if ((isLeftChild(x) && (w.left != null && w.left.isRed && (w.right == null || !w.right.isRed)))
            ||
            (isRightChild(x) && (w.right != null && w.right.isRed && (w.left == null || !w.left.isRed)))
        ) {
          handleCase3(x, w);
        } else if ((isLeftChild(x) && w.right != null && w.right.isRed)
            ||
            (isRightChild(x) && w.left != null && w.left.isRed)
        ) {
          handleCase4(x, w);
        }
      }
    }
  }

  void handleCase0(BSTNode<T> x) {
    x.isRed = false;
  }

  void handleCase1(BSTNode<T> x, BSTNode<T> w) {
    w.isRed = false;
    x.parent.isRed = true;

    if (x.parent.left == x) {
      leftRotate(x.parent);
      w = x.parent.right;
    } else {
      rightRotate(x.parent);
      w = x.parent.left;
    }

    handleCases(x, w);
  }

  void handleCase2(BSTNode<T> x, BSTNode<T> w) {
    w.isRed = true;
    x = x.parent;

    if (x.isRed) {
      x.isRed = false;
    } else {
      w = getSibling(x);
      handleCases(x, w);
    }
  }

  void handleCase3(BSTNode<T> x, BSTNode<T> w) {
    w.isRed = true;

    if (isLeftChild(x)) {
      w.left.isRed = false;
      rightRotate(w);
      w = x.parent.right;
    } else {
      w.right.isRed = false;
      leftRotate(w);
      w = x.parent.left;
    }

    handleCase4(x, w);
  }

  void handleCase4(BSTNode<T> x, BSTNode<T> w) {
    w.isRed = x.parent.isRed;
    x.parent.isRed = false;
    if (isLeftChild(x)) {
      w.right.isRed = false;
      leftRotate(x.parent);
    } else {
      w.left.isRed = false;
      rightRotate(x.parent);
    }
  }

  BSTNode<T> getSibling(BSTNode<T> node) {
    if (node.parent == null) {
      return null;
    }

    return node.parent.left != node ? node.parent.left : node.parent.right;
  }

  boolean isLeftChild(BSTNode<T> node) {
    return node.parent != null && node.parent.left == node;
  }

  boolean isRightChild(BSTNode<T> node) {
    return node.parent != null && node.parent.right == node;
  }
}
