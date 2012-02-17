package dtu.library.app;

public class Book {
    private String signature;
    private String title;
    private String author;
    
    public Book(String signature, String title, String author) {
        this.signature = signature;
        this.title = title;
        this.author = author;
    }


    public String getSignature() {
        return signature;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
}
