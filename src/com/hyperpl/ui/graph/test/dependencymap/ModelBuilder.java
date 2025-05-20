package com.hyperpl.ui.graph.test.dependencymap;

import java.util.HashMap;

import com.hyperpl.util.file.FileAccess;

public class ModelBuilder {

	public static HashMap<String, String> ignore = new HashMap<>();

	public static HashMap<String, String> allowModules = new HashMap<>();

	static {
		ignore.put("SYSWORKFLOW", "");
		ignore.put("CIASLOCKS", "");
		ignore.put("CSYSTABLES", "");
		ignore.put("DEVLOG", "");

		//allowModules.put("ALL", "");
		allowModules.put("SYS", "");
		//allowModules.put("DEV", "");
		allowModules.put("CRM", "");
	}

	public static Model buildModel() {

		String folder = "data\\";

		String[] classlist = FileAccess.readString(folder + "classlist.dat").split("\\n");

		Model model = new Model();

		for (int i = 0; i < classlist.length; i++) {

			String[] className = classlist[i].split("\t");

			String itemName = className[0].trim();
			// String baseName = className[1].trim();
			String moduleName = className[2].trim();

			if (allowModules.containsKey(moduleName) || allowModules.containsKey("ALL")) {

				Module module = model.getModule(moduleName);
				if (module == null) {
					module = model.addModule(moduleName);
				}

				Item c = new Item(itemName);
				module.addItem(c);
				
				model.EasyAccess.put(itemName, c);
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
