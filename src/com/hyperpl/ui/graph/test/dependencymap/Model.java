package com.hyperpl.ui.graph.test.dependencymap;

import java.util.Hashtable;
import java.util.Vector;

public class Model {

	int Radius;
	public double unitAngle;

	private Vector<Item> List = new Vector<Item>();
	private Hashtable<String, Item> EasyAccess = new Hashtable<String, Item>();

	public Model() {

	}

	public void addItem(Item i) {
		List.add(i);
		EasyAccess.put(i.Name, i);

		unitAngle = 360.0 / this.List.size();
	}

	public int getItemCount() {
		return List.size();
	}

	public Item getItemAt(int i) {
		return List.get(i);
	}

	public Item getItem(String name) {
		return EasyAccess.get(name);
	}

	public void sort() {
		java.util.Collections.sort(List);
	}

	public void calculatePositions() {
		
		this.Radius = (int) (List.size() * DependencyMap.RadiusFactor  / (2 * Math.PI));

		for (int i = 0; i < List.size(); i++) {
			
			List.get(i).Angle = unitAngle * i;

			List.get(i).X = (int) ( (Radius) * Math.sin(Math.PI * 2 * List.get(i).Angle / 360));
			List.get(i).Y = (int) ( (Radius) * Math.cos(Math.PI * 2 * List.get(i).Angle / 360));

		}

	}

}
