package paralaks_gmail_com.data_structures_algorithms.graph;

import paralaks_gmail_com.data_structures_algorithms.HashMap;
import paralaks_gmail_com.data_structures_algorithms.Heap;
import paralaks_gmail_com.data_structures_algorithms.LinkedList;
import paralaks_gmail_com.data_structures_algorithms.List;

import java.util.HashSet;
import java.util.Set;

/*
 Uses a linked list for calculations hence does not require vertex ids to be consecutive integers starting from 0.
 */
public class Dijkstra_LinkedList {
  private int startVertex;
  private HashMap<Integer, Edge> edges;

  public boolean isValidGraph(WeightedGraph graph) {
    return graph != null && graph.getVertices() != null && graph.getVertices().size() > 0;
  }


  public void calculateShortestPaths(WeightedGraph graph, int start) {
    if (!isValidGraph(graph)) {
      edges = null;

      System.err.println("Invalid graph. Vertex data is missing to calculate shortest path to destination.");

      return;
    }

    HashMap<Integer, LinkedList<Vertex>> vertices = graph.getVertices();
    int vertexCount = vertices.size();
    Set<Integer> visited = new HashSet<>(vertexCount);

    startVertex = start;
    edges = new HashMap<>(vertexCount);

    // Initialize shortest distance for all vertices with +infinity.
    for (Integer vertex : vertices.keys()) {
      edges.put(vertex, new Edge(vertex, -1, Integer.MAX_VALUE));
    }

    // Start vertex has distance 0.
    Edge edge = new Edge(startVertex, startVertex, 0);
    edges.put(startVertex, edge);

    // Use a min-heap to select the adjacent node with the shortest distance.
    Heap<Edge> minHeap = new Heap<>(vertexCount, edge);
    while (!minHeap.isEmpty()) {
      edge = minHeap.remove();

      int vertex1 = edge.getVertex1();
      visited.add(vertex1); // Mark vertex1 visited.

      for (Vertex adjacent : vertices.get(vertex1)) {
        if (visited.contains(adjacent.getVertex())) {
          continue;
        }

        Edge adjacentEdge = edges.get(adjacent.getVertex());
        int newDistance = edge.getWeight() + adjacent.getWeight();

        if (newDistance < adjacentEdge.getWeight()) {
          // Found a shorter path to vertex2 via vertex1.
          adjacentEdge.setWeight(newDistance);
          adjacentEdge.setVertex2(vertex1);
          minHeap.add(adjacentEdge);
        }
      }
    }
  }


  public HashMap<Integer, Edge> getEdges() {
    return edges;
  }

  public int getStartVertex() {
    return startVertex;
  }

  public boolean pathExistsTo(int end) {
    return edges != null && edges.get(end) != null && edges.get(end).getWeight() != Integer.MAX_VALUE;
  }

  public int getDistanceTo(int end) {
    return edges != null && edges.get(end) != null ? edges.get(end).getWeight() : 0;
  }

  public List<Integer> getPathTo(int end) {
    LinkedList<Integer> path = new LinkedList<>();

    if (pathExistsTo(end)) {
      int vertex = end;
      path.addFirst(vertex);

      while (vertex != startVertex) {
        vertex = edges.get(vertex).getVertex2();
        path.addFirst(vertex);
      }
    }

    return path;
  }

  public void printPathTo(int vertex) {
    List<Integer> path = getPathTo(vertex);
    if (path.isEmpty()) {
      System.out.printf("There is no path to %s from %s in the graph\n", vertex, startVertex);
    } else {
      System.out.printf("Shortest distance from %s to %s is %s and path is : ", startVertex, vertex, edges.get(vertex).getWeight());
      for (Integer v : path) {
        if (!v.equals(path.peekLast())) {
          System.out.printf("%s -> ", v);
        } else {
          System.out.printf("%s \n", v);
        }
      }
    }
  }
}
