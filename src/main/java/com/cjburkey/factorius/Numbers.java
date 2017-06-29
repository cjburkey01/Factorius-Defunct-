package com.cjburkey.factorius;

import java.text.NumberFormat;

/**
 * Number formatting utility.
 * @author cjburkey
 */
public final class Numbers {
	
	/**
	 * Formats a 64/32 bit number.
	 * @param number The number.
	 * @return The formatted number.
	 */
	public static String format(long number) {
		return getFormat().format(number);
	}
	
	/**
	 * Formats a 64/32 bit float.
	 * @param number The number.
	 * @return The formatted number.
	 */
	public static String format(double number) {
		return getFormat().format(number);
	}
	
	/**
	 * Gets the current NumberFormat instance.
	 * @return The instance of NumberFormat for the current local.
	 */
	public static NumberFormat getFormat() {
		return NumberFormat.getInstance();
	}
	
}