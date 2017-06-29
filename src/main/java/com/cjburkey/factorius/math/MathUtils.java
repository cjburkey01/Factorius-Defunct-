package com.cjburkey.factorius.math;

public class MathUtils {
	
	public static int clamp(int val, int min, int max) {
		return (int) clamp(val, min, max);
	}
	
	public static float clamp(float val, float min, float max) {
		if(val < min) return min;
		if(val > max) return max;
		return val;
	}
	
}