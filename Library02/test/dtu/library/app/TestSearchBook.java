package dtu.library.app;


import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Note that the test class has been refactor to inherit its setup method
 * from class SampleDataSetup. This allows one to reuse setup code, but still
 * keeps the testing for, e.g. searching, in a separate class.
 */
public class TestSearchBook extends SampleDataSetup {

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
