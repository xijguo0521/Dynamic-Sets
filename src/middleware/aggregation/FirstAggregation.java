package middleware.aggregation;


public class FirstAggregation extends AAggregations {
	
	public int intAggregate(int[] obj) {
		return obj[0];
	}
	
	public float floatAggregate(float[] obj) {
		return obj[0];
	}
	
	public boolean boolAggregate(boolean[] obj) {
		return obj[0];
	}

	public String strAggregate(String[] obj) {
		return obj[0];
	}
}
