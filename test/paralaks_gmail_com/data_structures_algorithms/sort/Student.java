package paralaks_gmail_com.data_structures_algorithms.sort;

public class Student implements Comparable<Student> {
  String name;
  int id;

  Student(String name, int id) {
    this.name = name;
    this.id = id;
  }

  @Override
  public int compareTo(Student o) {
    return this.name.compareTo(o.name);
  }

  @Override
  public String toString() {
    return name + " " + id;
  }
}
