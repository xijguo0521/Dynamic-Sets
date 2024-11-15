package middleware;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import middleware.annotations.OnMemberAdded;
import middleware.annotations.OnMemberRemoved;
import objectspace.ILight;

public class LightSetListener2 implements ILightSetListener {

	private Logger log = LogManager.getLogger(LightSetListener2.class);
	
	@OnMemberAdded
	public void add2(ILight... l) {
		log.info("second set listener added");
		log.info(Arrays.toString(l));
	}
	
	@OnMemberRemoved
	public void remove2(ILight... l) {
		log.info("second set listener removed");
		log.info(Arrays.toString(l));
	}
}
