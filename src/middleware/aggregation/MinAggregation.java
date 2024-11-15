package middleware.aggregation;

import java.util.Arrays;

public class MinAggregation extends AAggregations {

	public int intAggregate(int[] obj) {
		Arrays.sort(obj);
		return obj[0];
	}
	
	public float floatAggregate(float[] obj) {
		Arrays.sort(obj);
		return obj[0];
	}
	
	public boolean boolAggregate(boolean[] obj) {
		for(boolean o : obj) {
			if(!o) {
				return o;
			}
		}
		return true;
	}

	public String strAggregate(String[] obj) {
		Arrays.sort(obj);
		return obj[0];
	}
}
