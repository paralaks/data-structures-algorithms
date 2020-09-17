package paralaks_gmail_com.data_structures_algorithms;

import java.util.Iterator;


public abstract class BinaryTree<T extends Comparable<T>> {

  abstract public TreeNode<T> addNode(TreeNode<T> root, T value);

  abstract public boolean add(T value);

  abstract public TreeNode<T> swapWithBiggestChild(TreeNode<T> parent);

  abstract public TreeNode<T> deleteNode(T value);

  abstract public boolean delete(T value);

  abstract public TreeNode<T> findNode(TreeNode<T> root, T value);


  enum Traversal {
    PreOrder,
    InOrder,
    PostOrder,
    BreadthFirst
  }

  public static class TreeNode<T extends Comparable<T>> implements Comparable<TreeNode<T>> {
    T value;
    TreeNode<T> parent;
    TreeNode<T> left, right;

    TreeNode(T value) {
      this.value = value;
      parent = left = right = null;
    }

    TreeNode(T value, TreeNode<T> parent) {
      this.value = value;
      this.parent = parent;
      left = right = null;
    }

    TreeNode<T> getLeft() {
      return left;
    }

    TreeNode<T> getRight() {
      return right;
    }

    @Override
    public int compareTo(TreeNode<T> o) {
      return this.value.compareTo(o.value);
    }
  }

  // Class attributes.
  TreeNode<T> root;
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

  public int height(TreeNode<T> node) {
    if (node == null) {
      return -1;
    }

    return 1 + Math.max(height(node.left), height(node.right));
  }

  public T find(T value) {
    TreeNode<T> found = findNode(root, value);
    return found == null
           ? null
           : found.value;
  }

  public boolean contains(T value) {
    return value != null && findNode(root, value) != null;
  }

  public void swapValue(TreeNode<T> a, TreeNode<T> b) {
    T temp = a.value;
    a.value = b.value;
    b.value = temp;
  }

  protected void updateParentsPostRotate(TreeNode<T> node, TreeNode<T> temp) {
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
  }

  TreeNode<T> rightRotate(TreeNode<T> node) {
    TreeNode<T> temp = node.left;
    node.left = temp.right;
    temp.right = node;

    updateParentsPostRotate(node, temp);

    return temp;
  }

  TreeNode<T> leftRotate(TreeNode<T> node) {
    TreeNode<T> temp = node.right;
    node.right = temp.left;
    temp.left = node;

    updateParentsPostRotate(node, temp);

    return temp;
  }

  TreeNode<T> leftRightRotate(TreeNode<T> node) {
    node.left = leftRotate(node.left);
    return rightRotate(node);
  }

  TreeNode<T> rightLeftRotate(TreeNode<T> node) {
    node.right = rightRotate(node.right);
    return leftRotate(node);
  }

  public void traverseInOrder(TreeNode<T> node, final List<T> list) {
    if (node == null) {
      return;
    }

    traverseInOrder(node.left, list);
    list.addLast(node.value);
    traverseInOrder(node.right, list);
  }

  public void traversePreOrder(TreeNode<T> node, final List<T> list) {
    if (node == null) {
      return;
    }

    list.add(node.value);
    traversePreOrder(node.left, list);
    traversePreOrder(node.right, list);
  }

  public void traversePostOrder(TreeNode<T> node, final List<T> list) {
    if (node == null) {
      return;
    }

    traversePostOrder(node.left, list);
    traversePostOrder(node.right, list);
    list.addLast(node.value);
  }

  public void traverseBreadthFirst(TreeNode<T> node, final List<T> list) {
    if (node == null) {
      return;
    }

    Queue<TreeNode<T>> queue = new Queue<>();
    TreeNode<T> visited;

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
