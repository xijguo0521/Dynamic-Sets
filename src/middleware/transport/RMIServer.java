package middleware.transport;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Create and export remote objects 
 * @author Xijie Guo
 *
 */
public class RMIServer {
	
	private Logger log = LogManager.getLogger(RMIServer.class);
	
	public void export() {
		try {
			LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
		} catch (RemoteException e) {
			log.error("rmi server failed: {}", e.toString());
			//e.printStackTrace();
		}
		IRemote remoteObj = null;
		try {
			remoteObj = new RemoteImpl(); 
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			log.info("IP address of JVM host: {}", InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Naming.rebind("IRemote", remoteObj);
		} catch (RemoteException | MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("rmi server is ready");
		
	}
	
	public static void main(String[] args) {
		RMIServer server = new RMIServer();
		server.export();
	}
}
