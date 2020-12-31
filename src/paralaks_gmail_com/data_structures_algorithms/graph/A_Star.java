package paralaks_gmail_com.data_structures_algorithms.graph;

import paralaks_gmail_com.data_structures_algorithms.*;

import java.util.HashSet;
import java.util.Set;

public class A_Star {
  public static class Pair implements Comparable<Pair> {
    public int i;
    public int j;

    static public int toIndex(int i, int j, int cols) {
      return i * cols + j;
    }

    static public Pair fromIndex(int idx, int cols) {
      int row = idx / cols;
      return new Pair(row, idx - row * cols);
    }

    public Pair(int a, int b) {
      i = a;
      j = b;
    }

    @Override
    public String toString() {
      return "[" + i + "," + j + "]";
    }

    @Override
    public int compareTo(Pair other) {
      return i == other.i && j == other.j ? 0 : -1;
    }
  }

  static class FrontierVertex implements Comparable<FrontierVertex> {
    int vertex;
    int g; // Heuristic distance (h)
    int f; // Frontier distance (f) which is sum of: actual distance (g) and heuristic distance (h)

    FrontierVertex(int vertex, int g, int f) {
      this.vertex = vertex;
      this.g = g;
      this.f = f;
    }

    public int getVertex() {
      return vertex;
    }

    public int getF() {
      return f;
    }

    public int getG() {
      return g;
    }

    @Override
    public int compareTo(FrontierVertex other) {
      return Integer.compare(this.f, other.f);
    }
  }

  private final int SIZE = 25;
  Map<Integer, Integer> hDistance = new HashMap<>(SIZE); // Heuristic distance .
  Map<Integer, Integer> gDistance = new HashMap<>(SIZE); // Distance from start node.

  Map<Integer, Integer> destination = new HashMap<>(SIZE);
  Set<Integer> visited = new HashSet<>();

  int rowEnd;
  int rowStart;
  int colStart;
  int colEnd;
  int rows;
  int cols;

  private void initialize() {
    hDistance.clear();
    gDistance.clear();
    destination.clear();
    visited.clear();
    rowStart = 0;
    rowEnd = 0;
    colStart = 0;
    colEnd = 0;
    rows = 0;
    cols = 0;
  }

  public void calculateShortestPath(int[][] matrix, int rowStart, int colStart, int rowEnd, int colEnd) {

    initialize();

    if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
      System.err.println("There is no matrix data to calculate path.");
      return;
    }

    rows = matrix.length;
    cols = matrix[0].length;

    if (rowStart < 0 || rowStart >= rows || colStart < 0 || colStart >= cols) {
      System.err.println("Starting point is outside the matrix; can not calculate path.");
      return;
    }

    if (rowEnd < 0 || rowEnd >= rows || colEnd < 0 || colEnd >= cols) {
      System.err.println("Ending point is outside the matrix; can not calculate path.");
      return;
    }


    this.rowStart = rowStart;
    this.rowEnd = rowEnd;
    this.colStart = colStart;
    this.colEnd = colEnd;

    int startIdx = Pair.toIndex(this.rowStart, this.colStart, cols);
    Heap<FrontierVertex> openVertices = new Heap<>(SIZE, new FrontierVertex(startIdx, 0, Math.abs(this.rowStart - this.rowEnd) + Math.abs(this.colStart - this.colEnd)));
    gDistance.put(startIdx, 0);

    while (!openVertices.isEmpty()) {
      FrontierVertex current = openVertices.remove();

      Pair ij = Pair.fromIndex(current.getVertex(), cols);
      if (ij.i == this.rowEnd && ij.j == this.colEnd) {
        break;
      }

      visited.add(current.getVertex());

      int iStart = Math.max(0, ij.i - 1);
      int iEnd = Math.min(rows, ij.i + 2);
      int jStart = Math.max(0, ij.j - 1);
      int jEnd = Math.min(cols, ij.j + 2);

      for (int i = iStart; i < iEnd; i++) {
        for (int j = jStart; j < jEnd; j++) {

          int frontierIdx = Pair.toIndex(i, j, cols);
          if (matrix[i][j] == 1 || visited.contains(frontierIdx)) {
            continue;
          }

          // Heuristic distance for the frontier vertex.
          if (!hDistance.containsKey(frontierIdx)) {
            hDistance.put(frontierIdx, Math.abs(i - this.rowEnd) + Math.abs(j - this.colEnd)); // Manhattan distance.
          }

          // Distance between frontier and current vertex.
          int distance = (i == ij.i && j == ij.j)
                         ? 0
                         : (i == ij.i || j == ij.j)
                           ? 5
                           : 7; // Same row/column: 5; diagonal 7: Pythagoras Theorem approximation.
          // Distance between frontier and start vertex.
          int g = current.getG() + distance;

          // Found a shorter path, update the distance between frontier and the start vertex.
          if (!gDistance.containsKey(frontierIdx) || gDistance.get(frontierIdx) > g) {
            gDistance.put(frontierIdx, g);
            destination.put(frontierIdx, current.getVertex());

            // Total estimated cost (f) based on g and h  before adding the vertex to open vertices..
            int f = g + hDistance.get(frontierIdx);
            openVertices.add(new FrontierVertex(frontierIdx, g, f));
          }
        }
      }
    }
  }

  public boolean pathExistsToDestination() {
    return destination != null && destination.size() > 0 && destination.containsKey(Pair.toIndex(rowEnd, colEnd, cols));
  }

  public int getDistanceToDestination() {
    return gDistance != null && gDistance.size() > 0 && gDistance.containsKey(Pair.toIndex(rowEnd, colEnd, cols))
           ? gDistance.get(Pair.toIndex(rowEnd, colEnd, cols)) / 5 : 0; // Divide by 5 to normalize.
  }

  public List<Pair> getPathToDestination() {
    LinkedList<Pair> path = new LinkedList<>();

    if (pathExistsToDestination()) {
      Integer idx = Pair.toIndex(rowEnd, colEnd, cols);
      while (idx != null) {
        path.addFirst(Pair.fromIndex(idx, cols));
        idx = destination.get(idx);
      }
    }

    return path;
  }


  public void printPathToDestination() {
    List<Pair> path = getPathToDestination();
    if (path.isEmpty()) {
      System.out.println("There is no path to destination");
    } else {
      System.out.println("Shortest distance to destination follows the path:  ");
      for (Pair v : path) {
        if (!v.equals(path.peekLast())) {
          System.out.printf("%s -> ", v);
        } else {
          System.out.printf("%s \n", v);
        }
      }
    }
  }
}
