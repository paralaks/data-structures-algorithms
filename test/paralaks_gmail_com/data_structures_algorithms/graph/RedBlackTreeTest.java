package paralaks_gmail_com.data_structures_algorithms.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RedBlackTreeTest extends BinarySearchTreeTest {
  @Override
  @BeforeEach
  void setUp() {
    tree = new RedBlackTree<>();
    assertNull(tree.getRoot());
    assertEquals(0, tree.size());
  }

  @Override
  @Test
  void findNode() {
    // Since findNode(...) is implemented in BinarySearchTree, calling same test method  explicitly for visibility.
    super.findNode();
  }

  @Override
  @Test
  void addNode() {
    // Since addNode(...) is implemented in BinarySearchTree, calling same test method  explicitly for visibility.
    super.addNode();
  }

  @Override
  @Test
  void deleteNode() {
    // Since addNode(...) is implemented in BinarySearchTree, calling same test method  explicitly for visibility.
    super.deleteNode();
  }

  @Override
  @Test
  void add() {
    assertFalse(tree.add(null));
    assertEquals(0, tree.size());

    assertTrue(tree.add(13));
    assertEquals(1, tree.size());
    assertEquals(13, tree.getRoot().getValue());
    assertFalse(tree.getRoot().isRed(), "Root node must be black.");

    assertTrue(tree.add(17));
    assertEquals(2, tree.size());
    assertEquals(13, tree.getRoot().getValue());
    assertFalse(tree.getRoot().isRed(), "Root must be black.");
    assertEquals(17, tree.getRoot().getRight().getValue());
    assertTrue(tree.getRoot().getRight().isRed(), "Right must be red.");

    // Left rotation.
    assertTrue(tree.add(25));
    assertEquals(3, tree.size());
    assertEquals(17, tree.getRoot().getValue(), "Root must be rotated.");
    assertFalse(tree.getRoot().isRed(), "Root must be black.");
    assertEquals(13, tree.getRoot().getLeft().getValue());
    assertTrue(tree.getRoot().getLeft().isRed(), "Left must be red.");
    assertEquals(25, tree.getRoot().getRight().getValue());
    assertTrue(tree.getRoot().getRight().isRed(), "Right  must be red.");

    // Color flip.
    assertTrue(tree.add(22));
    assertEquals(4, tree.size());
    assertEquals(13, tree.getRoot().getLeft().getValue());
    assertFalse(tree.getRoot().getLeft().isRed(), "Left must color flip to black.");
    assertEquals(25, tree.getRoot().getRight().getValue());
    assertFalse(tree.getRoot().getRight().isRed(), "Right must color flip to black.");
    assertEquals(22, tree.getRoot().getRight().getLeft().getValue());
    assertTrue(tree.getRoot().getRight().getLeft().isRed(), "New node must be red.");

    // Left-right rotation.
    assertTrue(tree.add(24));
    assertEquals(5, tree.size());
    assertEquals(24, tree.getRoot().getRight().getValue(), "New node must become the parent after rotation.");
    assertFalse(tree.getRoot().getRight().isRed(), "New node must be color flipped to red after rotation.");
    assertEquals(22, tree.getRoot().getRight().getLeft().getValue(), "Parent must become new node's child after rotation.");
    assertTrue(tree.getRoot().getRight().getLeft().isRed(), "Parent must be black after rotation.");
    assertEquals(25, tree.getRoot().getRight().getRight().getValue(), "Grandparent must become new node's child after rotation.");
    assertTrue(tree.getRoot().getRight().getRight().isRed(), "Grandparent must be black after rotation.");

    assertTrue(tree.add(15));
    assertEquals(6, tree.size());
    assertEquals(13, tree.getRoot().getLeft().getValue());
    assertFalse(tree.getRoot().getLeft().isRed());
    assertEquals(15, tree.getRoot().getLeft().getRight().getValue());
    assertTrue(tree.getRoot().getLeft().getRight().isRed());

    // Right-left rotation.
    assertTrue(tree.add(14));
    assertEquals(7, tree.size());
    assertEquals(14, tree.getRoot().getLeft().getValue(), "New node must become the parent after rotation.");
    assertFalse(tree.getRoot().getLeft().isRed(), "New node must be color flipped to black after rotation.");
    assertEquals(13, tree.getRoot().getLeft().getLeft().getValue(), "Grandparent must become node's child after rotation.");
    assertTrue(tree.getRoot().getLeft().getLeft().isRed(), "Grandparent must be red after rotation.");
    assertEquals(15, tree.getRoot().getLeft().getRight().getValue(), "Parent must become new node's child after rotation.");
    assertTrue(tree.getRoot().getLeft().getRight().isRed(), "Parent must be red after rotation.");

    // Color flip.
    assertTrue(tree.add(10));
    assertEquals(8, tree.size());
    assertEquals(14, tree.getRoot().getLeft().getValue());
    assertTrue(tree.getRoot().getLeft().isRed(), "Grandparent must color flip to red.");
    assertEquals(13, tree.getRoot().getLeft().getLeft().getValue());
    assertFalse(tree.getRoot().getLeft().getLeft().isRed(), "Parent must color flip to black.");
    assertEquals(15, tree.getRoot().getLeft().getRight().getValue());
    assertFalse(tree.getRoot().getLeft().getRight().isRed(), "Aunt must color flip to black.");
    assertEquals(10, tree.getRoot().getLeft().getLeft().getLeft().getValue());
    assertTrue(tree.getRoot().getLeft().getLeft().getLeft().isRed(), "New node must be red.");

    // Right rotation.
    assertTrue(tree.add(5));
    assertEquals(9, tree.size());
    assertEquals(10, tree.getRoot().getLeft().getLeft().getValue(), "Grandparent must be rotated.");
    assertFalse(tree.getRoot().getLeft().getLeft().isRed(), "Parent must be black.");
    assertEquals(13, tree.getRoot().getLeft().getLeft().getRight().getValue());
    assertTrue(tree.getRoot().getLeft().getLeft().getRight().isRed(), "Grandparent must be red.");
    assertEquals(5, tree.getRoot().getLeft().getLeft().getLeft().getValue());
    assertTrue(tree.getRoot().getLeft().getLeft().getLeft().isRed(), "New node must be red.");
  }

  @Override
  @Test
  void delete() {
    fail("Test cases should be added.");
  }
}
