package version3.controller.conversion;

import java.util.ArrayList;
import java.util.HashMap;

import version3.model.library.Library;
import version3.model.material.Material;
import version3.model.organization.Organization;
import version3.model.user.User;

public class ConvertModel {
	
	public final static String TYPE_MATERIAL = "material";
	public final static String TYPE_USER = "user";
	public final static String TYPE_ORGANIZATION = "organization";
	
	private Library library;
	private int reference;
	private HashMap<String, DisplayFormat> hold;
	
	public ConvertModel(Library in) {
		library = in;
		hold = new HashMap<String, DisplayFormat>();
	}

	public ArrayList<DisplayFormat> getUserData(){
		ArrayList<DisplayFormat> out = new ArrayList<DisplayFormat>();
		for(User u : library.getUserCatalogue().getUsers()) {
			out.add(compileUserData(u));
		}
		return out;
	}
	
	public ArrayList<DisplayFormat> getMaterialData(){
		ArrayList<DisplayFormat> out = new ArrayList<DisplayFormat>();
		for(Material u : library.getMaterialCatalogue().getMaterials()) {
			out.add(compileMaterialData(u));
		}
		return out;
	}

	public ArrayList<DisplayFormat> getOrganizationData(){
		ArrayList<DisplayFormat> out = new ArrayList<DisplayFormat>();
		for(Organization u : library.getOrganizationCatalogue().getOrganizations()) {
			out.add(compileOrganizationData(u));
		}
		return out;
	}
	
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

	public DisplayFormat compileMaterialData(Material m) {
		ArrayList<String> form = new ArrayList<String>();
		form.add(m.getId() + "");
		form.add(m.getMaterialType());
		int days = m.getDaysCheckedOut();
		form.add(days == -1 ? null : days + "");
		return new DisplayFormat(TYPE_MATERIAL, form);
	}
	
	public DisplayFormat compileOrganizationData(Organization o) {
		ArrayList<String> form = new ArrayList<String>();
		form.add(o.getId() + "");
		form.add(o.getName());
		form.add(o.getFines() + "");
		return new DisplayFormat(TYPE_ORGANIZATION, form);
	}
	
	public DisplayFormat getHeldDisplayFormat(String val) {
		DisplayFormat d = hold.get(val);
		hold.remove(val);
		return d;
	}
	
}
