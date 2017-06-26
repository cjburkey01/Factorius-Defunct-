package com.cjburkey.factorius;

import java.text.NumberFormat;

public final class Numbers {
	
	public static String format(long number) {
		return NumberFormat.getInstance().format(number);
	}
	
	public static String format(double number) {
		return NumberFormat.getInstance().format(number);
	}
	
}