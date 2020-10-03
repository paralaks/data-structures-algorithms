package paralaks_gmail_com.data_structures_algorithms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import paralaks_gmail_com.data_structures_algorithms.graph.BSTNode;
import paralaks_gmail_com.data_structures_algorithms.graph.BinarySearchTree;

import static org.junit.jupiter.api.Assertions.*;

class BinarySearchTreeTest extends BinaryTreeTest {

  @Override
  @BeforeEach
  void setUp() {
    tree = new BinarySearchTree<>();
    assertNull(tree.getRoot());
    assertEquals(0, tree.size());
  }

  void populateTree() {
    tree.add(100);

    tree.add(80);
    tree.add(120);

    tree.add(60);
    tree.add(90);
    tree.add(110);
    tree.add(140);

    tree.add(160);
  }

  @Test
  void add() {
    assertFalse(tree.add(null), "Adding null value must fail.");
    assertEquals(0, tree.size(), "Adding null value must not increase size.");

    assertTrue(tree.add(100), "Adding non-null value must succeed.");
    assertEquals(100, tree.getRoot().getValue(), "Adding non-null value to empty tree must add as root node.");
    assertEquals(1, tree.size(), "Adding non-null value must increase size.");

    assertTrue(tree.add(50), "Adding non-null value must succeed.");
    assertEquals(50, tree.getRoot().getLeft().getValue(), "Adding a smaller value must add it as left child.");
    assertEquals(2, tree.size(), "Adding non-null value must increase size.");

    assertTrue(tree.add(150), "Adding non-null value must succeed.");
    assertEquals(150, tree.getRoot().getRight().getValue(), "Adding a smaller value must add it as right child.");
    assertEquals(3, tree.size(), "Adding non-null value must increase size.");

    assertTrue(tree.add(150), "Adding non-null value must succeed.");
    assertEquals(150, tree.getRoot().getRight().getRight().getValue(), "Adding an existing value must add it to the right sub-tree of the node of the same value.");
    assertEquals(4, tree.size(), "Adding non-null value must increase size.");

    assertTrue(tree.add(75), "Adding non-null value must succeed.");
    assertEquals(75, tree.getRoot().getLeft().getRight().getValue(), "Adding a value less than parent, greater than parent's child must add it to right sub-tree of the child.");
    assertEquals(5, tree.size(), "Adding non-null value must increase size.");

    assertTrue(tree.add(100));
    assertEquals(100, tree.getRoot().getRight().getLeft().getValue(), "Adding an existing value must add it to the right sub-tree of the node of the same value.");
    assertEquals(6, tree.size());

    assertTrue(tree.add(200));
    assertEquals(200, tree.getRoot().getRight().getRight().getRight().getValue(), "Adding the biggest value must add it as the rightmost leaf node.");
    assertEquals(7, tree.size());

    assertTrue(tree.add(25), "Adding non-null value must succeed.");
    assertEquals(25, tree.getRoot().getLeft().getLeft().getValue(), "Adding the smallest value must add it as the leftmost leaf node.");
    assertEquals(8, tree.size(), "Adding non-null value must increase size.");

    // Few extra additions for differentiating AVL tree tests.
    assertTrue(tree.add(300), "Adding non-null value must succeed.");
    assertEquals(300, tree.getRoot().getRight().getRight().getRight().getRight().getValue(), "Adding the biggest value must add it as the rightmost leaf node.");
    assertEquals(9, tree.size(), "Adding non-null value must increase size.");

    assertTrue(tree.add(60), "Adding non-null value must succeed.");
    assertEquals(60, tree.getRoot().getLeft().getRight().getLeft().getValue());
    assertEquals(10, tree.size(), "Adding non-null value must increase size.");

    assertTrue(tree.add(65), "Adding non-null value must succeed.");
    assertEquals(65, tree.getRoot().getLeft().getRight().getLeft().getRight().getValue());
    assertEquals(11, tree.size(), "Adding non-null value must increase size.");
  }

  @Test
  void delete() {
    assertEquals(0, tree.size());
    assertNull(tree.getRoot());
    assertFalse(tree.delete(1), "Deleting from empty tree must fail.");

    populateTree();

    assertFalse(tree.delete(null), "Deleting null value must fail.");
    assertEquals(8, tree.size(), "Deleting null value must not decrease size.");

    assertFalse(tree.delete(101), "Deleting a value which is not in the tree must fail.");
    assertEquals(8, tree.size(), "Deleting a value which is not in the tree must not decrease size.");

    assertTrue(tree.delete(90), "Deleting an existing value must succeed.");
    assertEquals(7, tree.size(), "Deleting an existing value must decrease size.");

    assertTrue(tree.delete(60));
    assertEquals(6, tree.size());

    assertTrue(tree.delete(160));
    assertEquals(5, tree.size());

    assertTrue(tree.delete(100));
    assertEquals(4, tree.size());

    assertTrue(tree.delete(80));
    assertEquals(3, tree.size());

    assertTrue(tree.delete(120));
    assertEquals(2, tree.size());

    assertTrue(tree.delete(110));
    assertEquals(1, tree.size());

    assertTrue(tree.delete(140), "Deleting an existing value must succeed.");
    assertEquals(0, tree.size(), "Deleting the only element must decrease size to zero.");
    assertNull(tree.getRoot(), "Deleting the only element must set root to null.");
  }

  @Test
  void findNode() {
    assertNull(tree.findNode(null, 30), "Search must return null when root node is null.");
    assertNull(tree.findNode(tree.getRoot(), null), "Search must return null searched value is null.");
    assertNull(tree.findNode(tree.getRoot(), 10), "Search must return null when searched value is not  in the tree.");

    populateTree();
    assertEquals(tree.getRoot(), tree.findNode(tree.getRoot(), 100), "Searching for root node value must return root not.");

    assertEquals(tree.getRoot().getLeft(), tree.findNode(tree.getRoot(), 80));
    assertEquals(tree.getRoot().getLeft().getLeft(), tree.findNode(tree.getRoot(), 60));
    assertEquals(tree.getRoot().getLeft().getLeft().getLeft(), tree.findNode(tree.getRoot(), 40));
    assertEquals(tree.getRoot().getRight(), tree.findNode(tree.getRoot(), 120));
    assertEquals(tree.getRoot().getRight().getLeft(), tree.findNode(tree.getRoot(), 110));
    assertEquals(tree.getRoot().getRight().getRight(), tree.findNode(tree.getRoot(), 140));
    assertEquals(tree.getRoot().getRight().getRight().getRight(), tree.findNode(tree.getRoot(), 160));

    assertEquals(tree.getRoot().getLeft().getLeft().getLeft(), tree.findNode(tree.getRoot().getLeft(), 40));
    assertNull(tree.findNode(tree.getRoot().getRight(), 40));
    assertEquals(tree.getRoot().getRight().getRight().getRight(), tree.findNode(tree.getRoot().getRight(), 160));
    assertNull(tree.findNode(tree.getRoot().getLeft(), 160));
  }

  @Test
  void addNode() {
    assertNull(tree.addNode(null, 1), "When parent node is null, nothing is added.");
    assertNull(tree.addNode(new BSTNode<>(1), null), "Null value can not be added.");

    tree.setRoot(new BSTNode<>(40));

    BSTNode<Integer> root = tree.getRoot();
    BSTNode<Integer> added;

    assertNull(tree.getRoot().getLeft());
    added = tree.addNode(tree.getRoot(), 20);
    assertEquals(root.getLeft(), added, "Adding a smaller value when parent's left child is null must make the new node left child.");
    assertEquals(root, added.getParent(), "New child's parent must be updated to point to the parent node.");
    assertEquals(20, added.getValue());

    assertNull(tree.getRoot().getRight());
    added = tree.addNode(tree.getRoot(), 60);
    assertEquals(root.getRight(), added, "Adding a bigger value when parent's right child is null must make the new node right child.");
    assertEquals(root, added.getParent(), "New child's parent must be updated to point to the parent node.");
    assertEquals(60, added.getValue());

    added = tree.addNode(tree.getRoot(), 30);
    assertEquals(root.getLeft().getRight(), added, "Adding a smaller value when parent's left child is not null must add the value to left child's subtree.");
    assertEquals(root.getLeft(), added.getParent(), "New child's parent must be updated to point to the parent's left child node.");
    assertEquals(30, added.getValue());

    added = tree.addNode(tree.getRoot(), 50);
    assertEquals(root.getRight().getLeft(), added, "Adding a bigger value when parent's right child is not null must add the value to right child's subtree.");
    assertEquals(root.getRight(), added.getParent(), "New child's parent must be updated to point to the parent's right child node.");
    assertEquals(50, added.getValue());

    added = tree.addNode(tree.getRoot(), 50);
    assertEquals(root.getRight().getLeft().getRight(), added, "Adding a duplicate value must add the value to right subtree of the existing duplicate node.");
    assertEquals(root.getRight().getLeft(), added.getParent(), "New child's parent must be updated to point to the parent's right child's left child node.");
    assertEquals(50, added.getValue());
  }

  @Test
  void swapWithBiggestChild() {
    tree.add(100);
    BSTNode<Integer> root = tree.getRoot();

    tree.swapWithBiggestChild(root);
    assertEquals(100, root.getValue(), "If node has no children, its value must not change.");

    tree.add(120);
    tree.swapWithBiggestChild(root);
    assertEquals(100, root.getValue(), "If node has no left subtree, its value must not change.");

    tree.add(80);
    tree.swapWithBiggestChild(root);
    assertEquals(80, root.getValue(), "If node has left child leaf node, its value must be updated to child's value.");
    assertEquals(100, root.getLeft().getValue(), "If node has left child leaf node, child's value must be updated to node.");
    tree.swapValue(root, root.getLeft()); // Revert swap.

    tree.add(90);
    tree.swapWithBiggestChild(root);
    assertEquals(90, root.getValue(), "If node has left child with children, its value must be updated to biggest value.");
    assertEquals(100, root.getLeft().getRight().getValue(), "If node has left child with children, biggest  value must be updated to node.");
    tree.swapValue(root, root.getLeft().getRight()); // Revert swap.
  }

  @Test
  void deleteNode() {
    assertNull(tree.deleteNode(null));
    assertNull(tree.deleteNode(99));

    tree.setRoot(new BSTNode<>(100));
    tree.getRoot().setLeft(new BSTNode<>(90, tree.getRoot()));
    tree.getRoot().setRight(new BSTNode<>(110, tree.getRoot()));

    tree.getRoot().getLeft().setLeft(new BSTNode<>(80, tree.getRoot().getLeft()));
    tree.getRoot().getRight().setRight(new BSTNode<>(120, tree.getRoot().getRight()));

    tree.getRoot().getLeft().getLeft().setLeft(new BSTNode<>(70, tree.getRoot().getLeft().getLeft()));
    tree.getRoot().getLeft().getLeft().setRight(new BSTNode<>(85, tree.getRoot().getLeft().getLeft()));
    tree.getRoot().getRight().getRight().setRight(new BSTNode<>(130, tree.getRoot().getRight().getRight()));

    BSTNode<Integer> node;
    BSTNode<Integer> parent;
    BSTNode<Integer> child;

    node = tree.findNode(tree.getRoot(), 70);
    parent = node.getParent();
    assertEquals(node, parent.getLeft());
    assertEquals(node, tree.deleteNode(node.getValue()), "Deleting a left leaf node returns the node.");
    assertNull(parent.getLeft(), "Deleting a left leaf node sets parent's left child to null.");

    node = tree.findNode(tree.getRoot(), 90);
    parent = node.getParent();
    child = node.getLeft();
    assertEquals(node, parent.getLeft());
    assertEquals(node, child.getParent());
    assertEquals(node, tree.deleteNode(node.getValue()), "Deleting a left node with only left child returns the node.");
    assertEquals(90, node.getValue(), "Deleting a left node with only left child returns the node with value unchanged.");
    assertEquals(child, parent.getLeft(), "Deleting a left node with only left child makes the child parent's left node.");
    assertEquals(parent, child.getParent(), "Deleting a left node with only left child sets node's parent to child's parent ");


    node = tree.findNode(tree.getRoot(), 130);
    parent = node.getParent();
    assertEquals(node, parent.getRight());
    assertEquals(node, tree.deleteNode(node.getValue()), "Deleting a right leaf node returns the node.");
    assertNull(parent.getRight(), "Deleting a right leaf node sets parent's right child to null.");

    node = tree.findNode(tree.getRoot(), 110);
    parent = node.getParent();
    child = node.getRight();
    assertEquals(node, parent.getRight());
    assertEquals(node, child.getParent());
    assertEquals(node, tree.deleteNode(node.getValue()), "Deleting a right node with only right child returns the node.");
    assertEquals(110, node.getValue(), "Deleting a right node with only right child returns the node with value unchanged.");
    assertEquals(child, parent.getRight(), "Deleting a right node with only right child makes the child parent's right node.");
    assertEquals(parent, child.getParent(), "Deleting a right node with only right child sets node's parent to child's parent ");


    BSTNode<Integer> biggestRightChild = tree.findNode(tree.getRoot(), 85);
    node = tree.getRoot();
    assertNotNull(node.getLeft());
    assertNotNull(node.getRight());
    assertEquals(node.getLeft().getRight(), biggestRightChild);
    assertEquals(biggestRightChild, tree.deleteNode(node.getValue()), "Deleting a node with both children returns the biggest right child node.");
    assertEquals(100, biggestRightChild.getValue(), "Deleting a node with both children sets biggest right child node's value to the value to be deleted.");
    assertEquals(85, node.getValue(), "Deleting a node with both children sets the value to be deleted to biggest right child node's value.");
    assertNull(node.getLeft().getRight(), "Deleting a node with both children removes biggest right child node from the tree.");


    assertEquals(tree.getRoot().getLeft(), tree.deleteNode(80));
    assertEquals(tree.getRoot().getRight(), tree.deleteNode(120));
    assertEquals(tree.getRoot(), tree.deleteNode(85));
    assertNull(tree.getRoot());
  }
}
