package version3.model.organization;

import java.util.HashSet;
import version3.model.catalogue.CatalogueItem;

/**
 * This class extends the CatalogueItem abstract class to allow for Organizations in the Library model
 * to be stored in Catalogue-type objects; this stores the object that extends CatalogueItem with an
 * associated int value representing its unique id.
 * 
 * An Organization in this model is associated to Library Users by their integer id values, and collectively
 * possesses the fines accrued by its composite users.
 * 
 * @author Ada Clevinger
 * @version 2.0
 *
 */

public class Organization extends CatalogueItem{

//---  Instance Variables   -------------------------------------------------------------------
	
	/** String object representing the name of the Organization modeled by this object*/
	private String name;
	/** int value representing the total number of fines accrued by this Organization; though represented as a positive number, it represents debt*/
	private int fines;
	/** HashSet<<r>Integer> object containing the integer id values associated to Users in the Library model who belong to this Organization*/
	private HashSet<Integer> members;
	
//---  Constructors   -------------------------------------------------------------------------
	
	/**
	 * Constructor for the Organization class that initializes the instance variables of this class and
	 * passes the provided integer id value to its parent constructor to ready it for placement in a Catalogue-type object.
	 * 
	 * @param id - int value representing the id value used to recognize this Organization in a Catalogue-type object.
	 * @param nom - String object representing the name associated to this Organization
	 */
	
	public Organization(int id, String nom) {
		super(id);
		name = nom;
		fines = 0;
		members = new HashSet<Integer>();
	}
	
//---  Operations   ---------------------------------------------------------------------------
	
	/**
	 * Function that adds fines to this Organization as long as the provided value is a positive integer.
	 * 
	 * @param in - int value representing the amount of fines accrued by a user belonging to this Organization
	 * @return - Returns a boolean signifying whether or not the fines were able to be added to this Organization
	 */
	
	public boolean addFines(int in) {
		if(in <= 0) {
			return false;
		}
		fines += in;
		return true;
	}
	
	/**
	 * Function that removes fines from this Organization as long as the integer value is positive and does not overpay
	 * the Organization's fines.
	 * 
	 * @param in - int value representing the amount of fines being paid to reduce the Organization's debt
	 * @return - Returns a boolean signifying whether or not the amount of paid fines was legal and succesfully paid
	 */
	
	public boolean deductFines(int in) {
		if(in <= 0 || fines - in < 0) {
			return false;
		}
		fines -= in;
		return true;
	}
	
	/**
	 * Function that adds a user to the set associated to this Organization as represented by a unique int id
	 * 
	 * @param id - int value representing the userId associated to a User object in this Library model
	 */

	public void addUser(int id) {
		members.add(id);
	}
	
	/**
	 * Function that checks whether a user designated by an int id has been associated to this Organization
	 * 
	 * @param id - int value representing the userId of a User being tested for inclusion in this Organization object
	 * @return - Returns a boolean value signifying whether or not the specified User is a member of this Organization
	 */
	
	public boolean checkStatusMember(int id) {
		return members.contains(id);
	}
	
//---  Getter Methods   -----------------------------------------------------------------------
	
	/**
	 * Getter method that returns the String object representing the name of this Organization
	 * 
	 * @return - Returns a String object representing the name of this Organization
	 */
	
	public String getName() {
		return name;
	}
	
	/**
	 * Getter method that returns the int value representing the fines accrued by this Organization
	 * 
	 * @return - Returns an int value representing the fines associated to this Organization
	 */
	
	public int getFines() {
		return fines;
	}

}
