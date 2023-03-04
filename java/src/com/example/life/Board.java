package com.example.life;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Graphics;
import java.awt.Toolkit;

public final class Board extends JPanel implements ActionListener {

    static private final int TICK_MS = 20;
    static private final int CELL_SIZE = 2;
    private final Timer timer;
    private final Colony colony;

    public Board() {
        colony = new Colony(500, 500, new int[]{3}, new int[]{2, 3});
        colony.init();

        setBackground(Color.white);
        setFocusable(true);
        setPreferredSize(new Dimension(colony.getSizeX() * CELL_SIZE, colony.getSizeY() * CELL_SIZE));

        timer = new Timer(TICK_MS, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        int[] cells = colony.getCells();
        for (int i = 0; i < cells.length; ++i) {
            if (cells[i] == 0) {
                continue;
            }
            graphics.setColor(Color.black);
            int xCoord = colony.getXCoordByIndex(i);
            int yCoord = colony.getYCoordByIndex(i);
            graphics.fillRect(xCoord * CELL_SIZE, yCoord * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        colony.iterate();
        repaint();
    }
}
