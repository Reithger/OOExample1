package version3.controller;

import java.util.Scanner;

import version3.model.library.Library;
import version3.view.CommandLineInterface;

public class InterpretInput {
	
	private Library library;
	
	public InterpretInput(Library in) {
		library = in;
	}
	
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
