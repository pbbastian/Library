package dtu.library.app;

import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Contains the tests for testing when a book is overdue.<p>
 * This tests also the business logic what happens when a user has overdue books
 * and wants to borrow another book.
 * @author hub
 *
 */
public class TestOverdue extends SampleDataSetup {

	// Maximal number of days a book can be on loan (4 weeks)
	private static final int MAX_DAYS_FOR_LOAN = 4 * 7;

	/***
	 * Tests the overdue status of a book. A book is overdue, when it is borrowed and
	 * the current date is more than 28 days (4 weeks) later than the borrow date.
	 * <ol>
	 * 	<li>Create a mock object for the date server and tell the library app to use that mock object
	 * 	<li>Make sure that on borrowing the date server returns 15.1.2011
	 * 	<li>Get a user by CPR number and a book by signature and let the user borrow the book
	 *  <li>Compute the date 28 days after the borrow date and set is as the new return value for the mock object
	 *  <li>Check that the book is not yet overdue
	 *  <li>Set the date 29 days after the borrow date
	 *  <li>Check that the book is overdue
	 * </ol>
	 * @throws Exception
	 */
	@Test
	public void testOverdueBook() throws Exception {

		// Step 1
		DateServer dateServer = mock(DateServer.class);
		libApp.setDateServer(dateServer);

		// Step 2
		Calendar cal = new GregorianCalendar(2011,Calendar.JANUARY,10);
		when(dateServer.getDate()).thenReturn(cal);

		// Step 3
		String cprNumber = "1234651234";
		User user = libApp.userByCprNumber(cprNumber);

		String signature = "Som001";
		Book book = libApp.bookBySignature(signature);

		user.borrowBook(book);

		// Step 4
		Calendar newCal = new GregorianCalendar();
		newCal.setTime(cal.getTime());
		newCal.add(Calendar.DAY_OF_YEAR, MAX_DAYS_FOR_LOAN);
		when(dateServer.getDate()).thenReturn(newCal);

		// Step 5
		assertFalse(book.isOverdue());

		// Step 6
		newCal = new GregorianCalendar();
		newCal.setTime(cal.getTime());
		newCal.add(Calendar.DAY_OF_YEAR, MAX_DAYS_FOR_LOAN + 1);
		when(dateServer.getDate()).thenReturn(newCal);

		// Step 7
		assertTrue(book.isOverdue());
	}

	/***
	 * Tests that the computation of when a book is overdue also works across year
	 * boundaries (e.g. borrow date in 2010 and last return date is 2011)
	 * @throws Exception
	 */
	@Test
	public void testOverdueBookYearRollOver() throws Exception {

		DateServer dateServer = mock(DateServer.class);
		libApp.setDateServer(dateServer);

		Calendar cal = new GregorianCalendar(2010, Calendar.DECEMBER, 15);
		when(dateServer.getDate()).thenReturn(cal);

		String cprNumber = "1234651234";
		User user = libApp.userByCprNumber(cprNumber);

		String signature = "Som001";
		Book book = libApp.bookBySignature(signature);

		user.borrowBook(book);

		Calendar newCal = new GregorianCalendar();
		newCal.setTime(cal.getTime());
		newCal.add(Calendar.DAY_OF_YEAR, MAX_DAYS_FOR_LOAN);
		when(dateServer.getDate()).thenReturn(newCal);

		assertFalse(book.isOverdue());

		newCal = new GregorianCalendar();
		newCal.setTime(cal.getTime());
		newCal.add(Calendar.DAY_OF_YEAR, MAX_DAYS_FOR_LOAN + 1);
		when(dateServer.getDate()).thenReturn(newCal);

		assertTrue(book.isOverdue());
	}

	/***
	 * Test that the user can't borrow a book if he has an overdue book.
	 * <ol>
	 * 	<li>The user borrows a book
	 * 	<li>The date is set to 29 days after the book has been borrowed
	 * 	<li>The user tries to borrow another book
	 * 	<li>The system throws a HasOverdueBooksException
	 * </ol>
	 * @throws Exception
	 */
	@Test
	public void testBorrowWithOverdueBooks() throws Exception {

			// Step 1
			DateServer dateServer = mock(DateServer.class);
			libApp.setDateServer(dateServer);

			Calendar cal = new GregorianCalendar(2011, Calendar.JANUARY, 1);

			when(dateServer.getDate()).thenReturn(cal);

			String cprNumber = "1234651234";
			User user = libApp.userByCprNumber(cprNumber);

			String signature = "Som001";
			Book book = libApp.bookBySignature(signature);

			user.borrowBook(book);

			// Step 2
			Calendar newCal = new GregorianCalendar();
			newCal.setTime(cal.getTime());
			newCal.add(Calendar.DAY_OF_YEAR,  MAX_DAYS_FOR_LOAN + 1);
			when(dateServer.getDate()).thenReturn(newCal);

			assertTrue(book.isOverdue());

			// Step 3
			signature = "book1";
			book = libApp.bookBySignature(signature);

			try {
				user.borrowBook(book);
				fail("Should throw HasOverdueBookException");
			} catch (HasOverdueBookException e) {
				// Step 4
				assertEquals("User has overdue books",e.getMessage());
				assertFalse(user.getBorrowedBooks().contains(book));
			}
	}

	/***
	 * Tests that the book is not overdue anymore, when it is being returned.
	 * <ol>
	 * 	<li>The user borrows a book
	 * 	<li>The date is set to 29 days after borrowing
	 * 	<li>Check that the book is overdue
	 * 	<li>The user returns the book
	 * 	<li>Check that the book is not overdue anymore
	 * </ol>
	 * @throws Exception
	 */
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

		String signature = "Som001";
		Book book = libApp.bookBySignature(signature);

		user.borrowBook(book);

		// Step 2
		Calendar newCal = new GregorianCalendar();
		newCal.setTime(cal.getTime());
		newCal.add(Calendar.DAY_OF_YEAR, MAX_DAYS_FOR_LOAN + 1);
		when(dateServer.getDate()).thenReturn(newCal);

		// Step 3
		assertTrue(book.isOverdue());

		// Step 4
		user.returnBook(book);

		// Step 5
		assertFalse(book.isOverdue());

	}

}
