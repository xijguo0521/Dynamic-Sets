package objectspace.rmi;

import objectspace.ObjectSpace;

public class RMIObjectSpace extends ObjectSpace implements Runnable {

	private static RMIObjectSpace instance;

	private RMIObjectSpace() {}

	public static synchronized RMIObjectSpace getInstance() {
		if(instance == null) {
			synchronized(RMIObjectSpace.class) {
				if(instance == null) {
					instance = new RMIObjectSpace();
				}
			}
		}
		return instance;
	}

	public static void main(String args[]) {
		ObjectSpace rmiOs = RMIObjectSpace.getInstance();
		rmiOs.setup();
		Thread t = new Thread(rmiOs);
		t.start();
	}
}
