package objectspace.local;
import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import objectspace.ILight;

public class Test {
	
	public static void main(String[] args) {
		Logger log = LogManager.getLogger(Test.class);
		LocalObjectSpace object = LocalObjectSpace.getInstance();
		object.setup();;
		
		Hashtable<String, Object> table = new Hashtable<String, Object>();
		table.put("interface", ILight.class);
		table.put("isPowered", true);
		table.put("isColored", false);
		
		ArrayList<Object> list = object.search(table);
		log.info(list.size());
	}
}