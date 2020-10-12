package version3.controller.conversion;

import java.util.ArrayList;

public class DisplayFormat {

	private String type;
	private ArrayList<String> data;
	
	public DisplayFormat(String typ, ArrayList<String> inDat) {
		type = typ;
		data = inDat;
	}
	
	public String getType() {
		return type;
	}
	
	public ArrayList<String> getData(){
		return data;
	}
	
	public String getIndexData(int ind) {
		if(ind >= data.size()) {
			return "Invalid Access";
		}
		return data.get(ind);
	}
	
}
