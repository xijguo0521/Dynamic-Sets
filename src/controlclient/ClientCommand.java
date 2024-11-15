package controlclient;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import middleware.broker.Middleware;

public class ClientCommand {
	
	public static void main(String[] args) {
		Logger log = LogManager.getLogger(ClientCommand.class);
		
		Middleware m = Middleware.getInstance();
		m.setup();
		RoomControl rc = new RoomControl();
		m.init(rc);
		
		log.info("---------test DynamicSet and Aggregation annotations-----------");
		log.info("getPower: " + rc.getPower());
		log.info("getColor: " + rc.getColor());
		log.info("getLuminosity: " + rc.getLum());
		log.info("getPosition: " + rc.getPos() + "\n");
		
		log.info("---------test SelectionConstraint annotation---------------");
		log.info("get power of the powered lights: " + rc.getClrClr());
		log.info("get color of the colored lights: " + rc.getPwrPwr());
		log.info("getPower of the colored lights: " + rc.getClrPower());
		log.info("getColor of the powered lights: " + rc.getPwrColor() + "\n");
		
		log.info("-----------test SelectionUpdate annotation--------------");
		log.info("getLum of the updated lights: " + rc.getUpdatedLum());
		log.info("getPower of the updated lights: " + rc.getPower() + "\n");
		 
		log.info("--------test SetListener annotation------------");
		log.info("getLum of the listener lights: " + rc.getListenerLum());
		log.info("getPower of the listener lights: " + rc.getListenerPwr() + "\n");
		
		log.info("------------test custom light part--------------");
	}
}
 