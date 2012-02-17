package dtu.library.app;


import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestSearchBook {

	LibraryApp libApp = new LibraryApp();

	/**
	 * Create the sample data for the search test cases.
	 * This method is executed each time one of the test methods is run.
	 * In contrast to old JUnit versions, it is now the @Before annotations that
	 * marks this method to be run before each test case and not the name setUp().
	 */
	@Before
	public void setUp() throws Exception {
		List<Book> books = new ArrayList<Book>();
		books.add(new Book("Som001","Software Engineering - 9","Ian Sommerville"));
		books.add(new Book("Sof001","XML for Dummies","Fred Software"));
		libApp.setBooks(books);
	}

	/**
	 * Tests searching for books in the library.
	 * Tests that the search finds matches a substring in the
	 * author field of a book.
	 */
	@Test
	public void testSearchBooksTitle() {
		List<Book> books = libApp.search("Ian");
		assertEquals(1,books.size());
		assertEquals("Software Engineering - 9",books.get(0).getTitle());
	}

	/**
	 * Tests searching for books in the library.
	 * Tests that the search finds matches a substring in the
	 * author field of a book and in the title field of a book.
	 * Tests that two books can be returned.
	 */
	@Test
	public void testSearchBooksTitleAuthor() {
		List<Book> books = libApp.search("Software");
		assertEquals(2,books.size());
	}

	/**
	 * Tests searching for books in the library.
	 * Tests that the search finds matches a substring in the
	 * signature field of a book.
	 * Tests that the correct book is returned.
	 */
	@Test
	public void testSearchBooksSignature() {
		List<Book> books = libApp.search("Som001");
		assertEquals(1,books.size());
		assertEquals("Som001",books.get(0).getSignature());
	}

	/**
	 * Tests searching for books in the library.
	 * Tests that an empty list is returned if no matches are found.
	 */
	@Test
	public void testSearchBooksNothingFound() {
		List<Book> books = libApp.search("Jan");
		assertEquals(0,books.size());
	}
}
