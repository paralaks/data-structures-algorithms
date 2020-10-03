package paralaks_gmail_com.data_structures_algorithms;

import org.junit.jupiter.api.*;
import paralaks_gmail_com.data_structures_algorithms.graph.AVLTree;

import static org.junit.jupiter.api.Assertions.*;

class AVLTreeTest extends BinarySearchTreeTest {
  @Override
  @BeforeEach
  void setUp() {
    tree = new AVLTree<>();
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
    assertEquals(150, tree.getRoot().getRight().getRight().getValue(), "Adding an existing value must add it to right sub-tree.");
    assertEquals(4, tree.size(), "Adding non-null value must increase size.");

    assertTrue(tree.add(75), "Adding non-null value must succeed.");
    assertEquals(75, tree.getRoot().getLeft().getRight().getValue(), "Adding a value less than parent, greater than child must add it to right sub-tree.");
    assertEquals(5, tree.size(), "Adding non-null value must increase size.");

    assertTrue(tree.add(100), "Adding non-null value must succeed.");
    assertEquals(100, tree.getRoot().getRight().getLeft().getValue(), "Adding a value greater than or equal to parent, less than child must add it to left sub-tree.");
    assertEquals(6, tree.size(), "Adding non-null value must increase size.");

    assertTrue(tree.add(200), "Adding non-null value must succeed.");
    assertEquals(200, tree.getRoot().getRight().getRight().getRight().getValue(), "Adding biggest value must add it as the rightmost leaf node.");

    assertTrue(tree.add(25), "Adding non-null value must succeed.");
    assertEquals(25, tree.getRoot().getLeft().getLeft().getValue(), "Adding smallest value must add it as the leftmost leaf node.");
    assertTrue(tree.add(300), "Adding non-null value must succeed.");
    assertEquals(200, tree.getRoot().getRight().getRight().getValue(), "Rebalancing must move added node's parent to grandparent location.");
    assertEquals(150, tree.getRoot().getRight().getRight().getLeft().getValue(), "Rebalancing must move added node's grandparent to parent's left child.");
    assertEquals(300, tree.getRoot().getRight().getRight().getRight().getValue(), "Rebalancing must leave added node as parent's right child.");

    assertTrue(tree.add(60), "Adding non-null value must succeed.");
    assertEquals(60, tree.getRoot().getLeft().getRight().getLeft().getValue(), "Rebalancing must move added node to grandparent location.");
    assertTrue(tree.add(65), "Adding non-null value must succeed.");
    assertEquals(65, tree.getRoot().getLeft().getRight().getValue(), "Rebalancing must move added node to grandparent location.");
    assertEquals(60, tree.getRoot().getLeft().getRight().getLeft().getValue(), "Rebalancing must move added node's grandparent to node's left child.");
    assertEquals(75, tree.getRoot().getLeft().getRight().getRight().getValue(), "Rebalancing must move added node's parent to node's right child.");
  }

  @Override
  @Test
  void delete() {
    assertFalse(tree.delete(1), "Deleting from empty tree must fail.");

    populateTree();
    tree.add(50);

    assertFalse(tree.delete(null), "Deleting null value must fail.");
    assertEquals(9, tree.size(), "Deleting null value must not decrease size.");

    assertFalse(tree.delete(40), "Deleting a value which is not in the tree must fail.");
    assertEquals(9, tree.size(), "Deleting a value which is not in the tree must not decrease size.");

    assertEquals(120, tree.getRoot().getRight().getValue());
    assertEquals(110, tree.getRoot().getRight().getLeft().getValue());
    assertEquals(140, tree.getRoot().getRight().getRight().getValue());
    assertEquals(160, tree.getRoot().getRight().getRight().getRight().getValue());
    assertTrue(tree.delete(110), "Deleting a leaf node must succeed.");
    assertEquals(8, tree.size(), "Deleting an existing value must decrease size.");
    assertEquals(140, tree.getRoot().getRight().getValue(), "Tree must be rebalanced.");
    assertEquals(120, tree.getRoot().getRight().getLeft().getValue(), "Tree must be rebalanced.");
    assertEquals(160, tree.getRoot().getRight().getRight().getValue(), "Tree must be rebalanced.");

    assertTrue(tree.delete(140), "Deleting a node with both left and right children must succeed.");
    assertEquals(7, tree.size(), "Deleting an existing value must decrease size.");
    assertEquals(120, tree.getRoot().getRight().getValue());
    assertNull(tree.getRoot().getRight().getLeft());
    assertEquals(160, tree.getRoot().getRight().getRight().getValue());

    assertTrue(tree.delete(90), "Deleting a leaf node must succeed.");
    assertEquals(6, tree.size(), "Deleting an existing value must decrease size.");
    assertEquals(60, tree.getRoot().getLeft().getValue(), "Tree must be rebalanced.");
    assertEquals(80, tree.getRoot().getLeft().getRight().getValue(), "Tree must be rebalanced.");


    assertTrue(tree.delete(100), "Deleting root node with both left and right children must succeed.");
    assertEquals(5, tree.size(), "Deleting an existing value must decrease size.");
    assertEquals(80, tree.getRoot().getValue());
    assertEquals(60, tree.getRoot().getLeft().getValue());
    assertEquals(120, tree.getRoot().getRight().getValue());

    assertTrue(tree.delete(160), "Deleting a leaf node must succeed.");
    assertEquals(4, tree.size(), "Deleting an existing value must decrease size.");
    assertNull(tree.getRoot().getRight().getRight());

    assertTrue(tree.delete(120), "Deleting a leaf node must succeed.");
    assertEquals(3, tree.size(), "Deleting an existing value must decrease size.");
    assertEquals(60, tree.getRoot().getValue(), "Tree must be rebalanced.");
    assertEquals(50, tree.getRoot().getLeft().getValue(), "Tree must be rebalanced.");
    assertEquals(80, tree.getRoot().getRight().getValue(), "Tree must be rebalanced.");

    assertTrue(tree.delete(50), "Deleting a leaf node must succeed.");
    assertEquals(2, tree.size(), "Deleting null value must decrease size.");
    assertEquals(60, tree.getRoot().getValue());
    assertEquals(80, tree.getRoot().getRight().getValue());

    assertTrue(tree.delete(60), "Deleting root node must succeed.");
    assertEquals(1, tree.size(), "Deleting null value must decrease size.");
    assertEquals(80, tree.getRoot().getValue());

    assertTrue(tree.delete(80), "Deleting root node must succeed.");
    assertEquals(0, tree.size(), "Deleting null value must decrease size.");
    assertNull(tree.getRoot(), "Tree must be empty.");
  }


  @Test
  void rebalance() {
    tree.add(50);
    tree.add(30);

    tree.add(10); // rebalance by right rotation
    assertEquals(tree.getRoot().getValue(), 30);
    assertEquals(tree.getRoot().getLeft().getValue(), 10);
    assertEquals(tree.getRoot().getRight().getValue(), 50);

    tree.add(70);
    tree.add(90); // rebalance by left rotation
    assertEquals(tree.getRoot().getValue(), 30);
    assertEquals(tree.getRoot().getLeft().getValue(), 10);
    assertEquals(tree.getRoot().getRight().getValue(), 70);
    assertEquals(tree.getRoot().getRight().getLeft().getValue(), 50);
    assertEquals(tree.getRoot().getRight().getRight().getValue(), 90);

    tree.add(40); // rebalance by right-left rotation
    assertEquals(tree.getRoot().getValue(), 50);
    assertEquals(tree.getRoot().getLeft().getValue(), 30);
    assertEquals(tree.getRoot().getLeft().getLeft().getValue(), 10);
    assertEquals(tree.getRoot().getLeft().getRight().getValue(), 40);
    assertEquals(tree.getRoot().getRight().getValue(), 70);
    assertEquals(tree.getRoot().getRight().getRight().getValue(), 90);

    tree.add(5);
    tree.add(3); // rebalance by left-right rotation
    assertEquals(tree.getRoot().getValue(), 50);
    assertEquals(tree.getRoot().getLeft().getValue(), 30);
    assertEquals(tree.getRoot().getLeft().getLeft().getValue(), 5);
    assertEquals(tree.getRoot().getLeft().getRight().getValue(), 40);
    assertEquals(tree.getRoot().getLeft().getLeft().getLeft().getValue(), 3);
    assertEquals(tree.getRoot().getLeft().getLeft().getRight().getValue(), 10);
    assertEquals(tree.getRoot().getRight().getValue(), 70);
    assertEquals(tree.getRoot().getRight().getRight().getValue(), 90);

    tree.add(45); // goes to right of 40
    assertEquals(tree.getRoot().getLeft().getRight().getRight().getValue(), 45);
    tree.add(68); // goes to left of 70
    assertEquals(tree.getRoot().getRight().getLeft().getValue(), 68);
    tree.add(65); // goes to left of 68
    assertEquals(tree.getRoot().getRight().getLeft().getLeft().getValue(), 65);
    tree.add(12); // goes to right of 10
    assertEquals(tree.getRoot().getLeft().getLeft().getRight().getRight().getValue(), 12);

    tree.delete(90); // rebalance after 2 rotations
    assertEquals(tree.getRoot().getValue(), 30);
    assertEquals(tree.getRoot().getLeft().getValue(), 5);
    assertEquals(tree.getRoot().getLeft().getLeft().getValue(), 3);
    assertEquals(tree.getRoot().getLeft().getRight().getValue(), 10);
    assertEquals(tree.getRoot().getLeft().getRight().getRight().getValue(), 12);
    assertEquals(tree.getRoot().getRight().getValue(), 50);
    assertEquals(tree.getRoot().getRight().getLeft().getValue(), 40);
    assertEquals(tree.getRoot().getRight().getLeft().getRight().getValue(), 45);
    assertEquals(tree.getRoot().getRight().getRight().getValue(), 68);
    assertEquals(tree.getRoot().getRight().getRight().getLeft().getValue(), 65);
    assertEquals(tree.getRoot().getRight().getRight().getRight().getValue(), 70);
  }
}
