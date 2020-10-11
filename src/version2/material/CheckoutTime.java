package version2.material;

/**
 * This class models the measure of time for how long a Material in the Library has been checked out for.
 * 
 * @author Ada Clevinger
 * @version 2.0
 *
 */

public class CheckoutTime {

//---  Constants   ----------------------------------------------------------------------------
	
	/** long value denoting how many milliseconds are in a day for time passage calculations*/
	public static final long DURATION_DAY = 86400000;	
	
//---  Instance Variables   -------------------------------------------------------------------
	
	/** long value denoting what time, in milliseconds (obtained from System.currentTimeMillis), an object was checked out at*/
	private long checkedOutTime;
	
//---  Constructors   -------------------------------------------------------------------------
	
	/**
	 * Constructor for the CheckoutTime class that takes a long value representing the time, in milliseconds, to designate
	 * the associated object as having been checked out at.
	 * 
	 * @param in - long value representing a time in milliseconds
	 */
	
	public CheckoutTime(long in) {
		checkedOutTime = in;
	}
	
	/**
	 * Constructor for the CheckoutTime class that defaults the checkout time to the current System time in milliseconds (using
	 * System.currentTimeMillis())
	 * 
	 */
	
	public CheckoutTime() {
		checkedOutTime = System.currentTimeMillis();
	}
	
//---  Operations   ---------------------------------------------------------------------------
	
	/**
	 * Function that calculates the elapsed number of days since this object was initialized (being given a time in milliseconds at that time).
	 * 
	 * @return - returns an int value representing the number of days (rounded) since this CheckoutTime object was initialized with a time.
	 */
	
	public int elapsedTimeDays() {
		return (int)((System.currentTimeMillis() - checkedOutTime) / DURATION_DAY);
	}
	
}
