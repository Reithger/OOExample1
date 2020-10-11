package version2.material;

import version2.catalogue.CatalogueItem;

public class Material extends CatalogueItem{

//---  Instance Variables   -------------------------------------------------------------------
	
	private CheckoutTime checkedOut;
	private MaterialType type;
	
//---  Constructors   -------------------------------------------------------------------------
	
	public Material(int inId, MaterialType inType) {
		super(inId);
		type = inType;
	}
	
//---  Operations   ---------------------------------------------------------------------------

	public void checkoutMaterial() {
		checkedOut = new CheckoutTime();
	}
	
	public void returnMaterial() {
		checkedOut = null;
	}
	
	public boolean isAvailable() {
		return checkedOut == null;
	}

	public boolean testOverdue() {
		return checkedOut.elapsedTimeDays() > type.getTimeLimit();
	}
	
//---  Getter Methods   -----------------------------------------------------------------------
	
	public String getMaterialType() {
		return type.getName();
	}
	
	public int getDaysCheckedOut() {
		return checkedOut.elapsedTimeDays();
	}
	
	public int getOverdueLimit() {
		return type.getTimeLimit();
	}
	
	public int getOverdueCost() {
		return type.getOverdueCost();
	}
	
//---  Setter Methods   -----------------------------------------------------------------------
	
	public void setCheckoutTime(long in) {
		checkedOut = new CheckoutTime(in);
	}

}
