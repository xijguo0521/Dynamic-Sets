package middleware.aggregation;

import java.util.Hashtable;

public class DynamicSetConfig {
	
	private Hashtable<String, Class<?>> aggrTable = new Hashtable<String, Class<?>>();
	
	public void add(String str, Class<?> clazz) {
		aggrTable.put(str, clazz);
	}
	
	public Hashtable<String, Class<?>> getTable() {
		return aggrTable;
	}
}
