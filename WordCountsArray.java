/**
 * This class represents a set words and its counts.
 * 
 * This class ensures, that no empty words are added and that the word count is
 * always greater than or equal to <code>0</code>.
 *
 */
public class WordCountsArray {
  /**
   * the administered WordCount-objects
   */
  private WordCount[] wordCounts;

  /**
   * the actual number of administered WordCount-objects
   */
  private int actualSize;

  /**
   * the maximum number of administrable WordCount-objects; not final because of
   * Uebung 3 g)
   */
  private int maxSize;

  /**
   * Creates a new instance of this class.
   * 
   * The created instance is able to administer at most <code>maxSize</code>
   * words.
   * 
   * @param maxSize the maximum number of words that can be administered by this
   *                instance
   */
  public WordCountsArray(int maxSize) {
    if (maxSize < 0) {
      this.maxSize = 0;
    } else {
      this.maxSize = maxSize;
    }

    this.actualSize = 0;
    this.wordCounts = new WordCount[this.maxSize];
  }

  /**
   * Adds the specified word with the specified count to this instance.
   * 
   * If the specified word is already administered by this instance, then the
   * count of the specified word is increased by the given count.
   * 
   * If the specified word is not already administered by this instance, this
   * method creates a new {@link WordCount} instance and administers this newly
   * created with count <code>count</code>. If the specified word is
   * <code>null</code> or an empty {@link String}, nothing will happen. If the
   * specified count is lower than <code>0</code>, nothing will happen.
   * 
   * 
   * @param word  the word to be added
   * @param count the count of the word to be added
   */
  public void add(String word, int count) {
    if (word == null || word.equals("")) {
      return;
    }

    if (count < 0) {
      return;
    }

    /* get the index, if the word is already administered */
    int index = getIndexOfWord(word.toLowerCase());

    /* word found? */
    if (index == -1) {
      /*
       * the word has not been found, so create a new WordCount instance and add it
       */

      /*
       * if we have reached the end of the array, increase the array size
       */
      if (actualSize == maxSize) {
        this.doubleSize();
      }

      this.wordCounts[actualSize] = new WordCount(word.toLowerCase(), count);
      this.actualSize++;
    } else {
      /*
       * the word has been found and therefore it is already administered, so add the
       * count
       */
      this.wordCounts[index].incrementCount(count);
    }
  }

  /**
   * Determines, whether the words administered by this instance and the words in
   * the specified {@link WordCountsArray} are equal.
   * 
   * This method returns <code>true</code>, if
   * <ul>
   * <li>the words administered by this instance and the words administered by the
   * specified {@link WordCountsArray} instance are the same <b>and</b></li>
   * <li>the words are in the same order</li>
   * </ul>
   * Otherwise, this method will return <code>false</code>.
   * 
   * @param wca the {@link WordCountsArray} that will be compared
   * @return <code>true</code>, if the administered words equal as described in
   *         detail above; <code>false</code> otherwise.
   */
  private boolean wordsEqual(WordCountsArray wca) {
    /* make it short: the same */
    if (this == wca) {
      return true;
    }

    /* cannot be the same */
    if ((wca == null) || (this.size() != wca.size())) {
      return false;
    }

    /* compare every single word at every position */
    for (int i = 0; i < this.size(); i++) {
      if (!this.getWord(i).equals(wca.getWord(i))) {
        return false;
      }
    }

    return true;
  }

  /**
   * Calculate the scalar product of the word counts of this instance and the word
   * counts of the specified {@link WordCountsArray}.
   * 
   * If the two {@link WordCountsArray} have a different size, <code>0</code> is
   * returned. Also, if the words contained in the two {@link WordCountsArray}s
   * are not exactly the same (cf.
   * {@link WordCountsArray#wordsEqual(WordCountsArray)}), the result is
   * <code>0</code>. If <code>wca</code> is <code>null</code>, <code>0</code> is
   * returned.
   * 
   * @param wca the 2nd {@link WordCountsArray}
   * @return the scalar product of this instance and the specified
   *         {@link WordCountsArray}
   */
  private double scalarProduct(WordCountsArray wca) {
    if (wca == null) {
      return 0;
    }

    /* scalar product is 0 by definition, if size is different */
    if (this.size() != wca.size()) {
      return 0;
    }

    /*
     * also, the scalar product is 0 by definition, if the contained words are not
     * exactly the same. Though, if this == wca we do not have to do the
     * wordsEqual()-check.
     */
    if ((this != wca) && !this.wordsEqual(wca)) {
      return 0;
    }

    double scalarProduct = 0;

    for (int i = 0; i < this.size(); i++) {
      scalarProduct += this.getCount(i) * wca.getCount(i);
    }

    return scalarProduct;
  }

  /**
   * Sorts the <code>WordCount</code> objects administered by this instance.
   * 
   * After calling this method the administered <code>WordCount</code> objects are
   * ordered lexicographically according to the words represented by the
   * <code>WordCount</code> objects.
   */
  public void sort() {
    this.doBucketSort();
  }

  /**
   * Sorts the <code>WordCount</code> objects administered by this instance with
   * the bubble sort algorithm.
   */
  private void doBubbleSort() {
    for (int pass = 1; pass < this.actualSize; pass++) {
      for (int i = 0; i < this.actualSize - pass; i++) {
        if (this.getWord(i).compareTo(this.getWord(i + 1)) > 0) {
          WordCount tmp = this.wordCounts[i];
          this.wordCounts[i] = this.wordCounts[i + 1];
          this.wordCounts[i + 1] = tmp;
        }
      }
    }
  }

  private static char bucketCharAt(String word, int maxLength, int index) {
    int iPrime = maxLength - index - 1;
    if(iPrime >= word.length())
      return 'a';
    return word.charAt(iPrime);
  }
  
  /**
   * Sorts the <code>WordCount</code> objects administered by this instance with
   * the bucket sort algorithm.
   * 
   * This method assumes, that all words begin with a lower case letter.
   */
  private void doBucketSort() {
    /* one bucket for every character */
    WordCountsArray[] buckets = new WordCountsArray[26];

    /* initialize the buckets */
    for (int i = 0; i < buckets.length; i++)
      buckets[i] = new WordCountsArray(this.actualSize / 26);

    int maxLength = 0;
    for (int i = 0; i < this.actualSize; i++) {
      maxLength = Math.max(maxLength, this.getWord(i).length());
    }

    /* sort words into buckets */
    for (int i = 0; i < this.actualSize; i++) {
      String word = this.getWord(i);
      int charIndex = bucketCharAt(word, maxLength, 0) - 'a';
      if (charIndex >= 0) {
        int count = this.getCount(i);
        buckets[charIndex].add(word, count);
      }
    }

    for (int pos = 1; pos < maxLength; pos++) {
      WordCountsArray[] bucketsNew = new WordCountsArray[26];
      for (int i = 0; i < bucketsNew.length; i++)
        bucketsNew[i] = new WordCountsArray(this.actualSize / 26);
      for (int i = 0; i < buckets.length; i++) {
        for (int j = 0; j < buckets[i].size(); j++) {
          String word = buckets[i].getWord(j);
          int charIndex = bucketCharAt(word, maxLength, pos) - 'a';
          int count = buckets[i].getCount(j);
          bucketsNew[charIndex].add(word, count);
        }
      }
      buckets = bucketsNew;
    }

    /* new WordCount-Array that will contain the sorted WordCount-objects */
    WordCount[] newWordCounts = new WordCount[this.actualSize];

    /* concatenate the sorted buckets into the new array */
    int j = 0;
    for (int bucket = 0; bucket < buckets.length; bucket++) {
      for (int i = 0; i < buckets[bucket].size(); i++) {
        newWordCounts[j] = buckets[bucket].get(i);
        j++;
      }
    }

    /* set the WordCount-Array of this instance to the new sorted WordCount-Array */
    this.wordCounts = newWordCounts;
    this.maxSize = this.actualSize;
  }

  /**
   * Private helper method that returns the <code>WordCount</code>-object at the
   * specified index.
   * 
   * @param index the index
   * @return the <code>WordCount</code>-object at the specified index or
   *         <code>null</code>, if the specified index is illegal
   */
  private WordCount get(int index) {
    if (index < 0 || index >= this.actualSize) {
      return null;
    }

    return this.wordCounts[index];
  }

  /**
   * Calculate the similarity of this instance and the specified
   * {@link WordCountsArray}.
   * 
   * This method will return a value between <code>0</code> and <code>1</code>. If
   * <code>wca</code> is <code>null</code>, <code>0</code> is returned.
   * 
   * @param wca the 2nd {@link WordCountsArray}
   * @return the similarity between this instance and the specified
   *         {@link WordCountsArray}
   */
  public double computeSimilarity(WordCountsArray wca) {
    if (wca == null) {
      return 0;
    }

    double scalarProductThis = this.scalarProduct(this);
    double scalarProductWca = wca.scalarProduct(wca);

    double scalarProduct = 0;

    if (scalarProductThis != 0 && scalarProductWca != 0) {
      scalarProduct = this.scalarProduct(wca) / (Math.sqrt(scalarProductThis * scalarProductWca));
    }

    return scalarProduct;
  }

  /**
   * Returns the number of words currently administered by this instance.
   * 
   * @return the number of words currently administered by this instance
   */
  public int size() {
    return this.actualSize;
  }

  /**
   * Returns the word at the position <code>index</code> of the
   * {@link WordCount}-Array.
   * 
   * @param index the index
   * @return the word at the specified <code>index</code> or <code>null</code>, if
   *         the specified <code>index</code> is illegal.
   */
  public String getWord(int index) {
    if (index < 0 || index >= this.actualSize) {
      return null;
    }

    return this.wordCounts[index].getWord();
  }

  /**
   * Returns the count of the word at position <code>index</code> of the
   * {@link WordCount}-Array.
   * 
   * @param index the index
   * @return the count of the word at the specified <code>index</code> or
   *         <code>-1</code>, if the specified <code>index</code> is illegal
   */
  public int getCount(int index) {
    if (index < 0 || index >= this.actualSize) {
      return -1;
    }

    return this.wordCounts[index].getCount();
  }

  /**
   * Returns the index of the internal {@link WordCount}-Array where the specified
   * word is administered.
   * 
   * @param word the word for which we want to know the index
   * @return the index of the specified word in the internal array, or
   *         <code>-1</code> if this word is not administered
   */
  public int getIndexOfWord(String word) {
    /* make it short */
    if (word == null || word.equals("")) {
      return -1;
    }

    /* search for the word in the array of WordCounts */
    for (int i = 0; i < this.actualSize; i++) {
      if (this.wordCounts[i].getWord().equals(word)) {
        return i;
      }
    }

    return -1;
  }

  /**
   * Sets the count of the word at position <code>index</code> of the
   * {@link WordCount}-Array to the specified <code>count</code>.
   * 
   * If the specified <code>index</code> is illegal, nothing will happen. If the
   * specified <code>count</code> is lower than <code>0</code>, the count is set
   * to <code>0</code>.
   * 
   * @param index the index of the word whose frequency will be changed
   * @param count the new frequency of the word at position <code>index</code>
   */
  public void setCount(int index, int count) {
    if (index < 0 || index >= this.actualSize) {
      return;
    }

    if (count < 0) {
      this.wordCounts[index].setCount(0);
    } else {
      this.wordCounts[index].setCount(count);
    }
  }

  /**
   * Doubles the number of administerable WordCount-objects,
   */
  private void doubleSize() {
    this.maxSize = this.maxSize * 2;

    /* would be stupid, if former size was 0, so take action... */
    if (this.maxSize <= 0) {
      this.maxSize = 1;
    }

    WordCount[] newWordCounts = new WordCount[this.maxSize];

    /* copy old array */
    for (int i = 0; i < this.wordCounts.length; i++) {
      newWordCounts[i] = this.wordCounts[i];
    }

    this.wordCounts = newWordCounts;
  }

  /**
   * Returns true, if this instance and the specified {@link WordCountsArray}
   * equal.
   * 
   * @param wca the other WordCountsArray
   * @return true, if this instance and the specified {@link WordCountsArray}
   *         equal
   */
  public boolean equals(WordCountsArray wca) {
    /* make it short: the same */
    if (this == wca) {
      return true;
    }

    /* cannot be the same */
    if ((wca == null) || (this.size() != wca.size())) {
      return false;
    }

    /* compare every single word and their counts at every position */
    for (int i = 0; i < this.size(); i++) {
      if (!this.getWord(i).equals(wca.getWord(i)) || this.getCount(i) != wca.getCount(i)) {
        return false;
      }
    }

    return true;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("[");
    for (int i = 0; i < size(); i++) {
      if (i > 0)
        sb.append(", ");
      sb.append("(" + getWord(i) + ", " + getCount(i) + ")");
    }
    sb.append("]");
    return sb.toString();
  }
}
