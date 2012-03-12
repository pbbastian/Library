package dtu.library.app;

import org.junit.Test;

import java.util.*;

import static org.mockito.Mockito.*;

public class TestEmailReminder extends SampleDataSetup {

    @Test
    public void testWhenOneUserHasOneOverdueBook() throws TooManyBooksException, HasOverdueBookException {
        // Have a user borrow a book
        User user = libApp.getUsers().get(0);
        Medium book = libApp.getMedia().get(0);
        user.borrowMedium(book);
        
        // Mock DateServer and EmailServer
        DateServer dateServerMock = mockLateDateServer();
        EmailServer emailServerMock = mock(EmailServer.class);
        libApp.setDateServer(dateServerMock);
        libApp.setEmailServer(emailServerMock);
        
        // Do the actual test
        libApp.sendEmailReminder();
        verify(emailServerMock).send(user.getEmail(), "Overdue book(s)", "You have 1 overdue books");
    }

    @Test
    public void testWhenOneUserHasTwoOverdueBooks() throws TooManyBooksException, HasOverdueBookException {
        // Have a user borrow a book
        User user = libApp.getUsers().get(0);
        Medium book1 = libApp.getMedia().get(0);
        Medium book2 = libApp.getMedia().get(1);
        user.borrowMedium(book1);
        user.borrowMedium(book2);

        // Mock DateServer and EmailServer
        DateServer dateServerMock = mockLateDateServer();
        EmailServer emailServerMock = mock(EmailServer.class);
        libApp.setDateServer(dateServerMock);
        libApp.setEmailServer(emailServerMock);

        // Do the actual test
        libApp.sendEmailReminder();
        verify(emailServerMock).send(user.getEmail(), "Overdue book(s)", "You have 2 overdue books");
    }

    @Test
    public void testWhenTwoUsersEachHasOneOverdueBook() throws TooManyBooksException, HasOverdueBookException {
        // Have a user borrow a book
        User user1 = libApp.getUsers().get(0);
        User user2 = libApp.getUsers().get(1);
        Medium book1 = libApp.getMedia().get(0);
        Medium book2 = libApp.getMedia().get(1);
        user1.borrowMedium(book1);
        user2.borrowMedium(book2);

        // Mock DateServer and EmailServer
        DateServer dateServerMock = mockLateDateServer();
        EmailServer emailServerMock = mock(EmailServer.class);
        libApp.setDateServer(dateServerMock);
        libApp.setEmailServer(emailServerMock);

        // Do the actual test
        libApp.sendEmailReminder();
        verify(emailServerMock).send(user1.getEmail(), "Overdue book(s)", "You have 1 overdue books");
        verify(emailServerMock).send(user2.getEmail(), "Overdue book(s)", "You have 1 overdue books");
    }

    @Test
    public void testWhenTwoUsersHasBorrowedBooksButNoOverdueBooks() throws TooManyBooksException, HasOverdueBookException {
        // Have a user borrow a book
        User user1 = libApp.getUsers().get(0);
        User user2 = libApp.getUsers().get(1);
        Medium book1 = libApp.getMedia().get(0);
        Medium book2 = libApp.getMedia().get(1);
        user1.borrowMedium(book1);
        user2.borrowMedium(book2);

        // Mock EmailServer
        EmailServer emailServerMock = mock(EmailServer.class);
        libApp.setEmailServer(emailServerMock);

        // Do the actual test
        libApp.sendEmailReminder();
        verify(emailServerMock, never()).send(user1.getEmail(), "Overdue book(s)", "You have 1 overdue books");
        verify(emailServerMock, never()).send(user2.getEmail(), "Overdue book(s)", "You have 1 overdue books");
    }
    
    DateServer mockLateDateServer() {
        DateServer dateServer = mock(DateServer.class);
        Calendar lateCalendar = Calendar.getInstance();
        lateCalendar.add(Calendar.DAY_OF_YEAR, 29);
        when(dateServer.getDate()).thenReturn(lateCalendar);
        return dateServer;
    }
}
