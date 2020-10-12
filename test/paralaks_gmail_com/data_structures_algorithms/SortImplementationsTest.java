package paralaks_gmail_com.data_structures_algorithms;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import paralaks_gmail_com.data_structures_algorithms.sort.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


class SortImplementationsTest {
  static class Student implements Comparable<Student> {
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


  static Set<Sort> sortAlgorithms = new HashSet<>();
  static Set<Sort> stableSortAlgorithms = new HashSet<>();

  @BeforeAll
  static void setUp() {
    sortAlgorithms.add(new SelectionSort());
    sortAlgorithms.add(new InsertionSort());
    sortAlgorithms.add(new ShellSort());
    sortAlgorithms.add(new MergeSort());
    sortAlgorithms.add(new QuickSort());
    sortAlgorithms.add(new HeapSort());

    stableSortAlgorithms.add(new MergeSort());
  }

  <T extends Comparable<T>> void testImplementations(int direction, T[] input, String message) {
    T[] inputRef = input;
    Comparator<T> comparator = (o1, o2) -> direction * o1.compareTo(o2);

    T[] expected = input.clone();
    Arrays.sort(expected, comparator);

    for (Sort algorithm : sortAlgorithms) {
      String name = algorithm.getClass().getSimpleName();

      algorithm.sort(input, direction);

      assertEquals(input, inputRef, name + " should not return a new copy of input.");

      for (int i = 0; i < input.length; i++) {
        assertEquals(expected[i], input[i], name + " " + message);
      }
    }
  }

  @Test
  void inputNull() {
    String[] nullInput = null;

    for (Sort algorithm : sortAlgorithms) {
      String name = algorithm.getClass().getSimpleName();

      algorithm.sort(nullInput, 1);

      assertNull(nullInput, name + ": when input is null, sort terminates.");
    }
  }

  @Test
  void inputEmpty() {
    testImplementations(1, new String[0], "when input is empty, sort must terminate.");
  }

  @Test
  void input_1_Item() {
    testImplementations(1, new String[]{"hello"}, "when input has one item, sort must terminate.");
  }

  @Test
  void input_2_ItemsSorted() {
    testImplementations(1, new String[]{"hello", "world"}, "when input has multiple items, they must be sorted.");
  }

  @Test
  void input_2_ItemsUnsorted() {
    testImplementations(1, new String[]{"world", "hello"}, "when input has multiple items, they must be sorted.");
  }

  @Test
  void input_3_ItemsSorted() {
    testImplementations(1, new String[]{"citizens", "hello", "world"}, "when input has multiple items, they must be sorted.");
  }

  @Test
  void input_3_ItemsUnsorted() {
    testImplementations(1, new String[]{"world", "citizens", "hello"}, "when input has multiple items, they must be sorted.");
  }

  @Test
  void input_withDuplicatesSorted_SortAscending() {
    testImplementations(1, new String[]{"aaa", "ccc", "ddd", "ddd", "lll", "mmm", "ppp", "zzz", "zzz"}, "when input has duplicate items, they must be sorted.");
  }

  @Test
  void input_withDuplicatesUnsorted_SortAscending() {
    testImplementations(1, new String[]{"ccc", "zzz", "aaa", "zzz", "lll", "ddd", "mmm", "ppp", "ddd"}, "when input has duplicate items, they must be sorted.");
  }

  @Test
  void input_withDuplicatesSorted_SortDescending() {
    testImplementations(-1, new String[]{"aaa", "ccc", "ddd", "ddd", "lll", "mmm", "ppp", "zzz", "zzz"}, "when input has duplicate items, they must be sorted.");
  }

  @Test
  void input_withDuplicatesUnsorted_SortDescending() {
    testImplementations(-1, new String[]{"ccc", "zzz", "aaa", "zzz", "lll", "ddd", "mmm", "ppp", "ddd"}, "when input has duplicate items, they must be sorted.");
  }

  @Test
  void input_numbersUnsorted_SortAscending() {
    testImplementations(1, new Integer[]{100, -10, 20, -1, 20, -1, 99, 40, -50, -45, 3, 200, 1, 5}, "when sort direction is descending, items must be sorted in descending order.");
  }

  @Test
  void input_numbersSorted_SortAscending() {
    testImplementations(1, new Integer[]{200, 100, 99, 40, 20, 20, 5, 3, 1, -1, -10, -45, -1, -50}, "when sort direction is descending, items must be sorted in descending order.");
  }

  @Test
  void input_numbersUnsorted_SortDescending() {
    testImplementations(-1, new Integer[]{100, -10, 20, -1, 20, -1, 99, 40, -50, -45, 3, 200, 1, 5}, "when sort direction is descending, items must be sorted in descending order.");
  }

  @Test
  void input_numbersSorted_SortDescending() {
    testImplementations(-1, new Integer[]{200, 100, 99, 40, 20, 20, 5, 3, 1, -1, -10, -45, -1, -50}, "when sort direction is descending, items must be sorted in descending order.");
  }

  @Test
  void testStableSortAlgorithms() {
    for (Sort algorithm : stableSortAlgorithms) {
      String name = algorithm.getClass().getSimpleName();

      Student[] expected = new Student[]{
          new Student("Anna", 5),
          new Student("Anna", 4),
          new Student("Bob", 9),
          new Student("Bob", 6),
          new Student("Harry", 10),
          new Student("Joe", 3),
          new Student("Joe", 7),
          new Student("Zelda", 2),
          new Student("Zelda", 8),
          new Student("Zelda", 1),
      };

      Student[] list = new Student[]{
          expected[7], // Zelda 2
          expected[0], // Anna 5
          expected[2], // Bob 9
          expected[8], // Zelda 8
          expected[5], // Joe 3
          expected[9],  // Zelda 1
          expected[4], // Harry 10
          expected[1], // Anna 4
          expected[3], // Bob 6
          expected[6] // Joe 7
      };

      algorithm.sort(list, 1);
      for (int i = 0; i < list.length; i++) {
        assertEquals(expected[i], list[i], name + " must produce stable output.");
      }
    }
  }
}
