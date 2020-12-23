package paralaks_gmail_com.data_structures_algorithms.graph;

public class Trie {
  public static class TrieNode {
    TrieNode parent;
    TrieNode[] children;
    boolean isWord;
    int childCount = 0;

    public TrieNode(TrieNode parent, int symbolCount) {
      this.parent = parent;
      children = new TrieNode[symbolCount];
      isWord = false;
    }
  }

  TrieNode root;
  int symbolCount;

  public static final int DEFAULT_SIZE = 26;

  public Trie() {
    symbolCount = DEFAULT_SIZE;
    root = new TrieNode(null, symbolCount);
  }

  public Trie(int alphabetSize) {
    symbolCount = Math.max(alphabetSize, DEFAULT_SIZE);
    root = new TrieNode(null, symbolCount);
  }

  public int charToIndex(String s, int pos) {
    return s == null || s.length() == 0 || pos < 0 ? -1 : s.charAt(pos) - 'a';
  }

  public void add(String str) {
    if (str == null || str.length() == 0) {
      return;
    }

    TrieNode node = root;
    String value = str.toLowerCase();
    for (int i = 0, end = value.length(); i < end; i++) {
      int idx = charToIndex(value, i);

      if (node.children[idx] == null) {
        node.children[idx] = new TrieNode(node, symbolCount);
        node.childCount++;
      }

      node = node.children[idx];
    }
    node.isWord = true;
  }

  public void remove(String str) {
    if (str == null || str.length() == 0) {
      return;
    }

    TrieNode node = root;
    String value = str.toLowerCase();
    for (int i = 0, end = value.length(); i < end; i++) {
      int idx = charToIndex(value, i);

      if ((node = node.children[idx]) == null) {
        return;
      }
    }
    node.isWord = false;

    TrieNode parent = node.parent;
    for (int i = value.length() - 1, end = -1; i > end; i--) {
      if (node.isWord || node.childCount > 0) {
        break;
      }
      int idx = charToIndex(value, i);

      parent.children[idx] = null;
      parent.childCount--;
      node.parent = null;
      node = parent;
    }
  }

  public boolean contains(String str) {
    if (str == null || str.length() == 0) {
      return false;
    }

    TrieNode node = root;
    String value = str.toLowerCase();

    for (int i = 0, end = value.length(); i < end; i++) {
      int idx = charToIndex(value, i);

      if ((node = node.children[idx]) == null) {
        return false;
      }
    }

    return node.isWord;
  }
}
