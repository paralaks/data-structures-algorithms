package paralaks_gmail_com.data_structures_algorithms;

import java.util.Iterator;


public abstract class BinaryTree<T extends Comparable<T>> {

  abstract public BSTNode<T> addNode(BSTNode<T> root, T value);

  abstract public boolean add(T value);

  abstract public BSTNode<T> swapWithBiggestChild(BSTNode<T> parent);

  abstract public BSTNode<T> deleteNode(T value);

  abstract public boolean delete(T value);

  abstract public BSTNode<T> findNode(BSTNode<T> root, T value);


  enum Traversal {
    PreOrder,
    InOrder,
    PostOrder,
    BreadthFirst
  }

  public static class BSTNode<T extends Comparable<T>> implements Comparable<BSTNode<T>> {
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

  // Class attributes.
  BSTNode<T> root;
  int size;


  public void clear() {
    root = null;
    size = 0;
  }


  int size() {
    return size;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public int height(BSTNode<T> node) {
    if (node == null) {
      return -1;
    }

    return 1 + Math.max(height(node.left), height(node.right));
  }

  public T find(T value) {
    BSTNode<T> found = findNode(root, value);
    return found == null
           ? null
           : found.value;
  }

  public boolean contains(T value) {
    return value != null && findNode(root, value) != null;
  }

  public void swapValue(BSTNode<T> a, BSTNode<T> b) {
    T temp = a.value;
    a.value = b.value;
    b.value = temp;
  }

  protected void updateParentsPostRotate(BSTNode<T> node, BSTNode<T> temp) {
    temp.parent = node.parent;

    if (node.parent != null) {
      if (node.parent.left == node) {
        node.parent.left = temp;
      } else if (node.parent.right == node) {
        node.parent.right = temp;
      }
    }

    if (node.left != null) {
      node.left.parent = node;
    }

    if (node.right != null) {
      node.right.parent = node;
    }

    node.parent = temp;

    if (root == node) {
      root = temp;
    }
  }

  BSTNode<T> rightRotate(BSTNode<T> node) {
    BSTNode<T> temp = node.left;
    node.left = temp.right;
    temp.right = node;

    updateParentsPostRotate(node, temp);

    return temp;
  }

  BSTNode<T> leftRotate(BSTNode<T> node) {
    BSTNode<T> temp = node.right;
    node.right = temp.left;
    temp.left = node;

    updateParentsPostRotate(node, temp);

    return temp;
  }

  BSTNode<T> leftRightRotate(BSTNode<T> node) {
    node.left = leftRotate(node.left);
    return rightRotate(node);
  }

  BSTNode<T> rightLeftRotate(BSTNode<T> node) {
    node.right = rightRotate(node.right);
    return leftRotate(node);
  }

  public void traverseInOrder(BSTNode<T> node, final List<T> list) {
    if (node == null) {
      return;
    }

    traverseInOrder(node.left, list);
    list.addLast(node.value);
    traverseInOrder(node.right, list);
  }

  public void traversePreOrder(BSTNode<T> node, final List<T> list) {
    if (node == null) {
      return;
    }

    list.add(node.value);
    traversePreOrder(node.left, list);
    traversePreOrder(node.right, list);
  }

  public void traversePostOrder(BSTNode<T> node, final List<T> list) {
    if (node == null) {
      return;
    }

    traversePostOrder(node.left, list);
    traversePostOrder(node.right, list);
    list.addLast(node.value);
  }

  public void traverseBreadthFirst(BSTNode<T> node, final List<T> list) {
    if (node == null) {
      return;
    }

    Queue<BSTNode<T>> queue = new Queue<>();
    BSTNode<T> visited;

    queue.enqueue(node);
    while (!queue.isEmpty()) {
      visited = queue.dequeue();

      if (visited.left != null) {
        queue.enqueue(visited.left);
      }

      if (visited.right != null) {
        queue.enqueue(visited.right);
      }

      list.addLast(visited.value);
    }
  }

  public Iterator<T> iterator(Traversal type) {
    List<T> list = new LinkedList<>();

    switch (type) {
      case InOrder:
        traverseInOrder(root, list);
        break;

      case PostOrder:
        traversePostOrder(root, list);
        break;

      case BreadthFirst:
        traverseBreadthFirst(root, list);
        break;

      case PreOrder:
      default:
        traversePreOrder(root, list);
    }

    return list.iterator();
  }
}
