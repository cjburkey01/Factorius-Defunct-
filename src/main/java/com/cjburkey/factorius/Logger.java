package com.cjburkey.factorius;

import java.io.PrintStream;
import java.util.Calendar;

/**
 * Interface to allow easier printing to console and files.
 * @author cjburkey
 */
public final class Logger {
	
	/**
	 * Print out a message to System.out.
	 * @param msg The message to print.
	 */
	public static void info(Object msg) {
		log(System.out, msg);
	}
	
	/**
	 * Print out a message to System.err.
	 * @param msg The message to print.
	 */
	public static void warn(Object msg) {
		log(System.err, msg);
	}
	
	/**
	 * Prints a blank line to System.out.
	 */
	public static void blank() {
		System.out.println();
	}
	
	/**
	 * Prints a message to the stream.
	 * @param stream The PrintStream to print the message.
	 * @param msg The message to print.
	 */
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
	
	/**
	 * Gets the information for the current time in this format: HH:MM:SS.
	 * @return String with time information.
	 */
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