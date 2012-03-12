package dtu.library.app;

import org.junit.*;

import static junit.framework.Assert.*;

public class TestUnregisterUser {
    @Test
    public void testUnregisterUser() throws OperationNotAllowedException {
        LibraryApp libraryApp = new LibraryApp();
        libraryApp.adminLogin("adminadmin");

        Address address = new Address("Kirkevej",2344,"Herlev");
        User user = new User("1234651234","User 1","user1@library.dk",address);
        
        libraryApp.register(user);
        assertEquals(1, libraryApp.getUsers().size());
        
        libraryApp.unregister(user);
        assertEquals(0, libraryApp.getUsers().size());
    }
    
    @Test
    public void testUnregisterUserAsNonAdmin() throws OperationNotAllowedException {
        LibraryApp libraryApp = new LibraryApp();
        libraryApp.adminLogin("adminadmin");

        Address address = new Address("Kirkevej",2344,"Herlev");
        User user = new User("1234651234","User 1","user1@library.dk",address);

        libraryApp.register(user);
        assertEquals(1, libraryApp.getUsers().size());
        
        libraryApp.adminLogoff();
        
        try {
            libraryApp.unregister(user);
            fail("An OperationNotAllowedException should've been thrown");
        } catch (OperationNotAllowedException e) {
            assertEquals("Only an admin can unregister users", e.getMessage());
            assertEquals("Unregister user", e.getOperation());
        }
    }

    @Test
    public void testUnregisterUserWithBorrowedBooks() throws OperationNotAllowedException, TooManyBooksException, HasOverdueBookException {
        LibraryApp libraryApp = new LibraryApp();
        libraryApp.adminLogin("adminadmin");

        Address address = new Address("Kirkevej",2344,"Herlev");
        User user = new User("1234651234","User 1","user1@library.dk",address);
        Medium book = new Book("Som001","Software Engineering - 9","Ian Sommerville");
        
        libraryApp.register(user);
        libraryApp.addMedium(book);
        user.borrowMedium(book);
        
        try {
            libraryApp.unregister(user);
            fail("An OperationNotAllowedException should've been thrown");
        } catch (OperationNotAllowedException e) {
            assertEquals("A user with borrowed books cannot be unregistered", e.getMessage());
            assertEquals("Unregister user", e.getOperation());
        }
    }
}
