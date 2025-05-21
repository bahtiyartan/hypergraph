package com.hyperpl.ui.graph.test.dependencymap;

import java.util.ArrayList;
import java.util.Hashtable;

public class Model {

	int Radius;

	public Hashtable<String, Item> EasyAccess = new Hashtable<String, Item>();

	public ArrayList<Module> Modules = new ArrayList<Module>();
	private Hashtable<String, Module> ModuleEasyAccess = new Hashtable<String, Module>();

	public Model() {

	}

	public Module getModule(String moduleName) {
		return this.ModuleEasyAccess.get(moduleName);
	}

	public Module addModule(String moduleName) {

		Module newModule = this.ModuleEasyAccess.get(moduleName);

		if (newModule == null) {

			newModule = new Module(moduleName);
			this.Modules.add(newModule);
			this.ModuleEasyAccess.put(moduleName, newModule);

			// int unitAngle = 360.0 / this.Modules.size();

		}

		return newModule;
	}

	public Module getItemAt(int i) {
		return Modules.get(i);
	}

	public Item getItem(String name) {
		return EasyAccess.get(name);
	}

	public void sort() {
		// java.util.Collections.sort(List);
	}

	public void calculatePositions() {

		int itemCount = this.getItemCount();

		this.Radius = (int) (itemCount * DependencyMap.RadiusFactor / (2 * Math.PI));

		double unitAngle = 360.0 / this.getItemCount();

		int count = 0;
		for (Module m : Modules) {
			
			m.sortItemsByDependencyCount();
			
			for (int i = 0; i < m.getItemCount(); i++) {

				Item subItem = m.SubItems.get(i);

				subItem.Angle = unitAngle * count;

				subItem.X = (int) ((Radius) * Math.sin(Math.PI * 2 * subItem.Angle / 360));
				subItem.Y = (int) ((Radius) * Math.cos(Math.PI * 2 * subItem.Angle / 360));

				count++;
			}
		}

	}

	public int getItemCount() {
		int nTotal = 0;

		for (int i = 0; i < Modules.size(); i++) {
			nTotal = nTotal + Modules.get(i).getItemCount();
		}

		return nTotal;
	}

	public Module getModuleAt(int moduleIndex) {
		return Modules.get(moduleIndex);
	}

	public int getModuleCount() {
		return Modules.size();
	}

}
