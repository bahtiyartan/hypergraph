package com.hyperpl.ui.graph.test.dependencymap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.awt.Color;

public class Module extends Point {

	public double unitAngle;

	Random rand = new Random();

	String Name;
	Color Color;

	public ArrayList<Item> SubItems = new ArrayList<Item>();

	public Module(String name) {
		this.Name = name;

		int r = 64 + (int) (Math.random() * 128);
		int g = 64 + (int) (Math.random() * 128);
		int b = 64 + (int) (Math.random() * 128);

		Color = new Color(r, g, b);
	}

	public void addItem(Item item) {
		SubItems.add(item);
		unitAngle = 360.0 / this.SubItems.size();
	}

	public int getItemCount() {
		return this.SubItems.size();
	}

	public void sortItemsByDependencyCount() {
		SubItems.sort(new DependencyCountComparator());
	}

	class DependencyCountComparator implements Comparator<Item> {

		@Override
		public int compare(Item o1, Item o2) {
			return Integer.compare(o2.DependentToMe.size(), o1.DependentToMe.size());
		}

	}

}
