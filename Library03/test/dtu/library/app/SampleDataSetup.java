package dtu.library.app;

import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

/**
 * A class the defines a common setUp (e.g. a method with the @Before Annotation) containing
 * sample data.<p>
 * Test classes that want to use that data should inherit from this class.
 * @author hub
 *
 */
public class SampleDataSetup {
	
	LibraryApp libApp = new LibraryApp();
	/**
	 * Create the sample data for the search test cases.
	 * This method is executed each time one of the test methods is run.
	 * In contrast to old JUnit versions, it is now the @Before annotations that
	 * marks this method to be run before each test case and not the name setUp().
	 * 
	 * This method is inherited by subclasses. Thus any tests that needs sample data,
	 * should inherit from this class and add sample data as necessary.
	 */
	@Before
	public void setUp() throws Exception {
		List<Book> books = new ArrayList<Book>();
		books.add(new Book("Som001","Software Engineering - 9","Ian Sommerville"));
		books.add(new Book("Sof001","XML for Dummies","Fred Software"));
		for (int i = 1; i <= 10; i++) {
			books.add(new Book("book"+i,"Book "+i,"Author "+i));
		}
		
		libApp.adminLogin("adminadmin");
		for (Book book : books) {
			libApp.addBook(book);
		}

		List<User> users = new ArrayList<User>();
		
		Address address = new Address("Kirkevej",2344,"Herlev");
		User user = new User("1234651234","User 1","user1@library.dk",address);
		users.add(user);
		address = new Address("Lyngby",2345,"Holte");
		user = new User("1212871234","User 2","user2@library.dk",address);
		users.add(user);
		
		for (User usr : users) {
			libApp.register(usr);
		}		
	}


}
