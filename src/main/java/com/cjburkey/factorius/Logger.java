package com.cjburkey.factorius;

import java.io.PrintStream;
import java.util.Calendar;

public final class Logger {
	
	public static void info(Object msg) {
		log(System.out, msg);
	}
	
	public static void warn(Object msg) {
		log(System.err, msg);
	}
	
	public static void blank() {
		System.out.println();
	}
	
	private static void log(PrintStream stream, Object msg) {
		String time = getTimeString();
		String out = (msg == null) ? "null" : msg.toString();
		while(out.startsWith("\n")) {
			out = out.substring(1);
		}
		while(out.endsWith("\n")) {
			out = out.substring(0, out.length() - 1);
		}
		stream.println(Static.OPEN_BRACKET + time + Static.CLOSE_BRACKET + Static.SPACE + out);
	}
	
	private static String getTimeString() {
		Calendar now = Calendar.getInstance();
		String hour = String.format("%02d", now.get(Calendar.HOUR_OF_DAY));
		String minute = String.format("%02d", now.get(Calendar.MINUTE));
		String second = String.format("%02d", now.get(Calendar.SECOND));
		StringBuilder out = new StringBuilder();
		out.append(hour);
		out.append(Static.COLON);
		out.append(minute);
		out.append(Static.COLON);
		out.append(second);
		return out.toString();
	}
	
}