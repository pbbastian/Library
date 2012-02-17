package dtu.library.app;

import java.util.Calendar;

public class Book {
    private String signature;
    private String title;
    private String author;
    private LibraryApp libraryApp;
    private boolean isBorrowed;
    private Calendar dateBorrowed;
    private Calendar dueDate;
    
    public Book(String signature, String title, String author) {
        this.signature = signature;
        this.title = title;
        this.author = author;
        this.isBorrowed = false;
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

    public boolean isOverdue() {
        if (isBorrowed && libraryApp.getDate().after(dueDate)) {
            return true;
        } else {
            return false;
        }
    }

    public void setLibraryApp(LibraryApp libraryApp) {
        this.libraryApp = libraryApp;
    }
    
    public void setReturned() {
        this.isBorrowed = false;
        this.dateBorrowed = null;
        this.dueDate = null;
    }

    public void setBorrowed(Calendar dateBorrowed) {
        this.isBorrowed = true;
        this.dateBorrowed = dateBorrowed;
        this.dueDate = (Calendar) this.dateBorrowed.clone();
        this.dueDate.add(Calendar.DAY_OF_YEAR, 28);
    }
}
