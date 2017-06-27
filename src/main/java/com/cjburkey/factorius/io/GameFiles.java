package com.cjburkey.factorius.io;

import java.io.File;

public final class GameFiles {
	
	public static File getGameDir() {
		File out = new File(System.getProperty("user.home"), "/Factorius/");
		if(!out.exists()) {
			out.mkdirs();
		}
		return out;
	}
	
	public static File getSettingsFile() {
		File out = new File(getGameDir(), "/settings.dat");
		if(!out.getParentFile().exists()) {
			out.getParentFile().mkdirs();
		}
		return out;
	}
	
}