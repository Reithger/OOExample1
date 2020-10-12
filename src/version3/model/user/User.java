package version3.model.user;

import version3.model.catalogue.CatalogueItem;

/**
 * This class models a User in the Library, having an id value for reference in the UserCatalogue through
 * extending the CatalogueItem abstract class and keeping track of the User's Organization and checked out
 * Materials.
 * 
 * Note: primitive data types are used here for the Materials and Organization because while a User is associated
 * to these, it doesn't need any info about them or for them to do any tasks for it; these are just pieces of data
 * that the Library uses for management, they are not active things in this model.
 * 
 * @author Ada Clevinger
 * @version 2.0
 *
 */

public class User extends CatalogueItem{

//---  Instance Variables   -------------------------------------------------------------------
	
	/** String object representing the name of an Organization to whom this User belongs*/
	private String organization;
	/** int[] object containing the int id values of Materials that this User has checked out; the size of the array is provided at instantiation of the User object*/
	private int[] matIds;
	/** int value representing how many Materials have been checked out by the User; it is used for indexing the fixed-size array matIds and not observing empty indexes*/
	private int numOut;
	
//---  Constructors   -------------------------------------------------------------------------
	
	/**
	 * Constructor for User objects that provides the given int inId to the super constructor of CatalogueItem
	 * and instantiates the Organization and int[] of Material ids with the provided arguments.
	 * 
	 * @param inId - int value representing the id value to be associated to this User (extending CatalogueItem for inclusion in a Catalogue via an integer id)
	 * @param inOrg - String object representing the name of the Organization to whom this User belongs
	 * @param maxMaterials - int value representing the maximum number of Materials this User can have checked out at one time
	 */
	
//---  Constructors   -------------------------------------------------------------------------
	
	public User(int inId, String inOrg, int maxMaterials) {
		super(inId);
		organization = inOrg;
		numOut = 0;
		matIds = new int[maxMaterials];
	}
	
//---  Operations   ---------------------------------------------------------------------------
	
	/**
	 * Function that takes a Material's int id value and puts it at the first empty spot in matIds.
	 * 
	 * While an error could be thrown by overflowing the int[], part of the process to add a material
	 * is verifying a number of conditions, one of which is that the User will not exceed their limit
	 * of Materials.
	 * 
	 * @param in - int value representing the id of the new Material to be associated to this User
	 */
	
//---  Operations   ---------------------------------------------------------------------------
	
	public void addMaterial(int in) {
		matIds[numOut++] = in;
	}
	
	/**
	 * Function that removes a Material from the list associated to this User by searching for the
	 * Material ID in matIds and removing it, then shuffling the contents of matIds to fill the empty
	 * spot if such was made.
	 * 
	 * @param matId - int value representing the id value associated to a Material whose reference is being removed from this User.
	 */
	
	public void removeMaterial(int matId) {
		boolean found = false;
		for(int i = 0; i < matIds.length - 1; i++) {
			found = found || matIds[i] == matId;
			matIds[i] = found ? matIds[i + 1] : matIds[i];
		}
		matIds[matIds.length - 1] = -1;
		numOut--;
	}
	
	/**
	 * Function that verifies whether or not this User can check out a material on the grounds of
	 * them having checked out too many Materials already; the limit for this is set at the time of
	 * the User object's instantiation.
	 * 
	 * @return - Returns a boolean value signifying whether or not the User can check out a material based on the grounds described previously.
	 */
	
	public boolean verifyCanCheckOut() {
		return getCheckedOutCount() < matIds.length;
	}
	
//---  Getter Methods   -----------------------------------------------------------------------
	
	 /**
	  * Getter method that requests the name of the Organization to which this User object is associated with
	  * 
	  * @return - Returns a String value representing the Organization to which this User object is associated with
	  */
	
//---  Getter Methods   -----------------------------------------------------------------------
	
	public String getOrganization() {
		return organization;
	}
	
	/**
	 * Getter method that requests an int[] containing the int id values of Materials that have been associated to
	 * this User object; this function takes the subsection of matIds which contain valid entries as designated by
	 * their index in relation to the numOut instance variable (how many Materials have been checked out?)
	 * 
	 * @return - Returns an int[] containing the int id values belonging to Materials that this User has checked out
	 */
	
	public int[] getCheckedOut() {
		int[] out = new int[getCheckedOutCount()];
		for(int i = 0; i < out.length; i++) {
			out[i] = matIds[i];
		}
		return out;
	}
	
	/**
	 * Function that queries this User object for whether or not the int[] of id values representing
	 * Materials associated to it contains the matId int value provided as an argument. Basically,
	 * has this User checked out the Material designated by the provide int id value?
	 * 
	 * @param matId - int id value associated to a Material object
	 * @return - Returns a boolean value signifying whether or not the User has checked out the Material associated to the provided Material id value
	 */

	public boolean hasMaterial(int matId) {
		boolean found = false;
		for(int i = 0; i < getCheckedOutCount(); i++) {
			found = found || matIds[i] == matId;
		}
		return found;
	}

	/**
	 * Getter method that requests the number of Materials that this User object has checked out, using
	 * the numOut instance variable.
	 * 
	 * @return - Returns an int value representing the number of Materials that this User has checked out
	 */
	
	public int getCheckedOutCount() {
		return numOut;
	}
	
}
