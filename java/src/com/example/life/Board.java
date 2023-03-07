package com.example.life;

import com.example.life.options.OptionsBoard;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Graphics;
import java.awt.Toolkit;

public final class Board extends JPanel implements ActionListener {

    private OptionsBoard options;
    private final Timer timer;
    private final Colony colony;

    public Board(Colony newColony, OptionsBoard newOptions) {
        colony = newColony;
        colony.init();

        options = newOptions;

        timer = new Timer(options.tickMs, this);

        if (options.cellColors.size() < 2) {
            System.err.println("cellColors.size() < 2: " + options.cellColors.size());
            return;
        }

        setBackground(options.cellColors.get(0));
        setFocusable(true);
        setPreferredSize(new Dimension(colony.getSizeX() * options.cellSize, colony.getSizeY() * options.cellSize));

        timer.start();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        int[] cells = colony.getCells();
        for (int i = 0; i < cells.length; ++i) {
            int cell = cells[i];
            if (cell == 0) {
                continue;
            }

            Color cellColor = cell >= 0 && cell < options.cellColors.size() ? options.cellColors.get(cell) : options.cellColors.get(0);

            graphics.setColor(cellColor);
            int xCoord = colony.getXCoordByIndex(i);
            int yCoord = colony.getYCoordByIndex(i);
            graphics.fillRect(xCoord * options.cellSize, yCoord * options.cellSize, options.cellSize, options.cellSize); // TODO optimize
        }

        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        colony.iterate();
        repaint();
    }
}
