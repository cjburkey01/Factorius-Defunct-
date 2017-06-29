package com.cjburkey.factorius.store;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import com.cjburkey.factorius.Logger;
import com.cjburkey.factorius.io.GameFiles;
import com.cjburkey.factorius.util.SemVer;

/**
 * Manager for core game settings.
 * @author cjburkey
 */
public final class GameSettings {
	
	private final ConcurrentHashMap<String, Object> keys;
	private final SettingsWriter writer;
	
	/**
	 * Instantiate manager
	 */
	public GameSettings() {
		keys = new ConcurrentHashMap<>();
		writer = new SettingsWriter();
	}
	
	/**
	 * Set the key to the value.
	 * @param key Key.
	 * @param value Value.
	 */
	public void set(String key, Object value) {
		keys.put(new String(key), value);
	}
	
	/**
	 * Writes the changes to the game settings file.
	 * @throws IOException
	 */
	public void save() throws IOException {
		writer.saveToFile();
	}
	
	/**
	 * Loads the game settings from the file.
	 * Will overwrite any set values not saved.
	 * @throws Exception
	 */
	public void load() throws Exception {
		writer.loadFromFile();
	}
	
	/**
	 * Empties loaded game settings.
	 */
	public void empty() {
		keys.clear();
	}
	
	/**
	 * Returns the value for a key.
	 * @param key Key.
	 * @return Value. Null if not set.
	 */
	public Object get(String key) {
		return keys.get(key);
	}
	
	/**
	 * Returns the value for a key.
	 * Cast to string if safe.
	 * @param key Key.
	 * @return Value. Null if not set or not string.
	 */
	public String getString(String key) {
		Object at = get(key);
		if(at != null && at instanceof String) {
			return (String) at;
		}
		return null;
	}
	
	/**
	 * Returns the value for a key.
	 * Cast to integer if safe.
	 * @param key Key.
	 * @return Value. Null if not set or not integer.
	 */
	public int getInteger(String key) {
		Object at = get(key);
		if(at != null && at instanceof Integer) {
			return (Integer) at;
		}
		return -1;
	}
	
	/**
	 * Returns the value for a key.
	 * Cast to long if safe.
	 * @param key Key.
	 * @return Value. Null if not set or not long.
	 */
	public long getLong(String key) {
		Object at = get(key);
		if(at != null && at instanceof Long) {
			return (Long) at;
		}
		return -1;
	}
	
	/**
	 * Returns the value for a key.
	 * Cast to long if safe.
	 * @param key Key.
	 * @return Value. Null if not set or not long.
	 */
	public float getFloat(String key) {
		Object at = get(key);
		if(at != null && at instanceof Float) {
			return (Float) at;
		}
		return -1;
	}
	
	/**
	 * Returns the value for a key.
	 * Cast to double if safe.
	 * @param key Key.
	 * @return Value. Null if not set or not double.
	 */
	public double getDouble(String key) {
		Object at = get(key);
		if(at != null && at instanceof Double) {
			return (Double) at;
		}
		return -1;
	}
	
	/**
	 * Returns the value for a key.
	 * Cast to SemVer if safe.
	 * @param key Key.
	 * @return Value. Null if not set or not SemVer.
	 */
	public SemVer getSemVer(String key) {
		Object at = get(key);
		if(at != null && at instanceof SemVer) {
			return (SemVer) at;
		}
		return SemVer.EMPTY;
	}
	
	/**
	 * Handles the writing and reading of the settings file.
	 * @author cjburkey
	 */
	private class SettingsWriter {
		
		/**
		 * Saves current settings to the settings file.
		 * @throws IOException
		 */
		public void saveToFile() throws IOException {
			File f = GameFiles.getSettingsFile();
			if(f.exists()) {
				f.delete();
			}
			FileOutputStream fos = new FileOutputStream(f);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(keys);
			oos.flush();
			oos.close();
			fos.close();
			Logger.info("Saved to settings file: " + f);
		}
		
		/**
		 * Overwrites current settings with those found in settings file.
		 * @throws Exception
		 */
		public void loadFromFile() throws Exception {
			File f = GameFiles.getSettingsFile();
			if(f.exists()) {
				FileInputStream fis = new FileInputStream(f);
				ObjectInputStream ois = new ObjectInputStream(fis);
				Object in = null;
				try {
					in = ois.readObject();
				} catch(Exception e) {
					f.delete();
					throw new Exception("There was an error reading the settings file. It has been deleted.");
				} finally {
					ois.close();
					fis.close();
				}
				if(in != null && in instanceof ConcurrentHashMap) {
					empty();
					ConcurrentHashMap<?, ?> inCast = (ConcurrentHashMap<?, ?>) in;
					for(Entry<?, ?> entry : inCast.entrySet()) {
						if(entry.getKey() != null && entry.getValue() != null) {
							set(entry.getKey().toString(), entry.getValue());
						}
					}
					Logger.info("Read from settings file: " + f);
					return;
				}
				Logger.info("Couldn't read settings from: " + f);
			}
			Logger.info("Couldn't find settings file: " + f);
		}
	}
	
}