package version2.catalogue;

/**
 * This abstract class models the basic behavior of CatalogueItem-type objects, which have int id values
 * for reference in a registry contained by a Catalogue-type object.
 * 
 * @author Ada Clevinger
 * @version 2.0
 *
 */

public abstract class CatalogueItem {
	
//---  Instance Variables   -------------------------------------------------------------------
	
	/** int value representing a hopefully unique id for use in a Catalogue of similar objects*/
	private int id;
	
//---  Constructors   -------------------------------------------------------------------------
	
	/**
	 * Constructor for the abstract class CatalogueItem that assigns the provided int id value to
	 * this object.
	 * 
	 * @param in - int value representing the id for the object extending this abstract class
	 */
	
	public CatalogueItem(int in) {
		id = in;
	}
	
//---  Getter Methods   -----------------------------------------------------------------------
	
	/**
	 * Getter method to request the int id value that identifies an object extending this abstract
	 * class.
	 * 
	 * @return - Returns an int value representing the id for the object extending this abstract class
	 */
	
	public int getId() {
		return id;
	}
	
}
