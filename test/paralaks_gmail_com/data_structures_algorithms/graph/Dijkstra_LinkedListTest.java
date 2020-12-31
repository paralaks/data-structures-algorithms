package paralaks_gmail_com.data_structures_algorithms.graph;

import org.junit.jupiter.api.Test;
import paralaks_gmail_com.data_structures_algorithms.List;

import static org.junit.jupiter.api.Assertions.*;

public class Dijkstra_LinkedListTest {
  Dijkstra_LinkedList dijkstra = new Dijkstra_LinkedList();

  @Test
  public void pathDoesNotExistInEmptyGraph() {
    WeightedGraph graph = new WeightedGraph();

    dijkstra.calculateShortestPaths(null, 0);
    assertFalse(dijkstra.pathExistsTo(0));

    dijkstra.calculateShortestPaths(graph, 0);
    assertFalse(dijkstra.pathExistsTo(0));
  }

  @Test
  public void pathExistToAdjacentVertex() {
    WeightedGraph graph = new WeightedGraph();
    graph.addEdge(0, 1, 2);

    List<Integer> path = null;
    dijkstra.calculateShortestPaths(graph, 0);
    assertTrue(dijkstra.pathExistsTo(1));
    path = dijkstra.getPathTo(1);
    assertFalse(path.isEmpty());
    assertEquals(0, path.getHead().getValue());
    assertEquals(1, path.getTail().getValue());

    dijkstra.calculateShortestPaths(graph, 1);
    assertTrue(dijkstra.pathExistsTo(0));
    path = dijkstra.getPathTo(0);
    assertFalse(path.isEmpty());
    assertEquals(1, path.getHead().getValue());
    assertEquals(0, path.getTail().getValue());
  }


  @Test
  public void pathDoesNotExistInDisconnectedGraph() {
    WeightedGraph graph = new WeightedGraph();
    graph.addEdge(0, 1, 2);
    graph.addEdge(3, 4, 1); // Disconnected edge.

    dijkstra.calculateShortestPaths(graph, 0);
    assertFalse(dijkstra.pathExistsTo(3));
    assertTrue(dijkstra.getPathTo(3).isEmpty());
    assertFalse(dijkstra.pathExistsTo(4));
    assertTrue(dijkstra.getPathTo(4).isEmpty());

    dijkstra.calculateShortestPaths(graph, 1);
    assertFalse(dijkstra.pathExistsTo(3));
    assertTrue(dijkstra.getPathTo(3).isEmpty());
    assertFalse(dijkstra.pathExistsTo(4));
    assertTrue(dijkstra.getPathTo(4).isEmpty());
  }

  @Test
  public void pathCalculatedCorrectlyForEachConnectedVertex() {

/*
             2 ----- 9
            /  \    /  \
          1    / 4 \    7     24----25
           \ /      \ /
            3 ----- 10

*/
    WeightedGraph graph = new WeightedGraph();
    int vertex1 = 1;
    graph.addEdge(vertex1, 2, 1);
    graph.addEdge(vertex1, 3, 2);

    graph.addEdge(2, 4, 1);
    graph.addEdge(2, 9, 7);

    graph.addEdge(3, 4, 6);
    graph.addEdge(3, 10, 5);

    graph.addEdge(4, 9, 2);
    graph.addEdge(4, 10, 1);

    graph.addEdge(9, 12, 3);
    graph.addEdge(10, 12, 1);

    graph.addEdge(24, 25, 6);


    dijkstra.calculateShortestPaths(graph, vertex1);


    assertFalse(dijkstra.pathExistsTo(0), "Path does not exist to a vertex which is not in the graph.");
    assertFalse(dijkstra.pathExistsTo(99), "Path does not exist to a vertex which is not in the graph.");
    assertFalse(dijkstra.pathExistsTo(24), "Path does not exist to a vertex which is part of a disconnected edge.");
    assertFalse(dijkstra.pathExistsTo(25), "Path does not exist to a vertex which is part of a disconnected edge.");


    assertTrue(dijkstra.pathExistsTo(vertex1), "Path exists to starting vertex.");
    assertEquals(0, dijkstra.getDistanceTo(vertex1), "Shortest distance to starting vertex is zero.");

    assertTrue(dijkstra.pathExistsTo(2), "Path exists to connected vertex 2.");
    assertEquals(1, dijkstra.getDistanceTo(2));

    assertTrue(dijkstra.pathExistsTo(3), "Path exists to connected vertex 3.");
    assertEquals(2, dijkstra.getDistanceTo(3));

    assertTrue(dijkstra.pathExistsTo(4), "Path exists to connected vertex 4.");
    assertEquals(2, dijkstra.getDistanceTo(4));

    assertTrue(dijkstra.pathExistsTo(9), "Path exists to connected vertex 9.");
    assertEquals(4, dijkstra.getDistanceTo(9));

    assertTrue(dijkstra.pathExistsTo(10), "Path exists to connected vertex 10.");
    assertEquals(3, dijkstra.getDistanceTo(10));

    assertTrue(dijkstra.pathExistsTo(12), "Path exists to connected vertex 12.");
    assertEquals(4, dijkstra.getDistanceTo(12));
  }
}
