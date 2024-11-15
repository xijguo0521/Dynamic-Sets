package controlclient.selectionconstraint;

import java.util.Dictionary;
import java.util.Hashtable;

import middleware.ISelectionConstraint;
import objectspace.ILight;

public class SecondSelConstraint implements ISelectionConstraint {

	@Override
	public Dictionary<String, Object> select() {
		Hashtable<String, Object> table = new Hashtable<String, Object>();
		table.put("interface", ILight.class);
		table.put("isColored", true);
		return table;
	}

}
