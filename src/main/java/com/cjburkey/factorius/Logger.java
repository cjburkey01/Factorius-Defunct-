package com.cjburkey.factorius;

import java.util.Calendar;

public final class Logger {
	
	public static void info(Object msg) {
		String time = getTimeString();
		String out = (msg == null) ? "null" : msg.toString();
		System.out.println(Static.OPEN_BRACKET + time + Static.CLOSE_BRACKET + Static.SPACE + out);
	}
	
	private static String getTimeString() {
		Calendar now = Calendar.getInstance();
		int hour = now.get(Calendar.HOUR_OF_DAY);
		int minute = now.get(Calendar.MINUTE);
		int second = now.get(Calendar.SECOND);
		StringBuilder out = new StringBuilder();
		out.append(hour);
		out.append(Static.COLON);
		out.append(minute);
		out.append(Static.COLON);
		out.append(second);
		return out.toString();
	}
	
}