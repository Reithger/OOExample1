package version3.view;

import version3.controller.conversion.ConvertModel;
import version3.controller.conversion.DisplayFormat;

/**
 * This static class handles the UI component of the project, being given a Library object whose information
 * it displays as directed to by user input.
 * 
 * Currently tightly coupled to the Model because it directly references/uses objects from the Model; proper
 * Model - View - Controller architecture would place a Controller interface between this and the Model, whereby
 * the Controller processes the Model into a generic data format (like, Strings) that the View is programmed to interpret.
 * 
 * Also tightly coupled because this class directly manipulates the state of the Library object
 * 
 * @author Ada Clevinger
 * @version 3.0
 *
 */

public class CommandLineInterface {

//---  Instance Variables   -------------------------------------------------------------------
	
	private static ConvertModel reference;
	
//---  Operations   ---------------------------------------------------------------------------
	
	public static void assignConvertModel(ConvertModel in) {
		reference = in;
	}
	
	public static void start() {
		System.out.println("Welcome to the Library!\nPlease use the following commands.");
		libraryInputCommands();
	}
	
	public static void displayUsers() {
		System.out.println("Users Registered to Library:");
		for(DisplayFormat d : reference.getUserData()) {
			displayUser(d);
		}
	}
	
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
	
	public static void displayMaterials() {
		System.out.println("Materials in Library:");
		for(DisplayFormat d : reference.getMaterialData()) {
			displayMaterial(d);
		}
	}
	
	private static void displayMaterial(DisplayFormat d) {
		String elapsed = d.getIndexData(2);
		String checkout = ", Status: " + (elapsed == null ? "Available to Check Out" : ("Checked out for " + elapsed + " days."));
		System.out.println(" - ID: " + d.getIndexData(0) + ", Type: " + d.getIndexData(1) + checkout);
	
	}
	
	public static void displayOrganizations() {
		System.out.println("Organizations Registered to Library");
		for(DisplayFormat d : reference.getOrganizationData()) {
			displayOrganization(d);
		}
	}
	
	private static void displayOrganization(DisplayFormat d) {
		System.out.println(" - ID: " + d.getIndexData(0) + ", Title: " + d.getIndexData(1) + ", Fines: $" + d.getIndexData(2));
	}
	
	public static void displayText(String text) {
		System.out.println(text);
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
