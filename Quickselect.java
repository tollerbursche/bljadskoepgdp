public class Quickselect {

  public static int quickselect(int[] numbers, int k) {
    int left = 0;
    int right = numbers.length - 1;
    while (right >= left) {
      int pivotIndex = partition(numbers, left, right);
      if(k == pivotIndex)
        // Wir haben das k-kleinste Element gefunden!
        return numbers[pivotIndex];
      else if(k < pivotIndex)
        // Der gesuchte Index ist vor dem Pivot-Element
        right = pivotIndex - 1;
      else
        // Der gesuchte Index ist hinter dem Pivot-Element
        left = pivotIndex + 1;
    }
    // Fehlerfall, wird nicht behandelt
    return Integer.MIN_VALUE;
  }

  private static int partition(int[] numbers, int left, int right) {
    int leftScan = left; // Links auf das erste Element
    int rightScan = right; // Rechts auf das letzte Element
    int pivot = numbers[right]; // Wahl des Pivot-Elements

    while (leftScan < rightScan) {
      while (leftScan < rightScan && numbers[leftScan] < pivot) {
        leftScan++; // eins weiter nach rechts
      }
      while (leftScan < rightScan && numbers[rightScan] >= pivot) {
        rightScan--; // eins weiter nach links
      }
      if (leftScan < rightScan) { // Falls leftScan und rightScan sich noch
        // nicht kreuzen, dann Elemente tauschen
        swap(numbers, leftScan, rightScan);
      }
      // Ansonsten ist die Partition fertig
    }
    // Pivot-Element an die Nahtstelle bringen
    swap(numbers, leftScan, right);
    return leftScan; // Index der Nahtstelle ausliefern
  }

  private static void swap(int[] numbers, int index1, int index2) {
    int temp = numbers[index1];
    numbers[index1] = numbers[index2];
    numbers[index2] = temp;
  }

  public static void main(String[] args) {
    // Initialisierung
    int[] test = {1, -4, 3, 5, 10, 2, -10, -5, 100};

    // Auswählen
    int x = quickselect(test, 6);

    // Ausgabe des 6t-kleinsten Wertes (Zählung ab 0)
    System.out.println(x);
  }
}
