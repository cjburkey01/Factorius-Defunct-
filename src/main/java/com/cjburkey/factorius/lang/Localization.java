package com.cjburkey.factorius.lang;

import java.util.concurrent.ConcurrentHashMap;

public class Localization {

	private final String unloc;
	private final String loc;
	private final ConcurrentHashMap<String, String> keys;
	
	public Localization(String unlocalizedName, String localizedName) {
		unloc = unlocalizedName;
		loc = localizedName;
		keys = new ConcurrentHashMap<>();
	}
	
	public void set(String key, String value) {
		keys.put(key, value);
	}
	
	public boolean hasKey(String key) {
		return keys.containsKey(key);
	}
	
	public String getString(String key) {
		String out = keys.get(key);
		if(out == null) {
			return key;
		}
		return out;
	}
	
	public String getUnlocalizedName() {
		return unloc;
	}
	
	public String getLocalizedName() {
		return loc;
	}
	
}