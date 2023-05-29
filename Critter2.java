/*
 * CRITTERS Critter2.java
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
The Troll has a nasty temper. Only when it gets over a certain threshold will he fight.
Additionally, his movement is based off how mad he is. If he is not mad, he will stand still.
He is denoted by a silver diamond on the map.
Implements look in doTimeStep.
 */

import javafx.scene.paint.Color;

public class Critter2 extends Critter {
    private int temper;

    public Critter2() {
        temper = Critter.getRandomInt(100);
    }

    @Override
    public CritterShape viewShape() {
        return CritterShape.DIAMOND;
    }

    @Override
    public javafx.scene.paint.Color viewColor() {
        return Color.SILVER;
    }

    public boolean fight(String s) {
        if(temper > 20)
            return true;
        else
            return false;
    }

    @Override
    public void doTimeStep() {
        if(50 > temper && temper > 30 )
            run(4);
        else if((temper >= 50) && (look(2, true) == null))
            run(2);
        else
            walk(0);
        temper += 10;
    }

    @Override
    public String toString() {
        return "2";
    }
}