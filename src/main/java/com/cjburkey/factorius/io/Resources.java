package com.cjburkey.factorius.io;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.cjburkey.factorius.Logger;

/**
 * Handles in-jar resources for the game.
 * @author cjburkey
 */
public final class Resources {
	
	private static final char SLASH = '/';
	private static final String SLASHS = "/";
	private static final String BSLASHS = "\\";
	
	/**
	 * Converts domain and path to a resource location.
	 * @param domain The domain of the files.
	 * @param path The path to the specific file.
	 * @return Location.
	 */
	public static String getLocation(String domain, String path) {
		while(path.startsWith(SLASHS) || path.startsWith(BSLASHS)) {
			path = path.substring(1);
		}
		while(path.endsWith(SLASHS) || path.endsWith(BSLASHS)) {
			path = path.substring(0, path.length() - 1);
		}
		while(domain.startsWith(SLASHS) || domain.startsWith(BSLASHS)) {
			domain = domain.substring(1);
		}
		while(domain.endsWith(SLASHS) || domain.endsWith(BSLASHS)) {
			domain = domain.substring(0, domain.length() - 1);
		}
		return domain + ':' + path;
	}
	
	/**
	 * Returns a file name from a path.
	 * @param path The path to the file.
	 * @return The file name, including extension.
	 */
	public static String getNameFromPath(String path) {
		String[] split = path.split("/");
		return split[split.length - 1];
	}
	
	/**
	 * Gets a stream for a resource in the jar file.
	 * @param loc The resource location.
	 * @return InputStream for file, or null if file does not exist.
	 */
	public static InputStream getResource(String loc) {
		String[] split = loc.split(":");
		if(split.length == 2) {
			String file = SLASH + split[0] + SLASH + split[1];
			InputStream out = Resources.class.getResourceAsStream(file);
			if(out == null) {
				Logger.info("Couldn't load: " + loc + " (" + file + ")");
				return null;
			}
			return out;
		}
		return null;
	}
	
	/**
	 * Reads lines of a resource.
	 * @param loc The resource location.
	 * @return An array of lines
	 */
	public static String[] getResourceAsLines(String loc) {
		List<String> out = new ArrayList<>();
		InputStream stream = getResource(loc);
		if(stream != null) {
			Scanner sc = new Scanner(stream);
			while(sc.hasNextLine()) {
				out.add(sc.nextLine());
			}
			sc.close();
		}
		return out.toArray(new String[out.size()]);
	}
	
	/**
	 * Returns a single string containing the contents of a file.
	 * @param loc The resource location.
	 * @return A string, containing new-lines, for a file.
	 */
	public static String getResourceAsString(String loc) {
		StringBuilder out = new StringBuilder();
		String[] lines = getResourceAsLines(loc);
		for(String line : lines) {
			out.append(line);
			out.append('\n');
		}
		return out.toString();
	}
	
}