package paralaks_gmail_com.data_structures_algorithms.graph;

public class Vertex implements Comparable<Vertex> {
  private int vertex;
  private int weight;

  public Vertex(int vertex, int weight) {
    this.vertex = vertex;
    this.weight = weight;
  }

  @Override
  public int compareTo(Vertex o) {
    return vertex == o.vertex ? 0 : -1;
  }

  @Override
  public String toString() {
    return "Vertex{" +
        "vertex=" + vertex +
        ", weight=" + weight +
        '}';
  }

  public int getVertex() {
    return vertex;
  }

  public void setVertex(int vertex) {
    this.vertex = vertex;
  }

  public int getWeight() {
    return weight;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }
}
