package controlclient;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import middleware.broker.Middleware;
import objectspace.ILight;
import objectspace.IShade;
import objectspace.LightImpl;
import objectspace.local.LocalObjectSpace;
import objectspace.ShadeImpl;

public class ClientAppTest {

	private Middleware m;
	private LocalObjectSpace objSpace;
	private RoomControl rc;

	@Before
	public void init() {
		objSpace = LocalObjectSpace.getInstance();
		setup();
		m = Middleware.getInstance();
		rc = new RoomControl();
		m.init(rc);
	}

	private void setup() {
		ArrayList<Object> list = objSpace.getCollection().getList();
		if(!list.isEmpty()) {
			list.clear();
		}
		ILight l1 = new LightImpl(true, true, 20);
		ILight l2 = new LightImpl(true, false, 40);
		ILight l3 = new LightImpl(true, false, 60);
		IShade s = new ShadeImpl(0.5f);
		objSpace.getCollection().add(l1);
		objSpace.getCollection().add(l2);
		objSpace.getCollection().add(l3);
		objSpace.getCollection().add(s);
	}

	@Test
	public void testAggregation() {
		assertEquals("test getLuminosity", rc.getLum(), 40);
		assertEquals("test isPowered", rc.getPower(), true);
		assertEquals("test isColored", rc.getColor(), false);
	}

}
