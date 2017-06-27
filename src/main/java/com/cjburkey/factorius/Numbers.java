package com.cjburkey.factorius;

import java.text.NumberFormat;

public final class Numbers {
	
	public static String format(long number) {
		return getFormat().format(number);
	}
	
	public static String format(double number) {
		return getFormat().format(number);
	}
	
	public static NumberFormat getFormat() {
		return NumberFormat.getInstance();
	}
	
}