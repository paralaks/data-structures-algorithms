package paralaks_gmail_com.data_structures_algorithms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RedBlackTreeTest extends BinarySearchTreeTest {
  @Override
  @BeforeEach
  void setUp() {
    tree = new RedBlackTree<>();
    assertNull(tree.root);
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
    assertEquals(0, tree.size);

    assertTrue(tree.add(13));
    assertEquals(1, tree.size);
    assertEquals(13, tree.root.value);
    assertFalse(tree.root.isRed, "Root node must be black.");

    assertTrue(tree.add(17));
    assertEquals(2, tree.size);
    assertEquals(13, tree.root.value);
    assertFalse(tree.root.isRed, "Root must be black.");
    assertEquals(17, tree.root.right.value);
    assertTrue(tree.root.right.isRed, "Right must be red.");

    // Left rotation.
    assertTrue(tree.add(25));
    assertEquals(3, tree.size);
    assertEquals(17, tree.root.value, "Root must be rotated.");
    assertFalse(tree.root.isRed, "Root must be black.");
    assertEquals(13, tree.root.left.value);
    assertTrue(tree.root.left.isRed, "Left must be red.");
    assertEquals(25, tree.root.right.value);
    assertTrue(tree.root.right.isRed, "Right  must be red.");

    // Color flip.
    assertTrue(tree.add(22));
    assertEquals(4, tree.size);
    assertEquals(13, tree.root.left.value);
    assertFalse(tree.root.left.isRed, "Left must color flip to black.");
    assertEquals(25, tree.root.right.value);
    assertFalse(tree.root.right.isRed, "Right must color flip to black.");
    assertEquals(22, tree.root.right.left.value);
    assertTrue(tree.root.right.left.isRed, "New node must be red.");

    // Left-right rotation.
    assertTrue(tree.add(24));
    assertEquals(5, tree.size);
    assertEquals(24, tree.root.right.value, "New node must become the parent after rotation.");
    assertFalse(tree.root.right.isRed, "New node must be color flipped to red after rotation.");
    assertEquals(22, tree.root.right.left.value, "Parent must become new node's child after rotation.");
    assertTrue(tree.root.right.left.isRed, "Parent must be black after rotation.");
    assertEquals(25, tree.root.right.right.value, "Grandparent must become new node's child after rotation.");
    assertTrue(tree.root.right.right.isRed, "Grandparent must be black after rotation.");

    assertTrue(tree.add(15));
    assertEquals(6, tree.size);
    assertEquals(13, tree.root.left.value);
    assertFalse(tree.root.left.isRed);
    assertEquals(15, tree.root.left.right.value);
    assertTrue(tree.root.left.right.isRed);

    // Right-left rotation.
    assertTrue(tree.add(14));
    assertEquals(7, tree.size);
    assertEquals(14, tree.root.left.value, "New node must become the parent after rotation.");
    assertFalse(tree.root.left.isRed, "New node must be color flipped to black after rotation.");
    assertEquals(13, tree.root.left.left.value, "Grandparent must become node's child after rotation.");
    assertTrue(tree.root.left.left.isRed, "Grandparent must be red after rotation.");
    assertEquals(15, tree.root.left.right.value, "Parent must become new node's child after rotation.");
    assertTrue(tree.root.left.right.isRed, "Parent must be red after rotation.");

    // Color flip.
    assertTrue(tree.add(10));
    assertEquals(8, tree.size);
    assertEquals(14, tree.root.left.value);
    assertTrue(tree.root.left.isRed, "Grandparent must color flip to red.");
    assertEquals(13, tree.root.left.left.value);
    assertFalse(tree.root.left.left.isRed, "Parent must color flip to black.");
    assertEquals(15, tree.root.left.right.value);
    assertFalse(tree.root.left.right.isRed, "Aunt must color flip to black.");
    assertEquals(10, tree.root.left.left.left.value);
    assertTrue(tree.root.left.left.left.isRed, "New node must be red.");

    // Right rotation.
    assertTrue(tree.add(5));
    assertEquals(9, tree.size);
    assertEquals(10, tree.root.left.left.value, "Grandparent must be rotated.");
    assertFalse(tree.root.left.left.isRed, "Parent must be black.");
    assertEquals(13, tree.root.left.left.right.value);
    assertTrue(tree.root.left.left.right.isRed, "Grandparent must be red.");
    assertEquals(5, tree.root.left.left.left.value);
    assertTrue(tree.root.left.left.left.isRed, "New node must be red.");
  }

  @Override
  @Test
  void delete() {
    fail("Test cases should be added.");
  }
}
