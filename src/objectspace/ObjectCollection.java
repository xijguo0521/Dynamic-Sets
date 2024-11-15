package objectspace;
import java.util.*;

public class ObjectCollection {

	public ArrayList<Object> objectList;

	public ObjectCollection() {
		objectList = new ArrayList<Object>();
	}

	public void add(Object obj) {
		objectList.add(obj);
	}

	public void remove(int i) {
		objectList.remove(i);
	}

	public Object searchTable(Hashtable<String, Object> t, String key) {
		if(t.containsKey(key)) {
			return t.get(key);
		}
		return null;
	}

	public ArrayList<Object> getList() {
		return objectList;
	}
	
}
