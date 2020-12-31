package paralaks_gmail_com.data_structures_algorithms.graph;

import org.junit.jupiter.api.Test;
import paralaks_gmail_com.data_structures_algorithms.LinkedList;
import paralaks_gmail_com.data_structures_algorithms.List;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;
import static paralaks_gmail_com.data_structures_algorithms.graph.A_Star.Pair;

public class A_StarTest {
  A_Star aStar = new A_Star();

  @Test
  public void pathDoesNotExistsInEmptyMatrix() {
    int[][] matrix = null;
    aStar.calculateShortestPath(matrix, 0, 0, 5, 5);
    assertFalse(aStar.pathExistsToDestination());

    matrix = new int[][]{};
    aStar.calculateShortestPath(matrix, 0, 0, 5, 5);
    assertFalse(aStar.pathExistsToDestination());

    matrix = new int[][]{{}};
    aStar.calculateShortestPath(matrix, 0, 0, 5, 5);
    assertFalse(aStar.pathExistsToDestination());
  }

  @Test
  public void pathDoesNotExistsToUnreachableDestination() {
    int[][] matrix = {
        {0, 0, 0, 0},
        {0, 1, 1, 1},
        {0, 1, 1, 0},
    };

    aStar.calculateShortestPath(matrix, -1, -1, 0, 4);
    assertFalse(aStar.pathExistsToDestination(), "Path does not exist when start vertex is outside the matrix.");

    aStar.calculateShortestPath(matrix, 0, 0, 99, 99);
    assertFalse(aStar.pathExistsToDestination(), "Path does not exist when end vertex is outside the matrix.");

    aStar.calculateShortestPath(matrix, 0, 0, 2, 3);
    assertFalse(aStar.pathExistsToDestination(), "Path does not exist when end vertex is not reachable due to obstacles.");

    aStar.calculateShortestPath(matrix, 0, 0, 2, 2);
    assertFalse(aStar.pathExistsToDestination(), "Path does not exist when end vertex itself is an obstacle.");
  }

  @Test
  public void pathCalculatedCorrectlyForValidMatrixWithValidStartAndEndVertices() {
    int[][] matrix = new int[][]{
        {0, 0, 0, 0, 0, 0},
        {0, 1, 1, 1, 1, 0},
        {0, 0, 0, 1, 0, 1},
        {0, 0, 1, 1, 0, 1},
        {0, 1, 1, 1, 0, 0},
        {0, 0, 1, 0, 1, 1},
        {0, 0, 1, 0, 0, 0},
    };

    List<Pair> expectedPath = new LinkedList<>();
    expectedPath.addLast(new Pair(6, 1)); // START
    expectedPath.addLast(new Pair(5, 1)); // ↑
    expectedPath.addLast(new Pair(4, 0)); // ↖
    expectedPath.addLast(new Pair(3, 0)); // ↑
    expectedPath.addLast(new Pair(2, 0)); // ↑
    expectedPath.addLast(new Pair(1, 0)); // ↑
    expectedPath.addLast(new Pair(0, 1)); // ↗
    expectedPath.addLast(new Pair(0, 2)); // →
    expectedPath.addLast(new Pair(0, 3)); // →
    expectedPath.addLast(new Pair(0, 4)); // →
    expectedPath.addLast(new Pair(1, 5)); // ↘
    expectedPath.addLast(new Pair(2, 4)); // ↙
    expectedPath.addLast(new Pair(3, 4)); // ↓
    expectedPath.addLast(new Pair(4, 4)); // ↓
    expectedPath.addLast(new Pair(5, 3)); // ↙
    expectedPath.addLast(new Pair(6, 4)); // ↘
    expectedPath.addLast(new Pair(6, 5)); // →

    aStar.calculateShortestPath(matrix, 6, 1, 6, 5);

    assertTrue(aStar.pathExistsToDestination());

    List<Pair> calculatedPath = aStar.getPathToDestination();
    Iterator<Pair> expectedIterator = expectedPath.iterator();
    Iterator<Pair> actualIterator = calculatedPath.iterator();
    for (int i = 0; i < Math.max(expectedPath.size(), calculatedPath.size()); i++) {
      Pair expected = expectedIterator.next();
      Pair actual = actualIterator.next();
      assertEquals(expected.i, actual.i);
      assertEquals(expected.j, actual.j);
    }

    int expectedDistance = (10 * 5 + 6 * 7) / 5; // 10 left/right/up/down moves +  6 diagonal moves; normalize by 5..
    assertEquals(expectedDistance, aStar.getDistanceToDestination());
  }
}
