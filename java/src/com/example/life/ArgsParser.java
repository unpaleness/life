package com.example.life;

import org.apache.commons.cli.*;

import java.util.ArrayList;

public class ArgsParser {
    private int xSize = 0;
    private int ySize = 0;
    private ArrayList<Integer> bornRules = new ArrayList<Integer>(0);
    private ArrayList<Integer> surviveRules = new ArrayList<Integer>(0);
    private int maxAge = 0;
    private int tickMs = 50;
    private int cellSize = 2;
    private boolean didSucceed = false;

    public ArgsParser(String[] args) {
        Option optionXSize = Option.builder("x").hasArg().argName("xSize").desc("amount of cells along x").required().build();
        Option optionYSize = Option.builder("y").hasArg().argName("ySize").desc("amount of cells along y").required().build();
        Option optionBornRules = Option.builder("B").hasArgs().desc("born rule").build();
        Option optionSurviveRules = Option.builder("S").hasArgs().desc("survive rule").build();
        Option optionMaxAge = Option.builder("m").hasArg().argName("maxAge").desc("maximum age of each cell when it has not enough neighbours to survive").build();
        Option optionTickMs = Option.builder("t").hasArg().desc("tick size in ms").build();
        Option optionCellSize = Option.builder("s").hasArg().desc("how much pixels for each cell").build();
        Options options = new Options();
        options.addOption(optionXSize);
        options.addOption(optionYSize);
        options.addOption(optionBornRules);
        options.addOption(optionSurviveRules);
        options.addOption(optionMaxAge);
        options.addOption(optionTickMs);
        options.addOption(optionCellSize);

        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("life", options);

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine commandLine = parser.parse(options, args);
            xSize = Integer.parseInt(commandLine.getOptionValue(optionXSize));
            ySize = Integer.parseInt(commandLine.getOptionValue(optionYSize));
            if (commandLine.hasOption(optionBornRules)) {
                bornRules.clear();
                for (String bornRule : commandLine.getOptionValues(optionBornRules)) {
                    bornRules.add(Integer.parseInt(bornRule));
                }
            }
            if (commandLine.hasOption(optionSurviveRules)) {
                surviveRules.clear();
                for (String surviveRule : commandLine.getOptionValues(optionSurviveRules)) {
                    surviveRules.add(Integer.parseInt(surviveRule));
                }
            }
            if (commandLine.hasOption(optionMaxAge)) {
                maxAge = Integer.parseInt(commandLine.getOptionValue(optionMaxAge));
            }
            if (commandLine.hasOption(optionTickMs)) {
                tickMs = Integer.parseInt(commandLine.getOptionValue(optionTickMs));
            }
            if (commandLine.hasOption(optionCellSize)) {
                cellSize = Integer.parseInt(commandLine.getOptionValue(optionCellSize));
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
}
