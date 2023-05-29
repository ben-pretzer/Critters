/*
 * CRITTERS Critter4.java
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
This Critter, the Wookie, has a unique ability- he roars every time step.
Roaring gives the Wookie a 50% chance to fight whoever he comes in contact with.
He also will both run and walk, running when he has more than 50 energy and walking the rest of the time.
He moves a random direction every step.
His position on the map is shown as a brown circle.
Implements look in fight.
 */

import javafx.scene.paint.Color;

public class Critter4 extends Critter{
    private int direction;
    private boolean willRoar;


    public Critter4 () {
        direction = Critter.getRandomInt(8);
        willRoar = true;
    }

    @Override
    public CritterShape viewShape() {
        return CritterShape.CIRCLE;
    }

    @Override
    public javafx.scene.paint.Color viewColor() {
        return Color.SADDLEBROWN;
    }

    public void roar() {
        int roar = Critter.getRandomInt(10);
        if(roar < 5)
            willRoar = true;
        else
            willRoar = false;
    }

    @Override
    public boolean fight(String s) {
        if(look(direction, true) == null) {
            run(direction);
        }
        return willRoar;
    }

    @Override
    public void doTimeStep() {
        roar();
        direction = Critter.getRandomInt(8);
        if(getEnergy() > 50)
            run(direction);
        else
            walk(direction);

    }

    @Override
    public String toString() {
        return "4";
    }
}