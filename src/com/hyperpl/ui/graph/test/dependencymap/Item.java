package com.hyperpl.ui.graph.test.dependencymap;

import java.util.Vector;

public class Item extends Point implements Comparable<Item> {

	String Name;
	Module Module;

	Vector<String> DependentToMe;

	public Item(String name, Module module) {
		this.Name = name;
		this.Module = module;

		DependentToMe = new Vector<String>();
	}

	@Override
	public int compareTo(Item o) {

		int moduleResult = this.Module.Name.compareTo(o.Module.Name);
		if (moduleResult == 0) {
			return this.Name.compareTo(o.Name);
			// return Integer.compare(this.Dependency.size(), o.Dependency.size())*-1;
		} else {
			return moduleResult;
		}

	}

	public void addDependency(Item from) {

		if (!DependentToMe.contains(from.Name)) {
			DependentToMe.add(from.Name);
		}
	}

	public int getBoxSize() {
		return DependencyMap.MinimumRadius + DependentToMe.size();
	}
}
