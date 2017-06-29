package com.cjburkey.factorius.math;

/**
 * Quick maths utilities
 * @author cjburkey
 */
public final class MathUtils {
	
	/**
	 * Clamps the value to the min and max.
	 * If the value is less than the minimum, it is set to the minimum.
	 * If the value is greater than the maximum, it is set to the maximum.
	 * (32bit integer function)
	 * @param val The value to clamp.
	 * @param min The minimum value.
	 * @param max The maximum value.
	 * @return Clamped value.
	 */
	public static int clamp(int val, int min, int max) {
		return (int) clamp(val, min, max);
	}
	
	/**
	 * Clamps the value to the min and max.
	 * If the value is less than the minimum, it is set to the minimum.
	 * If the value is greater than the maximum, it is set to the maximum.
	 * (64bit float function)
	 * @param val The value to clamp.
	 * @param min The minimum value.
	 * @param max The maximum value.
	 * @return Clamped value.
	 */
	public static float clamp(float val, float min, float max) {
		if(val < min) return min;
		if(val > max) return max;
		return val;
	}
	
}