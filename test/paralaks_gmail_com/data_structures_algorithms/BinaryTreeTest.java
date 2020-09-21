package paralaks_gmail_com.data_structures_algorithms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import paralaks_gmail_com.data_structures_algorithms.BinaryTree.BSTNode;

import static org.junit.jupiter.api.Assertions.*;


/*
  Tests abstract BinaryTree class methods by extending and providing minimum required functionality.
 */
class BinaryTreeTest {
  // Minimal BinaryTree implementation.
  static class FakeTree<T extends Comparable<T>> extends BinaryTree<T> {
    @Override
    public void clear() {
      root = null;
      size = 0;
    }

    @Override
    public boolean add(T value) {
      if (value == null) {
        return false;
      }

      if (root == null) {
        root = new BSTNode<>(value);
        size++;
        return true;
      }

      BSTNode<T> node = addNode(root, value);
      if (node != null) {
        size++;
      }

      return node != null;
    }

    @Override
    public BSTNode<T> addNode(BSTNode<T> root, T value) {
      return addHeapLike(root, value, 0, size);
    }

    // Provides heap tree like add (from top to bottom, left to right)
    BSTNode<T> addHeapLike(BSTNode<T> parent, T value, int idxParent, int count) {
      if (idxParent > count) {
        return null;
      }

      int idxLeft = 2 * idxParent + 1;
      if (count == idxLeft) {
        parent.left = new BSTNode<>(value, parent);
        return parent.left;
      }

      int idxRight = idxLeft + 1;
      if (count == idxRight) {
        parent.right = new BSTNode<>(value, parent);
        return parent.right;
      }

      BSTNode<T> node = addHeapLike(parent.left, value, idxLeft, count);
      if (node != null) {
        return node;
      }

      node = addHeapLike(parent.right, value, idxRight, count);
      return node;
    }

    @Override
    public BSTNode<T> swapWithBiggestChild(BSTNode<T> parent) {
      throw new RuntimeException("Methods must be implemented.");
    }

    // Helper function... finds last node used for swapping when deleting a node.
    BSTNode<T> findLastNode(BSTNode<T> parent, int idxParent) {
      if (root.left == null && root.right == null) {
        return root;
      }

      if (idxParent > size) {
        return null;
      }

      int idxLeft = 2 * idxParent + 1;
      if (size == idxLeft + 1) {
        return parent.left;
      }

      int idxRight = idxLeft + 1;
      if (size == idxRight + 1) {
        return parent.right;
      }

      BSTNode<T> node = findLastNode(parent.left, idxLeft);
      return node != null
             ? node
             : findLastNode(parent.right, idxRight);
    }


    @Override
    public BSTNode<T> deleteNode(T value) {
      BSTNode<T> node;

      if (value == null || root == null || (node = findNode(root, value)) == null) {
        return null;
      }

      // Remove last node from the tree and swap last node value with the to be deleted value.
      BSTNode<T> lastNode = findLastNode(root, 0);

      if (lastNode.parent != null) {
        if (lastNode.parent.left == lastNode) {
          lastNode.parent.left = null;
        } else {
          lastNode.parent.right = null;
        }
      } else {
        root = null;
      }

      if (node != lastNode) {
        node.value = lastNode.value;
      }

      return node;
    }

    @Override
    public boolean delete(T value) {
      BSTNode<T> node = deleteNode(value);
      if (node == null) {
        return false;
      }

      size--;
      return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public BSTNode<T> findNode(BSTNode<T> root, T value) {
      if (root == null || value == null) {
        return null;
      }

      if (root.value.compareTo(value) == 0) {
        return root;
      }

      BSTNode<T> node;
      if (root.left != null && (node = findNode(root.left, value)) != null) {
        return node;
      }

      if (root.right != null && (node = findNode(root.right, value)) != null) {
        return node;
      }

      return null;
    }

  }

  // Tree object to be tested against.
  BinaryTree<Integer> tree;

  @BeforeEach
  void setUp() {
    tree = new FakeTree<>();
  }

  void populateIterableTree() {
    tree = new FakeTree<>();

    tree.add(100);

    tree.add(80);
    tree.add(120);

    tree.add(70);
    tree.add(90);
    tree.add(110);
    tree.add(130);

    tree.add(65);
    tree.add(75);
    tree.add(85);
    tree.add(95);
  }

  @Test
  void clear() {
    assertNull(tree.root, "Tree is empty, root must be null.");
    assertEquals(0, tree.size(), "Tree is empty, size must be null.");

    tree.clear();
    assertNull(tree.root, "Tree was empty before clear method ran, root must be null.");
    assertEquals(0, tree.size(), "Tree was empty before clear method ran, size must be 0.");

    tree.add(2);
    tree.add(1);
    tree.add(3);
    assertEquals(3, tree.size(), "Three items added to the tree, size must be 3.");
    assertNotNull(tree.root, "Three items added to the tree, root must not be null.");
    tree.clear();
    assertNull(tree.root, "Tree has been cleared, root must be null.");
    assertEquals(0, tree.size(), "Tree has been cleared, size must be 0.");
  }

  @Test
  void size() {
    assertEquals(0, tree.size(), "Tree is empty, size must be 0.");

    tree.add(null);
    assertEquals(0, tree.size(), "Null value can not be added, size must not change.");

    tree.add(1);
    assertEquals(1, tree.size(), "First item added, size must be 1.");
    tree.add(2);
    assertEquals(2, tree.size(), "Second item added, size must be 2.");
    tree.add(3);
    assertEquals(3, tree.size(), "Third item added, size must be 3.");
    tree.add(4);
    assertEquals(4, tree.size(), "Fourth item added, size must be 4.");
    tree.add(5);
    assertEquals(5, tree.size(), "Fifth item added, size must be 5.");

    tree.delete(99);
    assertEquals(5, tree.size(), "Item to be deleted is not in the tree, size must remain same : 5.");

    tree.delete(5);
    assertEquals(4, tree.size(), "An item deleted from the tree, size must be 4.");

    tree.delete(null);
    assertEquals(4, tree.size(), "Null value can not be deleted from the tree, size must remain same : 4.");
    tree.delete(3);
    assertEquals(3, tree.size(), "An item deleted from the tree, size must be 3.");
    tree.delete(1);
    assertEquals(2, tree.size(), "Item was deleted before, size must remain same : 3.");
    tree.delete(2);
    assertEquals(1, tree.size(), "An item deleted from the tree, size must be 2.");
    tree.delete(4);
    assertEquals(0, tree.size(), "An item deleted from the tree, size must be 0.");
    tree.delete(1);
    assertEquals(0, tree.size(), "Item was deleted before, size must remain same : 0.");

    tree.add(9);
    tree.add(10);
    assertEquals(2, tree.size(), "Two items added to the tree, size must be 2.");
    tree.clear();
    assertEquals(0, tree.size(), "Tree has been cleared, size must be 0.");
  }

  @Test
  void isEmpty() {
    FakeTree<Character> tree = new FakeTree<>();
    assertTrue(tree.isEmpty(), "Tree is new and empty, must be true.");

    tree.add('A');
    assertFalse(tree.isEmpty(), "Tree has an item, must be false.");

    tree.delete('A');
    assertTrue(tree.isEmpty(), "Item has been removed, tree is empty, must be true.");

    tree.add('B');
    assertFalse(tree.isEmpty(), "Tree has an item, must be false.");

    tree.clear();
    assertTrue(tree.isEmpty(), "Tree has been cleared, must be true.");
  }

  @Test
  void height() {
    populateIterableTree();

    BSTNode<Integer> root = tree.root;

    assertEquals(3, tree.height(root));

    assertEquals(2, tree.height(root.left));
    assertEquals(1, tree.height(root.right));

    assertEquals(1, tree.height(root.left.left));
    assertEquals(1, tree.height(root.left.right));
    assertEquals(0, tree.height(root.right.left));
    assertEquals(0, tree.height(root.right.right));

    assertEquals(0, tree.height(root.left.left.left));
    assertEquals(0, tree.height(root.left.left.right));
    assertEquals(0, tree.height(root.left.right.left));
    assertEquals(0, tree.height(root.left.right.right));

    assertEquals(-1, tree.height(root.left.left.left.left));
    assertEquals(-1, tree.height(root.left.left.left.right));
    assertEquals(-1, tree.height(root.left.left.right.left));
    assertEquals(-1, tree.height(root.left.left.right.right));

    assertEquals(-1, tree.height(root.left.right.left.left));
    assertEquals(-1, tree.height(root.left.right.left.right));
    assertEquals(-1, tree.height(root.left.right.right.left));
    assertEquals(-1, tree.height(root.left.right.right.right));
  }

  @Test
  void contains() {
    populateIterableTree();

    assertFalse(tree.contains(null), "Null can not be stored, must return false.");
    assertFalse(tree.contains(1));

    assertTrue(tree.contains(100));
    assertTrue(tree.contains(80));
    assertTrue(tree.contains(120));
    assertTrue(tree.contains(70));
    assertTrue(tree.contains(90));
    assertTrue(tree.contains(110));
    assertTrue(tree.contains(130));
    assertTrue(tree.contains(65));
    assertTrue(tree.contains(75));
    assertTrue(tree.contains(85));
    assertTrue(tree.contains(95));

    assertTrue(tree.delete(65));
    assertFalse(tree.contains(65));
  }

  @Test
  void find() {
    populateIterableTree();

    assertNull(tree.find(null), "Null can not be stored, must return false.");
    assertNull(tree.find(1));

    assertEquals(100, tree.find(100));
    assertEquals(80, tree.find(80));
    assertEquals(120, tree.find(120));
    assertEquals(70, tree.find(70));
    assertEquals(90, tree.find(90));
    assertEquals(110, tree.find(110));
    assertEquals(130, tree.find(130));
    assertEquals(65, tree.find(65));
    assertEquals(75, tree.find(75));
    assertEquals(85, tree.find(85));
    assertEquals(95, tree.find(95));

    assertTrue(tree.delete(65));
    assertNull(tree.find(65));
  }

  @Test
  void traversePreOrder() {
    populateIterableTree();

    List<Integer> expected = new LinkedList<>();
    expected.addLast(100);
    expected.addLast(80);
    expected.addLast(70);
    expected.addLast(65);
    expected.addLast(75);
    expected.addLast(90);
    expected.addLast(85);
    expected.addLast(95);
    expected.addLast(120);
    expected.addLast(110);
    expected.addLast(130);

    List<Integer> actual = new LinkedList<>();
    tree.traversePreOrder(tree.root, actual);
    assertIterableEquals(expected, actual);
  }

  @Test
  void rightRotate() {
    BSTNode<Integer> grandParentsParent = new BSTNode<>(12);
    BSTNode<Integer> grandParent = new BSTNode<>(10, grandParentsParent);
    grandParent.left = new BSTNode<>(8, grandParent);
    grandParent.left.right = new BSTNode<>(9, grandParent.left);
    grandParent.left.left = new BSTNode<>(6, grandParent.left);
    assertEquals(grandParentsParent, grandParent.parent);
    assertEquals(grandParent, grandParent.left.parent);
    assertEquals(grandParent.left, grandParent.left.left.parent);
    assertEquals(grandParent.left, grandParent.left.right.parent);

    BSTNode<Integer> newParent = tree.rightRotate(grandParent);
    assertEquals(8, newParent.value, "Parent must become the new parent.");
    assertEquals(grandParentsParent, newParent.parent, "Grandparent's parent must become new parent's parent.");
    assertEquals(newParent, newParent.left.parent);
    assertEquals(6, newParent.left.value, "Rotating child node must become new parent's left child.");
    assertEquals(newParent, newParent.right.parent);
    assertEquals(10, newParent.right.value, "Grandparent must become new parent's right child.");
    assertEquals(newParent.right, newParent.right.left.parent);
    assertEquals(9, newParent.right.left.value, "Parent node's right child must become new parent's right child's left child.");
  }

  @Test
  void leftRotate() {
    BSTNode<Integer> grandParentsParent = new BSTNode<>(4);
    BSTNode<Integer> grandParent = new BSTNode<>(6, grandParentsParent);
    grandParent.right = new BSTNode<>(9, grandParent);
    grandParent.right.left = new BSTNode<>(8, grandParent.right);
    grandParent.right.right = new BSTNode<>(10, grandParent.right);
    assertEquals(grandParentsParent, grandParent.parent);
    assertEquals(grandParent, grandParent.right.parent);
    assertEquals(grandParent.right, grandParent.right.right.parent);
    assertEquals(grandParent.right, grandParent.right.left.parent);

    BSTNode<Integer> newParent = tree.leftRotate(grandParent);
    assertEquals(9, newParent.value, "Parent must become the new parent.");
    assertEquals(grandParentsParent, newParent.parent, "Grandparent's parent must become new parent's parent.");
    assertEquals(newParent, newParent.left.parent);
    assertEquals(6, newParent.left.value, "Grandparent must become new parent's left child.");
    assertEquals(newParent, newParent.right.parent);
    assertEquals(10, newParent.right.value, "Rotating child node must become new parent's right child.");
    assertEquals(newParent.left, newParent.left.right.parent);
    assertEquals(8, newParent.left.right.value, "Parent's left child must become new parent's left child's right child.");
  }

  @Test
  void leftRightRotate() {
    BSTNode<Integer> grandParentsParent = new BSTNode<>(6);
    BSTNode<Integer> grandParent = new BSTNode<>(10, grandParentsParent);
    grandParent.left = new BSTNode<>(8, grandParent);
    grandParent.left.right = new BSTNode<>(9, grandParent.left);
    assertEquals(grandParentsParent, grandParent.parent);
    assertEquals(grandParent, grandParent.left.parent);
    assertEquals(grandParent.left, grandParent.left.right.parent);

    BSTNode<Integer> newParent = tree.leftRightRotate(grandParent);
    assertEquals(9, newParent.value, "Parent's right child must become new parent.");
    assertEquals(grandParentsParent, newParent.parent, "Grandparent's parent must become new parent's parent.");
    assertEquals(8, newParent.left.value, "Parent must become new parent's left child.");
    assertEquals(newParent, newParent.left.parent);
    assertEquals(10, newParent.right.value, "Grandparent must become new parent's right child.");
    assertEquals(newParent, newParent.right.parent);
  }

  @Test
  void rightLeftRotate() {
    BSTNode<Integer> grandParentsParent = new BSTNode<>(6);
    BSTNode<Integer> grandParent = new BSTNode<>(8, grandParentsParent);
    grandParent.right = new BSTNode<>(10, grandParent);
    grandParent.right.left = new BSTNode<>(9, grandParent.right);
    assertEquals(grandParentsParent, grandParent.parent);
    assertEquals(grandParent, grandParent.right.parent);
    assertEquals(grandParent.right, grandParent.right.left.parent);

    BSTNode<Integer> newParent = tree.rightLeftRotate(grandParent);
    assertEquals(9, newParent.value, "Parent's left child must become the new parent.");
    assertEquals(grandParentsParent, newParent.parent, "Grandparent's parent must become new parent's parent.");
    assertEquals(8, newParent.left.value, "Grandparent must become new parent's left child.");
    assertEquals(newParent, newParent.left.parent);
    assertEquals(10, newParent.right.value, "Parent must become new parent's right child.");
    assertEquals(newParent, newParent.right.parent);
  }

  @Test
  void traverseInOrder() {
    populateIterableTree();

    List<Integer> expected = new LinkedList<>();
    expected.addLast(65);
    expected.addLast(70);
    expected.addLast(75);
    expected.addLast(80);
    expected.addLast(85);
    expected.addLast(90);
    expected.addLast(95);
    expected.addLast(100);
    expected.addLast(110);
    expected.addLast(120);
    expected.addLast(130);

    List<Integer> actual = new LinkedList<>();
    tree.traverseInOrder(tree.root, actual);
    assertIterableEquals(expected, actual);
  }

  @Test
  void traversePostOrder() {
    populateIterableTree();

    List<Integer> expected = new LinkedList<>();
    expected.addLast(65);
    expected.addLast(75);
    expected.addLast(70);
    expected.addLast(85);
    expected.addLast(95);
    expected.addLast(90);
    expected.addLast(80);
    expected.addLast(110);
    expected.addLast(130);
    expected.addLast(120);
    expected.addLast(100);

    List<Integer> actual = new LinkedList<>();
    tree.traversePostOrder(tree.root, actual);
    assertIterableEquals(expected, actual);
  }

  @Test
  void traverseBreadthFirst() {
    populateIterableTree();

    List<Integer> expected = new LinkedList<>();
    expected.addLast(100);
    expected.addLast(80);
    expected.addLast(120);
    expected.addLast(70);
    expected.addLast(90);
    expected.addLast(110);
    expected.addLast(130);
    expected.addLast(65);
    expected.addLast(75);
    expected.addLast(85);
    expected.addLast(95);

    List<Integer> actual = new LinkedList<>();
    tree.traverseBreadthFirst(tree.root, actual);
    assertIterableEquals(expected, actual);
  }

  @Test
  void iteratorPreOrder() {
    populateIterableTree();

    List<Integer> expected = new LinkedList<>();
    tree.iterator(BinaryTree.Traversal.PreOrder).forEachRemaining(expected::addLast);

    List<Integer> actual = new LinkedList<>();
    tree.traversePreOrder(tree.root, actual);

    assertIterableEquals(expected, actual);
  }

  @Test
  void iteratorInOrder() {
    populateIterableTree();

    List<Integer> expected = new LinkedList<>();
    tree.iterator(BinaryTree.Traversal.InOrder).forEachRemaining(expected::addLast);

    List<Integer> actual = new LinkedList<>();
    tree.traverseInOrder(tree.root, actual);

    assertIterableEquals(expected, actual);
  }

  @Test
  void iteratorPostOrder() {
    populateIterableTree();

    List<Integer> expected = new LinkedList<>();
    tree.iterator(BinaryTree.Traversal.PostOrder).forEachRemaining(expected::addLast);

    List<Integer> actual = new LinkedList<>();
    tree.traversePostOrder(tree.root, actual);

    assertIterableEquals(expected, actual);
  }

  @Test
  void iteratorBreadthFirst() {
    populateIterableTree();

    List<Integer> expected = new LinkedList<>();
    tree.iterator(BinaryTree.Traversal.BreadthFirst).forEachRemaining(expected::addLast);

    List<Integer> actual = new LinkedList<>();
    tree.traverseBreadthFirst(tree.root, actual);

    assertIterableEquals(expected, actual);
  }
}
