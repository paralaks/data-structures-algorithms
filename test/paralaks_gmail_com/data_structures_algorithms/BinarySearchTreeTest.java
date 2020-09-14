package paralaks_gmail_com.data_structures_algorithms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BinarySearchTreeTest extends BinaryTreeTest {

  @Override
  @BeforeEach
  void setUp() {
    tree = new BinarySearchTree<>();
    assertNull(tree.root);
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
    assertEquals(100, tree.root.value, "Adding non-null value to empty tree must add as root node.");
    assertEquals(1, tree.size(), "Adding non-null value must increase size.");

    assertTrue(tree.add(50), "Adding non-null value must succeed.");
    assertEquals(50, tree.root.left.value, "Adding a smaller value must add it as left child.");
    assertEquals(2, tree.size(), "Adding non-null value must increase size.");

    assertTrue(tree.add(150), "Adding non-null value must succeed.");
    assertEquals(150, tree.root.right.value, "Adding a smaller value must add it as right child.");
    assertEquals(3, tree.size(), "Adding non-null value must increase size.");

    assertTrue(tree.add(150), "Adding non-null value must succeed.");
    assertEquals(150, tree.root.right.right.value, "Adding an existing value must add it to the right sub-tree of the node of the same value.");
    assertEquals(4, tree.size(), "Adding non-null value must increase size.");

    assertTrue(tree.add(75), "Adding non-null value must succeed.");
    assertEquals(75, tree.root.left.right.value, "Adding a value less than parent, greater than parent's child must add it to right sub-tree of the child.");
    assertEquals(5, tree.size(), "Adding non-null value must increase size.");

    assertTrue(tree.add(100));
    assertEquals(100, tree.root.right.left.value, "Adding an existing value must add it to the right sub-tree of the node of the same value.");
    assertEquals(6, tree.size());

    assertTrue(tree.add(200));
    assertEquals(200, tree.root.right.right.right.value, "Adding the biggest value must add it as the rightmost leaf node.");
    assertEquals(7, tree.size());

    assertTrue(tree.add(25), "Adding non-null value must succeed.");
    assertEquals(25, tree.root.left.left.value, "Adding the smallest value must add it as the leftmost leaf node.");
    assertEquals(8, tree.size(), "Adding non-null value must increase size.");

    // Few extra additions for differentiating AVL tree tests.
    assertTrue(tree.add(300), "Adding non-null value must succeed.");
    assertEquals(300, tree.root.right.right.right.right.value, "Adding the biggest value must add it as the rightmost leaf node.");
    assertEquals(9, tree.size(), "Adding non-null value must increase size.");

    assertTrue(tree.add(60), "Adding non-null value must succeed.");
    assertEquals(60, tree.root.left.right.left.value);
    assertEquals(10, tree.size(), "Adding non-null value must increase size.");

    assertTrue(tree.add(65), "Adding non-null value must succeed.");
    assertEquals(65, tree.root.left.right.left.right.value);
    assertEquals(11, tree.size(), "Adding non-null value must increase size.");
  }

  @Test
  void delete() {
    assertEquals(0, tree.size());
    assertNull(tree.root);
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
    assertNull(tree.root, "Deleting the only element must set root to null.");
  }

  @Test
  void findNode() {
    assertNull(tree.findNode(null, 30), "Search must return null when root node is null.");
    assertNull(tree.findNode(tree.root, null), "Search must return null searched value is null.");
    assertNull(tree.findNode(tree.root, 10), "Search must return null when searched value is not  in the tree.");

    populateTree();
    assertEquals(tree.root, tree.findNode(tree.root, 100), "Searching for root node value must return root not.");

    assertEquals(tree.root.left, tree.findNode(tree.root, 80));
    assertEquals(tree.root.left.left, tree.findNode(tree.root, 60));
    assertEquals(tree.root.left.left.left, tree.findNode(tree.root, 40));
    assertEquals(tree.root.right, tree.findNode(tree.root, 120));
    assertEquals(tree.root.right.left, tree.findNode(tree.root, 110));
    assertEquals(tree.root.right.right, tree.findNode(tree.root, 140));
    assertEquals(tree.root.right.right.right, tree.findNode(tree.root, 160));

    assertEquals(tree.root.left.left.left, tree.findNode(tree.root.left, 40));
    assertNull(tree.findNode(tree.root.right, 40));
    assertEquals(tree.root.right.right.right, tree.findNode(tree.root.right, 160));
    assertNull(tree.findNode(tree.root.left, 160));
  }

  @Test
  void addNode() {
    assertNull(tree.addNode(null, 1), "When parent node is null, nothing is added.");
    assertNull(tree.addNode(new BinaryTree.TreeNode<>(1), null), "Null value can not be added.");

    tree.root = new BinaryTree.TreeNode<>(40);

    BinaryTree.TreeNode<Integer> root = tree.root;
    BinaryTree.TreeNode<Integer> added;

    assertNull(tree.root.left);
    added = tree.addNode(tree.root, 20);
    assertEquals(root.left, added, "Adding a smaller value when parent's left child is null must make the new node left child.");
    assertEquals(root, added.parent, "New child's parent must be updated to point to the parent node.");
    assertEquals(20, added.value);

    assertNull(tree.root.right);
    added = tree.addNode(tree.root, 60);
    assertEquals(root.right, added, "Adding a bigger value when parent's right child is null must make the new node right child.");
    assertEquals(root, added.parent, "New child's parent must be updated to point to the parent node.");
    assertEquals(60, added.value);

    added = tree.addNode(tree.root, 30);
    assertEquals(root.left.right, added, "Adding a smaller value when parent's left child is not null must add the value to left child's subtree.");
    assertEquals(root.left, added.parent, "New child's parent must be updated to point to the parent's left child node.");
    assertEquals(30, added.value);

    added = tree.addNode(tree.root, 50);
    assertEquals(root.right.left, added, "Adding a bigger value when parent's right child is not null must add the value to right child's subtree.");
    assertEquals(root.right, added.parent, "New child's parent must be updated to point to the parent's right child node.");
    assertEquals(50, added.value);

    added = tree.addNode(tree.root, 50);
    assertEquals(root.right.left.right, added, "Adding a duplicate value must add the value to right subtree of the existing duplicate node.");
    assertEquals(root.right.left, added.parent, "New child's parent must be updated to point to the parent's right child's left child node.");
    assertEquals(50, added.value);
  }

  @Test
  void swapWithBiggestChild() {
    tree.add(100);
    BinaryTree.TreeNode<Integer> root = tree.root;

    tree.swapWithBiggestChild(root);
    assertEquals(100, root.value, "If node has no children, its value must not change.");

    tree.add(120);
    tree.swapWithBiggestChild(root);
    assertEquals(100, root.value, "If node has no left subtree, its value must not change.");

    tree.add(80);
    tree.swapWithBiggestChild(root);
    assertEquals(80, root.value, "If node has left child leaf node, its value must be updated to child's value.");
    assertEquals(100, root.left.value, "If node has left child leaf node, child's value must be updated to node.");
    tree.swapValue(root, root.left); // Revert swap.

    tree.add(90);
    tree.swapWithBiggestChild(root);
    assertEquals(90, root.value, "If node has left child with children, its value must be updated to biggest value.");
    assertEquals(100, root.left.right.value, "If node has left child with children, biggest  value must be updated to node.");
    tree.swapValue(root, root.left.right); // Revert swap.
  }

  @Test
  void deleteNode() {
    assertNull(tree.deleteNode(null));
    assertNull(tree.deleteNode(99));

    tree.root = new BinaryTree.TreeNode<>(100);
    tree.root.left = new BinaryTree.TreeNode<>(90, tree.root);
    tree.root.right = new BinaryTree.TreeNode<>(110, tree.root);

    tree.root.left.left = new BinaryTree.TreeNode<>(80, tree.root.left);
    tree.root.right.right = new BinaryTree.TreeNode<>(120, tree.root.right);

    tree.root.left.left.left = new BinaryTree.TreeNode<>(70, tree.root.left.left);
    tree.root.left.left.right = new BinaryTree.TreeNode<>(85, tree.root.left.left);
    tree.root.right.right.right = new BinaryTree.TreeNode<>(130, tree.root.right.right);

    BinaryTree.TreeNode<Integer> node;
    BinaryTree.TreeNode<Integer> parent;
    BinaryTree.TreeNode<Integer> child;

    node = tree.findNode(tree.root, 70);
    parent = node.parent;
    assertEquals(node, parent.left);
    assertEquals(node, tree.deleteNode(node.value), "Deleting a left leaf node returns the node.");
    assertNull(parent.left, "Deleting a left leaf node sets parent's left child to null.");

    node = tree.findNode(tree.root, 90);
    parent = node.parent;
    child = node.left;
    assertEquals(node, parent.left);
    assertEquals(node, child.parent);
    assertEquals(node, tree.deleteNode(node.value), "Deleting a left node with only left child returns the node.");
    assertEquals(90, node.value, "Deleting a left node with only left child returns the node with value unchanged.");
    assertEquals(child, parent.left, "Deleting a left node with only left child makes the child parent's left node.");
    assertEquals(parent, child.parent, "Deleting a left node with only left child sets node's parent to child's parent ");


    node = tree.findNode(tree.root, 130);
    parent = node.parent;
    assertEquals(node, parent.right);
    assertEquals(node, tree.deleteNode(node.value), "Deleting a right leaf node returns the node.");
    assertNull(parent.right, "Deleting a right leaf node sets parent's right child to null.");

    node = tree.findNode(tree.root, 110);
    parent = node.parent;
    child = node.right;
    assertEquals(node, parent.right);
    assertEquals(node, child.parent);
    assertEquals(node, tree.deleteNode(node.value), "Deleting a right node with only right child returns the node.");
    assertEquals(110, node.value, "Deleting a right node with only right child returns the node with value unchanged.");
    assertEquals(child, parent.right, "Deleting a right node with only right child makes the child parent's right node.");
    assertEquals(parent, child.parent, "Deleting a right node with only right child sets node's parent to child's parent ");


    BinaryTree.TreeNode<Integer> biggestRightChild = tree.findNode(tree.root, 85);
    node = tree.root;
    assertNotNull(node.left);
    assertNotNull(node.right);
    assertEquals(node.left.right, biggestRightChild);
    assertEquals(biggestRightChild, tree.deleteNode(node.value), "Deleting a node with both children returns the biggest right child node.");
    assertEquals(100, biggestRightChild.value, "Deleting a node with both children sets biggest right child node's value to the value to be deleted.");
    assertEquals(85, node.value, "Deleting a node with both children sets the value to be deleted to biggest right child node's value.");
    assertNull(node.left.right, "Deleting a node with both children removes biggest right child node from the tree.");


    assertEquals(tree.root.left, tree.deleteNode(80));
    assertEquals(tree.root.right, tree.deleteNode(120));
    assertEquals(tree.root, tree.deleteNode(85));
    assertNull(tree.root);
  }
}
