package com.hyperpl.ui.graph.test.dependencymap;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class DrawVerticalText extends JPanel {
	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		// Define rendering hint, font name, font style and font size
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.RED);

		// Rotate 90 degree to make a vertical text
		AffineTransform at = new AffineTransform();

		int angle = 180;

		at.setToRotation(Math.toRadians(angle), 0, 0);
		g2.setTransform(at);

		int x = 100;
		int y = 100;

		//x = (int) (x + Math.sin(Math.PI * 2 * angle / 360));
		//y = (int) (y + Math.cos(Math.PI * 2 * angle / 360));

		g2.drawRect(x, y, 10, 20);

		g2.drawString("This is a vertical text", x, y);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setTitle("Draw Vertical Text Demo");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.add(new DrawVerticalText());
		frame.pack();
		frame.setSize(400, 400);
		frame.setVisible(true);
	}
}