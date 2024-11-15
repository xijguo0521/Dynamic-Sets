package middleware;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import controlclient.RoomControl;
import middleware.broker.Middleware;
import objectspace.ILight;
import objectspace.IShade;
import objectspace.LightImpl;
import objectspace.ShadeImpl;
import objectspace.local.LocalObjectSpace;

public class AggregationTest {

	private RoomControl rc;
	private Middleware m;
	private LocalObjectSpace os;
	
	@Before
	public void init() {
		os = LocalObjectSpace.getInstance();
		setup();
		m = Middleware.getInstance();
		rc = new RoomControl();
		m.init(rc);
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
	public void test() {
		assertEquals("test aggregation", rc.getLum(), 60);
		assertEquals("test aggregation", rc.getPower(), true);
		assertEquals("test aggregation", rc.getColor(), false);
	}
	
	
}
