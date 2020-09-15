package paralaks_gmail_com.data_structures_algorithms;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class AVLTreeTest extends BinarySearchTreeTest {
  @Override
  @BeforeEach
  void setUp() {
    tree = new AVLTree<>();
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
    assertEquals(150, tree.root.right.right.value, "Adding an existing value must add it to right sub-tree.");
    assertEquals(4, tree.size(), "Adding non-null value must increase size.");

    assertTrue(tree.add(75), "Adding non-null value must succeed.");
    assertEquals(75, tree.root.left.right.value, "Adding a value less than parent, greater than child must add it to right sub-tree.");
    assertEquals(5, tree.size(), "Adding non-null value must increase size.");

    assertTrue(tree.add(100), "Adding non-null value must succeed.");
    assertEquals(100, tree.root.right.left.value, "Adding a value greater than or equal to parent, less than child must add it to left sub-tree.");
    assertEquals(6, tree.size(), "Adding non-null value must increase size.");

    assertTrue(tree.add(200), "Adding non-null value must succeed.");
    assertEquals(200, tree.root.right.right.right.value, "Adding biggest value must add it as the rightmost leaf node.");

    assertTrue(tree.add(25), "Adding non-null value must succeed.");
    assertEquals(25, tree.root.left.left.value, "Adding smallest value must add it as the leftmost leaf node.");
    assertTrue(tree.add(300), "Adding non-null value must succeed.");
    assertEquals(200, tree.root.right.right.value, "Rebalancing must move added node's parent to grandparent location.");
    assertEquals(150, tree.root.right.right.left.value, "Rebalancing must move added node's grandparent to parent's left child.");
    assertEquals(300, tree.root.right.right.right.value, "Rebalancing must leave added node as parent's right child.");

    assertTrue(tree.add(60), "Adding non-null value must succeed.");
    assertEquals(60, tree.root.left.right.left.value, "Rebalancing must move added node to grandparent location.");
    assertTrue(tree.add(65), "Adding non-null value must succeed.");
    assertEquals(65, tree.root.left.right.value, "Rebalancing must move added node to grandparent location.");
    assertEquals(60, tree.root.left.right.left.value, "Rebalancing must move added node's grandparent to node's left child.");
    assertEquals(75, tree.root.left.right.right.value, "Rebalancing must move added node's parent to node's right child.");
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

    assertEquals(120, tree.root.right.value);
    assertEquals(110, tree.root.right.left.value);
    assertEquals(140, tree.root.right.right.value);
    assertEquals(160, tree.root.right.right.right.value);
    assertTrue(tree.delete(110), "Deleting a leaf node must succeed.");
    assertEquals(8, tree.size(), "Deleting an existing value must decrease size.");
    assertEquals(140, tree.root.right.value, "Tree must be rebalanced.");
    assertEquals(120, tree.root.right.left.value, "Tree must be rebalanced.");
    assertEquals(160, tree.root.right.right.value, "Tree must be rebalanced.");

    assertTrue(tree.delete(140), "Deleting a node with both left and right children must succeed.");
    assertEquals(7, tree.size(), "Deleting an existing value must decrease size.");
    assertEquals(120, tree.root.right.value);
    assertNull(tree.root.right.left);
    assertEquals(160, tree.root.right.right.value);

    assertTrue(tree.delete(90), "Deleting a leaf node must succeed.");
    assertEquals(6, tree.size(), "Deleting an existing value must decrease size.");
    assertEquals(60, tree.root.left.value, "Tree must be rebalanced.");
    assertEquals(80, tree.root.left.right.value, "Tree must be rebalanced.");


    assertTrue(tree.delete(100), "Deleting root node with both left and right children must succeed.");
    assertEquals(5, tree.size(), "Deleting an existing value must decrease size.");
    assertEquals(80, tree.root.value);
    assertEquals(60, tree.root.left.value);
    assertEquals(120, tree.root.right.value);

    assertTrue(tree.delete(160), "Deleting a leaf node must succeed.");
    assertEquals(4, tree.size(), "Deleting an existing value must decrease size.");
    assertNull(tree.root.right.right);

    assertTrue(tree.delete(120), "Deleting a leaf node must succeed.");
    assertEquals(3, tree.size(), "Deleting an existing value must decrease size.");
    assertEquals(60, tree.root.value, "Tree must be rebalanced.");
    assertEquals(50, tree.root.left.value, "Tree must be rebalanced.");
    assertEquals(80, tree.root.right.value, "Tree must be rebalanced.");

    assertTrue(tree.delete(50), "Deleting a leaf node must succeed.");
    assertEquals(2, tree.size(), "Deleting null value must decrease size.");
    assertEquals(60, tree.root.value);
    assertEquals(80, tree.root.right.value);

    assertTrue(tree.delete(60), "Deleting root node must succeed.");
    assertEquals(1, tree.size(), "Deleting null value must decrease size.");
    assertEquals(80, tree.root.value);

    assertTrue(tree.delete(80), "Deleting root node must succeed.");
    assertEquals(0, tree.size(), "Deleting null value must decrease size.");
    assertNull(tree.root, "Tree must be empty.");
  }


  @Test
  void rebalance() {
    tree.add(50);
    tree.add(30);

    tree.add(10); // rebalance by right rotation
    assertEquals(tree.root.value, 30);
    assertEquals(tree.root.left.value, 10);
    assertEquals(tree.root.right.value, 50);

    tree.add(70);
    tree.add(90); // rebalance by left rotation
    assertEquals(tree.root.value, 30);
    assertEquals(tree.root.left.value, 10);
    assertEquals(tree.root.right.value, 70);
    assertEquals(tree.root.right.left.value, 50);
    assertEquals(tree.root.right.right.value, 90);

    tree.add(40); // rebalance by right-left rotation
    assertEquals(tree.root.value, 50);
    assertEquals(tree.root.left.value, 30);
    assertEquals(tree.root.left.left.value, 10);
    assertEquals(tree.root.left.right.value, 40);
    assertEquals(tree.root.right.value, 70);
    assertEquals(tree.root.right.right.value, 90);

    tree.add(5);
    tree.add(3); // rebalance by left-right rotation
    assertEquals(tree.root.value, 50);
    assertEquals(tree.root.left.value, 30);
    assertEquals(tree.root.left.left.value, 5);
    assertEquals(tree.root.left.right.value, 40);
    assertEquals(tree.root.left.left.left.value, 3);
    assertEquals(tree.root.left.left.right.value, 10);
    assertEquals(tree.root.right.value, 70);
    assertEquals(tree.root.right.right.value, 90);

    tree.add(45); // goes to right of 40
    assertEquals(tree.root.left.right.right.value, 45);
    tree.add(68); // goes to left of 70
    assertEquals(tree.root.right.left.value, 68);
    tree.add(65); // goes to left of 68
    assertEquals(tree.root.right.left.left.value, 65);
    tree.add(12); // goes to right of 10
    assertEquals(tree.root.left.left.right.right.value, 12);

    tree.delete(90); // rebalance after 2 rotations
    assertEquals(tree.root.value, 30);
    assertEquals(tree.root.left.value, 5);
    assertEquals(tree.root.left.left.value, 3);
    assertEquals(tree.root.left.right.value, 10);
    assertEquals(tree.root.left.right.right.value, 12);
    assertEquals(tree.root.right.value, 50);
    assertEquals(tree.root.right.left.value, 40);
    assertEquals(tree.root.right.left.right.value, 45);
    assertEquals(tree.root.right.right.value, 68);
    assertEquals(tree.root.right.right.left.value, 65);
    assertEquals(tree.root.right.right.right.value, 70);
  }
}
