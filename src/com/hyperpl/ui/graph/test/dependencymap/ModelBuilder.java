package com.hyperpl.ui.graph.test.dependencymap;

import java.util.HashMap;
import java.util.Hashtable;

import com.hyperpl.util.file.FileAccess;

public class ModelBuilder {

	public static HashMap<String, String> ignore = new HashMap<>();

	public static HashMap<String, String> allowModules = new HashMap<>();

	static {
		ignore.put("SYSWORKFLOW", "");
		ignore.put("CIASLOCKS", "");
		ignore.put("CSYSTABLES", "");

		//allowModules.put("SYS", "");
		//allowModules.put("DEV", "");
		//allowModules.put("BPM", "");
	}

	public static Model buildModel() {

		String folder = "C:\\TMP\\Data\\";

		String[] classlist = FileAccess.readString(folder + "classlist.dat").split("\\n");

		Hashtable<String, Module> moduleCache = new Hashtable<>();

		Model model = new Model();

		for (int i = 0; i < classlist.length; i++) {

			String[] className = classlist[i].split("\t");

			String itemName = className[0].trim();
			// String baseName = className[1].trim();
			String moduleName = className[2].trim();

			if (!allowModules.containsKey(moduleName)) {

				Module module = moduleCache.get(moduleName);
				if (module == null) {
					module = new Module(moduleName);
					moduleCache.put(moduleName, module);
				}

				Item c = new Item(itemName, module);
				model.addItem(c);
			}

		}

		String[] dependencies = FileAccess.readString(folder + "classdep.TXT").split("\\n");

		if (dependencies.length > 1) {

			for (int i = 0; i < dependencies.length; i++) {
				String[] dependency = dependencies[i].split("\\|");

				String from = dependency[0];
				String to = dependency[1];

				Item fromItem = model.getItem(from);
				Item toItem = model.getItem(to);
				if (fromItem != null && toItem != null) {
					if (!ignore.containsKey(fromItem.Name) && !ignore.containsKey(toItem.Name)) {
						toItem.addDependency(fromItem);
					}
				}
			}
		}

		model.sort();
		model.calculatePositions();

		return model;

	}
}
