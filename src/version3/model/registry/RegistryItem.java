package version3.model.registry;

/**
 * This abstract class models RegistryItem objects, those being objects that can be stored in
 * a Registry-type object with an associated String key value to access the object.
 * 
 * @author Ada Clevinger
 * @version 2.0
 *
 */

public abstract class RegistryItem {

//---  Instance Variables   -------------------------------------------------------------------
	
	/** String object representing the name used to access this object within its encompassing Registry-type object*/
	private String name;
	
//---  Constructors   -------------------------------------------------------------------------
	
	/**
	 * Constructor for the RegistryItem class that assigns the String name to this object
	 * 
	 * @param nom - String object representing the name to be associated to this RegistryItem-type object, used as the Key for accessing it in any Registry it is put in
	 */
	
	public RegistryItem(String nom) {
		name = nom;
	}
	
//---  Getter Methods   -----------------------------------------------------------------------
	
	/**
	 * Getter method that returns the name associated to this RegistryItem-type object
	 * 
	 * @return - Returns a String representing the name associated to this RegistryItem-type object
	 */
	
	public String getName() {
		return name;
	}
	
}
