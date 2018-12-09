/**
 * TestIt-Klasse.
 */
public class TestIt {
  /**
   * Main-Methode
   * 
   * @param args Kommandozeilen-Argumente
   */
  public static void main(String[] args) {
    DocumentCollection dc = new DocumentCollection();
    String command;

    boolean exit = false;

    while (!exit) {
      command = Terminal.askString("> ");

      if (command == null || command.equals("exit")) {
        /* Exit program */
        exit = true;
      } else if (command.startsWith("add ")) {
        /* add a new document */
        String titleAndText = command.substring(4);

        /* title and text separated by : */
        int separator = titleAndText.indexOf(':');

        if (separator != -1) {
          String title = titleAndText.substring(0, separator);
          String text = titleAndText.substring(separator + 1);

          dc.appendDocument(new Document(title, "", "", null, null, text));
        }
      } else if (command.startsWith("list")) {
        /* list all document in collection */
        for (int i = 0; i < dc.numDocuments(); i++) {
          System.out.println(dc.get(i).getTitle());
        }
      } else if (command.startsWith("query ")) {
        /* query on the documents in the collection */
        String searchQuery = command.substring(6);

        dc.match(searchQuery);

        for (int i = 0; i < dc.numDocuments(); i++) {
          System.out.println((i + 1) + ". " + dc.get(i).getTitle() + "; Aehnlichkeit: " + dc.getQuerySimilarity(i));
        }

        System.out.println();
      } else if (command.startsWith("count ")) {
        /* print the count of a word in each document */
        String word = command.substring(6);

        for (int i = 0; i < dc.numDocuments(); i++) {
          Document doc = dc.get(i);
          WordCountsArray docWordCounts = doc.getWordCounts();

          int count = docWordCounts.getCount(docWordCounts.getIndexOfWord(word));

          /* -1 and 0 makes a difference! */
          if (count == -1) {
            System.out.println(doc.getTitle() + ": gar nicht.");
          } else {
            System.out.println(doc.getTitle() + ": " + count + "x ");
          }
        }
      }
    }
  }
}
