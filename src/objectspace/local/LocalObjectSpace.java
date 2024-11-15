package objectspace.local;

import objectspace.ObjectSpace;

public class LocalObjectSpace extends ObjectSpace {

	private static LocalObjectSpace instance;
	
	private LocalObjectSpace() {}

	public static synchronized LocalObjectSpace getInstance() {
		if(instance == null) {
			synchronized(LocalObjectSpace.class) {
				if(instance == null) {
					instance = new LocalObjectSpace();
				}
			}
		}
		return instance;
	}

	public static void main(String[] args) {
		ObjectSpace localOs = LocalObjectSpace.getInstance();
		localOs.setup();
		Thread t = new Thread(localOs);
		t.start();
	}
}