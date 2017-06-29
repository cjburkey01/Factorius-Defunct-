package com.cjburkey.factorius.lang;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import com.cjburkey.factorius.Logger;
import com.cjburkey.factorius.io.Resources;

/**
 * Handles game language translations.
 * @author cjburkey
 */
public final class LocalizationManager {
	
	private final List<Localization> locals;
	private Localization current;
	
	/**
	 * Instantiate the manager
	 */
	public LocalizationManager() {
		locals = new ArrayList<>();
	}
	
	/**
	 * Load locals from inside jar-file.
	 */
	public void loadLocals() {
		Logger.info("Loading localizations.");
		Reflections reflections = new Reflections(new ConfigurationBuilder().setScanners(new ResourcesScanner()).setUrls(ClasspathHelper.forPackage("")));
		Set<String> resourceList = reflections.getResources(Pattern.compile(".*\\.lang"));
		for(String path : resourceList) {
			InputStream stream = LocalizationManager.class.getResourceAsStream('/' + path);
			if(stream != null) {
				Scanner scanner = new Scanner(stream);
				String unlocalizedName = Resources.getNameFromPath(path).split("\\.")[0];
				String localizedName = scanner.nextLine();
				List<String> lines = new ArrayList<>();
				HashMap<String, String> keys = new HashMap<>();
				while(scanner.hasNextLine()) {
					lines.add(scanner.nextLine().trim());
				}
				scanner.close();
				for(String line : lines) {
					if(!line.trim().isEmpty()) {
						String[] split = line.split("=");
						if(split.length == 2) {
							keys.put(split[0].trim(), split[1].trim());
						} else {
							Logger.warn("  Couldn't read \"" + line + "\"");
						}
					}
				}
				Localization local = new Localization(unlocalizedName, localizedName);
				for(Entry<String, String> entry : keys.entrySet()) {
					local.set(entry.getKey(), entry.getValue());
				}
				locals.add(local);
				Logger.info("  Loaded: \"" + unlocalizedName + "\" or \"" + localizedName + "\" from \"" + path + '\"');
			} else {
				Logger.warn("  Couldn't load localization at: " + path);
			}
		}
		if(locals.size() < 1) {
			Logger.warn("There aren't any languages! May the lord save our souls. How am I talking?");
			System.exit(-69);
		}
		current = locals.get(0);
		Logger.info("Loaded localizations.");
	}
	
	/**
	 * Get a localized value from an unlocalized key.
	 * @param key The unlocalized key.
	 * @return The localized value.
	 */
	public String get(String key) {
		if(current != null && current.hasKey(key)) {
			current.getString(key);
		}
		return key;
	}
	
}