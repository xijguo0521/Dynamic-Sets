package middleware.aggregation;

import java.util.Arrays;

public class AverageAggregation extends AAggregations {

	public int intAggregate(int[] obj) {
		int sum = 0;
		for(int i = 0; i < obj.length; i++) {
			sum += obj[i];
		}
		return sum / obj.length;
	}

	public float floatAggregate(float[] obj) {
		float sum = 0;
		for(int i = 0; i < obj.length; i++) {
			sum += obj[i];
		}
		return sum / obj.length;
	}

	public boolean boolAggregate(boolean[] obj) {
		for(int i = 0; i < obj.length; i++) {
			for(int j = 1; j < obj.length - i; j++) {
				if(obj[j - 1] && !obj[j]) {
					swap(obj, j - 1, j);
				}
			}
		}
		return obj[obj.length / 2];
	}

	public String strAggregate(String[] obj) {
		Arrays.sort(obj);
		return obj[obj.length / 2];
	}
	
	private void swap(boolean[] array, int i, int j) {
		boolean temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}

}
