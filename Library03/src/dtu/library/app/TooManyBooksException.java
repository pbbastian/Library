package dtu.library.app;

public class TooManyBooksException extends Exception {
    public TooManyBooksException(String message) {
        super(message);
    }
}
