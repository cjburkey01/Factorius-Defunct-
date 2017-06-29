package com.cjburkey.factorius.io;

import java.io.File;

/**
 * Stores information about important files for the game.
 * @author cjburkey
 */
public final class GameFiles {
	
	/**
	 * Gets the game's working directory.
	 * @return Game directory.
	 */
	public static File getGameDir() {
		File out = new File(System.getProperty("user.home"), "/Factorius/");
		if(!out.exists()) {
			out.mkdirs();
		}
		return out;
	}
	
	/**
	 * Gets the game's settings file.
	 * @return Game settings file.
	 */
	public static File getSettingsFile() {
		File out = new File(getGameDir(), "/settings.dat");
		if(!out.getParentFile().exists()) {
			out.getParentFile().mkdirs();
		}
		return out;
	}
	
}