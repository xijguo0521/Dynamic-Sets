package middleware.broker;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import middleware.IDynSet;
import middleware.aggregation.AAggregations;
import middleware.broker.DynamicSetConfig;
import middleware.transport.Gateway;
import middleware.aggregation.MedianAggregation;
import objectspace.ObjectSpace;
import objectspace.local.LocalObjectSpace;

public class DynamicSetHandler implements InvocationHandler {

	private Object target;
	private DynamicSetConfig config;
	private ObjectSpace os = LocalObjectSpace.getInstance();
	//	private Gateway gateway = new Gateway();
	private Logger log = LogManager.getLogger(DynamicSetHandler.class);
	private IDynSet<?> ds;

	// previous list
	private ArrayList<?> prevList = new ArrayList<Object>();

	// The object list representing object space
	private ArrayList<?> objList;

	public DynamicSetHandler(Object target, DynamicSetConfig config, IDynSet<?> ds) {
		this.ds = ds;
		this.target = target;
		this.config = config;
		if (!config.isUpdated()) {
			objList = (!config.getSelConstraintTable().isEmpty()) ? os.search(config.getSelConstraintTable())
					: os.getCollection().getList();
		}
		else if(!ds.isAutoUpdate()) {
			if(ds.getConstraint() != null) {
				ds.select();
				objList = new ArrayList<Object>(ds.getSet());
			}
		}
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		//		log.debug("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::" + gateway.getAllLists().size());

		//if the method call comes from base interface
		if(config.getClassType().isAssignableFrom(method.getDeclaringClass())) {
			// Check if the auto update is true
			if (config.isUpdated()) {
				// Check if the selection constraints are given
				// If yes, retrieve the entire object space. If not, retrieve the
				// objects that satisfy the constraint
				objList = (!config.getSelConstraintTable().isEmpty()) ? os.search(config.getSelConstraintTable())
						: os.getCollection().getList();
			}
			
			// Check for members added or removed by comparing previous object space
			// with current one
			if (!prevList.isEmpty()) {
				compareObjSpace(prevList, objList);
			} 
			prevList = (ArrayList) objList.clone();
		}
		
		//If the method call comes from compound interface
		else {
			if(ds.isAutoUpdate()) {
				if(ds.getConstraint() != null) {
					 for(IDynSet<?> ds : ds.getDsList()) {
						 ds.select();
					 }
				} 
				objList = new ArrayList<Object>(ds.getSet());
			}
			
			if(method.getDeclaringClass() == IDynSet.class) {
				try {	
					return method.invoke(ds, args);
				} catch(InvocationTargetException e) {
					throw e.getCause();
				}
			}
		}

		Object[] params = null;
		ArrayList<Object> selectedList = new ArrayList<Object>();
		for (Object obj : objList) {
			Class<?>[] interfaces = obj.getClass().getInterfaces();
			for (Class<?> in : interfaces) {
				if (in == method.getDeclaringClass()) {
					Object selected = method.invoke(obj, params);
					selectedList.add(selected);
				} 
			}
		}

		AAggregations aggregation = searchAggrTable(config.getAggrTable(), method.getName());
		return aggregation.aggregate(selectedList.toArray());
	}

	/**
	 * Search for aggregation strategy
	 * 
	 * @param t
	 *            aggregation function table
	 * @param n
	 *            method name of corresponding function call
	 * @return a certain aggregation strategy
	 */
	private AAggregations searchAggrTable(Hashtable<String, Class<?>> t, String n) {
		AAggregations aggr = new MedianAggregation();
		for (String key : t.keySet()) {
			try {
				if (key.equals(n)) {
					return aggr = (AAggregations) t.get(key).newInstance();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return aggr;
	}

	/**
	 * Compare previous list with current list
	 * @param prevL either previous list or current list
	 * @param currentL either previous list or current list
	 */
	private void compareObjSpace(ArrayList<?> prevL, ArrayList<?> currentL) {
		boolean result = Arrays.equals(prevL.toArray(), currentL.toArray());
		if (!result) {
			ArrayList<?> copyPrevL = new ArrayList<Object>(prevL);
			ArrayList<?> copyCurrentL = new ArrayList<Object>(currentL);
			copyPrevL.removeAll(currentL);
			copyCurrentL.removeAll(prevL);

			// Some members have been added
			if (copyPrevL.isEmpty() && !copyCurrentL.isEmpty()) {
				executeListener(config.getMembersAddTable(), copyCurrentL);
			}
			// some members have been removed
			else if (!copyPrevL.isEmpty() && copyCurrentL.isEmpty()) {
				executeListener(config.getMemberRemoveTable(), copyPrevL);
			}
			// If some members have been added and some other members have been
			// removed
			else if (!copyPrevL.isEmpty() && !copyCurrentL.isEmpty()) {
				executeListener(config.getMembersAddTable(), copyCurrentL);
				executeListener(config.getMemberRemoveTable(), copyPrevL);
			}
		}
	}

	/**
	 * Invoke method annotated with either OnMemberAdded or OnMemberRemoved
	 * 
	 * @param t add or remove hashtable
	 */
	private void executeListener(Hashtable<Class<?>, Object> t, ArrayList<?> l) {

		//		log.debug("l size {}, l {}", l.size(), l.get(0).getClass().getInterfaces());
		//		log.debug("l {}", l.get(0).getClass());


		//Check here if the elements in the array list are all the same type
		//To be 
		Class<? extends Object> iface = l.get(0).getClass();

		Object p = Array.newInstance(iface, l.size());

		for (int i = 0; i < l.size(); i++) 
			Array.set(p, i, l.get(i));

		//log.debug("p {}", p);

		for (Class<?> key : t.keySet()) {
			Method m = (Method) t.get(key);

			for (Parameter param : m.getParameters()) {
				//log.debug("m {}, c {}", m.getName(), c.toString());

				Object keyInstance = null;
				try {
					keyInstance = key.newInstance();
					Object[] obj = new Object[] {l.toArray()};
					log.debug(obj.length); 
					//log.debug("key {}, keyInstance {}", key, keyInstance);
					m.invoke(keyInstance, new Object[] { p });

				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
	}
}