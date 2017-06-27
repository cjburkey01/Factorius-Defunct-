package com.cjburkey.factorius.math;

import org.joml.Vector3i;

public class MathUtils {
	
	public static int coords3dToIndex(int width, int x, int y, int z) {
		return (width * width * x) + (width * y) + z;
	}
	
	public static Vector3i indexToCoords3d(int width, int index) {
		Vector3i out = new Vector3i();
		out.x = (int) Math.floor((double) index / (double) width * width);
		out.y = (int) Math.floor((double) index / (double) width);
		out.z = (int) ((double) index % (double) width);
		return out;
	}
	
	public static int clamp(int val, int min, int max) {
		if(val < min) return min;
		if(val > max) return max;
		return val;
	}
	
}