package version3.model.material;

import version3.model.catalogue.CatalogueItem;

/**
 * This class extends the CatalogueItem abstract class to model a Material object in the
 * Library model, allowing it to be stored in a Catalogue with an int id value as well as
 * keep track of its MaterialType and CheckoutTime.
 * 
 * Point of contention in the design is interpreting the Material as being available when
 * the instance variable CheckoutTime is null; could easily make CheckoutTime manage this
 * instead of playing around with null type.
 * 
 * @author Ada Clevinger
 * @version 2.0
 *
 */

public class Material extends CatalogueItem{

//---  Instance Variables   -------------------------------------------------------------------
	
	/** CheckoutTime object that keeps track of when this Material object was checked out; if null, the Material is available for checkout*/
	private CheckoutTime checkedOut;
	/** MaterialType object that provides information regarding the type of this Material, holding information on the type name, checkout time limit, and overdue fees*/
	private MaterialType type;
	
//---  Constructors   -------------------------------------------------------------------------
	
	/**
	 * Constructor for the Material class that uses the provided int id value to satisfy the constructor of its
	 * parent class, CatalogueItem, and assign the provided MaterialType object to this object
	 * 
	 * @param inId - int id value used to access this object in any encompassing Catalogue
	 * @param inType - MaterialType object representing what kind of Material this object is (Book, DVD, etc.)
	 */
	
	public Material(int inId, MaterialType inType) {
		super(inId);
		type = inType;
	}
	
//---  Operations   ---------------------------------------------------------------------------

	/**
	 * Function that signifies to the Material object that it has been checked out and must
	 * instantiate a new CheckoutTime object to keep track of how long it has been checked out.
	 * 
	 */
	
	public void checkoutMaterial() {
		checkedOut = new CheckoutTime();
	}
	
	/**
	 * Function that signifies that the Material object has been returned and its CheckoutTime
	 * instance variable can be set to null, signifying that it is not checked out.
	 * 
	 */
	
	public void returnMaterial() {
		checkedOut = null;
	}
	
	/**
	 * Function that requests whether or not this Material object is available to be checked out,
	 * calculated by checking whether or not the CheckoutTime instance variable is null or not.
	 * 
	 * @return - Returns a boolean value informing the caller of whether or not the Material can be checked out
	 */
	
	public boolean isAvailable() {
		return checkedOut == null;
	}

	/**
	 * Function that tests whether or not the Material object is overdue by comparing the elapsed number
	 * of days to the time limit described by the MaterialType object given to this Material object.
	 * 
	 * @return
	 */
	
	public boolean testOverdue() {
		return checkedOut.elapsedTimeDays() > type.getTimeLimit();
	}
	
//---  Getter Methods   -----------------------------------------------------------------------
	
	/**
	 * Getter method to request the name of this Material object's name
	 * 
	 * @return - Returns a String object representing the name of the MaterialType associated with this Material object
	 */
	
	public String getMaterialType() {
		return type.getName();
	}
	
	/**
	 * Getter method to request how many days have passed (rounded) since this Material was checked out
	 * 
	 * @return - Returns an int value representing how many days this Material has been checked out for, or -1 if it is available
	 */
	
	public int getDaysCheckedOut() {
		if(checkedOut == null) {
			return -1;
		}
		return checkedOut.elapsedTimeDays();
	}
	
	/**
	 * Getter method to request the time limit, in weeks, that a Material can be checked out for before it
	 * is overdue; this uses the MaterialType associated to this Material object to request the information.
	 * 
	 * @return - Returns an int value representing the time limit, in weeks, that this Material can be checked out for before it is late
	 */
	
	public int getOverdueLimit() {
		return type.getTimeLimit();
	}
	
	/**
	 * Getter method to request the fines incurred per day that this Material is found overdue for.
	 * 
	 * @return - Returns an int value representing the fines incurred per day that this Material is found overdue for.
	 */
	
	public int getOverdueCost() {
		return type.getOverdueCost();
	}
	
//---  Setter Methods   -----------------------------------------------------------------------
	
	/**
	 * Setter method to assign a specific time of checkout to this Material object.
	 * 
	 * This function is used for testing purposes to create a fictional history for this object; usual
	 * behaviours of the Material class should not require the usage of this function.
	 * 
	 * @param in - long value representing the time, in milliseconds, that this Material object should be designated as having been checked out at
	 */
	
	public void setCheckoutTime(long in) {
		checkedOut = new CheckoutTime(in);
	}

}
