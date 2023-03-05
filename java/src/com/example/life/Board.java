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

    private int cellSize;
    private final Timer timer;
    private final Colony colony;

    public Board(ArgsParser argsParser) {
        colony = new Colony(argsParser.getXSize(), argsParser.getYSize(), argsParser.getBornRules(), argsParser.getSurviveRules());
        colony.init();

        cellSize = argsParser.getCellSize();

        setBackground(Color.white);
        setFocusable(true);
        setPreferredSize(new Dimension(colony.getSizeX() * cellSize, colony.getSizeY() * cellSize));

        timer = new Timer(argsParser.getTickMs(), this);
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
            graphics.fillRect(xCoord * cellSize, yCoord * cellSize, cellSize, cellSize);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        colony.iterate();
        repaint();
    }
}
