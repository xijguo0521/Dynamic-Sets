package middleware.broker;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import middleware.broker.DynamicSetConfig;
import middleware.DynSetImpl;
import middleware.IDynSet;
import middleware.ISelectionConstraint;
import middleware.annotations.Aggregation;
import middleware.annotations.Aggregations;
import middleware.annotations.DynamicSet;
import middleware.annotations.OnMemberAdded;
import middleware.annotations.OnMemberRemoved;
import middleware.annotations.SelectionConstraint;
import middleware.annotations.SelectionUpdate;
import middleware.annotations.SetListener;
import middleware.annotations.SetListeners;

public class AnnotationParser {

	private Object o;
	private Logger log = LogManager.getLogger(AnnotationParser.class);

	public void setClassToObserve(Object o) {
		this.o = o;
	}

	public void parse() {
		parseFields();
	}

	private void parseFields() {
		DynamicSetConfig config = null;
		IDynSet<Object> ds = null;
		Field[] fields = o.getClass().getFields(); 
		for(Field f : fields) {
			if(f.isAnnotationPresent(DynamicSet.class)) {
				config = new DynamicSetConfig(); 
				ds = new DynSetImpl<Object>();

				if(f.isAnnotationPresent(Aggregation.class) ||
						f.isAnnotationPresent(Aggregations.class)) {
					config.setAggrConfig(f);
				}

				else if(f.isAnnotationPresent(SelectionConstraint.class)) {
					// If the type of the field is a base interface type 
					if(f.getType().getInterfaces().length == 0) {
						config.setSelConfig(f);
					}
					//Compound interface type
					else {
						Class<?> selConstraintCls = f.getAnnotation(SelectionConstraint.class).clazz();
						ISelectionConstraint sc = null;
						try {
							sc = (ISelectionConstraint) selConstraintCls.newInstance();
						} catch (InstantiationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						ds.setConstraint(sc);
					}
				}

				else if(f.isAnnotationPresent(SelectionUpdate.class)) {
					boolean isUpdated = f.getAnnotation(SelectionUpdate.class).update();
					//If the type of the field is a base interface type
					if(f.getType().getInterfaces().length == 0) {
						config.setUpdateConfig(isUpdated);
					}
					//Compound interface type
					else {
						ds.setAutoUpdate(isUpdated);
					}

				}

				else if(f.isAnnotationPresent(SetListener.class) ||
						f.isAnnotationPresent(SetListeners.class)) {
					config.setListenerConfig(f);
					parseMethods(config);
				}

			}
			try { 
				Class<?> ifaceCls = f.getType();
				config.setClassType(ifaceCls);
				Object objProxy = Proxy.newProxyInstance(ifaceCls.getClassLoader(), 
						new Class[]{ifaceCls}, new DynamicSetHandler(ifaceCls.getClassLoader(), config, ds));
				f.set(o, objProxy);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	/**
	 * parse all methods in all concrete classes that implment ILightSetListener class
	 * @param config the DynamicSetConfig instance
	 */
	private void parseMethods(DynamicSetConfig config) {
		for(Class<?> listener : config.getListenerList()) {
			Method[] methods = listener.getMethods();
			for(Method m : methods) {
				if(m.isAnnotationPresent(OnMemberAdded.class)) {
					config.setMembersAddConfig(listener, m);
				}
				else if(m.isAnnotationPresent(OnMemberRemoved.class)) {
					config.setMemberRemoveConfig(listener, m);
				}
			}
		}
	}

}
