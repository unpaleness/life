package com.example.life.options;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class OptionsBoard {
    public int tickMs = 50;
    public int cellSize = 4;
    public ArrayList<Color> cellColors = new ArrayList<>(Arrays.asList(Color.white, Color.black));
    public OptionsBoard() {}
}
