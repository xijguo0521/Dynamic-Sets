package middleware.transport;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import objectspace.ObjectSpace;
import objectspace.rmi.RMIObjectSpace;

@SuppressWarnings("serial")
public class RemoteImpl extends UnicastRemoteObject implements IRemote {

	private Logger log = LogManager.getLogger(RemoteImpl.class);
	private ObjectSpace rmiSpace = RMIObjectSpace.getInstance();
	
	protected RemoteImpl() throws RemoteException {
		super();
	}

	@Override
	public ArrayList<Object> getList() throws RemoteException {
		log.debug("hello");
		log.debug("RemoteImpl getList() of size {}", rmiSpace.getCollection().getList().size());
		return rmiSpace.getCollection().getList();
	}
}
