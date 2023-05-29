/*
 * CRITTERS Critter1.java
 * EE422C Project 5 submission by
 * Ben Pretzer
 * fbp234
 * 16160
 * Harrison Hodges
 * hth397
 * 16160
 * Slip days used: <2>
 * Fall 2020
 */

package assignment5;

/*
Every Explorer will have the basic tool for exploring- a compass.
The Explorer only understands the compass, and thus only moves in cardinal directions.
They will never want to fight.
They are denoted by a gold star on the map.
 */

import javafx.scene.paint.Color;

import java.awt.*;

public class Critter1 extends Critter{
    private char[] compass;
    public Critter1() {
        compass = new char[]{'N', 'S', 'E', 'W'};
    }

    @Override
    public CritterShape viewShape() {
        return CritterShape.STAR;
    }

    @Override
    public javafx.scene.paint.Color viewColor() {
        return Color.GOLD;
    }

    @Override
    public void doTimeStep() {
        char direction = compass[Critter.getRandomInt(4)];
        switch(direction) {
            case 'N':
                walk(2);
                break;
            case 'S':
                walk(6);
                break;
            case 'E':
                walk(0);
                break;
            case 'W':
                walk(4);
                break;
        }
    }

    @Override
    public boolean fight(String s) {
        return false;
    }

    public String toString() {
        return "1";
    }

}