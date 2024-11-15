package middleware.transport;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Obtains a stub for the registry on the server's host, 
 * looks up the remote object's stub by name in the registry,
 * and then invokes methods on the remote object using the stub
 * @author Xijie Guo
 *
 */
public class RMIClient {
	
	private static Logger log = LogManager.getLogger(RMIClient.class);
	
	public List<Object> getRemoteList() throws RemoteException {
		
		log.debug("getList() on IRemote stub");
		IRemote stub = null;
		try {
			stub = (IRemote) Naming.lookup("IRemote");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List l = stub.getList();
		log.debug("received list via stub of size {}", l.size());
		return l;
	}
	
	public static void main(String[] args) {
		RMIClient client = new RMIClient();
		try {
			log.debug(client.getRemoteList().size());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
