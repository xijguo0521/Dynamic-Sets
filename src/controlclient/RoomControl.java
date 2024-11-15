package controlclient;

import java.util.HashSet;

import controlclient.selectionconstraint.FirstSelConstraint;
import controlclient.selectionconstraint.SecondSelConstraint;
import middleware.CustomLights;
import middleware.LightSetListener1;
import middleware.LightSetListener2;
import middleware.aggregation.MaxAggregation;
import middleware.aggregation.MedianAggregation;
import middleware.annotations.Aggregation;
import middleware.annotations.DynamicSet;
import middleware.annotations.SelectionConstraint;
import middleware.annotations.SelectionUpdate;
import middleware.annotations.SetListener;
import objectspace.ILight;
import objectspace.IShade;

public class RoomControl {
	
	@DynamicSet
	@Aggregation(clazz = MedianAggregation.class, method = "isPowered")
	@Aggregation(clazz = MaxAggregation.class, method = "getLuminosity")
	public ILight roomLight;
	
	@DynamicSet
	public IShade roomShades;
	
	@DynamicSet
	@SelectionConstraint(clazz = FirstSelConstraint.class)
	public ILight lightsOn;
	
	@DynamicSet
	@SelectionConstraint(clazz = SecondSelConstraint.class)
	public ILight lightsColored;
	
	@DynamicSet
	@SelectionUpdate
	public ILight lightsUpdated;
	
	@DynamicSet
	@SetListener(clazz = LightSetListener1.class)
	@SetListener(clazz = LightSetListener2.class)
	public ILight li;
	
	@DynamicSet
	public CustomLights c;
	
	@DynamicSet
	@SelectionConstraint(clazz = FirstSelConstraint.class)
	public CustomLights c1;
	
	@DynamicSet
	@SelectionConstraint(clazz = SecondSelConstraint.class)
	public CustomLights c2;
	
	
	//For DynamicSet and Aggregation annotation
	public Object getPower() {
		return roomLight.isPowered();
	}
	
	public Object getColor() {
		return roomLight.isColored();
	}
	
	public Object getLum() {
		return roomLight.getLuminosity();
	}
	
	public Object getPos() {
		return roomShades.getPosition();
	}
	
	
	//For selectionConstraint annotation
	public Object getClrPower() {
		return lightsColored.isPowered();
	}
	
	public Object getClrClr() {
		return lightsColored.isColored();
	}
	
	public Object getPwrColor() {
		return lightsOn.isColored();
	}
	
	public Object getPwrPwr() {
		return lightsOn.isPowered();
	}
	
	
	//For SelectionUpdate annotation
	public Object getUpdatedPwr() {
		return lightsUpdated.isPowered();
	}
	
	public Object getUpdatedClr() {
		return lightsUpdated.isColored();
	}
	
	public Object getUpdatedLum() {
		return lightsUpdated.getLuminosity();
	}
	
	
	//For SetListener annotation
	public Object getListenerLum() {
		return li.getLuminosity();
	}
	
	public Object getListenerPwr() {
		return li.isPowered();
	}
	
	//For Custom light part
	public void makeUnion() {
		c.union(c1, c2);
	}
	
	public void makeIntersection() {
		c.intersect(c1, c2);
	}
	
	public Object getBrightness() {
		return c.getLuminosity();
	}
	
	public void makeComplement() {
		c.complement(c1, c2);
	}
}
