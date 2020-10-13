package version3.controller;

import java.util.Scanner;

import version3.model.library.Library;
import version3.view.CommandLineInterface;

/**
 * This Class serves as the portion of the Controller functionality that manages the interpretation
 * of user input on how it should manipulate or request information from the Model. Due to the specificity
 * of a Command Line Interface for the UI, user input can be retrieved without having to be fielded by the
 * UI, creating a different relationship of the user to the Model View Controller architecture than you
 * would see with an application GUI whose visual elements would have to detect user interaction.
 * 
 * This Class is assigned a Library object by the MainController class for the purpose of containing a
 * reference to the Model that can be used to request or manipulate data.
 * 
 * CommandLineInterface, being a static class, can be accessed directly by this Class when instructing it
 * on displaying data. This Class does not provide the Model data, as CommandLineInterface requests that from
 * the ConvertModel Class.
 * 
 * While interpret is a bloated function, this could be fixed by dispensing responsibility across additional
 * functions or a complicated Class architecture that I just don't want to deal with right now, and which would
 * likely be overkill for the UI complexity.
 * 
 * @author Ada Clevinger
 * @version 3.0
 *
 */

public class InterpretInput {
	
//---  Instance Variables   -------------------------------------------------------------------
	
	/** Library object that provides a reference to the Model for the InterpretInput class to manipulate when the user input directs such to happen*/
	private Library library;
	
//---  Constructors   -------------------------------------------------------------------------
	
	/**
	 * Constructor for the InterpretInput class that assigns the provided Library to this object for
	 * manipulation and display-via-the-View purposes.
	 * 
	 * @param in - Library object providing a reference to access the project's Model for data and manipulation
	 */
	
	public InterpretInput(Library in) {
		library = in;
	}
	
//---  Operations   ---------------------------------------------------------------------------
	
	/**
	 * This massive function contains the rules for user interaction, prompting the View to display
	 * project or Model data to the user instructing them on what commands to give that this function
	 * can interpret for the purposes of manipulating the Model.
	 * 
	 * Interpreting input is a responsibility of the Controller.
	 * 
	 * Adding new user functionality is currently a case of adding a new 'case ...:' line to this
	 * function, though more complicated interactions may require being handled differently.
	 * 
	 * While we divorce the Model from the View, it is likely that implementing a different View would
	 * require the way that we receive user input to change and, thus, the Controller needing to rewire
	 * this function and potentially complicate the means of reacting to input. Them's the breaks.
	 * 
	 * @param sc - Scanner object used to request further input from the user if the interaction they desire requires further data from them to function
	 * @param input - String object representing the initial line of input from the user, though additional may be required via the Scanner object provided
	 */
	
	public void interpret(Scanner sc, String input) {
		boolean result;
		switch(input.toLowerCase()) {
			case "view materials": 
				CommandLineInterface.displayMaterials();
				break;
			case "view users": 
				CommandLineInterface.displayUsers();
				break;
			case "view organizations":
				CommandLineInterface.displayOrganizations();
				break;
			case "add material": 
				CommandLineInterface.displayText("Please provide the Material ID Number and the Material Type (Book, DVD, or Journal) in a single, space separated, line.");
				input = sc.nextLine();
				result = library.stockMaterial(Integer.parseInt(input.split(" ")[0]), input.split(" ")[1]);
				if(result) {
					CommandLineInterface.displayText("Material successfully stocked, please use the command 'view materials' to confirm.");
				}
				else {
					CommandLineInterface.displayText("System failed to add Material, please try again.");
				}
				break;
			case "add user": 
				CommandLineInterface.displayText("Please provide the User ID Number and their Organization in a single, space separated, line.");
				input = sc.nextLine();
				result = library.enrolUser(Integer.parseInt(input.split(" ")[0]), input.split(" ")[1]);
				if(result) {
					CommandLineInterface.displayText("User successfully enrolled, please use the command 'view users' to confirm.");
				}
				else {
					CommandLineInterface.displayText("System failed to add User, please try again.");
				}
				break;
			case "add organization": 
				CommandLineInterface.displayText("Please provide the Organization Name.");
				input = sc.nextLine();
				result = library.addOrganization(input.split(" ")[0]);
				if(result) {
					CommandLineInterface.displayText("Organization successfully added, please use the command 'view OrganizationRegistry' to confirm.");
				}
				else {
					CommandLineInterface.displayText("System failed to add Organization, please try again.");
				}
				break;
			case "checkout material": 
				CommandLineInterface.displayText("Please provide the User ID Number that is checking the Material out and Material ID Number of the Material being checked out in a single, space separated, line.");
				input = sc.nextLine();
				result = library.checkoutMaterial(Integer.parseInt(input.split(" ")[0]), Integer.parseInt(input.split(" ")[1]));
				if(result) {
					CommandLineInterface.displayText("Material successfully checked out, please use the command 'view materials' to confirm.");
				}
				else {
					CommandLineInterface.displayText("System failed to checkout Material, please try again.");
				}
				break;
			case "return material": 
				CommandLineInterface.displayText("Please provide the User ID Number that checked the Material out and Material ID Number of the Material being returned in a single, space separated, line.");
				input = sc.nextLine();
				result = library.returnMaterial(Integer.parseInt(input.split(" ")[0]), Integer.parseInt(input.split(" ")[1]));
				if(result) {
					CommandLineInterface.displayText("Material successfully returned, please use the command 'view materials' to confirm.");
				}
				else {
					CommandLineInterface.displayText("System failed to return Material, please try again.");
				}
				break;
			case "pay fee": 
				CommandLineInterface.displayText("Please provide the Organization name and the amount being paid to their account on separate lines.");
				try {
					result = library.payFee(sc.nextLine(), Integer.parseInt(sc.nextLine()));
					if(result) {
						CommandLineInterface.displayText("Fee successfully paid, please use the command 'view organizations' to confirm the account balance.");
					}
					else {
						CommandLineInterface.displayText("System failed to pay Fee, please try again.");
					}
				}
				catch(Exception e) {
					CommandLineInterface.displayText("System failed to pay fee: Invalid integer entry");
				}
				break;
			case "help": 
				CommandLineInterface.libraryInputCommands();
				break;
			default:
				CommandLineInterface.displayText("Input command was not recognized, please try again.");
				break;
		}
	}
	
}
