package version3.model.user;

import java.util.ArrayList;

import version3.model.catalogue.Catalogue;

/**
 * This class extends the Catalogue abstract class to provide a catalogue of the User objects that are associated
 * with the encompassing Library.
 * 
 * @author Ada Clevinger
 * @version 2.0
 *
 */

public class UserCatalogue extends Catalogue{

//---  Constants   ----------------------------------------------------------------------------
	
	/** Constant int value assigned during instantiation representing the maximum number of Materials that any User can checkout at one time*/
	private final int MAX_CHECKOUT;
	
//---  Constructors   -------------------------------------------------------------------------
	
	/**
	 * Constructor for the UserCatalogue class that takes an int value to instantiate the class constant MAX_CHECKOUT
	 * 
	 * @param maxCheckout - int value representing how many Materials any one User is permitted to have checked out at once
	 */
	
	public UserCatalogue(int maxCheckout) {
		super();
		MAX_CHECKOUT = maxCheckout;
	}
	
//---  Operations   ---------------------------------------------------------------------------
	
	/**
	 * Function that validates whether a User can checkout a Material or not, calling on the User to justify itself
	 * 
	 * Point of conflict: Should the User validate themselves, or should the UserCatalogue request information to do it itself?
	 * 
	 * @param id - int value representing the id associated with a User who is being tested for their ability to checkout a new Material
	 * @return - Returns a boolean value representing whether or not the denoted User is allowed to check out a new Material or not
	 */
	
	public boolean validateUserCheckout(int id) {
		return getUser(id).verifyCanCheckOut();
	}
	
	/**
	 * Function that instantiates and returns a new User object according to the arguments provided
	 * 
	 * @param userId - int value representing the id of the new User object (it being a subclass of CatalogueItem)
	 * @param organization - String object representing the Organization to which the new User object belongs
	 * @return - Returns a User object instantiated with the provided arguments
	 */

	public User compileUser(int userId, String organization) {
		return new User(userId, organization, MAX_CHECKOUT);
	}
	
//---  Getter Methods   -----------------------------------------------------------------------
	
	/**
	 * Getter method that requests a User object denoted by the provided int id value
	 * 
	 * @param id - int value representing the id value of a User object assumed to be stored by this UserCatalogue object
	 * @return - Returns a User object retrieved using the provided int id value
	 */
	
	public User getUser(int id) {
		return (User)getResource(id);
	}
	
	/**
	 * Getter method that requests a list of the User objects stored by this UserCatalogue.
	 * 
	 * @return - Returns an ArrayList<<r>String> object representing the Users that this UserCatalogue object keeps track of.
	 */
	
	public ArrayList<User> getUsers(){
		ArrayList<User> out = new ArrayList<User>();
		for(int i : getKeys()) {
			out.add(getUser(i));
		}
		return out;
	}

}
