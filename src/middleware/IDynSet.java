package middleware;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public interface IDynSet<T> extends Set<Object> {
	// derivation from existing sets
	public void subset(IDynSet<?> ds, ISelectionConstraint sc);
	public void union(IDynSet<?>... s);
	public void intersect(IDynSet<?>... s);
	public void complement(IDynSet<?> a, IDynSet<?> b);
	public void difference(IDynSet<?> a, IDynSet<?> b);
	
	// selection constraints
	public void select();
	public void setConstraint(ISelectionConstraint scs);
	public ISelectionConstraint getConstraint();
	
	// member updating
	public void setAutoUpdate(boolean b);
	public boolean isAutoUpdate();
	
	// Set listener
	public void addListener(Object listener);
	public void removeListener(Object listener);
	
	// Getter, setter
	public HashSet<Object> getSet();
	public void setSet(HashSet<Object> dynamicSet);

	public ArrayList<IDynSet<?>> getDsList();
}
