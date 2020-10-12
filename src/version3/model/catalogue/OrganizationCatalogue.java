package version3.model.catalogue;

import java.util.ArrayList;
import java.util.HashMap;
import version3.model.organization.Organization;

/**
 * This class extends the Catalogue class to provide Catalogue services for managing Organization
 * objects. It also keeps track of the Library's maximum permissable fines for an Organization before
 * its members can no longer check out new Materials.
 * 
 * This class also permits String access of Organizations despite being a Catalogue and its composite
 * objects, Organizations, extending the CatalogueItem abstract class (requiring int id values to access).
 * To permit this, the class assigns int id values to each new Organization, not requiring any management at
 * a higher level of this complication.
 * 
 * @author Ada Clevinger
 * @version 2.0
 *
 */

public class OrganizationCatalogue extends Catalogue {

//---  Instance Variables   -------------------------------------------------------------------
	
	/** HashMap<<r>String, Integer> object that maps the String name of Organization objects to their associated int id for use in the Catalogue*/
	private HashMap<String, Integer> idMapping;
	/** int value representing the maximum permissable fines that an Organization can have before their composite members can no longer check out Materials*/
	private int maxFines;
	/** int value representing how many Organizations have been added to this Catalogue to ensure unique id values for each added Organization*/
	private int countOrganizations;
	
//---  Constructors   -------------------------------------------------------------------------
	
	/**
	 * Constructor for the OrganizationCatalogue class that takes an integer value to instantiate its
	 * maximum fines limit for all composite Organizations.
	 * 
	 * @param in - int value representing the maximum fines an Organization can accrue before its composite members can no longer checkout Materials
	 */
	
	public OrganizationCatalogue(int in) {
		super();
		maxFines = in;
		idMapping = new HashMap<String, Integer>();
	}
	
//---  Operations   ---------------------------------------------------------------------------
	
	/**
	 * Function that verifies that the User signified by a userId is allowed to checkout a Material
	 * on the grounds of their encompassing Organization not having accrued too many fines.
	 * 
	 * @param userId - int value representing the id of the User whose Organization is being queried
	 * @return - Returns a boolean signifying whether the User represented by the int id value is allowed to checkout Materials based on their Organization not having accrued too many fines
	 */
	
	public boolean verifyOrganizationCheckout(int userId) {
		Organization use = getOrganizationByUser(userId);
		if(use == null) {
			return false;
		}
		return use.getFines() <= maxFines;
	}
	
	/**
	 * Function that attaches a User represented by an int id value to an Organization, permitting a reverse look-up
	 * whereby Organizations can be accessed by finding which contains a provided int id value.
	 * 
	 * @param userId - int value representing the id of a User that will be added to an Organization
	 * @param nom - String object representing the name of the Organization to which the user represented by the provided int id value will be added to
	 */
	
	public void addUserToOrganization(int userId, String nom) {
		getOrganization(nom).addUser(userId);
	}
	
	/**
	 * Function that takes the necessary arguments to instantiate an Organization object and
	 * return it to the calling function.
	 * 
	 * This lets the calling class avoid importing Organization if used carefully.
	 * 
	 * @param nom - String object representing the name of the Organization being created
	 * @return - Returns an Organization object instantiated using the provided arguments
	 */
	
	public Organization compileOrganization(String nom) {
		idMapping.put(nom, countOrganizations);
		return new Organization(countOrganizations++, nom);
	}

//---  Getter Methods   -----------------------------------------------------------------------
	
	/**
	 * Getter method that requests a list of the Organizations that this OrganizationCatalogue contains
	 * 
	 * @return - Returns an ArrayList<<r>Organization> object containing all of the Organizations within this OrganizationCatalogue
	 */
	
	public ArrayList<Organization> getOrganizations(){
		ArrayList<Organization> out = new ArrayList<Organization>();
		for(int i : getKeys()) {
			out.add((Organization)getResource(i));
		}
		return out;
	}
	
	/**
	 * Getter method that finds and returns an Organization object by finding which one has a provided int id representing
	 * a User assigned as one of its composite members.
	 * 
	 * @param userId - int value representing the id value of a User
	 * @return - Returns the Organization object that has the User represented as an int id value as a composite member
	 */

	public Organization getOrganizationByUser(int userId) {
		for(String s : idMapping.keySet()) {
			if(getOrganization(s).checkStatusMember(userId)) {
				return getOrganization(s);
			}
		}
		return null;
	}
	
	/**
	 * Getter method that returns an Organization object that has the name represented by the given String object
	 * 
	 * @param nom - String object representing the Organization name that is used as a key value to retrieve the Organization from the Catalogue
	 * @return - Returns an Organization object whose name matches the String object provided as an argument
	 */
	
	public Organization getOrganization(String nom) {
		if(idMapping.get(nom) == null) {
			return null;
		}
		return (Organization)getResource(idMapping.get(nom));
	}

}
