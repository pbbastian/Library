package dtu.library.app;

import org.junit.*;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TestOverdueCd extends SampleDataSetup {
    private static final int MAX_DAYS_FOR_LOAN = 7;
    private List<Cd> cds;
    private User user;

    @Test
    public void testOverdueCd() throws Exception {

        // Step 1
        DateServer dateServer = mock(DateServer.class);
        libApp.setDateServer(dateServer);

        // Step 2
        Calendar cal = new GregorianCalendar(2011,Calendar.JANUARY,10);
        when(dateServer.getDate()).thenReturn(cal);

        // Step 3
        String cprNumber = "1234651234";
        User user = libApp.userByCprNumber(cprNumber);

        String signature = "cd1";
        Medium cd = libApp.mediumBySignature(signature);

        user.borrowMedium(cd);

        // Step 4
        Calendar newCal = new GregorianCalendar();
        newCal.setTime(cal.getTime());
        newCal.add(Calendar.DAY_OF_YEAR, MAX_DAYS_FOR_LOAN);
        when(dateServer.getDate()).thenReturn(newCal);

        // Step 5
        assertFalse(cd.isOverdue());

        // Step 6
        newCal = new GregorianCalendar();
        newCal.setTime(cal.getTime());
        newCal.add(Calendar.DAY_OF_YEAR, MAX_DAYS_FOR_LOAN + 1);
        when(dateServer.getDate()).thenReturn(newCal);

        // Step 7
        assertTrue(cd.isOverdue());
    }

    @Test
    public void testReturningOverdueBook() throws Exception {

        // Step 1
        DateServer dateServer = mock(DateServer.class);
        libApp.setDateServer(dateServer);

        Calendar cal = new GregorianCalendar();
        cal.set(2011, Calendar.JANUARY, 10);
        when(dateServer.getDate()).thenReturn(cal);

        String cprNumber = "1234651234";
        User user = libApp.userByCprNumber(cprNumber);

        String signature = "cd1";
        Medium cd = libApp.mediumBySignature(signature);

        user.borrowMedium(cd);

        // Step 2
        Calendar newCal = new GregorianCalendar();
        newCal.setTime(cal.getTime());
        newCal.add(Calendar.DAY_OF_YEAR, MAX_DAYS_FOR_LOAN + 1);
        when(dateServer.getDate()).thenReturn(newCal);

        // Step 3
        assertTrue(cd.isOverdue());

        // Step 4
        user.returnMedium(cd);

        // Step 5
        assertFalse(cd.isOverdue());

    }
}
