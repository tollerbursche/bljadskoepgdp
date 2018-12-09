
public class Reverse {

  public static void print(int[] array) {
    for (int i = 0; i < 10; i++)
      System.out.println(array[i]);

  }

  public static int[] reverse(int[] array) {
    int revArray[] = new int[10];
    for (int i = 0; i < 10; i++)
      revArray[i] = array[10 - (i + 1)];
    return revArray;
  }

  public static void main(String[] args) {
    // Aufgabe a)
    // Zeichenkette vom Benutzer einlesen.
    int i = 0;
    int arrayInt[] = new int[10];
    while (i < 10) {
      arrayInt[i] = Terminal.askInt("Geben Sie bitte Zahl Nr. " + i + " ein: ");
      i++;
    }

    print(arrayInt);
    print(reverse(arrayInt));

  }
}
