package dtu.library.app;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String cprNumber;
    private String name;
    private String email;
    private Address address;
    private List<Book> borrowedBooks;
    private LibraryApp libraryApp;

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

    public void borrowBook(Book book) throws TooManyBooksException, HasOverdueBookException {
        if (book == null) {
            return;
        }
        if (borrowedBooks.size() == 10) {
            throw new TooManyBooksException("Can't borrow more than 10 books");
        }
        if (hasOverdueBooks()) {
            throw new HasOverdueBookException("User has overdue books");
        }
        book.setBorrowed(libraryApp.getDate());
        borrowedBooks.add(book);
    }

    public void returnBook(Book book) {
        borrowedBooks.remove(book);
        book.setReturned();
    }

    public void setLibraryApp(LibraryApp libraryApp) {
        this.libraryApp = libraryApp;
    }
    
    boolean hasOverdueBooks() {
        for (Book borrowedBook : borrowedBooks) {
            if (borrowedBook.isOverdue()) {
                return true;
            }
        }
        return false;
    }
}
