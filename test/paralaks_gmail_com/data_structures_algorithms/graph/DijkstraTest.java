package paralaks_gmail_com.data_structures_algorithms.graph;

import org.junit.jupiter.api.Test;
import paralaks_gmail_com.data_structures_algorithms.List;

import static org.junit.jupiter.api.Assertions.*;

public class DijkstraTest {
  Dijkstra dijkstra = new Dijkstra();

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
             1 ----- 4
            /  \    /  \
          0    / 3 \    6     7----8
           \ /      \ /
            2 ----- 5
*/

    WeightedGraph graph = new WeightedGraph();
    graph.addEdge(0, 1, 1);
    graph.addEdge(0, 2, 2);

    graph.addEdge(1, 3, 1);
    graph.addEdge(1, 4, 7);

    graph.addEdge(2, 3, 6);
    graph.addEdge(2, 5, 5);

    graph.addEdge(3, 4, 2);
    graph.addEdge(3, 5, 1);

    graph.addEdge(4, 6, 3);
    graph.addEdge(5, 6, 1);

    graph.addEdge(7, 8, 3);

    dijkstra.calculateShortestPaths(graph, 0);


    assertFalse(dijkstra.pathExistsTo(-1), "Path does not exist to a vertex which is not in the graph.");
    assertFalse(dijkstra.pathExistsTo(99), "Path does not exist to a vertex which is not in the graph.");
    assertFalse(dijkstra.pathExistsTo(7), "Path does not exist to a vertex which is part of a disconnected edge.");
    assertFalse(dijkstra.pathExistsTo(8), "Path does not exist to a vertex which is part of a disconnected edge.");


    assertTrue(dijkstra.pathExistsTo(0), "Path exists to starting vertex.");
    assertEquals(0, dijkstra.getDistanceTo(0), "Shortest distance to starting vertex is zero.");

    assertTrue(dijkstra.pathExistsTo(1), "Path exists to connected vertex 1.");
    assertEquals(1, dijkstra.getDistanceTo(1));

    assertTrue(dijkstra.pathExistsTo(2), "Path exists to connected vertex 2.");
    assertEquals(2, dijkstra.getDistanceTo(2));

    assertTrue(dijkstra.pathExistsTo(3), "Path exists to connected vertex 3.");
    assertEquals(2, dijkstra.getDistanceTo(3));

    assertTrue(dijkstra.pathExistsTo(4), "Path exists to connected vertex 4.");
    assertEquals(4, dijkstra.getDistanceTo(4));

    assertTrue(dijkstra.pathExistsTo(5), "Path exists to connected vertex 5.");
    assertEquals(3, dijkstra.getDistanceTo(5));

    assertTrue(dijkstra.pathExistsTo(6), "Path exists to connected vertex 6.");
    assertEquals(4, dijkstra.getDistanceTo(6));
  }
}
