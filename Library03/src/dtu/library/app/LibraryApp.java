package dtu.library.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LibraryApp {
    private boolean adminLoggedIn = false;
    private List<Medium> media;
    private List<User> users;
    private DateServer dateServer;
    private EmailServer emailServer;

    public LibraryApp() {
        media = new ArrayList<Medium>();
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

    public List<Medium> getMedia() {
        return media;
    }

    public void addMedium(Medium medium) throws OperationNotAllowedException {
        if (!adminLoggedIn()) {
            throw new OperationNotAllowedException("Add medium operation not allowed if not admin.", "Add medium");
        }
        medium.setLibraryApp(this);
        media.add(medium);
    }

    void setMedia(List<Medium> media) {
        this.media = media;
    }

    public List<Medium> search(String keyword) {
        List<Medium> matchingMedia = new ArrayList<Medium>();
        for (Medium medium : media) {
            if (medium.getAuthor().contains(keyword) || medium.getTitle().contains(keyword)
                    || medium.getSignature().contains(keyword)) {
                matchingMedia.add(medium);
            }
        }
        return matchingMedia;
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

    public Medium mediumBySignature(String signature) {
        for (Medium medium : media) {
            if (medium.getSignature().equals(signature)) {
                return medium;
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
        } else if (!user.getBorrowedMedia().isEmpty()) {
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
            List<Medium> overdueMedia = new ArrayList<Medium>();
            for (Medium medium : user.getBorrowedMedia()) {
                if (medium.isOverdue()) {
                    overdueMedia.add(medium);
                }
            }
            if (overdueMedia.size() > 0) {
                emailServer.send(user.getEmail(), "Overdue book(s)", String.format("You have %d overdue books", overdueMedia.size()));
            }
        }
    }

    public void setEmailServer(EmailServer emailServer) {
        this.emailServer = emailServer;
    }

}
