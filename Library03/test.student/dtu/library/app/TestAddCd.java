package dtu.library.app;

import org.junit.*;

import static junit.framework.Assert.*;

public class TestAddCd {
    private LibraryApp libraryApp;
    
    @Before
    public void setUp() {
        libraryApp = new LibraryApp();
    }

    @Test
    public void testAdminAddCd() throws OperationNotAllowedException {
        libraryApp.adminLogin("adminadmin");
        assertTrue(libraryApp.adminLoggedIn());
        
        Cd cd = new Cd("Awe001", "Awesome Album", "Awesome");

        libraryApp.addMedium(cd);
        assertTrue(libraryApp.getMedia().contains(cd));
    }
}
