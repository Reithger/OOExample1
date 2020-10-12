package version3.model.registry;

import version3.model.material.MaterialType;

/**
 * This class extends the abstract class Registry to provide a repository of data
 * that pairs String key values to RegistryItem-type objects; this class specifically
 * provides storage of MaterialType objects in a static way.
 * 
 * This class is not instantiated because the information is considered universal, unlike
 * Catalogues that are specific to a library.
 * 
 * @author Ada Clevinger
 * @version 2.0
 *
 */

public class MaterialTypeRegistry extends Registry{

//---  Operations   ---------------------------------------------------------------------------
	
	/**
	 * Function that generates a MaterialType object using the provided values to instantiate it and
	 * return the object to the caller.
	 * 
	 * @param nom - String object representing the name of the generated MaterialType object
	 * @param overdue - int value representing the time, in weeks, after being checked out that the Material object is considered overdue
	 * @param cost - int value representing the cost incurred per day that the Material object associated to the MaterialType is overdue
	 * @return - Returns a MaterialType object instantiated using the arguments provided to this function
	 */
	
	public static MaterialType compileMaterialType(String nom, int overdue, int cost) {
		return new MaterialType(nom, cost, overdue);
	}
	
//---  Getter Methods   -----------------------------------------------------------------------
	
	/**
	 * Getter method that returns the MaterialType object in the registry's stored data that has
	 * been associated to the String name provided
	 * 
	 * @param nom - String object representing the name of the RegistryItem (cast to MaterialType) to be returned
	 * @return - Returns the MaterialType object designated by the provided String name
	 */
	
	public static MaterialType getMaterialType(String nom) {
		return (MaterialType)getEntry(nom);
	}
	
}
