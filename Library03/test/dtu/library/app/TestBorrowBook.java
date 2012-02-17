package dtu.library.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;


/**
 * This class contains the test for testing borrowing of books.
 * It includes also a test for the case that more then 10 books are borrowed by the
 * same user.
 * Note that the tests for overdue books is done in class {@link TestOverdue}.
 * @author hub
 * @see TestOverdue
 *
 */
public class TestBorrowBook extends SampleDataSetup {
	
	/**
	 * Tests that the user can borrow a book. 
	 * <ol>
	 * 	<li>Retrieve the user by its CPR number
	 * 	<li>Retrieve the book by its signature
	 * 	<li>The user borrows the book
	 * 	<li>The book is in the list of books borrowed by that user
	 * </ol>
	 * @throws Exception
	 */
	@Test
	public void testBorrowBook() throws Exception {
		// Step 1)
		String cprNumber = "1234651234";
		User user = libApp.userByCprNumber(cprNumber);
		assertEquals(cprNumber,user.getCprNumber());
		
		// Step 2)
		String signature = "Som001";
		Book book = libApp.bookBySignature(signature);
		assertEquals(signature,book.getSignature());
		
		// Check that the user has not borrowed the book already
		List<Book> borrowedBooks = user.getBorrowedBooks();
		int n = borrowedBooks.size();
		assertFalse(borrowedBooks.contains(book));
		
		// Step 3)
		user.borrowBook(book);
		borrowedBooks = user.getBorrowedBooks();

		// Step 4)
		assertEquals(1,borrowedBooks.size());
		assertTrue(borrowedBooks.contains(book));
	}

	/**
	 * Test the case when the user has already borrowed 10 books and want to
	 * borrow one more.
	 * <ol>
	 * 	<li>Retrieve the user by its CPR number
	 * 	<li>Borrow 10 books
	 * 	<li>Retrieve the book by its signature
	 * 	<li>Borrow the 11th book
	 * 	<li>A TooManyBooksException is thrown
	 * </ol>
	 * @throws Exception
	 */
	@Test
	public void testBorrowBookMoreThan10() throws Exception {
		
		// Step 1
		String cprNumber = "1234651234";
		User user = libApp.userByCprNumber(cprNumber);
		assertEquals(cprNumber,user.getCprNumber());
		
		// Step 2
		for (int i = 1; i <= 10; i++) {
			Book book = libApp.bookBySignature("book"+i);
			user.borrowBook(book);
		}

		List<Book> borrowedBooks = user.getBorrowedBooks();
		assertEquals(10,borrowedBooks.size());

		// Step 3
		String signature = "Som001";
		Book book = libApp.bookBySignature(signature);
		assertEquals(signature,book.getSignature());

		// Check that the book is not already borrowed
		assertFalse(borrowedBooks.contains(book));

		try {
			user.borrowBook(book);
			fail("A TooManyBooksException should have been thrown");
		} catch ( TooManyBooksException e) {
			// Step 4
			assertEquals("Can't borrow more than 10 books", e.getMessage());
		}

		// Check that the number of borrowed books by the user hasn't changed.
		borrowedBooks = user.getBorrowedBooks();
		assertEquals(10,borrowedBooks.size());
		assertFalse(borrowedBooks.contains(book));
	}
	
	/**
	 * Tests the case when the user tries to borrow the null value.
	 * This should be ignored.
	 * <ol>
	 * 	<li>Get the user via his CPR number
	 * 	<li>Call the borrowBook operation with the null value
	 * 	<li>Check that the number of borrowed books has not changed
	 * @throws Exception
	 */
	@Test
	public void testBorrowBookNull() throws Exception {
		// Step 1
		String cprNumber = "1234651234";
		User user = libApp.userByCprNumber(cprNumber);
		assertEquals(cprNumber,user.getCprNumber());
		
		Book book = null;
		assertNull(book);
		
		// Check that the user hasn't borrowed anything
		List<Book> borrowedBooks = user.getBorrowedBooks();
		assertEquals(0,borrowedBooks.size());
		
		// Step 2
		user.borrowBook(book);
		
		// Step 3
		borrowedBooks = user.getBorrowedBooks();
		assertEquals(0,borrowedBooks.size());
	}

}
