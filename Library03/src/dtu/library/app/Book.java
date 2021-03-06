package dtu.library.app;

public class Book extends Medium {
    public Book(String signature, String title, String author) {
        super(signature, title, author);
    }

    @Override
    public int getMaxDaysForLoan() {
        return 28;
    }

    @Override
    public int getFine() {
        return 20;
    }
}
