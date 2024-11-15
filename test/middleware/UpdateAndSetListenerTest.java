package middleware;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import controlclient.RoomControl;
import middleware.broker.Middleware;
import objectspace.ILight;
import objectspace.LightImpl;
import objectspace.ObjectCollection;
import objectspace.ObjectSpace;
import objectspace.local.LocalObjectSpace;

public class UpdateAndSetListenerTest {

	private Middleware m;
	private ObjectSpace objSpace;
	private RoomControl rc;
	private ObjectCollection oc;
	
	@Before
	public void init() {
		objSpace = LocalObjectSpace.getInstance();
		oc = objSpace.getCollection();
		setup();
		m = Middleware.getInstance();
		rc = new RoomControl();
		m.init(rc);
	}
	
	private void setup() {
		if(!oc.getList().isEmpty()) {
			oc.getList().clear();
		}
		
		ILight l1 = new LightImpl(true, true, 20);
		ILight l2 = new LightImpl(true, false, 40);
		ILight l3 = new LightImpl(false, true, 60);
		ILight l4 = new LightImpl(false, false, 70);
		ILight l5 = new LightImpl(false, true, 80);
		oc.add(l1);
		oc.add(l2);
		oc.add(l3);
		oc.add(l4);
		oc.add(l5);
	}
	
	@Test
	public void testAuptoUpdate() {
		assertEquals("test autoupdate by getting luminosity", rc.getUpdatedLum(), 60);
		oc.remove(1);
		assertEquals("test autoupdate", rc.getUpdatedLum(), 70);
		oc.remove(3);
		assertEquals("test aotuupdate", rc.getUpdatedLum(), 60);
	}
	
	@Test
	public void testSetListener() {
		rc.getListenerLum();
		ILight l6 = new LightImpl(false, false, 100);
		ILight l7 = new LightImpl(true, true, 120);
		oc.add(l6);
		oc.add(l7);
		rc.getListenerLum();
		oc.remove(1);
		rc.getListenerLum();
	}
}
