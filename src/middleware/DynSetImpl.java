package middleware;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import middleware.annotations.OnMemberAdded;
import middleware.annotations.OnMemberRemoved;
import objectspace.ObjectSpace;
import objectspace.local.LocalObjectSpace;

public class DynSetImpl<T> implements IDynSet<T> {
	
	private Logger log = LogManager.getLogger(DynSetImpl.class);
	public HashSet<Object> dsMembers = new HashSet<Object>();
	private HashSet<Object> listenerSet = new HashSet<Object>();
	private ISelectionConstraint sc;
	private boolean autoUpdate;
	private ArrayList<IDynSet<?>> dsList = new ArrayList<IDynSet<?>>();

	@Override
	public boolean add(Object e) {
		return dsMembers.add(e);
	} 

	@Override
	public boolean addAll(Collection<?> c) {
		return dsMembers.addAll(c);
	}

	@Override
	public void clear() {
		dsMembers.clear();
	}

	@Override
	public boolean contains(Object o) {
		return dsMembers.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return dsMembers.containsAll(c);
	}

	@Override
	public boolean isEmpty() {
		return dsMembers.isEmpty();
	}

	@Override
	public Iterator<Object> iterator() {
		return dsMembers.iterator();
	}

	@Override
	public boolean remove(Object o) {
		return dsMembers.remove(o);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return dsMembers.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return dsMembers.retainAll(c);
	}

	@Override
	public int size() {
		return dsMembers.size();
	}

	@Override
	public Object[] toArray() {
		return dsMembers.toArray();
	}

	@Override
	public Object[] toArray(Object[] a) {
		return dsMembers.toArray(a);
	}

	@Override
	public void subset(IDynSet<?> originalSet, ISelectionConstraint sc) {
		dsMembers.clear();
		Object[] params = null;
		Hashtable<String, Object> constraintT = (Hashtable<String, Object>) sc.select();
		for(Object o : originalSet) {
			Class<?> clazz = o.getClass().getInterfaces()[0];
			if(clazz.equals(constraintT.get("interface"))) {
				Method[] methods = clazz.getMethods();
				for(Method m : methods) {
					String methodName = m.getName();
					
					if(constraintT.containsKey(methodName)) {
		
						try {
							Object value = m.invoke(o, params);
							if(constraintT.get(methodName).equals(value)) {
								add(o);
							}
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block1
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	@Override
	public void union(IDynSet<?>... s) {
		storeDsArray(s);
		dsMembers.clear();
		for(int i = 0; i < s.length; i++)
			for(Object o : s[i].getSet())
				add(o);
	}
 
	@Override
	public void intersect(IDynSet<?>... s) {
		storeDsArray(s);
		dsMembers.clear();
		for(Object o : s[0].getSet()) 
			add(o);
		
		HashSet<Object> helper = new HashSet<Object>();
		for(int i = 1; i < s.length; i++) 
			for(Object o : s[i].getSet()) 
				helper.add(o);
			
		    retainAll(helper); 
			helper.clear();
		
	}

	private void storeDsArray(IDynSet<?>... s) {
		dsList.clear();
		for(IDynSet<?> ds : s) {
			dsList.add(ds);
		}
	}
	
	@Override
	public void complement(IDynSet<?> a, IDynSet<?> b) {
		dsList.clear();
		dsList.add(a);
		dsList.add(b);
		dsMembers.clear();
		HashSet<Object> copy = new HashSet<Object>(a.getSet());
		copy.removeAll(b.getSet());
		setSet(copy);
	}

	@Override
	public void difference(IDynSet<?> a, IDynSet<?> b) {
		dsList.clear();
		dsList.add(a);
		dsList.add(b);
		dsMembers.clear();
		HashSet<Object> copyA = new HashSet<Object>(a.getSet());
		HashSet<Object> copyB = new HashSet<Object>(b.getSet()); 
		copyA.removeAll(b.getSet());
		copyB.removeAll(a.getSet());
		copyA.addAll(copyB);
		setSet(copyA);
	}
 
	@Override
	public void select() {
		dsMembers.clear();
		ObjectSpace objSpace = LocalObjectSpace.getInstance();
		ArrayList<Object> list = objSpace.search((Hashtable<String, Object>) sc.select());
		addAll(list);
	}

	@Override
	public void setConstraint(ISelectionConstraint sc) {
		this.sc = sc;
	}

	@Override
	public ISelectionConstraint getConstraint() {
		return sc;
	}

	@Override
	public void setAutoUpdate(boolean b) {
		this.autoUpdate = b;
	}

	@Override
	public boolean isAutoUpdate() {
		return autoUpdate;
	}

	@Override
	public void addListener(Object listener) {
		listenerSet.add(listener);
	}

	@Override
	public void removeListener(Object listener) {
		listenerSet.remove(listener);
	}

	@Override
	public HashSet<Object> getSet() {
		return dsMembers;
	}

	@Override
	public void setSet(HashSet<Object> dynamicSet) {
		this.dsMembers = dynamicSet;
	}
	
	@OnMemberAdded
	public void doMembersAdded() {
		log.info("add listener");
	}
	
	@OnMemberRemoved
	public void doMembersdRemoved() {
		log.info("remove listener");
	}

	@Override
	public ArrayList<IDynSet<?>> getDsList() {
		return dsList;
	}
}