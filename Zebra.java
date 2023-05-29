package assignment5;

public class Zebra extends Critter {

    @Override
    public CritterShape viewShape() {
        return null;
    }

    @Override
    public void doTimeStep() {

    }

    @Override
    public boolean fight(String opponent) {
        return false;
    }
}
