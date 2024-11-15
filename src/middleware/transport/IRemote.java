package middleware.transport;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IRemote extends Remote {
	
	 public ArrayList<Object> getList() throws RemoteException;
}
