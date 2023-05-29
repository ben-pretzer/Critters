/*
 * CRITTERS Critter3.java
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
Velociraptors have 2 things in mind- eat and reproduce.
They have a 60% chance of fighting and will have a child if they have over 200 energy.
They will only move in one direction the entirety of the game.
He is denoted by a red triangle on the map.
 */

import javafx.scene.paint.Color;

public class Critter3 extends Critter{
    private int direction;

    public Critter3()
    {
        direction = Critter.getRandomInt(8);
    }
    @Override
    public CritterShape viewShape() {
        return CritterShape.TRIANGLE;
    }

    @Override
    public javafx.scene.paint.Color viewColor() {
        return Color.CRIMSON;
    }


    @Override
    public boolean fight(String s) {
        int willFight = Critter.getRandomInt(5);
        if(willFight < 3)
            return true;
        else
            return false;
    }

    @Override
    public void doTimeStep() {
        run(direction);
        if(getEnergy() > 200) {
            Critter3 child = new Critter3();
            reproduce(child, 0);
        }
    }
    @Override
    public String toString() {
        return "3";
    }

}