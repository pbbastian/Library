package dtu.library.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LibraryApp {
    private boolean adminLoggedIn = false;
    private List<Book> books;
    private List<User> users;
    private DateServer dateServer;
    private EmailServer emailServer;

    public LibraryApp() {
        books = new ArrayList<Book>();
        users = new ArrayList<User>();
        dateServer = new DateServer();
        emailServer = new EmailServer();
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
        book.setLibraryApp(this);
        books.add(book);
    }

    void setBooks(List<Book> books) {
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
        user.setLibraryApp(this);
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

    public void adminLogoff() {
        adminLoggedIn = false;
    }

    public void unregister(User user) throws OperationNotAllowedException {
        if (!adminLoggedIn()) {
            throw new OperationNotAllowedException("Only an admin can unregister users", "Unregister user");
        } else if (!user.getBorrowedBooks().isEmpty()) {
            throw new OperationNotAllowedException("A user with borrowed books cannot be unregistered", "Unregister user");
        }
        users.remove(user);
    }

    public Calendar getDate() {
        return dateServer.getDate();
    }

    public void setDateServer(DateServer dateServer) {
        this.dateServer = dateServer;
    }

    public void sendEmailReminder() {
        for (User user : users) {
            List<Book> overdueBooks = new ArrayList<Book>();
            for (Book book : user.getBorrowedBooks()) {
                if (book.isOverdue()) {
                    overdueBooks.add(book);
                }
            }
            if (overdueBooks.size() > 0) {
                emailServer.send(user.getEmail(), "Overdue book(s)", String.format("You have %d overdue books", overdueBooks.size()));
            }
        }
    }

    public void setEmailServer(EmailServer emailServer) {
        this.emailServer = emailServer;
    }
}
