package version3.view;

import version3.controller.conversion.ConvertModel;
import version3.controller.conversion.DisplayFormat;

/**
 * This static class handles the UI component of the project, greatly changed from version 2.
 * 
 * Responsibility for interpreting input has been removed now that there is a Controller to provide
 * that functionality, and instead of there being a main loop in this View it instead serves as a
 * repository of functions containing the rules for displaying Model information to the user, which
 * the InterpretInput Class in the Controller makes use of.
 * 
 * The information necessary to display Model data is received through the ConvertModel class which
 * converts Model objects to a generic DisplayFormat object type which this Class knows how to access
 * and assumes certain information compositions in what it is given (with handling of incorrect
 * assumptions by the DisplayFormat Class).
 * 
 * This particular format of a View that is entirely reactive to Controller requests for display is
 * not a norm in View designs; a View that relies on an Application Window will likely have some kind
 * of Timer to refresh the display frequently (especially if there are animations in your project), and
 * would update its generic form of Model data on a semi-frequent basis or when prompted to update itself.
 * 
 * That should not be taken as a prescription on how to manage your View - Controller relationship in
 * your project based on your View format, CLI Views are just kinda weird because of how the input is
 * divorced from the display and its visual display updates very statically (as in static vs. continuous,
 * not static as in memory allocation).
 * 
 * @author Ada Clevinger
 * @version 3.0
 *
 */

public class CommandLineInterface {

//---  Instance Variables   -------------------------------------------------------------------
	
	/** ConvertModel object used to access Model data when necessary, this Class acting as an intermediary to provide a generic data format (DisplayFormat)*/
	private static ConvertModel reference;
	
//---  Operations   ---------------------------------------------------------------------------

	/**
	 * Function that commands this class to display the text to the user that they see
	 * when the program starts.
	 * 
	 */
	
	public static void start() {
		System.out.println("Welcome to the Library!\nPlease use the following commands.");
		libraryInputCommands();
	}
	
	//-- Display Methods  -------------------------------------
	
	/**
	 * Function that commands this class to display information regarding the Users in
	 * the Library model, requesting data via the ConvertModel Class.
	 * 
	 */
	
	public static void displayUsers() {
		System.out.println("Users Registered to Library:");
		for(DisplayFormat d : reference.getUserData()) {
			displayUser(d);
		}
	}
	
	/**
	 * Function that takes a DisplayFormat object compiled from a User object from the Model
	 * and displays its relevant information to the user.
	 * 
	 * @param d - DisplayFormat object containing the relevant information necessary to display User data to the user
	 */
	
	private static void displayUser(DisplayFormat d) {
		System.out.print(" - ID: " + d.getIndexData(0) + ", Organization: \"" + d.getIndexData(1) + "\", Checked Out Materials: ");
		if(d.getData().size() == 2) {
			System.out.println("None");
		}
		else {
			for(int i = 2; i < d.getData().size(); i++) {
				displayMaterial(reference.getHeldDisplayFormat(d.getIndexData(i)));
			}
		}
	}
	
	/**
	 * Function that commands this class to display information regarding the Materials in
	 * the Library model, requesting data via the ConvertModel Class.
	 * 
	 */
	
	public static void displayMaterials() {
		System.out.println("Materials in Library:");
		for(DisplayFormat d : reference.getMaterialData()) {
			displayMaterial(d);
		}
	}
	
	/**
	 * Function that takes a DisplayFormat object compiled from a Material object from the Model
	 * and displays its relevant information to the user.
	 * 
	 * @param d - DisplayFormat object containing the relevant information necessary to display Material data to the user
	 */
	
	private static void displayMaterial(DisplayFormat d) {
		String elapsed = d.getIndexData(2);
		String checkout = ", Status: " + (elapsed == null ? "Available to Check Out" : ("Checked out for " + elapsed + " days."));
		System.out.println(" - ID: " + d.getIndexData(0) + ", Type: " + d.getIndexData(1) + checkout);
	
	}
	
	/**
	 * Function that commands this class to display information regarding the Organizations in
	 * the Library model, requesting data via the ConvertModel Class.
	 * 
	 */
	
	public static void displayOrganizations() {
		System.out.println("Organizations Registered to Library");
		for(DisplayFormat d : reference.getOrganizationData()) {
			displayOrganization(d);
		}
	}
	
	/**
	 * Function that takes a DisplayFormat object compiled from a Organization object from the Model
	 * and displays its relevant information to the user.
	 * 
	 * @param d - DisplayFormat object containing the relevant information necessary to display Organization data to the user
	 */
	
	private static void displayOrganization(DisplayFormat d) {
		System.out.println(" - ID: " + d.getIndexData(0) + ", Title: " + d.getIndexData(1) + ", Fines: $" + d.getIndexData(2));
	}
	
	/**
	 * Function that displays the provided text to the user; it's just a wrapper of System.out.println,
	 * but I wanted to ensure all display-related behavior was firmly in the View.
	 * 
	 * Also, if the UI changes, then the Controller can stay put while this function handles the changes
	 * (hopefully, if it gets more complex you might have to trash most everything).
	 * 
	 * @param text - String object representing the text that should be displayed to the user via the Terminal.
	 */
	
	public static void displayText(String text) {
		System.out.println(text);
	}

//---  Setter Methods   -----------------------------------------------------------------------
	
	/**
	 * Setter method that assigns the provided ConvertModel object to this class statically, providing
	 * access to data from the Model.
	 * 
	 * @param in - ConvertModel object providing access to Model data
	 */
	
	public static void assignConvertModel(ConvertModel in) {
		reference = in;
	}
	
//---  Helper Methods   -----------------------------------------------------------------------
	
	/**
	 * This helper function just automates some output to make it neater to print the user direction text.
	 * 
	 */
	
	public static void libraryInputCommands() {
		System.out.println("View:\n - Users\n - Materials\n - Organizations");
		System.out.println("Add:\n - User\n - Material\n - Organization");
		System.out.println("Checkout/Return Material");
		System.out.println("Pay Fee");
		System.out.println("For example, the command 'View Materials' would display the Materials in the Library.");
		System.out.println("Type 'help' to see these commands again.");
	}

}
