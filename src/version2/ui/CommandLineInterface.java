package version2.ui;

import java.util.Scanner;

import version2.library.Library;
import version2.material.Material;
import version2.organization.Organization;
import version2.user.User;

public class CommandLineInterface {

//---  Instance Variables   -------------------------------------------------------------------
	
	private static Library library;

//---  Operations   ---------------------------------------------------------------------------
	
	/**
	 * This function creates a UI in the command line terminal for the purposes of showing Model View Controller
	 * architecture in subsequent updates to this collection of code.
	 * 
	 */
	
	public static void displayLibrary() {
		if(library == null) {
			return;
		}
		Scanner sc = new Scanner(System.in);
		boolean result;
		System.out.println("Welcome to the Library!\nPlease use the following commands.");
		libraryInputCommands();
		String input = sc.nextLine();
		while(!input.equals("end")) {
			switch(input.toLowerCase()) {
				case "view materials":
					System.out.println("Materials in Library:");
					for(Material m : library.getMaterialCatalogue().getMaterials()) {
						printMaterial(m);
					}
					break;
				case "view users":
					System.out.println("Users Registered to Library:");
					for(User u : library.getUserCatalogue().getUsers()) {
						printUser(u);
					}
					break;
				case "view organizations":
					System.out.println("Organizations Registered to Library");
					for(Organization o : library.getOrganizationCatalogue().getOrganizations()) {
						printOrganization(o);
					}
					break;
				case "add material":
					System.out.println("Please provide the Material ID Number and the Material Type (Book, DVD, or Journal) in a single, space separated, line.");
					input = sc.nextLine();
					result = library.stockMaterial(Integer.parseInt(input.split(" ")[0]), input.split(" ")[1]);
					if(result) {
						System.out.println("Material successfully stocked, please use the command 'view materials' to confirm.");
					}
					else {
						System.out.println("System failed to add Material, please try again.");
					}
					break;
				case "add user":
					System.out.println("Please provide the User ID Number and their Organization in a single, space separated, line.");
					input = sc.nextLine();
					result = library.enrolUser(Integer.parseInt(input.split(" ")[0]), input.split(" ")[1]);
					if(result) {
						System.out.println("User successfully enrolled, please use the command 'view users' to confirm.");
					}
					else {
						System.out.println("System failed to add User, please try again.");
					}
					break;
				case "add organization":
					System.out.println("Please provide the Organization Name.");
					input = sc.nextLine();
					result = library.addOrganization(input.split(" ")[0]);
					if(result) {
						System.out.println("Organization successfully added, please use the command 'view OrganizationRegistry' to confirm.");
					}
					else {
						System.out.println("System failed to add Organization, please try again.");
					}
					break;
				case "checkout material":
					System.out.println("Please provide the User ID Number that is checking the Material out and Material ID Number of the Material being checked out in a single, space separated, line.");
					input = sc.nextLine();
					result = library.checkoutMaterial(Integer.parseInt(input.split(" ")[0]), Integer.parseInt(input.split(" ")[1]));
					if(result) {
						System.out.println("Material successfully checked out, please use the command 'view materials' to confirm.");
					}
					else {
						System.out.println("System failed to checkout Material, please try again.");
					}
					break;
				case "return material":
					System.out.println("Please provide the User ID Number that checked the Material out and Material ID Number of the Material being returned in a single, space separated, line.");
					input = sc.nextLine();
					result = library.returnMaterial(Integer.parseInt(input.split(" ")[0]), Integer.parseInt(input.split(" ")[1]));
					if(result) {
						System.out.println("Material successfully returned, please use the command 'view materials' to confirm.");
					}
					else {
						System.out.println("System failed to return Material, please try again.");
					}
					break;
				case "pay fee":
					System.out.println("Please provide the Organization name and the amount being paid to their account on separate lines.");
					result = library.payFee(sc.nextLine(), Integer.parseInt(sc.nextLine()));
					if(result) {
						System.out.println("Fee successfully paid, please use the command 'view organizations' to confirm the account balance.");
					}
					else {
						System.out.println("System failed to pay Fee, please try again.");
					}
					break;
				case "help":
					libraryInputCommands();
					break;
				default:
					System.out.println("Input command was not recognized, please try again.");
					break;
			}
			input = sc.nextLine();
		}
		sc.close();
	}

	private static void libraryInputCommands() {
		System.out.println("View:\n - Users\n - Materials\n - Organizations");
		System.out.println("Add:\n - User\n - Material\n - Organization");
		System.out.println("Checkout/Return Material");
		System.out.println("Pay Fee");
		System.out.println("For example, the command 'View Materials' would display the Materials in the Library.");
		System.out.println("Type 'help' to see these commands again.");
	}

	public static void printMaterial(Material m) {
		if(m == null) {
			return;
		}
		int elapsed = m.getDaysCheckedOut();
		String checkout = ", Status: " + (elapsed == -1 ? "Available to Check Out" : ("Checked out for " + elapsed + " days."));
		System.out.println(" - ID: " + m.getId() + ", Type: " + m.getMaterialType() + checkout);
	}

	public static void printUser(User u) {
		System.out.print(" - ID: " + u.getId() + ", Organization: \"" + u.getOrganization() + "\", Checked Out Materials: ");
		if(u.getCheckedOut()[0] == -1) {
			System.out.println("None");
		}
		else {
			for(int m2 : u.getCheckedOut()) {
				printMaterial(library.getMaterialCatalogue().getMaterial(m2));
			}
		}
	}
	
	public static void printOrganization(Organization o) {
		System.out.println(" - ID: " + o.getId() + ", Title: " + o.getName() + ", Fines: $" + o.getFines());
	}
	
//---  Setter Methods   -----------------------------------------------------------------------
	
	public static void assignLibrary(Library assign) {
		library = assign;
	}

}
