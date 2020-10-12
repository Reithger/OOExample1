package version3.model.library;

import version3.model.catalogue.MaterialsCatalogue;
import version3.model.catalogue.OrganizationCatalogue;
import version3.model.catalogue.UserCatalogue;
import version3.model.registry.MaterialTypeRegistry;

/**
 * 
 * This is v2.0 of the project, now distributing responsibility across many classes to lighten the load on Library and isolate behaviors/responsibilities.
 * 
 * Classes with similar purposes/themes are put into the same packages to organize the project's code and provide structure to the produced UML Diagram.
 * 
 * This class still functions as a central brain, but is able to behave more simply now that the many Constants and complicated Instance Variables have
 * been turned into classes that provide the same functions but also handle the logic of them operating.
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
 * @author Ada Clevinger
 * @version 2.0
 * 
 */

public class Library {
	
//---  Constant Values   ----------------------------------------------------------------------
	
	/** int value denoting the maximum number of materials that a user can have checked out at once*/
	private static final int MAXIMUM_CHECKOUT_LIMIT = 5;	
	/** int value denoting the maximum size of fines that a user's organization can have before they are not permitted to check out materials*/
	private static final int MAXIMUM_CHECKOUT_FINES = 100;	
	
//---  Instance Variables   -------------------------------------------------------------------

	/** UserCatalogue object that manages the users stored by the Library who may interface with it*/
	private UserCatalogue users;
	/** MaterialsCatalogue object that manages the Materials stored by the Library*/
	private MaterialsCatalogue materials;
	/** OrganizationCatalogue object that manages the Organizations that Users of the Library are members of and that are fined by the Library.*/
	private OrganizationCatalogue organizations;
	
//---  Constructors   -------------------------------------------------------------------------
	
	/**
	 * '
	 * Constructor for Library objects that instantiates instance variables to prepare the Library for operation.
	 * 
	 */
	
	public Library() {
		users = new UserCatalogue(MAXIMUM_CHECKOUT_LIMIT);
		materials = new MaterialsCatalogue();
		organizations = new OrganizationCatalogue(MAXIMUM_CHECKOUT_FINES);
	}
	
//---  Operations   ---------------------------------------------------------------------------
	
	/**
	 * This function allows the caller to add a new Material to the Library with details specified by the arguments.
	 * If the Library already has a material with the provided ID, the function returns false to notify the caller that
	 * the operation has failed. Otherwise it returns true.
	 * 
	 * @param materialId - int value representing the ID associated with the new material
	 * @param type - String value representing the type associated with the new material (assumed to be using class constants to identify type)
	 * @return - Returns a boolean value representing whether or not the material was successfully stocked
	 */
	
	public boolean stockMaterial(int materialId, String type) {
		if(materials.getResource(materialId) != null || MaterialTypeRegistry.getEntry(type) == null) {
			return false;
		}
		materials.addResource(materials.compileMaterial(materialId, type));
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
		if(users.getResource(userId) != null) {
			return false;
		}
		addOrganization(organization);
		users.addResource(users.compileUser(userId, organization));
		organizations.addUserToOrganization(userId, organization);
		return true;
	}

	/**
	 * This function allows the caller to add an organization to the Library for the purposes of maintaining information
	 * regarding fines associated to the OrganizationRegistry that users belong to.
	 * 
	 * @param organization - String object representing the organization that is being added to the Library
	 * @return - Returns a boolean value denoting whether or not the added organization already existed; true if it didn't, false if it did
	 */
	
	public boolean addOrganization(String organization) {
		if(organizations.getOrganization(organization) != null) {
			return false;
		}
		organizations.addResource(organizations.compileOrganization(organization));
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
		if(!validateCheckout(userId, materialId)) {
			return false;
		}
		users.getUser(userId).addMaterial(materialId);
		materials.getMaterial(materialId).checkoutMaterial();
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
		if(!users.getUser(userId).hasMaterial(materialId)) {
			return false;
		}
		users.getUser(userId).removeMaterial(materialId);
		int cost = materials.calculateOverdueCost(materialId);
		organizations.getOrganizationByUser(userId).addFines(cost);
		materials.getMaterial(materialId).returnMaterial();
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
		if(organizations.getOrganization(organization) == null) {
			return false;
		}
		return organizations.getOrganization(organization).deductFines(amount);
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
		return organizations.getOrganization(organization).getFines();
	}
	
	/**
	 * Getter method that requests the UserCatalogue object associated with this Library object.
	 * 
	 * @return - Returns the UserCatalogue object associated with this Library object.
	 */
	
	public UserCatalogue getUserCatalogue() {
		return users;
	}
	
	/**
	 * Getter method that requests the MaterialCatalogue object associated with this Library object.
	 * 
	 * @return - Returns the MaterialCatalogue object associated with this Library object.
	 */
	
	public MaterialsCatalogue getMaterialCatalogue() {
		return materials;
	}
	
	/**
	 * Getter method that requests the OrganizationCatalogue object associated with this Library object.
	 * 
	 * @return - Returns the OrganizationCatalogue object associated with this Library object.
	 */
	
	public OrganizationCatalogue getOrganizationCatalogue() {
		return organizations;
	}
	
//---  Support Methods   ----------------------------------------------------------------------
	
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
	 * @param userlId - int value denoting which User in the Library is trying to check out a Material
	 * @param materialId - int value denoting which material in the Library the User is trying to check out
	 * @return - Returns a boolean value informing the calling function of whether the represented User can checkout the denoted material or not
	 */
	
	private boolean validateCheckout(int userId, int materialId) {
		return users.validateUserCheckout(userId) && organizations.verifyOrganizationCheckout(userId) && materials.validateCheckoutAvailability(materialId, users.getUser(userId).getCheckedOut());
	}

}
