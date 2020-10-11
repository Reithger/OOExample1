package version2.catalogue;

import java.util.Collection;
import java.util.HashMap;

/**
 * 
 * Abstract class that sets the basis for Catalogue-type objects; this provides the basic functionality
 * for storing a catalogue of objects that extend the CatalogueItem abstract class (requiring them to
 * have an integer id and be able to return that value; this allows any object stored in the Catalogue
 * to be accessed in the same way).
 * 
 * Catalogues are used by the Library to map various stored data items to an integer id, such as Users
 * or Materials.
 * 
 * @author Ada Clevinger
 * @version 2.0
 *
 */

public abstract class Catalogue{

//---  Instance Variables   -------------------------------------------------------------------
	
	/** HashMap<<r>Integer, CatalogueItem> object matching CatalogueItems to their integer id values for regularized access*/
	private HashMap<Integer, CatalogueItem> registry;
	
//---  Constructors   -------------------------------------------------------------------------
	
	/**
	 * Constructor for the Catalogue abstract class that initializes the registry instance variable.
	 * 
	 * Abstract classes cannot be instantiated, but their constructors can be called by children
	 * classes through the super() call in that child class' constructor.
	 * 
	 */
	
	public Catalogue() {
		registry = new HashMap<Integer, CatalogueItem>();
	}
	
//---  Operations   ---------------------------------------------------------------------------
	
	/**
	 * Function that adds a CatalogueItem-type object to the registry HashMap; because CatalogueItem
	 * objects have the function getId(), any child class is assumed to satisfy the requirements to
	 * be used in this class.
	 * 
	 * @param in - CatalogueItem-extending object being added to the registry stored by this Catalogue object
	 */
	
	public void addResource(CatalogueItem in) {
		registry.put(in.getId(), in);
	}
	
//---  Getter Methods   -----------------------------------------------------------------------
	
	/**
	 * Function that returns the CatalogueItem stored by this class associated to the provided int id value.
	 * 
	 * @param id - int value representing the id associated to a CatalogueItem to be returned
	 * @return - Returns a CatalogueItem-extending object associated to the id provided to the function
	 */
	
	public CatalogueItem getResource(int id) {
		return registry.get(id);
	}
	
	/**
	 * Function that provides the Key Set of values in the HashMap registry so the calling function
	 * can iterate over its contents.
	 * 
	 * @return - Returns a Collection<<r>Integer> object containing the Key Set of this Catalogue's registry
	 */

	public Collection<Integer> getKeys(){
		return registry.keySet();
	}
	
}
