package dtu.library.app;

import java.util.Calendar;

public abstract class Medium {
    private String signature;
    private String title;
    private String author;
    private LibraryApp libraryApp;
    private boolean isBorrowed;
    private Calendar dateBorrowed;
    private Calendar dueDate;
    
    public Medium(String signature, String title, String author) {
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
    
    public abstract int getMaxDaysForLoan();
    
    public abstract int getFine();

    public boolean isOverdue() {
        return isBorrowed && libraryApp.getDate().after(dueDate);
    }

    public void setLibraryApp(LibraryApp libraryApp) {
        this.libraryApp = libraryApp;
    }
    
    public void markAsReturned() {
        this.isBorrowed = false;
        this.dateBorrowed = null;
        this.dueDate = null;
    }

    public void markAsBorrowed(Calendar dateBorrowed) {
        this.isBorrowed = true;
        this.dateBorrowed = dateBorrowed;
        this.dueDate = (Calendar) dateBorrowed.clone();
        this.dueDate.add(Calendar.DAY_OF_YEAR, getMaxDaysForLoan());
    }
}
