package paralaks_gmail_com.data_structures_algorithms.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import paralaks_gmail_com.data_structures_algorithms.LinkedList;
import paralaks_gmail_com.data_structures_algorithms.List;

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
        parent.setLeft(new BSTNode<>(value, parent));
        return parent.getLeft();
      }

      int idxRight = idxLeft + 1;
      if (count == idxRight) {
        parent.setRight(new BSTNode<>(value, parent));
        return parent.getRight();
      }

      BSTNode<T> node = addHeapLike(parent.getLeft(), value, idxLeft, count);
      if (node != null) {
        return node;
      }

      node = addHeapLike(parent.getRight(), value, idxRight, count);
      return node;
    }

    @Override
    public BSTNode<T> swapWithBiggestChild(BSTNode<T> parent) {
      throw new RuntimeException("Methods must be implemented.");
    }

    // Helper function... finds last node used for swapping when deleting a node.
    BSTNode<T> findLastNode(BSTNode<T> parent, int idxParent) {
      if (root.getLeft() == null && root.getRight() == null) {
        return root;
      }

      if (idxParent > size) {
        return null;
      }

      int idxLeft = 2 * idxParent + 1;
      if (size == idxLeft + 1) {
        return parent.getLeft();
      }

      int idxRight = idxLeft + 1;
      if (size == idxRight + 1) {
        return parent.getRight();
      }

      BSTNode<T> node = findLastNode(parent.getLeft(), idxLeft);
      return node != null
             ? node
             : findLastNode(parent.getRight(), idxRight);
    }


    @Override
    public BSTNode<T> deleteNode(T value) {
      BSTNode<T> node;

      if (value == null || root == null || (node = findNode(root, value)) == null) {
        return null;
      }

      // Remove last node from the tree and swap last node value with the to be deleted value.
      BSTNode<T> lastNode = findLastNode(root, 0);

      if (lastNode.getParent() != null) {
        if (lastNode.getParent().getLeft() == lastNode) {
          lastNode.getParent().setLeft(null);
        } else {
          lastNode.getParent().setRight(null);
          ;
        }
      } else {
        root = null;
      }

      if (node != lastNode) {
        node.setValue(lastNode.getValue());
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
    public BSTNode<T> findNode(BSTNode<T> root, T value) {
      if (root == null || value == null) {
        return null;
      }

      if (root.getValue().compareTo(value) == 0) {
        return root;
      }

      BSTNode<T> node;
      if (root.getLeft() != null && (node = findNode(root.getLeft(), value)) != null) {
        return node;
      }

      if (root.getRight() != null && (node = findNode(root.getRight(), value)) != null) {
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
    assertNull(tree.getRoot(), "Tree is empty, root must be null.");
    assertEquals(0, tree.size(), "Tree is empty, size must be null.");

    tree.clear();
    assertNull(tree.getRoot(), "Tree was empty before clear method ran, root must be null.");
    assertEquals(0, tree.size(), "Tree was empty before clear method ran, size must be 0.");

    tree.add(2);
    tree.add(1);
    tree.add(3);
    assertEquals(3, tree.size(), "Three items added to the tree, size must be 3.");
    assertNotNull(tree.getRoot(), "Three items added to the tree, root must not be null.");
    tree.clear();
    assertNull(tree.getRoot(), "Tree has been cleared, root must be null.");
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

    BSTNode<Integer> root = tree.getRoot();

    assertEquals(3, tree.height(root));

    assertEquals(2, tree.height(root.getLeft()));
    assertEquals(1, tree.height(root.getRight()));

    assertEquals(1, tree.height(root.getLeft().getLeft()));
    assertEquals(1, tree.height(root.getLeft().getRight()));
    assertEquals(0, tree.height(root.getRight().getLeft()));
    assertEquals(0, tree.height(root.getRight().getRight()));

    assertEquals(0, tree.height(root.getLeft().getLeft().getLeft()));
    assertEquals(0, tree.height(root.getLeft().getLeft().getRight()));
    assertEquals(0, tree.height(root.getLeft().getRight().getLeft()));
    assertEquals(0, tree.height(root.getLeft().getRight().getRight()));

    assertEquals(-1, tree.height(root.getLeft().getLeft().getLeft().getLeft()));
    assertEquals(-1, tree.height(root.getLeft().getLeft().getLeft().getRight()));
    assertEquals(-1, tree.height(root.getLeft().getLeft().getRight().getLeft()));
    assertEquals(-1, tree.height(root.getLeft().getLeft().getRight().getRight()));

    assertEquals(-1, tree.height(root.getLeft().getRight().getLeft().getLeft()));
    assertEquals(-1, tree.height(root.getLeft().getRight().getLeft().getRight()));
    assertEquals(-1, tree.height(root.getLeft().getRight().getRight().getLeft()));
    assertEquals(-1, tree.height(root.getLeft().getRight().getRight().getRight()));
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
    tree.traversePreOrder(tree.getRoot(), actual);
    assertIterableEquals(expected, actual);
  }

  @Test
  void rightRotate() {
    BSTNode<Integer> grandParentsParent = new BSTNode<>(12);
    BSTNode<Integer> grandParent = new BSTNode<>(10, grandParentsParent);
    grandParent.setLeft(new BSTNode<>(8, grandParent));
    grandParent.getLeft().setRight(new BSTNode<>(9, grandParent.getLeft()));
    grandParent.getLeft().setLeft(new BSTNode<>(6, grandParent.getLeft()));
    assertEquals(grandParentsParent, grandParent.getParent());
    assertEquals(grandParent, grandParent.getLeft().getParent());
    assertEquals(grandParent.getLeft(), grandParent.getLeft().getLeft().getParent());
    assertEquals(grandParent.getLeft(), grandParent.getLeft().getRight().getParent());

    BSTNode<Integer> newParent = tree.rightRotate(grandParent);
    assertEquals(8, newParent.getValue(), "Parent must become the new parent.");
    assertEquals(grandParentsParent, newParent.getParent(), "Grandparent's parent must become new parent's parent.");
    assertEquals(newParent, newParent.getLeft().getParent());
    assertEquals(6, newParent.getLeft().getValue(), "Rotating child node must become new parent's left child.");
    assertEquals(newParent, newParent.getRight().getParent());
    assertEquals(10, newParent.getRight().getValue(), "Grandparent must become new parent's right child.");
    assertEquals(newParent.getRight(), newParent.getRight().getLeft().getParent());
    assertEquals(9, newParent.getRight().getLeft().getValue(), "Parent node's right child must become new parent's right child's left child.");
  }

  @Test
  void leftRotate() {
    BSTNode<Integer> grandParentsParent = new BSTNode<>(4);
    BSTNode<Integer> grandParent = new BSTNode<>(6, grandParentsParent);
    grandParent.setRight(new BSTNode<>(9, grandParent));
    grandParent.getRight().setLeft(new BSTNode<>(8, grandParent.getRight()));
    grandParent.getRight().setRight(new BSTNode<>(10, grandParent.getRight()));
    assertEquals(grandParentsParent, grandParent.getParent());
    assertEquals(grandParent, grandParent.getRight().getParent());
    assertEquals(grandParent.getRight(), grandParent.getRight().getRight().getParent());
    assertEquals(grandParent.getRight(), grandParent.getRight().getLeft().getParent());

    BSTNode<Integer> newParent = tree.leftRotate(grandParent);
    assertEquals(9, newParent.getValue(), "Parent must become the new parent.");
    assertEquals(grandParentsParent, newParent.getParent(), "Grandparent's parent must become new parent's parent.");
    assertEquals(newParent, newParent.getLeft().getParent());
    assertEquals(6, newParent.getLeft().getValue(), "Grandparent must become new parent's left child.");
    assertEquals(newParent, newParent.getRight().getParent());
    assertEquals(10, newParent.getRight().getValue(), "Rotating child node must become new parent's right child.");
    assertEquals(newParent.getLeft(), newParent.getLeft().getRight().getParent());
    assertEquals(8, newParent.getLeft().getRight().getValue(), "Parent's left child must become new parent's left child's right child.");
  }

  @Test
  void leftRightRotate() {
    BSTNode<Integer> grandParentsParent = new BSTNode<>(6);
    BSTNode<Integer> grandParent = new BSTNode<>(10, grandParentsParent);
    grandParent.setLeft(new BSTNode<>(8, grandParent));
    grandParent.getLeft().setRight(new BSTNode<>(9, grandParent.getLeft()));
    assertEquals(grandParentsParent, grandParent.getParent());
    assertEquals(grandParent, grandParent.getLeft().getParent());
    assertEquals(grandParent.getLeft(), grandParent.getLeft().getRight().getParent());

    BSTNode<Integer> newParent = tree.leftRightRotate(grandParent);
    assertEquals(9, newParent.getValue(), "Parent's right child must become new parent.");
    assertEquals(grandParentsParent, newParent.getParent(), "Grandparent's parent must become new parent's parent.");
    assertEquals(8, newParent.getLeft().getValue(), "Parent must become new parent's left child.");
    assertEquals(newParent, newParent.getLeft().getParent());
    assertEquals(10, newParent.getRight().getValue(), "Grandparent must become new parent's right child.");
    assertEquals(newParent, newParent.getRight().getParent());
  }

  @Test
  void rightLeftRotate() {
    BSTNode<Integer> grandParentsParent = new BSTNode<>(6);
    BSTNode<Integer> grandParent = new BSTNode<>(8, grandParentsParent);
    grandParent.setRight(new BSTNode<>(10, grandParent));
    grandParent.getRight().setLeft(new BSTNode<>(9, grandParent.getRight()));
    assertEquals(grandParentsParent, grandParent.getParent());
    assertEquals(grandParent, grandParent.getRight().getParent());
    assertEquals(grandParent.getRight(), grandParent.getRight().getLeft().getParent());

    BSTNode<Integer> newParent = tree.rightLeftRotate(grandParent);
    assertEquals(9, newParent.getValue(), "Parent's left child must become the new parent.");
    assertEquals(grandParentsParent, newParent.getParent(), "Grandparent's parent must become new parent's parent.");
    assertEquals(8, newParent.getLeft().getValue(), "Grandparent must become new parent's left child.");
    assertEquals(newParent, newParent.getLeft().getParent());
    assertEquals(10, newParent.getRight().getValue(), "Parent must become new parent's right child.");
    assertEquals(newParent, newParent.getRight().getParent());
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
    tree.traverseInOrder(tree.getRoot(), actual);
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
    tree.traversePostOrder(tree.getRoot(), actual);
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
    tree.traverseBreadthFirst(tree.getRoot(), actual);
    assertIterableEquals(expected, actual);
  }

  @Test
  void iteratorPreOrder() {
    populateIterableTree();

    List<Integer> expected = new LinkedList<>();
    tree.iterator(BinaryTree.Traversal.PreOrder).forEachRemaining(expected::addLast);

    List<Integer> actual = new LinkedList<>();
    tree.traversePreOrder(tree.getRoot(), actual);

    assertIterableEquals(expected, actual);
  }

  @Test
  void iteratorInOrder() {
    populateIterableTree();

    List<Integer> expected = new LinkedList<>();
    tree.iterator(BinaryTree.Traversal.InOrder).forEachRemaining(expected::addLast);

    List<Integer> actual = new LinkedList<>();
    tree.traverseInOrder(tree.getRoot(), actual);

    assertIterableEquals(expected, actual);
  }

  @Test
  void iteratorPostOrder() {
    populateIterableTree();

    List<Integer> expected = new LinkedList<>();
    tree.iterator(BinaryTree.Traversal.PostOrder).forEachRemaining(expected::addLast);

    List<Integer> actual = new LinkedList<>();
    tree.traversePostOrder(tree.getRoot(), actual);

    assertIterableEquals(expected, actual);
  }

  @Test
  void iteratorBreadthFirst() {
    populateIterableTree();

    List<Integer> expected = new LinkedList<>();
    tree.iterator(BinaryTree.Traversal.BreadthFirst).forEachRemaining(expected::addLast);

    List<Integer> actual = new LinkedList<>();
    tree.traverseBreadthFirst(tree.getRoot(), actual);

    assertIterableEquals(expected, actual);
  }
}
