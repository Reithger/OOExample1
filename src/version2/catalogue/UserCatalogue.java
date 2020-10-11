package version2.catalogue;

import java.util.ArrayList;
import version2.user.User;

/**
 * 
 * 
 * @author Borinor
 *
 */

public class UserCatalogue extends Catalogue{

//---  Constants   ----------------------------------------------------------------------------
	
	private final int MAX_CHECKOUT;
	
//---  Constructors   -------------------------------------------------------------------------
	
	public UserCatalogue(int maxCheckout) {
		MAX_CHECKOUT = maxCheckout;
	}
	
//---  Operations   ---------------------------------------------------------------------------
	
	public boolean validateUserCheckout(int id) {
		return getUser(id).verifyCanCheckOut();
	}

	public User compileUser(int userId, String organization, int maxCheckout) {
		return new User(userId, organization, maxCheckout);
	}
	
//---  Getter Methods   -----------------------------------------------------------------------
	
	public User getUser(int id) {
		return (User)getResource(id);
	}
	
	public ArrayList<User> getUsers(){
		ArrayList<User> out = new ArrayList<User>();
		for(int i : getKeys()) {
			out.add(getUser(i));
		}
		return out;
	}

}
