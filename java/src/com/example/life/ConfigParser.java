package com.example.life;

import com.example.life.options.OptionsBoard;
import com.example.life.options.OptionsColony;
import org.yaml.snakeyaml.Yaml;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

public class ConfigParser {
    static private String SIZE_X = "sizeX";
    static private String SIZE_Y = "sizeY";
    static private String HAS_BORDERS = "hasBorders";
    static private String BORN_RULES = "bornRules";
    static private String SURVIVE_RULES = "surviveRules";
    static private String MAX_AGE = "maxAge";
    static private String TICK_MS = "tickMs";
    static private String CELL_SIZE = "cellSize";
    static private String CELL_COLORS = "cellColors";
    private OptionsBoard optionsBoard = new OptionsBoard();
    private OptionsColony optionsColony = new OptionsColony();
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
            if (data.containsKey(SIZE_X)) {
                optionsColony.sizeX = (int) data.get(SIZE_X);
            }
            if (data.containsKey(SIZE_Y)) {
                optionsColony.sizeY = (int) data.get(SIZE_Y);
            }
            if (data.containsKey(HAS_BORDERS)) {
                optionsColony.hasBorders = (boolean) data.get(HAS_BORDERS);
            }
            if (data.containsKey(BORN_RULES)) {
                optionsColony.bornRules = 0;
                for (int bornRule : (ArrayList<Integer>) data.get(BORN_RULES)) {
                    optionsColony.bornRules |= (1 << bornRule);
                }
            }
            if (data.containsKey(SURVIVE_RULES)) {
                optionsColony.surviveRules = 0;
                for (int surviveRule : (ArrayList<Integer>) data.get(SURVIVE_RULES)) {
                    optionsColony.surviveRules |= (1 << surviveRule);
                }
            }
            if (data.containsKey(MAX_AGE)) {
                optionsColony.maxAge = (int) data.get(MAX_AGE);
            }
            if (data.containsKey(TICK_MS)) {
                optionsBoard.tickMs = (int) data.get(TICK_MS);
            }
            if (data.containsKey(CELL_SIZE)) {
                optionsBoard.cellSize = (int) data.get(CELL_SIZE);
            }
            if (data.containsKey(CELL_COLORS)) {
                optionsBoard.cellColors.clear();
                for (int colorInt : (ArrayList<Integer>) data.get(CELL_COLORS)) {
                    optionsBoard.cellColors.add(new Color(colorInt));
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

    public OptionsBoard getOptionsBoard() {
        return optionsBoard;
    }
    public OptionsColony getOptionsColony() {
        return optionsColony;
    }
}
