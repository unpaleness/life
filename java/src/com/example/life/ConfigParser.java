package com.example.life;

import org.yaml.snakeyaml.Yaml;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class ConfigParser {
    static private String X_SIZE = "xSize";
    static private String Y_SIZE = "ySize";
    static private String HAS_BORDERS = "hasBorders";
    static private String BORN_RULES = "bornRules";
    static private String SURVIVE_RULES = "surviveRules";
    static private String MAX_AGE = "maxAge";
    static private String TICK_MS = "tickMs";
    static private String CELL_SIZE = "cellSize";
    static private String CELL_COLORS = "cellColors";
    private int xSize = 100;
    private int ySize = 100;
    private boolean hasBorders = false;
    private ArrayList<Integer> bornRules = new ArrayList<>(Arrays.asList(3));
    private ArrayList<Integer> surviveRules = new ArrayList<>(Arrays.asList(2, 3));
    private int maxAge = 0;
    private int tickMs = 50;
    private int cellSize = 4;
    private ArrayList<Color> cellColors = new ArrayList<>(Arrays.asList(Color.white, Color.black));
    private String filename;
    private boolean didSucceed = false;

    public ConfigParser(String newFilename) {
        filename = newFilename;
    }

    public void parse() {
        try {
            InputStream input = new FileInputStream(new File(filename));
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(input);
            System.out.println("Parsed config: " + data);
            if (data.containsKey(X_SIZE)) {
                xSize = (int) data.get(X_SIZE);
            }
            if (data.containsKey(Y_SIZE)) {
                ySize = (int) data.get(Y_SIZE);
            }
            if (data.containsKey(HAS_BORDERS)) {
                hasBorders = (boolean) data.get(HAS_BORDERS);
            }
            if (data.containsKey(BORN_RULES)) {
                bornRules = (ArrayList<Integer>) data.get(BORN_RULES);
            }
            if (data.containsKey(SURVIVE_RULES)) {
                surviveRules = (ArrayList<Integer>) data.get(SURVIVE_RULES);
            }
            if (data.containsKey(MAX_AGE)) {
                maxAge = (int) data.get(MAX_AGE);
            }
            if (data.containsKey(TICK_MS)) {
                tickMs = (int) data.get(TICK_MS);
            }
            if (data.containsKey(CELL_SIZE)) {
                cellSize = (int) data.get(CELL_SIZE);
            }
            if (data.containsKey(CELL_COLORS)) {
                cellColors.clear();
                for (int colorInt : (ArrayList<Integer>) data.get(CELL_COLORS)) {
                    cellColors.add(new Color(colorInt));
                }
            }
            didSucceed = true;
        } catch (Exception ex) {
            System.err.println("Parsing failed: " + ex.getMessage());
        }
    }

    public boolean getDidSucceed() {
        return didSucceed;
    }

    public int getXSize() {
        return xSize;
    }

    public int getYSize() {
        return ySize;
    }

    public boolean getHasBorders() {
        return hasBorders;
    }

    public ArrayList<Integer> getBornRules() {
        return bornRules;
    }

    public ArrayList<Integer> getSurviveRules() {
        return surviveRules;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public int getTickMs() {
        return tickMs;
    }

    public int getCellSize() {
        return cellSize;
    }

    public ArrayList<Color> getCellColors() {
        return cellColors;
    }
}
