package dtu.library.app;

import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;

public class TestDateServer extends SampleDataSetup {

	/***
	 * Tests that getDate function of the DateServer returns the current date.
	 */
	@Test
	public void testDateServer() {
		Calendar expected = GregorianCalendar.getInstance();
		Calendar result = new DateServer().getDate();
		assertEquals(expected.get(Calendar.YEAR), result.get(Calendar.YEAR));
		assertEquals(expected.get(Calendar.MONTH), result.get(Calendar.MONTH));
		assertEquals(expected.get(Calendar.DAY_OF_MONTH), result
				.get(Calendar.DAY_OF_MONTH));
	}

	/***
	 * Tests that the getDate function of the library application returns the current date.
	 */
	@Test
	public void testLibAppGetDate() {
		Calendar expected = GregorianCalendar.getInstance();
		Calendar result = libApp.getDate();
		assertEquals(expected.get(Calendar.YEAR), result.get(Calendar.YEAR));
		assertEquals(expected.get(Calendar.MONTH), result.get(Calendar.MONTH));
		assertEquals(expected.get(Calendar.DAY_OF_MONTH), result
				.get(Calendar.DAY_OF_MONTH));
	}
}
