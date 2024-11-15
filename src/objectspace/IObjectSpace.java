package objectspace;

import java.util.ArrayList;
import java.util.Hashtable;

public interface IObjectSpace {
	
	public void setup();
	public ObjectCollection getCollection();
	public ArrayList<Object> search(Hashtable<String, Object> table);
}
