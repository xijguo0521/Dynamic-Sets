package middleware.aggregation;


public abstract class AAggregations {
	
	public Object aggregate(Object[] o) {
		
		Class<?> type = o[0].getClass();
		
		if(type == Integer.class) {
			int[] integerA = new int[o.length];
			for(int i = 0; i < integerA.length; i++) {
				integerA[i] = ((Integer) o[i]).intValue();
			}
			return intAggregate(integerA);
		}

		else if(type == Float.class) {
			
			float[] floatA = new float[o.length];
			for(int i = 0; i < floatA.length; i++) {
				floatA[i] = ((Float) o[i]).floatValue();
			}
			return floatAggregate(floatA);
		}

		else if(type == Boolean.class) {
			
			boolean[] boolA = new boolean[o.length];
			for(int i = 0; i < boolA.length; i++) {
				boolA[i] = ((Boolean) o[i]).booleanValue();
			}
			return boolAggregate(boolA);
		}

		else if(String.class.isAssignableFrom(type)) {
			return strAggregate((String[]) o);
		}
		return null;
	}
	
	public abstract int intAggregate(int[] obj);
	
	public abstract float floatAggregate(float[] obj);
	
	public abstract boolean boolAggregate(boolean[] obj);
	
	public abstract String strAggregate(String[] obj);
}
