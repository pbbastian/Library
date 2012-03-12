package dtu.library.app;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Contains tests for returning books, after they have been borrowed.
 * @author hub
 *
 */
public class TestReturnBook extends SampleDataSetup {
	
	/**
	 * Tests that the user can return a book he has borrowed.
	 * <ol>
	 * 	<li>First get the user by its CPR number and the book by its signature
	 * 	<li>The user borrows the book
	 * 	<li>Check that the user has borrowed the book
	 * 	<li>The user returns the book
	 * 	<li>Check that the user returned the book
	 * </ol>
	 * @throws Exception
	 */
	@Test
	public void testReturnBook() throws Exception {
		// Step 1
		String cprNumber = "1234651234";
		User user = libApp.userByCprNumber(cprNumber);
		String signature = "Som001";
		Medium book = libApp.mediumBySignature(signature);

		// Step 2
		user.borrowMedium(book);
		
		// Step 3
		List<Medium> borrowedBooks = user.getBorrowedMedia();
		assertEquals(1,borrowedBooks.size());
		assertTrue(borrowedBooks.contains(book));
		
		// Step 4
		user.returnMedium(book);
		borrowedBooks = user.getBorrowedMedia();
		
		// Step 5
		assertEquals(0,borrowedBooks.size());
		assertFalse(borrowedBooks.contains(book));
	}
}
