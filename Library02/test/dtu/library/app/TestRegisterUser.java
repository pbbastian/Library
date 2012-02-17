package dtu.library.app;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TestRegisterUser {

	/**
	 * Tests the registration of a user with an address. For this, the
	 * administrator needs to have logged in.
	 * <ol>
	 * <li>Check that no users are registered
	 * <li>The administrator logs in
	 * <li>A new user with address is created and is registered with the library
	 * application
	 * <li>Check that the user is registered
	 * </ol>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRegisterUser() throws Exception {
		LibraryApp libApp = new LibraryApp();

		// Step 1)
		List<User> users = libApp.getUsers();
		assertEquals(0, users.size());

		// Step 2)
		libApp.adminLogin("adminadmin");
		assertTrue(libApp.adminLoggedIn());

		// Step 3)
		Address address = new Address("Roskilde vej", 2345, "Roskilde");
		User user = new User("0112851323", "Flemming Knudsen", "fk@mail.dk",
				address);

		libApp.register(user);
		users = libApp.getUsers();

		// Step 4)
		assertEquals(1, users.size());

		User registeredUser = users.get(0);
		assertEquals("0112851323", registeredUser.getCprNumber());
		assertEquals("Flemming Knudsen", registeredUser.getName());
		assertEquals("fk@mail.dk", registeredUser.getEmail());
		assertEquals("Roskilde vej", registeredUser.getAddress().getStreet());
		assertEquals(2345, registeredUser.getAddress().getPostnumber());
		assertEquals("Roskilde", registeredUser.getAddress().getTown());
	}

	/**
	 * Tests that it is not allowed to register a user if not logged in as
	 * administrator.
	 * <ol>
	 * 	<li>Check that the administrator is not logged in
	 * 	<li>Create a user with an address and register the user
	 * 	<li>Check that an OperationNotAllowedException is thrown with the correct error message
	 * </ol>
	 *
	 * @throws Exception
	 */
	@Test
	public void testRegisterUserIfNotLoggedIn() throws Exception {
		LibraryApp libraryApp = new LibraryApp();

		// Step 1)
		assertFalse(libraryApp.adminLoggedIn());

		// Step 2)
		Address address = new Address("Roskilde vej", 2345, "Roskilde");
		User user = new User("0112851323", "Flemming Knudsen", "fk@mail.dk",
				address);

		try {
			libraryApp.register(user);
			fail("An OperationNotAllowedException should have been thrown");
		} catch (OperationNotAllowedException e) {

			// Step 3
			assertEquals("Register user operation not allowed if not admin.", e
					.getMessage());
			assertEquals("Register user", e.getOperation());
		}
	}
}
