package dtu.library.app;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String cprNumber;
    private String name;
    private String email;
    private Address address;
    private List<Book> borrowedBooks;

    public User(String cprNumber, String name, String email, Address address) {
        this.cprNumber = cprNumber;
        this.name = name;
        this.email = email;
        this.address = address;
        this.borrowedBooks = new ArrayList<Book>();
    }

    public String getCprNumber() {
        return cprNumber;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void borrowBook(Book book) throws TooManyBooksException {
        if (book == null) {
            return;
        }
        if (borrowedBooks.size() == 10) {
            throw new TooManyBooksException("Can't borrow more than 10 books");
        }
        borrowedBooks.add(book);
    }

    public void returnBook(Book book) {
        borrowedBooks.remove(book);
    }
}
