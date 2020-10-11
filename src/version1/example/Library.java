package version1.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * 
 * This class represents the problem scenario described in the README file before any Object Oriented design schemes have been implemented.
 * 
 * Every behavior of the program is performed through this singular class through public methods that can be utilized by any instance of the class.
 * 
 * In lieu of having other classes to store functionality and information, everything is piled into this one class bloating it with responsibilities.
 * 
 * Note of interpretation regarding the problem scenario: given that organizations pay fees, I assumed that fines that users accrue are collected to their
 * associated organization, similarly restricting all users belonging to the same organization if, as a group, their fines are too large.
 * 
 * As of v1.1 this program now has a View component that allows for manipulation of the model and viewing the model data; this is to allow for later
 * refactoring that will create a Model View Controller architecture to aid in classroom understandings.
 * 
 * @author Ada Clevinger
 * @version 1.1
 * 
 * Note: Java has a special form of commenting called Javadoc Comments that allows an API to automatically be generated from your project's classes.
 * It is really useful and neat, most IDEs will use Javadoc info to auto-fill information about the classes you have written. This is useful in larger
 * projects so that your IDE can remind you what the eight arguments for a function are supposed to be, or give you a quick description of what an object
 * is according to the description you wrote for it.
 * 
 * The format is used widely within here, but to be clear:
 * 
 * 		/** - This starts your javadoc comment, and will usually auto-propogate certain fields based on context (such as author or param)
 * 		*\/ - This ends your javadoc comment; if you are reading the source file, I have included a backslash so it would not end this comment too soon.
 * 
 * Use them wisely and often! Experiment or read about them to get more info, too.
 * 
 */

public class Library {
	
//---  Constant Values   ----------------------------------------------------------------------
	
	/** int value denoting that a particular material object is of the 'book' type; also used to index into conglomerate data regarding materials*/
	public static final int MATERIAL_TYPE_BOOK = 0;	
	/** int value denoting that a particular material object is of the 'journal' type; also used to index into conglomerate data regarding materials*/
	public static final int MATERIAL_TYPE_JOURNAL = 1;	
	/** int value denoting that a particular material object is of the 'dvd' type; also used to index into conglomerate data regarding materials*/
	public static final int MATERIAL_TYPE_DVD = 2;	
	/** int[] containing the data regarding how long each type of material can be checked out for, measured in weeks (index position denotes type of material)*/
	public static final int[] CHECKOUT_TIME_LIMIT = new int[] {4, 2, 1};	
	/** int[] containing the data regarding how much to fine a user per day that each type of material is overdue for (index position denotes type of material)*/
	public static final int[] OVERDUE_COST = new int[] {1, 3, 5};	
	/** String[] containing the names of each type of material, the index in the list corresponding to MATERIAL_TYPE_... values*/
	public static final String[] MATERIAL_TYPE_NAMES = new String[] {"Book", "Journal", "DVD"};
	/** int value denoting the maximum number of materials that a user can have checked out at once*/
	private static final int MAXIMUM_CHECKOUT_LIMIT = 5;	
	/** int value denoting the maximum size of fines that a user's organization can have before they are not permitted to check out materials*/
	private static final int MAXIMUM_CHECKOUT_FINES = 100;	
	/** String value used to identify when a material is available to be checked out by an eligible user*/
	private static final String MATERIAL_AVAILABLE = "H";	
	
	/** int value denoting how many elements of the user data set are used for metadata*/
	private static final int USER_METADATA_COUNT = 2;	
	/** int value denoting the index at which the ID for a user is stored in the user data set*/
	private static final int USER_INDEX_ID = 0;	
	/** int value denoting the index at which the organization for a user is stored in the user data set*/
	private static final int USER_INDEX_ORG = 1;

	/** int value denoting how many elements of the material data set are used for metadata*/
	private static final int MATERIAL_METADATA_COUNT = 3;	
	/** int value denoting the index at which the ID for a material is stored in the material data set*/
	private static final int MATERIAL_INDEX_ID = 0;	
	/** int value denoting the index at which the type of the material is stored in the material data set*/
	private static final int MATERIAL_INDEX_TYPE = 1;	
	/** int value denoting the index at which the status of a material being checked is stored in the material data set; if checked out, this index stores a long value
	 * representing the millisecond timestamp for when the item was checked out*/
	private static final int MATERIAL_INDEX_CHECKOUT = 2;
	
	/** long value denoting how many milliseconds are in a day for time passage calculations*/
	public static final long DURATION_DAY = 86400000;	
	/** int value denoting how many days are in a week so that I didn't have to just put the number 7 in my code*/
	public static final int DURATION_WEEK = 7;
	
//---  Instance Variables   -------------------------------------------------------------------

	/** HashMap that maps Integers to ArrayList<<r>String> objects to model Users of the library with the following format:  id --> id, organization, [] of material ids*/
	private HashMap<Integer, ArrayList<String>> users;
	/** HashMap that maps Integers to ArrayList<<r>String> objects to model Materials in the library with the following format:id --> id, type, checked out (date)*/
	private HashMap<Integer, ArrayList<String>> materials;
	/** HashMap that maps Strings to Integers to model the fines that are attributed to each Organization */
	private HashMap<String, Integer> organizationFines;
	
//---  Constructors   -------------------------------------------------------------------------
	
	/**
	 * '
	 * Constructor for Library objects that instantiates instance variables to prepare the Library for operation.
	 * 
	 */
	
	public Library() {
		users = new HashMap<Integer, ArrayList<String>>();
		materials = new HashMap<Integer, ArrayList<String>>();
		organizationFines = new HashMap<String, Integer>();
	}
	
//---  Operations   ---------------------------------------------------------------------------
	
	/**
	 * This function allows the caller to add a new Material to the Library with details specified by the arguments.
	 * If the Library already has a material with the provided ID, the function returns false to notify the caller that
	 * the operation has failed. Otherwise it returns true.
	 * 
	 * @param materialId - int value representing the ID associated with the new material
	 * @param type - int value representing the type associated with the new material (assumed to be using class constants to identify type)
	 * @return - Returns a boolean value representing whether or not the material was successfully stocked
	 */
	
	public boolean stockMaterial(int materialId, int type) {
		if(materials.get(materialId) != null || type < 0 || type >= MATERIAL_TYPE_NAMES.length) {
			return false;
		}
		ArrayList<String> info = configureMaterial(materialId, type);
		materials.put(materialId, info);
		return true;
	}
	
	/**
	 * This function allows the caller to add a new Material to the Library with details specified by the arguments.
	 * If the Library already has a material with the provided ID, the function returns false to notify the caller that
	 * the operation has failed. Otherwise it returns true.
	 * 
	 * This overloads the default stockMaterial function to allow the caller to provide a pre-figured set of data for the
	 * new material, primarily for purposes of initializing the library to have stock with a history for novel uses of the class.
	 * 
	 * @param materialId - int value representing the ID associated with the new material
	 * @param info - ArrayList<<r>String> representing the metadata to associated with the new material object
	 * @return - Returns a boolean value representing whether or not the material was successfully stocked
	 */
	
	public boolean stockMaterial(int materialId, ArrayList<String> info) {
		if(materials.get(materialId) != null) {
			return false;
		}
		materials.put(materialId, info);
		return true;
	}
	
	/**
	 * This function allows the caller to add a new user to the Library with details specified by the arguments.
	 * If the Library already has a user with the provided ID, the function returns false to notify the caller that
	 * the operation has failed. Otherwise it returns true.
	 * 
	 * If the user belongs to a new organization, that organization is added to the list that the Library stores.
	 * 
	 * @param userId - int value representing the ID associated with the new user
	 * @param organization - String object representing the organization that the new user is associated with
	 * @return - Returns a boolean value representing whether or not the user was successfully added
	 */
	
	public boolean enrolUser(int userId, String organization) {
		if(users.get(userId) != null) {
			return false;
		}
		ArrayList<String> info = configureUser(userId, organization);
		users.put(userId, info);
		addOrganization(info.get(USER_INDEX_ORG));
		return true;
	}
	
	/**
	 * This function allows the caller to add a new user to the Library with details specified by the arguments.
	 * If the Library already has a user with the provided ID, the function returns false to notify the caller that
	 * the operation has failed. Otherwise it returns true.
	 * 
	 * This overloads the default enrolUser function to allow the caller to provide a pre-figured set of data for the
	 * new user, primarily for purposes of initializing the library to have users with a history for novel uses of the class.
	 * 
	 * @param userId - int value representing the ID associated with the new user
	 * @param info - ArrayList<<r>String> representing the metadata to associated with the new user object
	 * @return - Returns a boolean value representing whether or not the user was successfully added
	 */
	
	public boolean enrolUser(int userId, ArrayList<String> info) {
		if(users.get(userId) != null) {
			return false;
		}
		users.put(userId, info);
		addOrganization(info.get(USER_INDEX_ORG));
		return true;
	}
	
	/**
	 * This function allows the caller to add an organization to the Library for the purposes of maintaining information
	 * regarding fines associated to the organizations that users belong to.
	 * 
	 * @param organization - String object representing the organization that is being added to the Library
	 * @return - Returns a boolean value denoting whether or not the added organization already existed; true if it didn't, false if it did
	 */
	
	public boolean addOrganization(String organization) {
		if(organizationFines.get(organization) != null) {
			return false;
		}
		organizationFines.put(organization, 0);
		return true;
	}
	
	/**
	 * This function allows the caller to have a specified user checkout the specified material, checking along the way
	 * whether or not the user is allowed to check out that material. If successfully added, the id of the material is
	 * appended to the data stored by the Library about that user.
	 * 
	 * @param userId - int value representing the ID associated with the user checking out a material
	 * @param materialId - int value representing the ID associated with the material being checked out
	 * @return - Returns a boolean denoting the result of this operation; false if it did not succeed, true if it did
	 */
	
	public boolean checkoutMaterial(int userId, int materialId) {
		ArrayList<String> test = users.get(userId);
		if(test == null || materials.get(materialId) == null || !validateCheckout(test, materialId)) {
			return false;
		}
		addMaterialToUser(userId, materialId);
		return true;
	}
	
	/**
	 * This function allows the caller to have a specified user return the specified material, checking along the way
	 * whether or not the user has checked out that material or not. If successful, this function also calculates whether
	 * the user will be fined for the material being overdue, and charges their organization if so.
	 * 
	 * If this operation fails, it may be because either ID was not in the Library's database or the user had not
	 * checked out that item. If it succeeds, then the material ID is removed from the list of data associated with
	 * the user.
	 * 
	 * @param userId - int value representing the ID of the user that is returning the material
	 * @param materialId - int value representing the Id of the material that is being returned
	 * @return - Returns a boolean value representing the result of this operation; false if it did not succeed, true if it did
	 */

	public boolean returnMaterial(int userId, int materialId) {
		if(users.get(userId) == null || materials.get(materialId) == null) {
			return false;
		}
		boolean pass = false;
		ArrayList<String> user = users.get(userId);
		for(int i = USER_METADATA_COUNT; i < user.size(); i++) {
			if(user.get(i).equals(""+materialId)) {
				user.remove(""+materialId);
				pass = true;
			}
		}
		if(!pass) {
			return false;
		}
		ArrayList<String> material = materials.get(materialId);
		int days = elapsedCheckoutTime(materialId) - DURATION_WEEK * CHECKOUT_TIME_LIMIT[Integer.parseInt(material.get(MATERIAL_INDEX_TYPE))];
		material.set(MATERIAL_INDEX_CHECKOUT, MATERIAL_AVAILABLE);
		if(days > 0) {
			String org = users.get(userId).get(USER_INDEX_ORG);
			organizationFines.put(org, organizationFines.get(org) + days * OVERDUE_COST[Integer.parseInt(material.get(MATERIAL_INDEX_TYPE))]);
		}
		return true;
	}
	
	/**
	 * This method allows the caller to attempt to pay a fee associated with an organization as stored by the Library.
	 * 
	 * It does not allow payment if the payment amount is negative or larger than the fine.
	 * 
	 * @param organization - String object representing the organization that is having their fee paid
	 * @param amount - int value representing the amount of money to pay towards the fee
	 * @return - Returns a boolean value denoting whether or not the operation was successful; true if it did, false if not.
	 */
	
	public boolean payFee(String organization, int amount) {
		if(organizationFines.get(organization) == null || organizationFines.get(organization) - amount < 0 || amount < 0) {
			return false;
		}
		organizationFines.put(organization,  organizationFines.get(organization) - amount);
		return true;
	}
	
	/**
	 * This function creates a UI in the command line terminal for the purposes of showing Model View Controller
	 * architecture in subsequent updates to this collection of code.
	 * 
	 */
	
	public void displayLibrary() {
		Scanner sc = new Scanner(System.in);
		boolean result;
		System.out.println("Welcome to the Library!\nPlease use the following commands.");
		libraryInputCommands();
		String input = sc.nextLine();
		while(!input.equals("end")) {
			switch(input.toLowerCase()) {
				case "view materials":
					System.out.println("Materials in Library:");
					for(ArrayList<String> st : materials.values()) {
						int elapsed = elapsedCheckoutTime(Integer.parseInt(st.get(0)));
						String checkout = ", Status: " + (elapsed == -1 ? "Available to Check Out" : ("Checked out for " + elapsed + " days."));
						System.out.println(" - ID: " + st.get(0) + ", Type: " + MATERIAL_TYPE_NAMES[Integer.parseInt(st.get(1))] + checkout);
					}
					break;
				case "view users":
					System.out.println("Users Registered to Library:");
					for(ArrayList<String> st : users.values()) {
						System.out.print(" - ID: " + st.get(0) + ", Organization: \"" + st.get(1) + "\", Checked Out Materials: ");
						if(st.size() == USER_METADATA_COUNT) {
							System.out.println("None");
						}
						else {
							for(int i = USER_METADATA_COUNT; i < st.size(); i++) {
								System.out.print(st.get(i) + (i + 1 == st.size() ? ".\n" : ", "));
							}
						}
					}
					break;
				case "view organizations":
					System.out.println("Organizations Registered to Library");
					for(String st : organizationFines.keySet()) {
						System.out.println(" - Title: " + st + ", Fines: $" + organizationFines.get(st));
					}
					break;
				case "add material":
					System.out.println("Please provide the Material ID Number and the Material Type (Book, DVD, or Journal) in a single, space separated, line.");
					input = sc.nextLine();
					result = stockMaterial(Integer.parseInt(input.split(" ")[0]), indexOf(MATERIAL_TYPE_NAMES, input.split(" ")[1]));
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
					result = enrolUser(Integer.parseInt(input.split(" ")[0]), input.split(" ")[1]);
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
					result = addOrganization(input.split(" ")[0]);
					if(result) {
						System.out.println("Organization successfully added, please use the command 'view organizations' to confirm.");
					}
					else {
						System.out.println("System failed to add Organization, please try again.");
					}
					break;
				case "checkout material":
					System.out.println("Please provide the User ID Number that is checking the Material out and Material ID Number of the Material being checked out in a single, space separated, line.");
					input = sc.nextLine();
					result = checkoutMaterial(Integer.parseInt(input.split(" ")[0]), Integer.parseInt(input.split(" ")[1]));
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
					result = returnMaterial(Integer.parseInt(input.split(" ")[0]), Integer.parseInt(input.split(" ")[1]));
					if(result) {
						System.out.println("Material successfully returned, please use the command 'view materials' to confirm.");
					}
					else {
						System.out.println("System failed to return Material, please try again.");
					}
					break;
				case "pay fee":
					System.out.println("Please provide the Organization name and the amount being paid to their account on separate lines.");
					result = payFee(sc.nextLine(), Integer.parseInt(sc.nextLine()));
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
	
	private void libraryInputCommands() {
		System.out.println("View:\n - Users\n - Materials\n - Organizations");
		System.out.println("Add:\n - User\n - Material\n - Organization");
		System.out.println("Checkout/Return Material");
		System.out.println("Pay Fee");
		System.out.println("For example, the command 'View Materials' would display the Materials in the Library.");
		System.out.println("Type 'help' to see these commands again.");
	}
	
//---  Getter Methods   -----------------------------------------------------------------------
	
	/**
	 * Getter method that returns the integer fine associated to the Organization described by the provided
	 * String object. Returns null if no organization exists in the Library's memory.
	 * 
	 * @param organization - String object representing the name of the Organization that is being queried.
	 * @return - Returns an Integer value representing the fines associated to the provided Organization, or null if no such organization exists.
	 */
	
	public Integer getOrganizationFine(String organization) {
		if(organizationFines.get(organization) == null) {
			return null;
		}
		return organizationFines.get(organization);
	}
	
//---  Support Methods   ----------------------------------------------------------------------
	
	/**
	 * Helper method that does the busy work of adding a new material denoted by the provided material ID to the
	 * list of materials that have been checked out by the user denoted by the provided user ID.
	 * 
	 * @param userId - int value representing the id associated to a User in the Library
	 * @param materialId = int value representing the id associated to a Material in the Library
	 */
	
	private void addMaterialToUser(int userId, int materialId) {
		users.get(userId).add(""+materialId);
		materials.get(materialId).remove(MATERIAL_INDEX_CHECKOUT);
		materials.get(materialId).add("" + System.currentTimeMillis());
	}
	
	/**
	 * Helper method that validates that a user represented by their ArrayList<<r>String> of information is
	 * allowed to check-out a material denoted by its material ID according to the three rules in the design
	 * specification:
	 * 
	 * The User may not check out more than five items
	 * The User cannot have fines exceeding $100
	 * The User cannot have any materials that are overdue
	 * 
	 * This method checks these three constraints and returns a response denoting whether the user is allowed
	 * to checkout the material or not.
	 * 
	 * @param info - ArrayList<<r>String> object representing the information that the Library stores about the prompted User
	 * @param materialId - int value denoting which material in the Library the User is trying to check out
	 * @return - Returns a boolean value informing the calling function of whether the represented User can checkout the denoted material or not
	 */
	
	private boolean validateCheckout(ArrayList<String> info, int materialId) {
		String org = info.get(USER_INDEX_ORG);
		if(info.size() <= USER_METADATA_COUNT + MAXIMUM_CHECKOUT_LIMIT && organizationFines.get(org) <= MAXIMUM_CHECKOUT_FINES && materials.get(materialId).get(MATERIAL_INDEX_CHECKOUT).equals(MATERIAL_AVAILABLE)){
			for(int i = USER_METADATA_COUNT; i < info.size(); i++) {
				int days = elapsedCheckoutTime(Integer.parseInt(info.get(i)));
				if(days != -1 && days > MAXIMUM_CHECKOUT_LIMIT) {
					return false;
				}
			}
		}
		else {
			return false;
		}
		return true;
	}

	/**
	 * Helper method that automatically calculates how many days have elapsed since the material denoted by the material ID
	 * was last checked out, or -1 if the material has not been checked out (and is available).
	 * 
	 * This method converts the system time in milliseconds to days using the class constant DURATION_DAY; would likely be better
	 * to use a more fleshed out Date class but integrating that at the time of development was harder than just doing this.
	 * 
	 * @param materialId - int value denoting the material in this Library that is being queried regarding the elapsed time since last checkout.
	 * @return - Returns an int value representing the number of days that the material has been checked out from the library for.
	 */
	
	private int elapsedCheckoutTime(int materialId) {
		if(materials.get(materialId).get(MATERIAL_INDEX_CHECKOUT).equals(MATERIAL_AVAILABLE)) {
			return -1;
		}
		return (int)((System.currentTimeMillis() - (Long.parseLong(materials.get(materialId).get(MATERIAL_INDEX_CHECKOUT)))) / DURATION_DAY);
	}
	
	/**
	 * Helper method that configures a default User for storage in the Library given an ID and organization to which
	 * they belong, the only two pieces of meta information kept about Users.
	 * 
	 * @param userId - int value representing the ID that will be used for this new User.
	 * @param organization - String object representing the name of the Organization that the user is associated with
	 * @return - Returns an ArrayList<<r>String> containing the information for a new User in the Library according to the provided arguments
	 */
	
	private ArrayList<String> configureUser(int userId, String organization){
		ArrayList<String> user = new ArrayList<String>();
		user.add("" + userId);
		user.add(organization);
		return user;
	}
	
	/**
	 * Helper method that configures a default material for storage in the Library given an ID and the new material's type,
	 * two of the three pieces of meta information kept about materials. The third value, being availability, is defaulted
	 * to the class constant representing that the material is available for checkout.
	 * 
	 * @param materialId - int value representing the ID that will be used for this new material.
	 * @param type - String object representing the type that the material is associated with
	 * @return - Returns an ArrayList<<r>String> containing the information for a new material in the Library according to the provided arguments
	 */
	
	private ArrayList<String> configureMaterial(int materialId, int type){
		ArrayList<String> material = new ArrayList<String>();
		material.add("" + materialId);
		material.add("" + type);
		material.add(MATERIAL_AVAILABLE);
		return material;
	}
	
//---  Mechanics   ----------------------------------------------------------------------------
	
	private int indexOf(String[] array, String key) {
		for(int i = 0; i < array.length; i++) {
			if(array[i].equals(key)) {
				return i;
			}
		}
		return -1;
	}
	
}
