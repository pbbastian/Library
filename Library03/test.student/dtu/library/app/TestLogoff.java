package dtu.library.app;

import org.junit.*;

import static org.junit.Assert.*;

public class TestLogoff {
    @Test
    public void testLogoff() {
        LibraryApp libraryApp = new LibraryApp();
        libraryApp.adminLogin("adminadmin");
        libraryApp.adminLogoff();
        assertFalse(libraryApp.adminLoggedIn());
    }
}
