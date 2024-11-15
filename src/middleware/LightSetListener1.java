package middleware;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import middleware.annotations.OnMemberAdded;
import middleware.annotations.OnMemberRemoved;
import objectspace.ILight;

public class LightSetListener1 implements ILightSetListener {
	
	private Logger log = LogManager.getLogger(LightSetListener1.class);
	
	@OnMemberAdded
	public static void add1(ILight[] l) {
		LogManager.getLogger(LightSetListener1.class).info("first set listener added");
		LogManager.getLogger(LightSetListener1.class).info(Arrays.toString(l));
	}
	
	@OnMemberRemoved
	public void remove1(ILight[] l) {
		log.info("first set listener removed");
		log.info(Arrays.toString(l));
	}
}
