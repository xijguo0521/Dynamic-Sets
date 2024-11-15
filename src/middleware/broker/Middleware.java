package middleware.broker;

import java.util.ArrayList;

import objectspace.ObjectSpace;
import objectspace.local.LocalObjectSpace;

public class Middleware {

	private static Middleware instance;
	private ObjectSpace objSpace = LocalObjectSpace.getInstance(); 
	private AnnotationParser ap;
	
	private Middleware() {};

	public static synchronized Middleware getInstance() {
		if(instance == null) {
			synchronized(Middleware.class) {
				if(instance == null) {
					instance = new Middleware();
				}
			}
		}
		return instance;
	}
	
	public void init(Object o) {
		ap = new AnnotationParser();
		ap.setClassToObserve(o);
		ap.parse();
	}
	
	public ArrayList<Object> getObjCollection() {
		return objSpace.getCollection().getList();
	}
	
	public void setup() {
		objSpace.setup();
	}
}
