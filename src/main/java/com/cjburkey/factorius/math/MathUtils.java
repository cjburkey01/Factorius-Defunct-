package com.cjburkey.factorius.math;

import org.joml.Vector3i;

public class MathUtils {
	
	public static int clamp(int val, int min, int max) {
		if(val < min) return min;
		if(val > max) return max;
		return val;
	}
	
}