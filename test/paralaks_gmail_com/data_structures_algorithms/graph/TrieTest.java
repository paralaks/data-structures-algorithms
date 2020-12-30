package paralaks_gmail_com.data_structures_algorithms.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TrieTest {
  Trie trie;

  @BeforeEach
  public void initTrie() {
    trie = new Trie();
  }

  @Test
  public void trieDoesNotStoreNullOrEmptyString() {
    String s = null;

    assertFalse(trie.contains(s));

    trie.add(s);
    assertFalse(trie.contains(s));

    s = "";
    trie.add(s);
    assertFalse(trie.contains(s));
  }

  @Test
  public void trieStoresAndReturnsOneCharacterString() {
    String a = "a";
    String A = "A";
    String b = "b";
    String B = "B";

    trie.add(a);

    assertTrue(trie.contains(a));
    assertTrue(trie.contains(A));

    assertFalse(trie.contains(b));
    assertFalse(trie.contains(B));
  }

  @Test
  public void trieStoresAndReturnsMultiCharacterString() {
    String a = "a";
    String ab = "Ab";
    String abc = "abC";

    trie.add(a);
    trie.add(ab);
    trie.add(abc);

    assertTrue(trie.contains(a));
    assertTrue(trie.contains(ab));
    assertTrue(trie.contains(abc));

    assertFalse(trie.contains("b"));
    assertFalse(trie.contains("ba"));
    assertFalse(trie.contains("bac"));
    assertFalse(trie.contains("bca"));
  }


  @Test
  public void deletingRemovesString() {
    Trie trie = new Trie(26);

    String a = "a";
    String B = "B";
    String Ab = "Ab";
    String aBc = "aBc";

    trie.add(a);
    trie.add(B);
    trie.add(Ab);
    trie.add(aBc);


    assertTrue(trie.contains(a));
    trie.remove(a);
    assertFalse(trie.contains(a));


    assertTrue(trie.contains(aBc));
    trie.remove(aBc);
    assertFalse(trie.contains(aBc));
    assertTrue(trie.contains(Ab));

    assertTrue(trie.contains(B));
    trie.remove(B);
    assertFalse(trie.contains(B));

    assertTrue(trie.contains(Ab));
    trie.remove(Ab);
    assertFalse(trie.contains(Ab));
  }
}
