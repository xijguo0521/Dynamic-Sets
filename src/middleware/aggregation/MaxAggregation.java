package middleware.aggregation;

import java.util.Arrays;

public class MaxAggregation extends AAggregations {

	public int intAggregate(int[] obj) {
		Arrays.sort(obj);
		return obj[obj.length - 1];
	}
	
	public float floatAggregate(float[] obj) {
		Arrays.sort(obj);
		return obj[obj.length - 1];
	}
	
	public boolean boolAggregate(boolean[] obj) {
		for(boolean o : obj) {
			if(o) {
				return o;
			}
		}
		return false;
	}

	public String strAggregate(String[] obj) {
		Arrays.sort(obj);
		return obj[obj.length - 1];
	}
}
