package dtu.library.app;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * This class contains tests, testing the functionality of adding books to the library.
 * @author hub
 *
 */
public class TestAddBook {
	/** 
	 * Tests the scenario when the administrator successfully logs in.
	 * <ol>
	 *  <li> The administrator logs in with the correct password
	 * 	<li> The library application knows that the administrator is logged in
	 * </ol>
	 */
	@Test
	public void testLogin() {

		LibraryApp libApp = new LibraryApp();
		
		// Check first that the administrator is not logged in.
		
		assertFalse(libApp.adminLoggedIn());
		
		// Step 1)
		
		boolean login = libApp.adminLogin("adminadmin");
		
		// Step 2) Check that the method returned true and check that admin is logged in.
		assertTrue(login);
		assertTrue(libApp.adminLoggedIn());
	}

	/** 
	 * Tests the scenario when the administrator wants to log in with the wrong password.
	 * <ol>
	 *  <li> The administrator logs in with the wrong password
	 *  <li> The library app responds false to the login request
	 *  <li> The administrator is not logged in to the library application
	 * </ol>
	 */
	@Test
	public void testLoginFailed() {

		LibraryApp libApp = new LibraryApp();
		
		// Check first that the administrator is not logged in.

		assertFalse(libApp.adminLoggedIn());
		
		// Step 1)
		
		boolean login = libApp.adminLogin("wrong password");
		
		// Step 2+3) Check that the method returned false and check that admin is still not logged in.
		assertFalse(login);
		assertFalse(libApp.adminLoggedIn());
	}

	/** 
	 * Tests the scenario that the administrator can add a book to the library via the library application.
	 * <ol>
	 *  <li> The administrator has to login with password "adminadmin".
	 *  <li> The book to be added is created.
	 *  <li> The book is added to the library by calling addBook from the library application.
	 * </ol>
	 */
	@Test
	public void testAdminAddBook() throws Exception {
		
		LibraryApp libApp = new LibraryApp();
		
		// Check first the initial state of the library and the library application.
		// a) The library has no books.
		// b) The administrator is not logged in.
		
		assertTrue(libApp.getMedia().isEmpty());
		assertFalse(libApp.adminLoggedIn());
		
		// Step 1)
		
		boolean login = libApp.adminLogin("adminadmin");
		
		// Check that the login was successful.
		// a) The method adminLogin returned true
		// b) The library applications knows that the administrator has logged in,
		//    by checking that libApp.adminLoggedIn() returns true
		
		assertTrue(login);
		assertTrue(libApp.adminLoggedIn());
		
		// Step 2)
				
		String signature = "Som002";
		String title = "Software Engineering - 9";
		String author = "Ian Sommerville";
		
		Medium book1 = new Book(signature,title,author);
		
		// Step 3)
		
		libApp.addMedium(book1);
		
		// Check that the book was added to the library.
		// a) The library has now one book.
		// b) The book in the library has the correct title and author.

		List<Medium> books = libApp.getMedia();
		assertEquals(1,books.size());
		assertEquals(signature,books.get(0).getSignature());
		assertEquals(title,books.get(0).getTitle());
		assertEquals(author,books.get(0).getAuthor());
	}

	/** 
	 * Tests the scenario when the administrator wants to add a book, but is not logged in.
	 * <ol>
	 *  <li> The book to be added is created.
	 *  <li> The book is added to the library by calling addBook from the library application.
	 *  <li> The library application throws an exception.
	 * </ol>
	 */
	@Test
	public void testAdminAddBookNotLoggedIn() {

		LibraryApp libApp = new LibraryApp();
		
		// Check first the initial state of the library and the library application.
		// a) The library has no books.
		// b) The administrator is not logged in.
		
		assertTrue(libApp.getMedia().isEmpty());
		assertFalse(libApp.adminLoggedIn());
		
		// Step 1)
		
		Medium book1 = new Book("Som001","Software Engineering - 9","Ian Sommerville");
		
		// Step 2)
		
		try {
			libApp.addMedium(book1);
			// The fail assertion fails all the time.
			// The argument is a description describing the reason for the failure.
			// The basic idea is to say it is a failure if addBook is terminates normally, i.e.,
			// if no exception is thrown.
			fail("OperationNotAllowedException exception should have been thrown");
		} catch (OperationNotAllowedException e) {
			// Step 3)
			// Check that the exception thrown has the correct error message and knows which operation failed.
			
			assertEquals("Add medium operation not allowed if not admin.",e.getMessage());
			assertEquals("Add medium",e.getOperation());
		}
		
		// Check that the book has not been added to the library.
		
		assertEquals(0,libApp.getMedia().size());
	}	
}
