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

public class LocalizationManager {
	
	private final List<Localization> locals;
	
	public LocalizationManager() {
		locals = new ArrayList<>();
	}
	
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
					String[] split = line.split("=");
					if(split.length == 2) {
						keys.put(split[0].trim(), split[1].trim());
					} else {
						Logger.warn("  Couldn't read \"" + line + "\"");
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
		Logger.info("Loaded localizations.");
	}
	
}