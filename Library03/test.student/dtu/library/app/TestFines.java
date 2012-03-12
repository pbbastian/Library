package dtu.library.app;

import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TestFines extends SampleDataSetup {
    @Test
    public void testBookFine() throws TooManyBooksException, HasOverdueBookException {
        Medium medium = libApp.mediumBySignature("book1");
        User user = libApp.userByCprNumber("1234651234");

        user.borrowMedium(medium);

        DateServer dateServer = mock(DateServer.class);
        libApp.setDateServer(dateServer);

        Calendar cal = GregorianCalendar.getInstance();
        cal.add(GregorianCalendar.DAY_OF_YEAR, 29);
        when(dateServer.getDate()).thenReturn(cal);

        assertEquals(20, user.getFine());

        user.returnMedium(medium);
        
        assertEquals(0, user.getFine());
    }

    @Test
    public void testTwoBooksFine() throws TooManyBooksException, HasOverdueBookException {
        User user = libApp.userByCprNumber("1234651234");
        Medium medium1 = libApp.mediumBySignature("book1");
        Medium medium2 = libApp.mediumBySignature("book2");

        user.borrowMedium(medium1);
        user.borrowMedium(medium2);

        DateServer dateServer = mock(DateServer.class);
        libApp.setDateServer(dateServer);

        Calendar cal = GregorianCalendar.getInstance();
        cal.add(GregorianCalendar.DAY_OF_YEAR, 29);
        when(dateServer.getDate()).thenReturn(cal);
        assertEquals(40, user.getFine());

        user.returnMedium(medium1);
        assertEquals(20, user.getFine());

        user.returnMedium(medium2);
        assertEquals(0, user.getFine());
    }

    @Test
    public void testCdFine() throws TooManyBooksException, HasOverdueBookException {
        Medium medium = libApp.mediumBySignature("cd1");
        User user = libApp.userByCprNumber("1234651234");

        user.borrowMedium(medium);

        DateServer dateServer = mock(DateServer.class);
        libApp.setDateServer(dateServer);

        Calendar cal = GregorianCalendar.getInstance();
        cal.add(GregorianCalendar.DAY_OF_YEAR, 8);
        when(dateServer.getDate()).thenReturn(cal);

        assertEquals(40, user.getFine());

        user.returnMedium(medium);

        assertEquals(0, user.getFine());
    }
}
