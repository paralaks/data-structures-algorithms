package paralaks_gmail_com.data_structures_algorithms.graph;

public class Edge implements Comparable<Edge> {
  private int vertex1;
  private int vertex2;
  private int weight;

  public Edge(int vertex1, int vertex2, int weight) {
    this.vertex1 = vertex1;
    this.vertex2 = vertex2;
    this.weight = weight;
  }

  @Override
  public int compareTo(Edge o) {
    return Integer.compare(weight, o.weight);
  }

  @Override
  public String toString() {
    return "Edge{" +
        "vertex1=" + vertex1 +
        ", vertex2=" + vertex2 +
        ", weight=" + weight +
        '}';
  }

  public int getVertex1() {
    return vertex1;
  }

  public void setVertex1(int vertex1) {
    this.vertex1 = vertex1;
  }

  public int getVertex2() {
    return vertex2;
  }

  public void setVertex2(int vertex2) {
    this.vertex2 = vertex2;
  }

  public int getWeight() {
    return weight;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }
}
