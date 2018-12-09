
public class LinkedDocument extends Document {
    final private String iD;

    public LinkedDocument(String title, String language, String description, Date releaseDate, Author author,
	    String content, String iD) {
	super(title, language, description, releaseDate, author, content);
	this.iD = iD;
    }

    public String getiD() {
	return iD;
    }

    public boolean equals(Document doc) {
	if (doc instanceof LinkedDocument && this.iD.equals(((LinkedDocument) doc).getiD()))
	    return true;
	else
	    return super.equals(doc);
    }

    private static String[] findOutgoingIDs(String text) {
	int counts = 0;
	int counts1 = 0;
	String[] text1 = null;
	text1 = text.split(" ");
	for (int i = 0; i < text1.length; i++) {
	    if (text1[i].contains("list:")) {
		counts++;
	    }
	}
	String[] tmp = new String[counts];
	for (int i = 0; i < text1.length; i++) {
	    if (text1[i].contains("list:")) {
		tmp[counts1] = text1[i].substring(5);
		counts1++;
	    }
	}

	return tmp;

    }

    public static void main(String[] args) {
	String a = "list; list:a list:bb ";
	findOutgoingIDs(a);
    }

    public static LinkedDocument createLinkedDocumentFromFile(String fileName) {
	return null;

    }

}
