package com.cjburkey.factorius.lang;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Individual translations.
 * @author cjburkey
 */
public final class Localization {

	private final String unloc;
	private final String loc;
	private final ConcurrentHashMap<String, String> keys;
	
	/**
	 * Create an instance of the localization.
	 * @param unlocalizedName The unlocalized name of the language.
	 * @param localizedName The localized name of the language.
	 */
	public Localization(String unlocalizedName, String localizedName) {
		unloc = unlocalizedName;
		loc = localizedName;
		keys = new ConcurrentHashMap<>();
	}
	
	/**
	 * Sets the unlocalized key to the localized value.
	 * Allows runtime key-setting, although not recommended.
	 * @param key The unlocalized key.
	 * @param value The localized name.
	 */
	public void set(String key, String value) {
		keys.put(key, value);
	}
	
	/**
	 * Gets whether or not the language has an unlocalized key.
	 * @param key The unlocalized key.
	 * @return Contains.
	 */
	public boolean hasKey(String key) {
		return keys.containsKey(key);
	}
	
	/**
	 * Returns the localized string for an unlocalized key.
	 * @param key The unlocalized key.
	 * @return The localized value.
	 */
	public String getString(String key) {
		String out = keys.get(key);
		if(out == null) {
			return key;
		}
		return out;
	}
	
	/**
	 * Gets the unlocalized name of the language.
	 * @return Unlocalized name.
	 */
	public String getUnlocalizedName() {
		return unloc;
	}
	
	/**
	 * Gets the localized name of the language.
	 * @return Localized name.
	 */
	public String getLocalizedName() {
		return loc;
	}
	
}