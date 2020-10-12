package version3.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import version3.controller.MainController;
import version3.model.library.Library;
import version3.model.registry.MaterialTypeRegistry;

/**
 * 
 * This is a JUnit Test class for running our program and doing an automated set of tests to ensure that base functionality is retained
 * across multiple versions of the program.
 * 
 * Using JUnit tests requires that your project have imported the 'JUnit 5' library in your classpath. Eclipse is able to automatically import
 * it if you create a class that is a JUnit Test (new > Other > Java > JUnit) and accept the prompt to import it.
 * 
 * As of v3.0, these tests are largely unchanged besides how we initiate some starting data for the system; this made ensuring that the program
 * works very easy as it quickly identified where functionality was broken.
 * 
 * @author Ada Clevinger
 * @version 2.0
 *
 */

class JUnitTest {
	
//---  Constant Values   ----------------------------------------------------------------------
	
	/** long value denoting the current system time, used to create Materials that have been checked out before the runtime so we can test overdue items*/
	private static final long NOW = System.currentTimeMillis();
	/** long value denoting the length of a day in milliseconds so that we can deduct time from the NOW constant value to make materials overdue*/
	private static final long DURATION_DAY = 86400000;
	/** String value to quickly reference a user's organization multiple times*/
	private static final String GOOFUS_ORG = "Twilight Town Disciplinary Committee";
	/** String value to quickly reference a user's organization multiple times*/
	private static final String GALLANT_ORG = "Frontier Militia";
	/** int value denoting how many days that a material has been checked out from the library*/
	private static final int OVERDUE_TIME_GALLANT = 2;
	/** int value denoting how many days that a material has been checked out from the library*/
	private static final int OVERDUE_TIME_GOOFUS = 150;
	
	/**
	 * 
	 * Test function that uses the Assert class (https://junit.org/junit4/javadoc/latest/index.html) to do a series of comparison that your IDE
	 * will usually have an interface for displaying to tell you whether any tests failed and why.
	 * 
	 * I use functions with boolean pass/fail return values to test whether operations were successful so I can use the 'assertTrue(some_boolean)' test that takes a boolean.
	 * There is also an assertFalse you can use.
	 * I use expected values from querying the Library with a getter method so I can use 'assertEquals(val, val2)' that takes two values of the same expected type and compares them.
	 * 
	 * Please read the API I linked above to see what the Assert class can do for you!
	 *
	 */
	
	@Test
	public void test() {

		MaterialTypeRegistry.registerEntry(MaterialTypeRegistry.compileMaterialType("Book",  4, 1));
		MaterialTypeRegistry.registerEntry(MaterialTypeRegistry.compileMaterialType("Journal",  2, 3));
		MaterialTypeRegistry.registerEntry(MaterialTypeRegistry.compileMaterialType("DVD",  1, 5));
		
		//Instantiate our class so that we can test its functionality
		Library instance = new Library();
		
		//Start populating our instance of Library with users and materials, testing that the functions succeed (return true)
		//Note: The functions return a boolean because I programmed them to do so in case of bad input, such as enrolling two users with the same id
		
		assertTrue(instance.enrolUser(420, GOOFUS_ORG));
		assertTrue(instance.enrolUser(7, GALLANT_ORG));

		assertTrue(instance.stockMaterial(2, "Book"));
		assertTrue(instance.stockMaterial(1, "DVD"));
		
		instance.getMaterialCatalogue().getMaterial(2).setCheckoutTime((NOW - OVERDUE_TIME_GALLANT * DURATION_DAY));
		instance.getMaterialCatalogue().getMaterial(1).setCheckoutTime((NOW - OVERDUE_TIME_GOOFUS * DURATION_DAY));
		
		instance.getUserCatalogue().getUser(420).addMaterial(1);
		instance.getUserCatalogue().getUser(7).addMaterial(2);
		
		
		//Test that the organizations have fines of 0 by default; currently no way to instantiate an organization with a fine
		
		assertEquals(instance.getOrganizationFine(GOOFUS_ORG), 0);
		assertEquals(instance.getOrganizationFine(GALLANT_ORG), 0);
		
		//Test that a user cannot return a material that they have not checked out
		
		assertFalse(instance.returnMaterial(420, 2));
		assertTrue(instance.returnMaterial(7, 2));
		
		//Test that after returning their book, Gallant's organization has no fine (it was checked out for two days)
		
		assertEquals(instance.getOrganizationFine(GALLANT_ORG), 0);
		
		//Test that a user returning a late item has incurred a fee to their organization
		
		assertTrue(instance.returnMaterial(420, 1));
		
		assertNotEquals(instance.getOrganizationFine(GOOFUS_ORG), 0);

		//Test that the fee is the exact amount we expect when a DVD has been out for 150 days; after a week it is late, so 143 days of overtime with $5 per day (715)
		
		assertEquals(instance.getOrganizationFine(GOOFUS_ORG), (OVERDUE_TIME_GOOFUS - 1 * 7) * 5);
	
		//Test that Gallant can checkout a new material but Goofus cannot due to oustanding fines
		
		assertTrue(instance.checkoutMaterial(7, 2));
		
		assertFalse(instance.checkoutMaterial(420, 1));
		
		//Test that an organization cannot overpay their fines, nor pay a negative amount.		
		
		assertFalse(instance.payFee(GOOFUS_ORG, 99999999));

		assertFalse(instance.payFee(GOOFUS_ORG, -5));
		
		//Test that the organization fine has been paid the correct amount
		
		assertTrue(instance.payFee(GOOFUS_ORG, 700));
		
		assertEquals(instance.getOrganizationFine(GOOFUS_ORG), 15);

		//Test that after paying a fine, Goofus can now check out a book
		
		assertTrue(instance.checkoutMaterial(420, 1));
		
		//And this starts up the CLI UI so you can interact with the program
		
		MainController mc = new MainController(instance);
		
	}

}
