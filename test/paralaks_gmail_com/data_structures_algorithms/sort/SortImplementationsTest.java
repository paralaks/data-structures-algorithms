package paralaks_gmail_com.data_structures_algorithms.sort;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


class SortImplementationsTest {
  static List<Sort> sortAlgorithms = new LinkedList<>();
  static List<Sort> stableSortAlgorithms = new LinkedList<>();

  @BeforeAll
  static void setUp() {
    sortAlgorithms.add(new SelectionSort());
    sortAlgorithms.add(new InsertionSort());
    sortAlgorithms.add(new ShellSort());
    sortAlgorithms.add(new MergeSort());
    sortAlgorithms.add(new HeapSort());
    sortAlgorithms.add(new QuickSort());

    stableSortAlgorithms.add(new MergeSort());
  }

  <T extends Comparable<T>> void testImplementations(Sort.DIRECTION direction, T[] input, String message) {
    int d = direction == Sort.DIRECTION.DESCENDING ? -1 : 1;
    Comparator<T> comparator = (o1, o2) -> d * o1.compareTo(o2);

    T[] expected = Arrays.copyOf(input, input.length);
    Arrays.sort(expected, comparator);

    for (Sort algorithm : sortAlgorithms) {
      String name = algorithm.getClass().getSimpleName();
      T[] sorted = Arrays.copyOf(input, input.length);
      T[] sortedRef = sorted;

      long start = System.currentTimeMillis();
      algorithm.sort(sorted, direction);
      long difference = System.currentTimeMillis() - start;

      assertEquals(sorted, sortedRef, name + " should not return a new copy of input.");

      for (int i = 0; i < input.length; i++) {
        assertEquals(expected[i], sorted[i], name + " " + message);
      }

      System.out.println("Sorted " + input.length + " elements using " + name + " in " + (difference > 1000 ? difference / 1000.0 + " s." : difference + " ms."));
    }
  }

  @Test
  void inputNull() {
    String[] nullInput = null;

    for (Sort algorithm : sortAlgorithms) {
      String name = algorithm.getClass().getSimpleName();

      algorithm.sort(nullInput, Sort.DIRECTION.ASCENDING);

      assertNull(nullInput, name + ": when input is null, sort terminates.");
    }
  }

  @Test
  void inputEmpty() {
    testImplementations(Sort.DIRECTION.ASCENDING, new String[0], "when input is empty, sort must terminate.");
  }

  @Test
  void input_1_Item() {
    testImplementations(Sort.DIRECTION.ASCENDING, new String[]{"hello"}, "when input has one item, sort must terminate.");
  }

  @Test
  void input_2_ItemsSorted() {
    testImplementations(Sort.DIRECTION.ASCENDING, new String[]{"hello", "world"}, "when input has multiple items, they must be sorted.");
  }

  @Test
  void input_2_ItemsUnsorted() {
    testImplementations(Sort.DIRECTION.ASCENDING, new String[]{"world", "hello"}, "when input has multiple items, they must be sorted.");
  }

  @Test
  void input_3_ItemsSorted() {
    testImplementations(Sort.DIRECTION.ASCENDING, new String[]{"citizens", "hello", "world"}, "when input has multiple items, they must be sorted.");
  }

  @Test
  void input_3_ItemsUnsorted() {
    testImplementations(Sort.DIRECTION.ASCENDING, new String[]{"world", "citizens", "hello"}, "when input has multiple items, they must be sorted.");
  }

  @Test
  void input_withDuplicatesSorted_SortAscending() {
    testImplementations(Sort.DIRECTION.ASCENDING, new String[]{"aaa", "ccc", "ddd", "ddd", "lll", "mmm", "ppp", "zzz", "zzz"}, "when input has duplicate items, they must be sorted.");
  }

  @Test
  void input_withDuplicatesUnsorted_SortAscending() {
    testImplementations(Sort.DIRECTION.ASCENDING, new String[]{"ccc", "zzz", "aaa", "zzz", "lll", "ddd", "mmm", "ppp", "ddd"}, "when input has duplicate items, they must be sorted.");
  }

  @Test
  void input_withDuplicatesSorted_SortDescending() {
    testImplementations(Sort.DIRECTION.DESCENDING, new String[]{"aaa", "ccc", "ddd", "ddd", "lll", "mmm", "ppp", "zzz", "zzz"}, "when input has duplicate items, they must be sorted.");
  }

  @Test
  void input_withDuplicatesUnsorted_SortDescending() {
    testImplementations(Sort.DIRECTION.DESCENDING, new String[]{"ccc", "zzz", "aaa", "zzz", "lll", "ddd", "mmm", "ppp", "ddd"}, "when input has duplicate items, they must be sorted.");
  }

  @Test
  void input_numbersUnsorted_SortAscending() {
    testImplementations(Sort.DIRECTION.ASCENDING, new Integer[]{100, -10, 20, -1, 20, -1, 99, 40, -50, -45, 3, 200, 1, 5}, "when sort direction is descending, items must be sorted in descending order.");
  }

  @Test
  void input_numbersSorted_SortAscending() {
    testImplementations(Sort.DIRECTION.ASCENDING, new Integer[]{200, 100, 99, 40, 20, 20, 5, 3, 1, -1, -10, -45, -1, -50}, "when sort direction is descending, items must be sorted in descending order.");
  }

  @Test
  void input_numbersUnsorted_SortDescending() {
    testImplementations(Sort.DIRECTION.DESCENDING, new Integer[]{100, -10, 20, -1, 20, -1, 99, 40, -50, -45, 3, 200, 1, 5}, "when sort direction is descending, items must be sorted in descending order.");
  }

  @Test
  void input_numbersSorted_SortDescending() {
    testImplementations(Sort.DIRECTION.DESCENDING, new Integer[]{200, 100, 99, 40, 20, 20, 5, 3, 1, -1, -10, -45, -1, -50}, "when sort direction is descending, items must be sorted in descending order.");
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

      algorithm.sort(list, Sort.DIRECTION.ASCENDING);
      for (int i = 0; i < list.length; i++) {
        assertEquals(expected[i], list[i], name + " must produce stable output.");
      }
    }
  }

  @Test
  public void test_input_large_random_number_array() {
    final int size = 25000;
    final int maxIteration = 10;

    for (int iteration = 1; iteration <= maxIteration; iteration++) {
      System.out.println("Sorting " + size + " elements. Iteration " + iteration + "/" + maxIteration);
      Double[] bigArray = new Double[size];
      for (int i = 0; i < size; i++) {
        bigArray[i] = Math.random() * size;
      }
      testImplementations(Sort.DIRECTION.ASCENDING, bigArray, " must sort a random number array of " + size + " elements.");
    }
  }
}
