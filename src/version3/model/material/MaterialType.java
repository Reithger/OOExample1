package version3.model.material;

import version3.model.registry.RegistryItem;

/**
 * This class extends the RegistryItem abstract class to model a type of Material generically; at construction, the
 * details of the Material Type's name, overdue cost, and time limit are assigned.
 * 
 * Extending the abstract class RegistryItem allows this class to be used by Registry-type classes and stored with
 * a paired String key value (here using the Material Type's name as the key).
 * 
 * The usage of this class is to generate a Registry of different Material Types that can be re-used when generating Material
 * objects; the static class MaterialTypeRegistry is the repository for unique MaterialType objects.
 * 
 * @author Ada Clevinger
 * @version 2.0
 *
 */

public class MaterialType extends RegistryItem {

//---  Instance Variables   -------------------------------------------------------------------
	
	/** int value representing the cost, per day, that is accrued by returning the Material after its due date*/
	private int overdueCost;
	/** int value representing the number of weeks that the Material can be checked out for before it is late and incurs an overdue charge*/
	private int timeLimit;
	
//---  Constructors   -------------------------------------------------------------------------
	
	/**
	 * Constructor for the MaterialType class that assigns this Material Type's name, the cost of it
	 * being overdue, and the time limit (in weeks) before the Material is considered overdue by the library.
	 * 
	 * @param typ - String object representing the name of the Material Type modeled by this class (such as "Book" or "DVD")
	 * @param cost - int value representing the daily cost incurred each day that the Material of this type is overdue for
	 * @param limit - int value representing the number of weeks that the Material of this type can be checked out for being costs are incurred
	 */
	
	public MaterialType(String typ, int cost, int limit) {
		super(typ);
		overdueCost = cost;
		timeLimit = limit;
	}
	
//---  Getter Methods   -----------------------------------------------------------------------
	
	/**
	 * Getter method to request the cost incurred each day that a Material of this type is overdue for
	 * 
	 * @return = Returns an int value representing the cost incurred each day that a Material of this type is overdue for
	 */
	
	public int getOverdueCost() {
		return overdueCost;
	}
	
	/**
	 * Getter method to request the time limit, in weeks, before a Material of this type is considered overdue after being checked out
	 * 
	 * @return - Returns an int value representing the time limit, in weeks, before a Material of this type is considered overdue after being checked out
	 */
	
	public int getTimeLimit() {
		return timeLimit;
	}
	
}
