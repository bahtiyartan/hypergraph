package com.hyperpl.ui.graph.test.dependencymap;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class DependencyMap extends JPanel implements ActionListener {

	public static final int RadiusFactor = 15;
	public static final int MinimumRadius = 8;

	Model model;

	JScrollPane sp;

	public DependencyMap() {
		this.setBackground(Color.WHITE);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D gr = (Graphics2D) g;

		gr.setFont(gr.getFont().deriveFont(11f).deriveFont(Font.BOLD));

		gr.setStroke(new BasicStroke(1f)); // Set line width

		int shiftX = getWidth() / 2;
		int shiftY = getHeight() / 2;

		for (Module module : model.Modules) {

			for (int i = 0; i < module.getItemCount(); i++) {
				Item item = module.SubItems.get(i);

				g.setColor(module.Color);

				for (int j = 0; j < item.DependentToMe.size(); j++) {
					Item from = item.DependentToMe.get(j);

					if (from != null) {

						int itemX = shiftX + item.X; // - item.getBoxSize() / 2;
						int itemY = shiftY + item.Y; // - item.getBoxSize() / 2;

						int toX = shiftX + from.X; // - from.getBoxSize() / 2;
						int toY = shiftY + from.Y; // - from.getBoxSize() / 2;

						gr.drawLine(itemX, itemY, toX, toY);

					}

				}

			}

		}

		for (int moduleIndex = 0; moduleIndex < model.getModuleCount(); moduleIndex++) {

			Module module = model.getModuleAt(moduleIndex);

			for (int i = 0; i < module.getItemCount(); i++) {
				Item item = module.SubItems.get(i);

				int x = (int) (shiftX + item.X) - item.getBoxSize() / 2;
				int y = (int) (shiftY + item.Y) - item.getBoxSize() / 2;

				g.setColor(module.Color);

				gr.fillOval(x, y, item.getBoxSize(), item.getBoxSize());

				gr.setColor(Color.BLACK);

				String name = anonymize(item.Name) + " " + item.DependentToMe.size();
				int xPosShift = -18;
				if (item.Angle > 180) {
					xPosShift = gr.getFontMetrics().stringWidth(name) + 10;
				}

				// if ((item.Angle < 135 && item.Angle > 45) || (item.Angle < 315 && item.Angle
				// > 225)) {

				if (i == 0) {
					gr.drawString(name, x - xPosShift, y + 8);
					System.out.println(name);
				}
				// }

			}

		}

		gr.fillRect(10, this.getHeight() - 20, 11, 11);
		gr.drawString(model.getItemCount() + " items", 25, this.getHeight() - 11);

	}

	@SuppressWarnings("unused")
	private static final String anonymize(String item) {

		if (true)
			return item;

		StringBuilder rtrn = new StringBuilder(item);

		for (int i = 1; i < item.length() - 1; i++) {
			rtrn.setCharAt(i, '*');
		}

		return rtrn.toString();
	}

	public static void main(String[] args) {

		JFrame f = new JFrame("Dependency Map");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(new Dimension(1200, 1000));
		f.setLocationRelativeTo(null);

		DependencyMap map = new DependencyMap();

		map.setPreferredSize(new Dimension((int) (2.5 * 5000), (int) (2.5 * 5000)));

		JPanel all = new JPanel(new BorderLayout());

		map.sp = new JScrollPane(map);

		JPanel northPanel = new JPanel(new GridLayout(1, 2));

		JButton refresh = new JButton("Refresh");
		refresh.setActionCommand("refresh");
		refresh.addActionListener(map);
		northPanel.add(refresh);

		JButton export = new JButton("Export");
		export.setActionCommand("export");
		export.addActionListener(map);
		northPanel.add(export);

		all.add(northPanel, BorderLayout.NORTH);
		all.add(map.sp);

		f.setContentPane(all);

		map.actionPerformed(null);

		f.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e != null && "export".equalsIgnoreCase(e.getActionCommand())) {
			try {
				exportAsImage();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			model = ModelBuilder.buildModel();
			this.setPreferredSize(new Dimension((int) (model.Radius * 2.2), (int) (model.Radius * 2.2)));
			this.repaint();
			this.revalidate();
		}

	}

	public void exportAsImage() {
		BufferedImage jImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);

		Graphics jGraphics = jImage.getGraphics();
		this.paint(jGraphics);

		jImage.flush();
		jGraphics.dispose();

		try {
			ImageIO.write(jImage, "png", new File("C:\\TMP\\out.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
