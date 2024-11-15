package objectspace;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Hashtable;

import org.junit.Before;
import org.junit.Test;

import objectspace.local.LocalObjectSpace;

public class ObjectSpaceTest {

	private ObjectCollection oc;
	private LocalObjectSpace os;
	
	
	@Before
	public void init() {
		os = LocalObjectSpace.getInstance();
		oc = new ObjectCollection();
		oc.add("Rostock");
		oc.add(1234);
		oc.add('z');
	}
	
	@Test
	public void getList() {
		ObjectCollection c = new ObjectCollection();
		assertEquals("test getList", "[]", Arrays.toString(c.getList().toArray()));
		assertEquals("test getList method", "[Rostock, 1234, z]", Arrays.toString(oc.getList().toArray()));
	}

	@Test
	public void testAdd() {
		assertTrue(oc.getList().size() == 3);
		oc.add(new Integer(10));
		assertTrue(oc.getList().size() == 4);
		assertEquals("test add method", "[Rostock, 1234, z, 10]", Arrays.toString(oc.getList().toArray()));
	}

	@Test
	public void testRemove() {
		oc.remove(0);
		assertEquals("test remove", "[1234, z]", Arrays.toString(oc.getList().toArray()));
		oc.add("hello");
		oc.add("blabla");
		oc.remove(1);
		assertEquals("test remove method", "[1234, hello, blabla]", Arrays.toString(oc.getList().toArray()));
	}

	private void setup() {
		if(!os.getCollection().getList().isEmpty()) {
			os.getCollection().getList().clear();
		}
		ILight l1 = new LightImpl(true, true, 20);
		ILight l2 = new LightImpl(true, false, 40);
		ILight l3 = new LightImpl(true, false, 60);
		IShade s = new ShadeImpl(0.5f);
		os.getCollection().add(l1);
		os.getCollection().add(l2);
		os.getCollection().add(l3);
		os.getCollection().add(s);
	}
	
	@Test
	public void testSearch() {
		setup();
		
		Hashtable<String, Object> table = new Hashtable<String, Object>();
		table.put("interface", ILight.class);
		table.put("isPowered", true);
		table.put("isColored", false);
		assertEquals("test search", 2, os.search(table).size());
		
		Hashtable<String, Object> table2 = new Hashtable<String, Object>();
		table2.put("interface", IShade.class);
		table2.put("getPosition", 0.5f);
		assertEquals("test search", 1, os.search(table2).size());
	}
}