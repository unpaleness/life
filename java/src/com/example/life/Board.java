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

    static private Color COLOR_BACKGROUND = Color.white;
    static private Color COLOR_FALLBACK = new Color(255, 0, 255);
    static private Color[] COLORS = new Color[]{Color.black, Color.green, Color.cyan, Color.blue};
    private int cellSize;
    private final Timer timer;
    private final Colony colony;

    public Board(ArgsParser argsParser) {
        colony = new Colony(argsParser.getXSize(), argsParser.getYSize(), argsParser.getBornRules(), argsParser.getSurviveRules(), argsParser.getMaxAge());
        colony.init();

        cellSize = argsParser.getCellSize();

        setBackground(COLOR_BACKGROUND);
        setFocusable(true);
        setPreferredSize(new Dimension(colony.getSizeX() * cellSize, colony.getSizeY() * cellSize));

        timer = new Timer(argsParser.getTickMs(), this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        int[] cells = colony.getCells();
        int maxAge = colony.getMaxAge();
        for (int i = 0; i < cells.length; ++i) {
            int cell = cells[i];
            if (cell == 0) {
                continue;
            }

            float ageRatio = (float) cell / maxAge;
            Color cellColor = blendColors(COLOR_BACKGROUND, COLOR_FALLBACK, ageRatio);

            graphics.setColor(cellColor);
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
