package paralaks_gmail_com.data_structures_algorithms.graph;

import paralaks_gmail_com.data_structures_algorithms.HashMap;
import paralaks_gmail_com.data_structures_algorithms.LinkedList;

public class WeightedGraph {
  private HashMap<Integer, LinkedList<Vertex>> vertices; // key: vertex; value: adjacency list.
  private boolean directed;

  public WeightedGraph() {
    directed = false;
    clear();
  }

  public WeightedGraph(boolean directed) {
    this.directed = directed;
    clear();
  }


  public void clear() {
    vertices = new HashMap<>(10);
  }

  public void addEdge(int vertex1, int vertex2, int distance) {
    addVertexWithAdjacency(vertex1, vertex2, distance);

    if (directed) {
      return;
    }

    addVertexWithAdjacency(vertex2, vertex1, distance);
  }

  protected void addVertexWithAdjacency(int vertex1, int vertex2, int distance) {
    LinkedList<Vertex> list = vertices.get(vertex1);
    Vertex v = new Vertex(vertex2, distance);

    // add vertex1 to graph.
    if (list == null) {
      list = vertices.put(vertex1, new LinkedList<>());
    }

    // add vertex2 to adjacency list
    if (!list.contains(v)) {
      list.add(v);
    }
  }

  public HashMap<Integer, LinkedList<Vertex>> getVertices() {
    return vertices;
  }
}
