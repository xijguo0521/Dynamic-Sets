package middleware.transport;

import java.rmi.RemoteException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import objectspace.ObjectSpace;
import objectspace.local.LocalObjectSpace;

public class Gateway {
	
	private Logger log = LogManager.getLogger(Gateway.class);
	private RMIServer server;
	private RMIClient client;
	
	public Gateway() {}
	
	public ArrayList<Object> getAllLists() throws RemoteException {
		server = new RMIServer();
		client = new RMIClient();
		server.export();
		ObjectSpace localOs = LocalObjectSpace.getInstance();
		ArrayList<Object> mergeList = localOs.getCollection().getList();
		log.debug("calling RMI client get remote list.");
		log.debug("get remote list size: " + client.getRemoteList().size());
		mergeList.addAll(client.getRemoteList());
		log.debug("merge list size: " + mergeList.size());
		return mergeList;
	}
}
