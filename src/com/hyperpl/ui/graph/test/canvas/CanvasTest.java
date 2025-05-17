package com.hyperpl.ui.graph.test.canvas;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class CanvasTest extends JFrame {

    public CanvasTest() {
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().add(new TestCanvas());
    }

    public static void main(String[] args) {
        // Swing code must run in the UI thread, so
        // must invoke setVisible rather than just calling it.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CanvasTest().setVisible(true);
            }
        });
    }
}
