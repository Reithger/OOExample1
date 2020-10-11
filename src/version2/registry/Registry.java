package version2.registry;

import java.util.HashMap;

/**
 * 
 * 
 * @author Borinor
 *
 */

public abstract class Registry {

//---  Constants   ----------------------------------------------------------------------------
	
	private static HashMap<String, RegistryItem> registry;
	
//---  Operations   ---------------------------------------------------------------------------
	
	public static void registerEntry(RegistryItem in) {
		if(registry == null) {
			registry = new HashMap<String, RegistryItem>();
		}
		registry.put(in.getName(), in);
	}
	
//---  Getter Methods   -----------------------------------------------------------------------
	
	public static RegistryItem getEntry(String name) {
		return registry.get(name);
	}
	
}
