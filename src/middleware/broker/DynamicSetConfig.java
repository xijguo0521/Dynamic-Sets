package middleware.broker;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Hashtable;

import middleware.ISelectionConstraint;
import middleware.annotations.Aggregation;
import middleware.annotations.SelectionConstraint;
import middleware.annotations.SetListener;

public class DynamicSetConfig {

	private Hashtable<String, Class<?>> aggrTable = new Hashtable<String, Class<?>>();
	private Hashtable<String, Object> selConstraintTable = new Hashtable<String, Object>();
	private boolean isUpdated;
	private ArrayList<Class<?>> listenerList = new ArrayList<Class<?>>();
	private Hashtable<Class<?>, Object> membersAddedTable = new Hashtable<Class<?>, Object>();
	private Hashtable<Class<?>, Object> membersRemovedTable = new Hashtable<Class<?>, Object>();
	private Class<?> classType;
	
	/**
	 * Create the aggregation function table
	 * @param list contains the field marked with aggregation annotation
	 */
	public void setAggrConfig(Field f) { 
		for(Aggregation a : f.getAnnotationsByType(Aggregation.class)) {
			aggrTable.put(a.method(), a.clazz());
		}
	}

	public Hashtable<String, Class<?>> getAggrTable() {
		return aggrTable;
	}

	public void setSelConfig(Field f) {
		Class<?> sc = f.getAnnotation(SelectionConstraint.class).clazz();
		ISelectionConstraint scInstance = null;
		try {
			scInstance = (ISelectionConstraint) sc.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.selConstraintTable = (Hashtable<String, Object>) scInstance.select();
	}

	public Hashtable<String, Object> getSelConstraintTable() {
		return selConstraintTable;
	}

	public void setUpdateConfig(boolean b) {
		this.isUpdated = b;
	}

	public boolean isUpdated() {
		return isUpdated;
	}

	public void setListenerConfig(Field f) { 
		for(SetListener sl : f.getAnnotationsByType(SetListener.class)) {
			listenerList.add(sl.clazz());
		}
	} 

	public ArrayList<Class<?>> getListenerList() {
		return listenerList;
	}

	public void setMembersAddConfig(Class<?> c, Method m) {
		membersAddedTable.put(c, m); 
	}
	
	public void setMemberRemoveConfig(Class<?> c, Method m) {
		membersRemovedTable.put(c,  m);
	}
	
	public Hashtable<Class<?>, Object> getMembersAddTable() {
		return membersAddedTable;
	}
	
	public Hashtable<Class<?>, Object> getMemberRemoveTable() {
		return membersRemovedTable;
	}
	
	public void setClassType(Class<?> classType) {
		this.classType = classType;
	}
	
	public Class<?> getClassType() {
		return classType;
	}
}
