package com.hyperpl.ui.graph.test;

import java.awt.Dimension;

import javax.swing.JFrame;

public class GraphTest extends JFrame {

	
	public static void main(String[] args) {
		
		GraphTest t = new GraphTest();
		t.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		t.setSize(new Dimension(2000,1200));
		t.setLocationRelativeTo(null);
		t.setVisible(true);
		
	}
}
