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
            graphics.fillRect(xCoord * options.cellSize, yCoord * options.cellSize, options.cellSize, options.cellSize);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        colony.iterate();
        repaint();
    }

    static Color blendColors(Color c1, Color c2, float ratio) {
        if (ratio > 1f) ratio = 1f;
        else if (ratio < 0f) ratio = 0f;
        float iRatio = 1.0f - ratio;

        int i1 = c1.getRGB();
        int i2 = c2.getRGB();

        int a1 = (i1 >> 24 & 0xff);
        int r1 = ((i1 & 0xff0000) >> 16);
        int g1 = ((i1 & 0xff00) >> 8);
        int b1 = (i1 & 0xff);

        int a2 = (i2 >> 24 & 0xff);
        int r2 = ((i2 & 0xff0000) >> 16);
        int g2 = ((i2 & 0xff00) >> 8);
        int b2 = (i2 & 0xff);

        int a = (int) ((a1 * iRatio) + (a2 * ratio));
        int r = (int) ((r1 * iRatio) + (r2 * ratio));
        int g = (int) ((g1 * iRatio) + (g2 * ratio));
        int b = (int) ((b1 * iRatio) + (b2 * ratio));

        return new Color(a << 24 | r << 16 | g << 8 | b);
    }
}
