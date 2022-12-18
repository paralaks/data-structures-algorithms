package paralaks_gmail_com.data_structures_algorithms.graph;

import paralaks_gmail_com.data_structures_algorithms.*;

import java.util.HashSet;
import java.util.Set;

public class Dijkstra {
  int[] destination;
  int[] distance;
  int[] visited;
  int startVertex;

  public boolean isValidGraph(WeightedGraph graph) {
    if (graph == null || graph.getVertices() == null) {
      return false;
    }

    HashMap<Integer, LinkedList<Vertex>> vertices = graph.getVertices();
    Set<Integer> vertexSet = new HashSet<>();
    MutableMinMax<Integer> minMax = new MutableMinMax<>(Integer.MAX_VALUE, 0);

    for (Integer key : vertices.keys()) {
      vertexSet.add(key);
      minMax.minMax(key);

      vertices.get(key).forEach(vertex -> {
        Integer v = vertex.getVertex();

        vertexSet.add(v);
        minMax.minMax(v);
      });
    }

    // Min index must be 0 and max index must match number of vertices
    return minMax.getMin() == 0 && minMax.getMax() == (vertexSet.size() - 1);
  }

  public void calculateShortestPaths(WeightedGraph graph, int start) {
    if (!isValidGraph(graph)) {
      distance = null;
      destination = null;

      System.err.println("Invalid graph. Vertex ids should be a set of consecutive numbers starting from 0 in order to calculate shortest path.");

      return;
    }

    HashMap<Integer, LinkedList<Vertex>> vertices = graph.getVertices();
    int count = vertices.size();
    destination = new int[count];
    distance = new int[count];
    visited = new int[count];
    startVertex = start;

    for (Integer v : vertices.keys()) {
      distance[v] = Integer.MAX_VALUE;
      visited[v] = -1;
    }
    distance[start] = 0;

    Heap<Integer> heap = new Heap<>(count, start);
    while (!heap.isEmpty()) {
      Integer current = heap.remove();
      visited[current] = 1;

      for (Vertex v : vertices.get(current)) {
        Integer adjacent = v.getVertex();

        // Skip vertex if it was visited
        if (visited[adjacent] == 1) {
          continue;
        }

        int newDistance = distance[current] + v.getWeight();
        if (newDistance < distance[adjacent]) {
          distance[adjacent] = newDistance;
          destination[adjacent] = current;
          heap.add(adjacent);
        }
      }
    }
  }


  public int getStartVertex() {
    return startVertex;
  }

  public boolean pathExistsTo(int end) {
    return distance != null && end >= 0 && end < distance.length && distance[end] != Integer.MAX_VALUE;
  }

  public int getDistanceTo(int end) {
    return distance != null && end >= 0 && end < distance.length ? distance[end] : 0;
  }

  public List<Integer> getPathTo(int end) {
    LinkedList<Integer> path = new LinkedList<>();

    if (pathExistsTo(end)) {
      int vertex = end;
      path.addFirst(vertex);

      while (vertex != startVertex) {
        vertex = destination[vertex];
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
      System.out.printf("Shortest distance from %s to %s is %s and path is : ", startVertex, vertex, distance[vertex]);
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
