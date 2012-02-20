package dtu.library.app;

import org.junit.Test;

import java.util.Calendar;

import static org.mockito.Mockito.*;

public class TestEmailReminder {
    @Test
    public void testEmailReminder() throws OperationNotAllowedException, TooManyBooksException, HasOverdueBookException {
        LibraryApp libraryApp = new LibraryApp();
        
        libraryApp.adminLogin("adminadmin");
        
        Address address = new Address("Kirkevej", 2344, "Herlev");
        User user1 = new User("1234651234", "User 1", "user1@library.dk", address);
        User user2 = new User("5903853467", "User 2", "user2@library.dk", address);

        libraryApp.register(user1);
        libraryApp.register(user2);

        for (int i = 1; i <= 10; i++) {
            Book book = new Book("book"+i,"Book "+i,"Author "+i);
            libraryApp.addBook(book);
            if (i % 2 == 0) {
                user1.borrowBook(book);
            } else {
                user2.borrowBook(book);
            }
        }
        
        DateServer dateServer = mock(DateServer.class);
        Calendar lateCalendar = Calendar.getInstance();
        lateCalendar.add(Calendar.DAY_OF_YEAR, 29);
        when(dateServer.getDate()).thenReturn(lateCalendar);
        
        EmailServer emailServer = mock(EmailServer.class);
        
        libraryApp.setDateServer(dateServer);
        libraryApp.setEmailServer(emailServer);

        libraryApp.sendEmailReminder();
        
        verify(emailServer).send(user1.getEmail(), "Overdue book(s)", "You have 5 overdue books");
        verify(emailServer).send(user2.getEmail(), "Overdue book(s)", "You have 5 overdue books");
    }
}
