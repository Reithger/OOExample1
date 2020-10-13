package version3.controller.conversion;

import java.util.ArrayList;
import java.util.HashMap;

import version3.model.library.Library;
import version3.model.material.Material;
import version3.model.organization.Organization;
import version3.model.user.User;

/**
 * This Class serves as the portion of the Controller functionality that manages the conversion of
 * the Model's state to a generic data format for the View to interpret in displaying the GUI.
 * 
 * It is given the reference to the Library object it needs to convert by the MainController class,
 * representing a distribution of responsibility to another class to reduce the complexity of MainController.
 * 
 * If the model changes, this is where we change the Model interpretation, preferably in a way that requires no
 * change in the View. If you add program functionality, you may need to add to the view new rules for interpretation
 * unless you've made the View flexible enough to only require change in the Controller. Things tend to get
 * messy, and the best we can do is make the tidying up easier.
 * 
 * Oh, yeah, I do a thing here where sometimes you need to prepare another DisplayFormat object but when you pass
 * that first DisplayFormat object you can't make it store a DisplayFormat object (it could if I programmed it to,
 * but nah), so instead we give the first DisplayFormat object unique id values that map to pre-prepared DisplayFormat
 * objects which are stored in a HashMap, so that when the View reads the DisplayFormat object it can pick up the id
 * stored in it and then retrieve the entire associated DisplayFormat object.
 * 
 * The reason for this is because when the View displays a User, it also displays any Materials they have checked out,
 * both of which are represented by DisplayFormat objects in this context. I could pass the Material id references forward
 * to the View which could then request from ConvertModel that it accesses the Model again to get the Material data and
 * convert it to a DisplayFormat object, or when I process a User I could just prepare the Materials at the same time and
 * provide the DisplayFormat for the User object reference ids to quickly retrieve the pre-prepared DisplayFormat object
 * (and immediately delete them after being accessed to not have infinite data growth).
 * 
 * Or I could just make DisplayFormat objects hold other DisplayFormat objects. It's a choice. (That I am aware I'm making
 * and writing down in my documentation so other people know a) what I did and b) why I did it).
 * 
 * @author Ada Clevinger
 * @version 3.0
 *
 */

public class ConvertModel {
	
//---  Constants   ----------------------------------------------------------------------------
	
	/** String value representing a constant representation of the Material type of DisplayFormat objects*/
	public final static String TYPE_MATERIAL = "material";
	/** String value representing a constant representation of the User type of DisplayFormat objects*/
	public final static String TYPE_USER = "user";
	/** String value representing a constant representation of the Organization type of DisplayFormat objects*/
	public final static String TYPE_ORGANIZATION = "organization";
	
//---  Instance Variables   -------------------------------------------------------------------
	
	/** Library object used to access the Model for the purposes of requesting state data for conversion to DisplayFormat objects, which the View interprets*/
	private Library library;
	/** int value that iterates on every use to uniquely map integers to stored DisplayFormat objects so that information can be prepared for and accessed by the View*/
	private int reference;
	/** HashMap<<r>String, DisplayFormat> object that temporarily holds onto DisplayFormat objects mapped to String ids (just an integer I convert) for rapid access*/
	private HashMap<String, DisplayFormat> hold;
	
//---  Constructors   -------------------------------------------------------------------------
	
	/**
	 * Constructor for the ConvertModel Class that takes a Library object as reference to
	 * the Model when converting its state data to a format mutually understood between the
	 * Controller and View.
	 * 
	 * Also initializes and prepares the HashMap for short-term storage of DisplayFormat objects
	 * when convenient.
	 * 
	 * @param in - Library object used to access data from the Model
	 */
	
	public ConvertModel(Library in) {
		library = in;
		hold = new HashMap<String, DisplayFormat>();
	}
	
//---  Operations   ---------------------------------------------------------------------------
	
	/**
	 * Function that takes a User object to instantiate a DisplayFormat object with the
	 * data relevant to the View's method of displaying that User object.
	 * 
	 * Also compiles the Material data associated to the given User for later retrieval if necessary.
	 * 
	 * @param u - User object provided to have its relevant data converted to a generic DisplayFormat object
	 * @return - Returns a DisplayFormat object containing the relevant information for the View from a User object in the Model.
	 */
	
	public DisplayFormat compileUserData(User u) {
		ArrayList<String> form = new ArrayList<String>();
		form.add(u.getId() + "");
		form.add(u.getOrganization());
		for(int id : u.getCheckedOut()) {
			DisplayFormat d = compileMaterialData(library.getMaterialCatalogue().getMaterial(id));
			hold.put(reference + "", d);
			form.add("" + reference++);
		}
		return new DisplayFormat(TYPE_USER, form);
	}
	
	/**
	 * Function that takes a Material object to instantiate a DisplayFormat object with the
	 * data relevant to the View's method of displaying that Material object.
	 * 
	 * @param u - Material object provided to have its relevant data converted to a generic DisplayFormat object
	 * @return - Returns a DisplayFormat object containing the relevant information for the View from a Material object in the Model.
	 */
	
	public DisplayFormat compileMaterialData(Material m) {
		ArrayList<String> form = new ArrayList<String>();
		form.add(m.getId() + "");
		form.add(m.getMaterialType());
		int days = m.getDaysCheckedOut();
		form.add(days == -1 ? null : days + "");
		return new DisplayFormat(TYPE_MATERIAL, form);
	}
	
	/**
	 * Function that takes an Organization object to instantiate a DisplayFormat object with the
	 * data relevant to the View's method of displaying that Organization object.
	 * 
	 * @param u - Organization object provided to have its relevant data converted to a generic DisplayFormat object
	 * @return - Returns a DisplayFormat object containing the relevant information for the View from an Organization object in the Model.
	 */
	
	public DisplayFormat compileOrganizationData(Organization o) {
		ArrayList<String> form = new ArrayList<String>();
		form.add(o.getId() + "");
		form.add(o.getName());
		form.add(o.getFines() + "");
		return new DisplayFormat(TYPE_ORGANIZATION, form);
	}

//---  Getter Methods   -----------------------------------------------------------------------
	
	//-- Interpretation  --------------------------------------
	
	/**
	 * Getter method that requests a list of DisplayFormat objects each containing the relevant information
	 * about a User object from the Model that has been made generic by the corresponding compile[type]Data()
	 * for the View to use in displaying that object visually.
	 * 
	 * @return - Returns an ArrayList<<r>DisplayFormat> object containing generic data on the User objects in the Model
	 */
	
	public ArrayList<DisplayFormat> getUserData(){
		ArrayList<DisplayFormat> out = new ArrayList<DisplayFormat>();
		for(User u : library.getUserCatalogue().getUsers()) {
			out.add(compileUserData(u));
		}
		return out;
	}
	
	/**
	 * Getter method that requests a list of DisplayFormat objects each containing the relevant information
	 * about a Material object from the Model that has been made generic by the corresponding compile[type]Data()
	 * for the View to use in displaying that object visually.
	 * 
	 * @return - Returns an ArrayList<<r>DisplayFormat> object containing generic data on the Material objects in the Model
	 */
	
	public ArrayList<DisplayFormat> getMaterialData(){
		ArrayList<DisplayFormat> out = new ArrayList<DisplayFormat>();
		for(Material u : library.getMaterialCatalogue().getMaterials()) {
			out.add(compileMaterialData(u));
		}
		return out;
	}
	
	/**
	 * Getter method that requests a list of DisplayFormat objects each containing the relevant information
	 * about a Organization object from the Model that has been made generic by the corresponding compile[type]Data()
	 * for the View to use in displaying that object visually.
	 * 
	 * @return - Returns an ArrayList<<r>DisplayFormat> object containing generic data on the Organization objects in the Model
	 */
	
	public ArrayList<DisplayFormat> getOrganizationData(){
		ArrayList<DisplayFormat> out = new ArrayList<DisplayFormat>();
		for(Organization u : library.getOrganizationCatalogue().getOrganizations()) {
			out.add(compileOrganizationData(u));
		}
		return out;
	}
	
	//-- Mechanical  ------------------------------------------
	
	/**
	 * Function that retrieves a DisplayFormat object that has been stored temporarily by this ConvertModel object using
	 * the provided String key value.
	 * 
	 * @param val - String object representing a key for accessing a DisplayFormat object in the HashMap stored by this ConvertModel object
	 * @return - Returns a DisplayFormat object associated to the provided String key value
	 */
	
	public DisplayFormat getHeldDisplayFormat(String val) {
		DisplayFormat d = hold.get(val);
		hold.remove(val);
		return d;
	}

}
