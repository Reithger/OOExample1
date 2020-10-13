package version3.controller.conversion;

import java.util.ArrayList;

/**
 * This Class serves as a support Class to provide a custom data structure that can be passed
 * between the View and the Controller, allowing the Controller to convert the Model into a
 * consistent format that the View knows how to read.
 * 
 * It's kind of a glorified ArrayList<<r>String> with a currently unused 'type' variable, but
 * it's nicer to look at and use in our context as well as providing the starting grounds for
 * any future features we may want to make things easier.
 * 
 * I wanted it and it doesn't hurt anything to use. That can be your justification sometimes.
 * 
 * That said, maybe this could be an abstract class for which there would be UserDisplay,
 * MaterialDisplay, OrganizationDisplay, etc. classes to hold the rules for displaying each,
 * but that expands very quickly into a lot of Classes and the GUI is not comlpicated enough
 * to require that. Complexity in architecture is used to make the logic of your project simpler.
 * 
 * @author Ada Clevinger
 * @version 3.0
 *
 */

public class DisplayFormat {
	
//---  Instance Variables   -------------------------------------------------------------------

	/** String value representing what type of DisplayFormat this is for interpretation by the View; meant to give the data context, though it is not needed at the moment*/
	private String type;
	/** ArrayList<<r>String> object that contains any relevant information that the View will need to display Model information (after converting it to a String)*/
	private ArrayList<String> data;
	
//---  Constructors   -------------------------------------------------------------------------
	
	/**
	 * Constructor for the Displayformat class that takes the DisplayFormat's type a list of
	 * the String data that this will hold for user by the View.
	 * 
	 * @param typ - String object representing the type of DisplayFormat that this object is
	 * @param inDat - ArrayList<<r>String> object containing information for the View to use
	 */
	
	public DisplayFormat(String typ, ArrayList<String> inDat) {
		type = typ;
		data = inDat;
	}
	
//---  Getter Methods   -----------------------------------------------------------------------
	
	/**
	 * Getter method that requests the type of this DisplayFormat object
	 * 
	 * @return - Returns a String object representing the type of this DisplayFormat object
	 */
	
	public String getType() {
		return type;
	}
	
	/**
	 * Getter method that requests the data that this DisplayFormat holds representing some
	 * amount of Model information for the View to use when drawing the GUI.
	 * 
	 * @return - Returns an ArrayList<<r>String> object containing information from the Model converted to a generic String format
	 */
	
	public ArrayList<String> getData(){
		return data;
	}
	
	/**
	 * Getter method that requests the String data stored at the index designated by the provided
	 * integer index value. An illegal index returns a default String: "Invaild Access".
	 * 
	 * @param ind - int value representing the index in the list of data stored by this DisplayFormat object to return
	 * @return - Returns a String object located at the index designated by the provided integer index value.
	 */
	
	public String getIndexData(int ind) {
		if(ind >= data.size()) {
			return "Invalid Access";
		}
		return data.get(ind);
	}
	
}
