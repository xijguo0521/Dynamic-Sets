package objectspace;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ObjectSpace implements IObjectSpace, Runnable {

	private ObjectCollection collection = new ObjectCollection();
	private Logger log = LogManager.getLogger(ObjectSpace.class);

	@Override
	public void setup() {
		IShade[] shades = new ShadeImpl[4];
		for(int i = 0; i < shades.length; i++) {
			shades[i] = new ShadeImpl((float) (Math.random() * 100));
			collection.add(shades[i]);
			log.info("number of shades " + i + ": ");
			log.info("getPosition: " + shades[i].getPosition());
			log.info("-------------------------------");
		}

		ILight[] lights = new LightImpl[5];
		for(int i = 0; i < lights.length; i++) {
			lights[i] = new LightImpl(Math.random() < 0.5, Math.random() < 0.5,
					(int) (Math.random() * 100));
			collection.add(lights[i]);
			log.info("number " + i + ": ");
			log.info("isPowered: " + lights[i].isPowered());
			log.info("isColored: " + lights[i].isColored());
			log.info("getLuminosity: " + lights[i].getLuminosity());
			log.info("----------------------------");
		}
	}

	@Override
	public ObjectCollection getCollection() {
		return collection;
	}

	@Override
	public ArrayList<Object> search(Hashtable<String, Object> table) {
		ArrayList<Object> selected = new ArrayList<Object>();
		for(Object o : collection.getList()) {
			Class<?>[] interfaces = o.getClass().getInterfaces();
			for(Class<?> in : interfaces) {
				if(in == table.get("interface")) {
					selected.add(o);
					break;
				}
			}
		}
		return getFinalList(selected, table);
	}

	private ArrayList<Object> getFinalList(ArrayList<Object> l, Hashtable<String, Object> t) {
		ArrayList<Object> finalList = new ArrayList<Object>();
		Object[] params = null;
		int count = 0;
		for(Object obj : l) {
			Method[] methods = obj.getClass().getMethods();
			for(Method m : methods) {
				for(Entry<String, Object> entry : t.entrySet()) {
					try {
						if(m.getName().equals(entry.getKey())
								&& m.invoke(obj, params).equals(entry.getValue())) {
							count++;
						}
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
			}
			
			if(count == t.size() - 1) {
				finalList.add(obj);
			}
			count = 0;
		}
		return finalList = (!finalList.isEmpty()) ? finalList : null;
	}

	public void reset() {
		for(Object o : getCollection().getList()) {
			if(Math.random() < 0.5) {
				resetAtr(o);
			}
		}
	}

	private void resetAtr(Object obj) {
		Object[] params = null;
		Class<?> c = obj.getClass();
		Method[] methods = c.getMethods();
		for(Method m : methods) {
			if(Math.random() < 0.5) {
				try {
					if(m.getName().contains("set")) {
						for(Class<?> type : m.getParameterTypes()) {
							if(type.equals(Boolean.TYPE)) {
								m.invoke(obj, Math.random() < 0.5);
							}

							else if(type.equals(Integer.TYPE)) {
								m.invoke(obj, (int) (Math.random() * 100));
							}

							else if(type.equals(Float.TYPE)) {
								m.invoke(obj, (float) (Math.random() * 100));
							}
							break;
						}
					}
					else if(m.getName().contains("get") || m.getName().contains("is")) {
						if(m.getReturnType() == boolean.class) {
							log.info(m.getName() + m.invoke(obj, params));
						}
						else if(m.getReturnType() == int.class) {
							log.info(m.invoke(obj, params));
						}
						else if(m.getReturnType() == float.class) {
							log.info(m.invoke(obj, params));
						}
					}

				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}

	@Override
	public void run() {
		while(true) {
			reset();
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
