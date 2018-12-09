/**
 * This class represents a ordered collection of documents.
 * 
 * The collection is implemented using a list. This class ensures, that no
 * <code>null</code> elements are ever added.
 * 
 *
 */
public class DocumentCollection {
  /**
   * the first element in the collection
   */
  private DocumentCollectionCell first;

  /**
   * the last element in the collection
   */
  private DocumentCollectionCell last;

  /**
   * the number of elements in this collection
   */
  private int size;

  /**
   * Constructs an empty collection
   */
  public DocumentCollection() {
    this.first = null;
    this.last = null;
    this.size = 0;
  }

  /**
   * Inserts the specified {@link Document} at the beginning of the collection.
   * 
   * Nothing will happen, if the specified {@link Document} is <code>null</code>.
   * 
   * @param doc the {@link Document} to add
   */
  public void prependDocument(Document doc) {
    if (doc == null) {
      return;
    }

    if (this.isEmpty()) {
      /* list empty, add as one and only element */
      this.first = new DocumentCollectionCell(doc, null, null);
      this.last = first;
    } else {
      first.setPrevious(new DocumentCollectionCell(doc, null, first));
      this.first = first.getPrevious();
    }

    size++;
  }

  /**
   * Inserts the specified {@link Document} at the end of the collection.
   * 
   * Nothing will happen, if the specified {@link Document} is <code>null</code>.
   * 
   * @param doc the {@link Document} to add
   */
  public void appendDocument(Document doc) {
    if (doc == null) {
      return;
    }

    if (this.isEmpty()) {
      /* list empty, add as only element */
      this.first = new DocumentCollectionCell(doc, null, null);
      this.last = first;
    } else {
      last.setNext(new DocumentCollectionCell(doc, last, null));
      last = last.getNext();
    }

    size++;
  }

  /**
   * Returns the index in this collection of the specified {@link Document}.
   * 
   * If the specified {@link Document} is contained in the collection more than
   * once, then the lowest index is returned. If the {@link Document} is not
   * contained in the list or if <code>doc</code> is <code>null</code>, then
   * <code>-1</code> will be returned.
   * 
   * @param doc the {@link Document} to look for
   * @return the index in this collection of the specified document
   */
  public int indexOf(Document doc) {
    if (doc == null || this.isEmpty()) {
      return -1;
    }

    /* loop over list and find document */

    DocumentCollectionCell tmp = this.first;
    int index = 0;

    while (tmp != null) {
      if (tmp.getDocument().equals(doc)) {
        return index;
      }

      tmp = tmp.getNext();
      index++;
    }

    return -1;
  }

  /**
   * Returns <code>true</code>, if the specified {@link Document} is contained in
   * this collection.
   * 
   * @param doc the {@link Document}
   * @return <code>true</code>, if the specified {@link Document} is contained in
   *         this collection
   */
  public boolean contains(Document doc) {
    return (this.indexOf(doc) != -1);
  }

  /**
   * Removes the element at the specified index.
   * 
   * If the specified index is invalid or if this collection is empty, nothing
   * will happen.
   * 
   * @param index the index of the element to be deleted
   */
  public boolean remove(int index) {
    if (index < 0 || index >= this.numDocuments()) {
      return false;
    }

    if (this.isEmpty()) {
      return false;
    }

    /* remove first */
    if (index == 0) {
      this.removeFirstDocument();
      return true;
    }

    /* remove last */
    if (index == this.numDocuments() - 1) {
      this.removeLastDocument();
      return true;
    }

    /* we will only get here, if index >= 1 and size >= 2 */

    /* loop to index, keep track of previous */
    DocumentCollectionCell actual = this.first.getNext();
    DocumentCollectionCell prev = this.first;
    int i = 1;

    while (i < index) {
      prev = actual;
      actual = actual.getNext();
      i++;
    }

    /* delete actual */
    prev.setNext(actual.getNext());
    prev.getNext().setPrevious(prev);
    size--;
    return true;
  }

  /**
   * Removes the last element from the collection.
   * 
   * If the collection is empty, nothing will happen. If the collection has size
   * <code>1</code>, the collection will be empty afterwards.
   */
  public void removeLastDocument() {
    if (this.isEmpty()) {
      return;
    }

    /* one element: clear list and return */
    if (this.numDocuments() == 1) {
      this.clear();
      return;
    }

    /* remove last element */
    this.last = this.last.getPrevious();
    this.last.setNext(null);
    size--;
  }

  /**
   * Removes the first element from the collection.
   * 
   * If the collection is empty, nothing will happen. If the collection has size
   * <code>1</code>, the collection will be empty afterwards.
   */
  public void removeFirstDocument() {
    if (this.isEmpty()) {
      return;
    }

    /* one element: clear list and return */
    if (this.numDocuments() == 1) {
      this.clear();
      return;
    }

    /* remove first element */
    this.first = this.first.getNext();
    this.first.setPrevious(null);
    size--;
  }

  /**
   * Returns the first element of the collection or <code>null</code>, if it is
   * empty.
   * 
   * @return the first element of the collection or <code>null</code>, if it is
   *         empty
   */
  public Document getFirstDocument() {
    if (this.isEmpty()) {
      return null;
    }

    return this.first.getDocument();
  }

  /**
   * Returns the last element of the collection or <code>null</code>, if it is
   * empty.
   * 
   * @return the last element of the collection or <code>null</code>, if it is
   *         empty
   */
  public Document getLastDocument() {
    if (this.isEmpty()) {
      return null;
    }

    return this.last.getDocument();
  }

  /**
   * Deletes all elements from the collection.
   */
  private void clear() {
    this.first = null;
    this.last = null;
    this.size = 0;
  }

  /**
   * Determines, whether this collection is empty.
   * 
   * @return <code>true</code>, if this collection is empty, <code>false</code>
   *         otherwise
   */
  public boolean isEmpty() {
    return this.size == 0;
  }

  /**
   * Returns the number of {@link Document}s in this collection.
   * 
   * @return the number of {@link Document}s in this collection
   */
  public int numDocuments() {
    return this.size;
  }

  /**
   * Returns the {@link Document} in this collection at the specified index.
   * 
   * If the specified index is invalid, this method will return <code>null</code>.
   * 
   * @param index the index of which we want to get the {@link Document}
   * @return the {@link Document} at index <code>index</code> or
   *         <code>null</code>, if the specified index is invalid
   */
  public Document get(int index) {
    if (index < 0 || index >= this.size) {
      return null;
    }

    return getDocumentCollectionCell(index).getDocument();
  }

  /**
   * This method returns a {@link WordCountsArray} containing all words of all
   * {@link Document}s in this collection.
   * 
   * Note, that no word will be contained twice in the resulting
   * {@link WordCountsArray}. The count of every word in the resulting
   * {@link WordCountsArray} will be <code>0</code>.
   * 
   * @return a {@link WordCountsArray} containing all words of all
   *         {@link Document}s in this collection
   */
  private WordCountsArray allWords() {
    /*
     * loop over all documents to create a WordCountsArray containing *all* words of
     * all documents
     */
    DocumentCollectionCell tmp = this.first;

    WordCountsArray allWords = new WordCountsArray(0);

    while (tmp != null) {
      Document doc = tmp.getDocument();
      WordCountsArray wca = doc.getWordCounts();

      for (int i = 0; i < wca.size(); i++) {
        allWords.add(wca.getWord(i), 0);
      }

      tmp = tmp.getNext();
    }

    return allWords;
  }

  /**
   * This method calculates the similarity between the specified query and all
   * {@link Document}s in this {@link DocumentCollection} and sorts the
   * {@link Document}s in this collection according to the calculated similarity.
   * 
   * @param searchQuery the query String
   */
  public void match(String searchQuery) {
    if (this.isEmpty()) {
      return;
    }

    if (searchQuery == null || searchQuery.equals("")) {
      return;
    }

    /* add query to collection as document */
    Document queryDocument = new Document("", "", "", null, null, searchQuery);
    this.prependDocument(queryDocument);

    /* add every word to every document with count 0 */
    this.addZeroWordsToDocuments();

    /* sort all WordCountsArrays of all documents */
    DocumentCollectionCell tmp = this.first;
    while (tmp != null) {
      tmp.getDocument().getWordCounts().sort();
      tmp = tmp.getNext();
    }

    /* calculate similarities with query document */
    tmp = this.first.getNext();
    while (tmp != null) {
      tmp.setQuerySimilarity(tmp.getDocument().getWordCounts().computeSimilarity(queryDocument.getWordCounts()));
      tmp = tmp.getNext();
    }

    /* remove the query we added in the beginning */
    this.removeFirstDocument();

    this.sortBySimilarityDesc();
  }

  /**
   * This private helper method swaps the content of the two specified
   * {@link DocumentCollectionCell}s of this {@link DocumentCollection}.
   * 
   * @param cell1 the first {@link DocumentCollectionCell}
   * @param cell2 the second {@link DocumentCollectionCell}
   */
  private void swap(DocumentCollectionCell cell1, DocumentCollectionCell cell2) {
    /*
     * swap both contained the contained document and the corresponding similarity
     */

    Document tmpDoc = cell1.getDocument();
    double tmpSim = cell1.getQuerySimilarity();

    cell1.setDocument(cell2.getDocument());
    cell1.setQuerySimilarity(cell2.getQuerySimilarity());

    cell2.setDocument(tmpDoc);
    cell2.setQuerySimilarity(tmpSim);
  }
  
  private static DocumentCollectionCell[] merge(DocumentCollectionCell[] a, DocumentCollectionCell[] b) {
    DocumentCollectionCell[] merged = new DocumentCollectionCell[a.length + b.length];
    int aIndex = 0;
    int bIndex = 0;
    for (int i = 0; i < merged.length; i++) {
      if (aIndex >= a.length)
        merged[i] = b[bIndex++];
      else if (bIndex >= b.length)
        merged[i] = a[aIndex++];
      else if (a[aIndex].getQuerySimilarity() > b[bIndex].getQuerySimilarity())
        merged[i] = a[aIndex++];
      else
        merged[i] = b[bIndex++];
    }
    return merged;
  }
  
  private static DocumentCollectionCell[] mergeSortIt(DocumentCollectionCell[] a) {
    // Wir teilen zunächst das Array in a.length viele 1-elementige Arrays auf
    DocumentCollectionCell[][] parts = new DocumentCollectionCell[a.length][];
    for (int i = 0; i < a.length; i++)
      parts[i] = new DocumentCollectionCell[] { a[i] };
    // Wir mergen wiederholt je zwei benachbarte Arrays, bis nur mehr ein Teil-Array
    // über bleibt.
    while (parts.length > 1) {
      DocumentCollectionCell[][] partsNew = new DocumentCollectionCell[(parts.length + 1) / 2][];
      for (int i = 0; i < partsNew.length; i++) {
        if (2 * i + 1 < parts.length)
          partsNew[i] = merge(parts[2 * i], parts[2 * i + 1]);
        else
          // Ist die Länge nicht durch zwei teilbar, übernehmen wir den letzten Teil-Array
          // einfach
          partsNew[i] = parts[2 * i];
      }
      parts = partsNew;
    }
    return parts[0];
  }

  /**
   * This method sorts the documents in this collection descending, according to
   * their similarity. The similarity of each document is calculated by calling
   * {@link DocumentCollection#match(String)} and then stored in the corresponding
   * {@link DocumentCollectionCell}.
   */
  private void sortBySimilarityDesc() {
    // Zellen in ein Array kopieren
    DocumentCollectionCell[] cells = new DocumentCollectionCell[this.numDocuments()];
    DocumentCollectionCell actCell = this.first;
    for(int i = 0; actCell != null; i++) {
      cells[i] = actCell;
      actCell = actCell.getNext();
    }
    
    // Sortieren
    cells = mergeSortIt(cells);
    
    // Wieder in eine Liste umwandeln
    DocumentCollectionCell previous = null, next = null;
    for(int i = 0; i < cells.length; i++) {
      if (i > 0)
        previous = cells[i - 1];
      if (i == cells.length - 1)
        next = null;
      else
        next = cells[i + 1];
      cells[i].setNext(next);
      cells[i].setPrevious(previous);
    }
    this.first = cells[0];
  }

  /**
   * This method gets a set of all words of all {@link Document}s in this
   * collection and adds every word to every {@link Document} in this collection
   * with count <code>0</code>.
   * 
   * After this method has been executed, all {@link Document}s in this administer
   * the same words. Note, that the words are always added with count
   * <code>0</code>.
   * 
   */
  private void addZeroWordsToDocuments() {
    WordCountsArray allWords = this.allWords();

    DocumentCollectionCell tmp = this.first;

    while (tmp != null) {
      for (int j = 0; j < allWords.size(); j++) {
        String word = allWords.getWord(j);

        tmp.getDocument().getWordCounts().add(word, 0);
      }

      tmp = tmp.getNext();
    }
  }

  /**
   * This method returns the similarity of the {@link Document} at the specified
   * index. This similarity must have been calculated before using the method
   * {@link DocumentCollection#match(String)}.
   * 
   * If the specified index is invalid, <code>-1</code> is returned.
   * 
   * @param index the index
   * @return the similarity of the {@link Document} at the specified index
   */
  public double getQuerySimilarity(int index) {
    if (index < 0 || index >= this.numDocuments()) {
      return -1;
    }

    return this.getDocumentCollectionCell(index).getQuerySimilarity();
  }

  /**
   * Returns the {@link DocumentCollectionCell} that is at the specified index in
   * this {@link DocumentCollection}.
   * 
   * @param index the index
   * @return the {@link DocumentCollectionCell} at the specified index
   */
  private DocumentCollectionCell getDocumentCollectionCell(int index) {
    if (index < 0 || index >= this.numDocuments()) {
      return null;
    }

    /* navigate to corresponding cell at the index */

    DocumentCollectionCell tmp = this.first;

    int i = 0;
    while (i < index) {
      tmp = tmp.getNext();
      i++;
    }

    return tmp;
  }

  /**
   * Returns a string representation of this {@link DocumentCollection} using the
   * titles of the documents.
   * 
   * @return a string representation of this {@link DocumentCollection}.
   */
  public String toString() {
    if (this.numDocuments() == 0) {
      return "[]";
    }

    if (this.numDocuments() == 1) {
      return "[" + this.get(0).getTitle() + "]";
    }

    String res = "[";
    for (int i = 0; i < this.numDocuments() - 1; i++) {
      res += this.get(i).getTitle() + ", ";
    }
    res += this.get(this.numDocuments() - 1).getTitle() + "]";
    return res;
  }
}
