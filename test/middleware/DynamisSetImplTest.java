package middleware;

import static org.junit.Assert.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import controlclient.selectionconstraint.FirstSelConstraint;
import objectspace.ILight;
import objectspace.LightImpl;

public class DynamisSetImplTest {

	private IDynSet<?> ds;
	private IDynSet<?> ds1;
	private IDynSet<?> ds2;
	private Logger log = LogManager.getLogger(DynamisSetImplTest.class);
	
	@Before
	public void init() {
		setup();
		ds = new DynSetImpl<Object>();
	}
	
	private void setup() {
		ds1 = new DynSetImpl<Object>();
		ds2 = new DynSetImpl<Object>();
		ILight l1 = new LightImpl(true, true, 20);
		ILight l2 = new LightImpl(false, false, 30);
		ILight l3 = new LightImpl(true, false, 40);
		ds1.add(l1);
		ds1.add(l2);
		ds1.add(l3);
		ILight l4 = new LightImpl(false, false, 60);
		ILight l5 = new LightImpl(true, true, 70);
		ds2.add(l4);
		ds2.add(l5);
		ds2.add(l3);
	}
	
	@Test
	public void testUnion() {
		ds.union(ds1, ds2);
		assertEquals("Test union method", ds.getSet().size(), 5);
	}

	@Test
	public void testIntersect() {
		ds.intersect(ds1, ds2);
		assertEquals("Test intersection", ds.getSet().size(), 1);
	}
	
	@Test
	public void testComplement() {
		ds.complement(ds1, ds2);
		for(Object o : ds.getSet()) {
			log.info(o);
		}
		assertEquals("Test complement", ds.getSet().size(), 2);
	}
	
	@Test
	public void testDifference() {
		ds.difference(ds1, ds2);
		assertEquals("Test difference", ds.getSet().size(), 4);
	}
	
	@Test
	public void testSubset() {
		ISelectionConstraint sc = new FirstSelConstraint();
		ds.subset(ds1, sc);
		assertEquals("Test subset", ds.getSet().size(), 2);
		for(Object o : ds.getSet()) {
			log.info(o);
		}
	}
}
