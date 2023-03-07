package com.example.life;

import com.sun.source.tree.NewArrayTree;

import java.util.ArrayList;

public final class Colony {
    private int sizeX = 0;
    private int sizeY = 0;
    private boolean hasBorders = false;
    private ArrayList<Integer> bornRules;
    private ArrayList<Integer> surviveRules;
    private int maxAge = 1;
    private int[] cells1;
    private int[] cells2;
    private boolean cellsSwitcher = false;

    public Colony(int newSizeX, int newSizeY, boolean newHasBorders, ArrayList<Integer> newBornRules, ArrayList<Integer> newSurviveRules, int newMaxAge) {
        sizeX = newSizeX;
        sizeY = newSizeY;
        hasBorders = newHasBorders;
        bornRules = newBornRules;
        surviveRules = newSurviveRules;
        maxAge = newMaxAge > 0 ? newMaxAge : 1;
        cells1 = new int[sizeX * sizeY];
        cells2 = new int[sizeX * sizeY];
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public int[] getCells() {
        return cellsSwitcher ? cells2 : cells1;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public int getXCoordByIndex(int index) {
        return index % sizeX;
    }

    public int getYCoordByIndex(int index) {
        return index / sizeX;
    }

    public void init() {
        int[] cells = getCells();
        for (int i = 0; i < cells.length; ++i) {
            cells[i] = Math.random() < 0.5f ? 1 : 0;
        }
    }

    public void iterate() {
        int[] oldCells = getCells();
        int[] newCells = getNewCells();
        for (int i = 0; i < oldCells.length; ++i) {
            int aliveNeighboursAmount = getAliveNeighboursAmount(i);
            int cell = oldCells[i];
            boolean isOkToBornValue = isOkToBorn(aliveNeighboursAmount);
            boolean isOkToSurviveValue = isOkToSurvive(aliveNeighboursAmount);
            if ((cell == 0 && isOkToBornValue) || (cell == 1 && isOkToSurviveValue)) {
                newCells[i] = 1;
            } else if (cell < maxAge && cell >= 1) {
                newCells[i] = cell + 1;
            } else {
                newCells[i] = 0;
            }
        }
        cellsSwitcher = !cellsSwitcher;
    }

    private int[] getNewCells() {
        return cellsSwitcher ? cells1 : cells2;
    }

    private int getIndexByCoords(int xCoord, int yCoord) {
        return yCoord * sizeX + xCoord;
    }

    private boolean areCoordsValid(int xCoord, int yCoord) {
        return xCoord >= 0 && xCoord < sizeX && yCoord >= 0 && yCoord < sizeY;
    }

    private int getAliveNeighboursAmount(int cellIndex) {
        int xCoord = getXCoordByIndex(cellIndex);
        int yCoord = getYCoordByIndex(cellIndex);
        int[] coords = new int[]{xCoord - 1, yCoord - 1, xCoord - 1, yCoord + 1, xCoord + 1, yCoord - 1, xCoord + 1, yCoord + 1, xCoord, yCoord - 1, xCoord, yCoord + 1, xCoord - 1, yCoord, xCoord + 1, yCoord,};
        int result = 0;
        int[] cells = getCells();
        for (int i = 0; i < 16; i += 2) {
            if (!hasBorders) {
                coords[i] = coords[i] != -1 ? coords[i] != sizeX ? coords[i] : 0 : sizeX - 1;
                coords[i + 1] = coords[i + 1] != -1 ? coords[i + 1] != sizeY ? coords[i + 1] : 0 : sizeY - 1;
            }
            if (areCoordsValid(coords[i], coords[i + 1])) {
                result += (cells[getIndexByCoords(coords[i], coords[i + 1])] == 1 ? 1 : 0);
            }
        }
        return result;
    }

    private boolean isOkToBorn(int neighboursAmount) {
        return bornRules.contains(neighboursAmount);
    }

    private boolean isOkToSurvive(int neighboursAmount) {
        return surviveRules.contains(neighboursAmount);
    }
}
