package version2.catalogue;

import java.util.ArrayList;
import java.util.HashMap;
import version2.organization.Organization;

public class OrganizationCatalogue extends Catalogue {

//---  Instance Variables   -------------------------------------------------------------------
	
	private HashMap<String, Integer> idMapping;
	private int maxFines;
	private int countOrganizations;
	
//---  Constructors   -------------------------------------------------------------------------
	
	public OrganizationCatalogue(int in) {
		maxFines = in;
		idMapping = new HashMap<String, Integer>();
	}
	
//---  Operations   ---------------------------------------------------------------------------
	
	public boolean verifyOrganizationCheckout(int userId) {
		Organization use = getOrganizationByUser(userId);
		if(use == null) {
			return false;
		}
		return use.getFines() <= maxFines;
	}
	
	public void addUserToOrganization(int userId, String nom) {
		getOrganization(nom).addUser(userId);
	}
	
	public Organization compileOrganization(String nom) {
		idMapping.put(nom, countOrganizations);
		return new Organization(countOrganizations++, nom);
	}

//---  Getter Methods   -----------------------------------------------------------------------
	
	public ArrayList<Organization> getOrganizations(){
		ArrayList<Organization> out = new ArrayList<Organization>();
		for(int i : getKeys()) {
			out.add((Organization)getResource(i));
		}
		return out;
	}

	public Organization getOrganizationByUser(int userId) {
		for(String s : idMapping.keySet()) {
			if(getOrganization(s).checkStatusMember(userId)) {
				return getOrganization(s);
			}
		}
		return null;
	}
	
	public Organization getOrganization(String nom) {
		if(idMapping.get(nom) == null) {
			return null;
		}
		return (Organization)getResource(idMapping.get(nom));
	}

}
