package dtu.library.app;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String cprNumber;
    private String name;
    private String email;
    private Address address;
    private List<Medium> borrowedMedia;
    private LibraryApp libraryApp;

    public User(String cprNumber, String name, String email, Address address) {
        this.cprNumber = cprNumber;
        this.name = name;
        this.email = email;
        this.address = address;
        this.borrowedMedia = new ArrayList<Medium>();
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

    public List<Medium> getBorrowedMedia() {
        return borrowedMedia;
    }

    public void borrowMedium(Medium medium) throws TooManyBooksException, HasOverdueBookException {
        if (medium == null) {
            return;
        }
        if (borrowedMedia.size() == 10) {
            throw new TooManyBooksException("Can't borrow more than 10 books");
        }
        if (hasOverdueMedia()) {
            throw new HasOverdueBookException("User has overdue books");
        }
        medium.markAsBorrowed(libraryApp.getDate());
        borrowedMedia.add(medium);
    }

    public void returnMedium(Medium medium) {
        borrowedMedia.remove(medium);
        medium.markAsReturned();
    }

    public void setLibraryApp(LibraryApp libraryApp) {
        this.libraryApp = libraryApp;
    }
    
    boolean hasOverdueMedia() {
        for (Medium borrowedMedium : borrowedMedia) {
            if (borrowedMedium.isOverdue()) {
                return true;
            }
        }
        return false;
    }

    public int getFine() {
        int fine = 0;
        for (Medium borrowedMedium : borrowedMedia) {
            if (borrowedMedium.isOverdue()) {
                fine += borrowedMedium.getFine();
            }
        }
        return fine;
    }
}
