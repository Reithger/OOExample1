package version3.model.material;

import java.util.ArrayList;
import java.util.Arrays;

import version3.model.catalogue.Catalogue;
import version3.model.material.materialtype.MaterialTypeRegistry;

/**
 * This class extends the Catalogue abstract class to specify itself as a Catalogue
 * of Material objects (Materials extending the CatalogueItem abstract class).
 * 
 * This class also provides tools for generating a Material object based on provided
 * input so that calling classes can avoid a dependency on the Material class.
 * 
 * @author Ada Clevinger
 * @version 2.0
 *
 */

public class MaterialsCatalogue extends Catalogue {

//---  Constants   ----------------------------------------------------------------------------
	
	/** int value denoting how many days are in a week so that I didn't have to just put the number 7 in my code*/
	public static final int DURATION_WEEK = 7;
	
//---  Instance Variables   -------------------------------------------------------------------
	
	public MaterialsCatalogue() {
		super();
	}
	
//---  Operations   ---------------------------------------------------------------------------
	
	/**
	 * This function performs a check of whether or not a Material can be checked out in the calling
	 * function by informing the caller of whether the Material object prompted by the
	 * provided id is available or any of the Materials signified by the id values in matIds is overdue.
	 * 
	 * @param id - int value representing the id of the Material whose availability is being tested
	 * @param matIds - int[] object representing the ids of Materials checked out by a user whose overdue status is being tested
	 * @return
	 */
	
	public boolean validateCheckoutAvailability(int id, int[] matIds) {
		return checkOverdueMaterials(matIds) && checkAvailableMaterial(id);
	}
	
	/**
	 * This function checks whether a Material identified by the int value materialId has been
	 * checked out already by a user.
	 * 
	 * @param materialId - int value representing the id associated to a Material in this MaterialCatalogue object.
	 * @return - Returns a boolean signifying whether or not the designated Material is available to be checked out or not.
	 */
	
	public boolean checkAvailableMaterial(int materialId) {
		return getMaterial(materialId).isAvailable();
	}
	
	/**
	 * This function takes an array of Material ids and tests each of the Materials
	 * associated to those int values for whether or not those Materials are overdue.
	 * 
	 * @param ids - int[] object containing id values associated to Materials that are being tested for being overdue
	 * @return - Returns a boolean value signifying whether any of the Materials associated to the provided ids were overdue or not
	 */
	
	public boolean checkOverdueMaterials(int[] ids) {
		if(ids.length == 0) {
			return true;
		}
		for(int i : ids) {
			if(checkOverdueMaterial(i)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Function that checks whether or not the Material described by the provided int value is overdue or not.
	 * 
	 * @param id - int value representing the id of the Material object being tested
	 * @return - Returns a boolean value signifying whether or not the Material object described by the provided argument is overdue or not.
	 */
	
	public boolean checkOverdueMaterial(int id) {
		if(getMaterial(id) == null) {
			return false;
		}
		return getMaterial(id).testOverdue();
	}
	
	/**
	 * Function that calculates the overdue cost of the Material associated to the provided id int value.
	 * 
	 * @param id - int value representing the id of the Material whose overdue cost is being calculated
	 * @return - Returns an int value representing the overdue cost currently attributed to the Material associated to the provided id int value.
	 */
	
	public int calculateOverdueCost(int id) {
		Material m = getMaterial(id);
		int days = m.getDaysCheckedOut();
		days -= DURATION_WEEK * m.getOverdueLimit();
		if(days < 0) {
			return 0;
		}
		else {
			return days * m.getOverdueCost();
		}
	}
	
	/**
	 * Function that generates a Material object using the provided int id and String type. A call to the
	 * static class MaterialTypeRegistry is made to get the MaterialType from the provided String type.
	 * 
	 * @param matId - int value representing the id of the Material being created
	 * @param mType - String object representing the name of the MaterialType being associated to the new Material object
	 * @return - Returns a Material object that is constructed using the provided id and type values
	 */
	
	public Material compileMaterial(int matId, String mType) {
		return new Material(matId, MaterialTypeRegistry.getMaterialType(mType));
	}
	
//---  Getter Methods   -----------------------------------------------------------------------
	
	/**
	 * Getter method that returns the Materials stored by this Catalogue
	 * 
	 * @return - Returns the Material objects stored by this Catalogue
	 */
	
	public ArrayList<Material> getMaterials(){
		ArrayList<Material> out = new ArrayList<Material>();
		for(int i : getKeys()) {
			out.add(getMaterial(i));
		}
		return out;
	}
	
	/**
	 * Getter method that returns the Material associated to the provided id int value
	 * 
	 * @param id - int value representing the id of the Material object being returned
	 * @return - Returns a Material object associated to the provided id int value
	 */
	
	public Material getMaterial(int id) {
		return (Material)getResource(id);
	}

}
