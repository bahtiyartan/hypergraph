package com.hyperpl.ui.graph.test.dependencymap;

import java.util.Random;
import java.awt.Color;

public class Module extends Point {

	Random rand = new Random();

	String Name;
	Color Color;

	public Module(String name) {
		this.Name = name;

		int r = 64 + (int) (Math.random() * 128);
		int g = 64 + (int) (Math.random() * 128);
		int b = 64 + (int) (Math.random() * 128);

		Color = new Color(r, g, b);
	}

}
