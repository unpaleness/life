package com.example.life.options;

import java.util.ArrayList;
import java.util.Arrays;

public class OptionsColony {
    public int sizeX = 100;
    public int sizeY = 100;
    public boolean hasBorders = false;
    public ArrayList<Integer> bornRules = new ArrayList<>(Arrays.asList(3)); // TODO use bits
    public ArrayList<Integer> surviveRules = new ArrayList<>(Arrays.asList(2, 3)); // TODO use bits
    public int maxAge = 0;
    public OptionsColony() {}
}
