package version3.model.registry;

import java.util.HashMap;

/**
 * This class models a Registry which maps String names to RegistryItem objects, much like a Catalogue would do with
 * integer ids. However, this class uses static functions to access the contents, decentralizing it from being an object
 * owned by Library (as it was decided that the MaterialType objects were a universal presence and not strictly speaking
 * a thing unique to a particular Library.)
 * 
 * One concern I have realized is that multiple child classes of Registry may still use the same registry HashMap; this needs
 * testing and may require some reworking if it doesn't quite work as intended.
 * 
 * @author Ada Clevinger
 * @version 2.0
 *
 */

public abstract class Registry {

//---  Constants   ----------------------------------------------------------------------------
	
	/** static HashMap<<r>String, RegistryItem> object that stores RegistryItem-type objects with their names used to access them*/
	private static HashMap<String, RegistryItem> registry;
	
//---  Operations   ---------------------------------------------------------------------------
	
	/**
	 * Function that registers a new entry into the Registry-type object; it puts the provided RegistryItem-type
	 * object into the storage registry HashMap and uses the name received from the getName() function of RegistryItem
	 * for the key value.
	 * 
	 * @param in - RegistryItem-type object being added to this registry
	 */
	
	public static void registerEntry(RegistryItem in) {
		if(registry == null) {
			registry = new HashMap<String, RegistryItem>();
		}
		registry.put(in.getName(), in);
	}
	
//---  Getter Methods   -----------------------------------------------------------------------
	
	/**
	 * Getter method that requests the RegistryItem-type object associated with the provided name
	 * 
	 * @param name - String object representing the name associated with a RegistryItem-type object
	 * @return - Returns a RegistryItem-type object accessed using the provided name as the key value in the registry HashMap
	 */
	
	public static RegistryItem getEntry(String name) {
		return registry.get(name);
	}
	
}
