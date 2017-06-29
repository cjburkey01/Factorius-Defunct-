package com.cjburkey.factorius;

import com.cjburkey.factorius.util.SemVer;

/**
 * Contains unchanging values for Factorius.
 * @author cjburkey
 */
public final class Static {
	
	public static final char COLON = ':';
	public static final char OPEN_BRACKET = '[';
	public static final char CLOSE_BRACKET = ']';
	public static final char SPACE = ' ';
	public static final char DOT = '.';
	public static final char PIPE = '|';
	
	public static final SemVer FACTORIUS_VERSION = new SemVer(0, 0, 1);
	
	public static final long NANOS_PER_SECOND = 1000000000L;
	
	public static final String WINDOW_TITLE = "Factorius " + FACTORIUS_VERSION;
	public static final String FPS = "FPS";
	public static final String UPS = "UPS";
	
}