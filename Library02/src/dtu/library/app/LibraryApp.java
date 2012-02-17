package dtu.library.app;

import java.util.ArrayList;
import java.util.List;

public class LibraryApp {
    private boolean adminLoggedIn = false;
    private List<Book> books;
    private List<User> users;

    public LibraryApp() {
        books = new ArrayList<Book>();
        users = new ArrayList<User>();
    }
    
    public boolean adminLoggedIn() {
        return adminLoggedIn;
    }

    public boolean adminLogin(String password) {
        adminLoggedIn = password.equals("adminadmin");
        return adminLoggedIn;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void addBook(Book book) throws OperationNotAllowedException {
        if (!adminLoggedIn()) {
            throw new OperationNotAllowedException("Add book operation not allowed if not admin.", "Add book");
        }
        books.add(book);
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public List<Book> search(String keyword) {
        List<Book> matchingBooks = new ArrayList<Book>();
        for (Book book : books) {
            if (book.getAuthor().contains(keyword) || book.getTitle().contains(keyword)
                    || book.getSignature().contains(keyword)) {
                matchingBooks.add(book);
            }
        }
        return matchingBooks;
    }


    void setUsers(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public void register(User user) throws OperationNotAllowedException {
        if (!adminLoggedIn()) {
            throw new OperationNotAllowedException("Register user operation not allowed if not admin.", "Register user");
        }
        users.add(user);
    }

    public User userByCprNumber(String cprNumber) {
        for (User user : users) {
            if (user.getCprNumber().equals(cprNumber)) {
                return user;
            }
        }
        return null;
    }

    public Book bookBySignature(String signature) {
        for (Book book : books) {
            if (book.getSignature().equals(signature)) {
                return book;
            }
        }
        return null;
    }
}
