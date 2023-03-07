package com.example.life;

import com.example.life.options.OptionsColony;

public final class Colony {
    private OptionsColony options;
    private int[] cells1;
    private int[] cells2;
    private boolean cellsSwitcher = false;

    public Colony(OptionsColony newOptions) {
        options = newOptions;
        cells1 = new int[options.sizeX * options.sizeY];
        cells2 = new int[options.sizeX * options.sizeY];
    }

    public int getSizeX() {
        return options.sizeX;
    }

    public int getSizeY() {
        return options.sizeY;
    }

    public int[] getCells() {
        return cellsSwitcher ? cells2 : cells1;
    }

    public int getMaxAge() {
        return options.maxAge;
    }

    public int getXCoordByIndex(int index) {
        return index % options.sizeX;
    }

    public int getYCoordByIndex(int index) {
        return index / options.sizeX;
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
            } else if (cell < options.maxAge && cell >= 1) {
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
        return yCoord * options.sizeX + xCoord;
    }

    private boolean areCoordsValid(int xCoord, int yCoord) {
        return xCoord >= 0 && xCoord < options.sizeX && yCoord >= 0 && yCoord < options.sizeY;
    }

    private int getAliveNeighboursAmount(int cellIndex) {
        int xCoord = getXCoordByIndex(cellIndex);
        int yCoord = getYCoordByIndex(cellIndex);
        int[] coords = new int[]{xCoord - 1, yCoord - 1, xCoord - 1, yCoord + 1, xCoord + 1, yCoord - 1, xCoord + 1, yCoord + 1, xCoord, yCoord - 1, xCoord, yCoord + 1, xCoord - 1, yCoord, xCoord + 1, yCoord,};
        int result = 0;
        int[] cells = getCells();
        for (int i = 0; i < 16; i += 2) {
            if (!options.hasBorders) {
                coords[i] = coords[i] != -1 ? coords[i] != options.sizeX ? coords[i] : 0 : options.sizeX - 1;
                coords[i + 1] = coords[i + 1] != -1 ? coords[i + 1] != options.sizeY ? coords[i + 1] : 0 : options.sizeY - 1;
            }
            if (areCoordsValid(coords[i], coords[i + 1])) {
                result += (cells[getIndexByCoords(coords[i], coords[i + 1])] == 1 ? 1 : 0);
            }
        }
        return result;
    }

    private boolean isOkToBorn(int neighboursAmount) {
        return options.bornRules.contains(neighboursAmount);
    }

    private boolean isOkToSurvive(int neighboursAmount) {
        return options.surviveRules.contains(neighboursAmount);
    }
}
