/*
 * CRITTERS Critter.java
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

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * See the PDF for descriptions of the methods and fields in this
 * class.
 * You may add fields, methods or inner classes to Critter ONLY
 * if you make your additions private; no new public, protected or
 * default-package code or data can be added to Critter.
 */

public abstract class Critter {

    private static int critterHeight = 4;
    private static int critterWidth = 4;
    private static Canvas display;
    private static GraphicsContext gc;
    private int energy = 0;
    private int x_coord;
    private int y_coord;
    private static List<Critter> population = new ArrayList<Critter>();
    private static List<Critter> babies = new ArrayList<Critter>();
    private static List<Critter> oldPopulation = new ArrayList<Critter>();
    private boolean hasMoved = false;

    /* START --- NEW FOR PROJECT 5 */
    public enum CritterShape {
        CIRCLE,
        SQUARE,
        TRIANGLE,
        DIAMOND,
        STAR
    }

    /* the default color is white, which I hope makes critters invisible by default
     * If you change the background color of your View component, then update the default
     * color to be the same as you background
     *
     * critters must override at least one of the following three methods, it is not
     * proper for critters to remain invisible in the view
     *
     * If a critter only overrides the outline color, then it will look like a non-filled
     * shape, at least, that's the intent. You can edit these default methods however you
     * need to, but please preserve that intent as you implement them.
     */
    public javafx.scene.paint.Color viewColor() {
        return javafx.scene.paint.Color.WHITE;
    }

    public javafx.scene.paint.Color viewOutlineColor() {
        return viewColor();
    }

    public javafx.scene.paint.Color viewFillColor() {
        return viewColor();
    }

    public abstract CritterShape viewShape();

    protected final String look(int direction, boolean steps) {
        energy -= Params.LOOK_ENERGY_COST;
        int lookx = x_coord;
        //lookx = (lookx + Params.WORLD_WIDTH) % Params.WORLD_WIDTH;
        int looky = y_coord;
        //looky = (looky + Params.WORLD_HEIGHT) % Params.WORLD_HEIGHT;
        int distance;
        if (steps)
            distance = 2;
        else
            distance = 1;
        switch (direction) {
            case 0:
                lookx += distance;
                break;
            case 1:
                lookx += distance;
                looky += distance;
                break;
            case 2:
                looky += distance;
                break;
            case 3:
                lookx -= distance;
                looky += distance;
                break;
            case 4:
                lookx -= distance;
                break;
            case 5:
                lookx -= distance;
                looky -= distance;
                break;
            case 6:
                looky -= distance;
                break;
            case 7:
                lookx += distance;
                looky -= distance;
                break;
        }
        for (Critter c : oldPopulation) {
            if ((c.x_coord == lookx) && (c.y_coord == looky))
                return c.toString();
        }
        return null;
    }

        public static String runStats(List<Critter> critters) {
            //create String to be returned in the form of Stats: \n critter1.toString(): #ofcritter1\n...etc
            String result = "";

            //create HashMap to store number of each critter that is currently alive
            java.util.Map<String, Integer> numCritters = new java.util.HashMap<String, Integer>();

            //for each critter, add to the number of its class. If it is not currently in the map, make a key for it
            for(Critter c : population){
                //check to see if the critter we are viewing from population is of the type we want stats for
                boolean isInstance = false;
                for(Critter o : critters){
                    if(c.getClass() == o.getClass())
                        isInstance = true;
                }
                //if we have the right critter type, we increase its number in the hashmap by 1
                if(isInstance) {
                    String crit = c.toString();
                    Integer count = numCritters.get(crit);
                    if(count == null)
                        numCritters.put(crit, 1);
                    else
                        numCritters.put(crit, new Integer(count.intValue()+1));
                }
            }
            //add the number of critters for each critter selected to the String that is to be returned
            ArrayList<String> done = new ArrayList<String>();
            for(Critter c : critters){
                if(!done.contains(c.toString())){
                    result += c.toString() + ": " + numCritters.get(c.toString());
                    done.add(c.toString());
                }
            }
            return result;
        }


        public static void displayWorld(Object pane) {
            double canvasWidth = critterWidth * Params.WORLD_WIDTH;
            double canvasHeight = critterHeight * Params.WORLD_HEIGHT;
            Canvas display = new Canvas(canvasWidth, canvasHeight);
            GraphicsContext gc = display.getGraphicsContext2D();
            gc.setFill(Color.WHITE);
            gc.fillRect(0, 0, canvasWidth-1, canvasHeight-1);

            for(Critter c : population){

                switch(c.viewShape()){
                    case CIRCLE:
                        Shapes.drawCircle(c.x_coord*5, c.y_coord*5, critterWidth, critterHeight, c.viewColor(), c.viewFillColor(), gc);
                        break;

                    case SQUARE:
                        Shapes.drawSquare(c.x_coord*5, c.y_coord*5, critterWidth, critterHeight, c.viewColor(), c.viewFillColor(), gc);
                        break;

                    case TRIANGLE:
                        Shapes.drawTriangle(c.x_coord*5, c.y_coord*5, critterWidth, critterHeight, c.viewColor(), c.viewFillColor(), gc);
                        break;

                    case STAR:
                        Shapes.drawStar(c.x_coord*5, c.y_coord*5, critterWidth, critterHeight, c.viewColor(), c.viewFillColor(), gc);
                        break;

                    case DIAMOND:
                        Shapes.drawDiamond(c.x_coord*5, c.y_coord*5, critterWidth, critterHeight, c.viewColor(), c.viewFillColor(), gc);
                        break;

                }
            }

            ScrollPane finalWorld = new ScrollPane();
            finalWorld.setContent(display);
            finalWorld.fitToWidthProperty().setValue(true);
            finalWorld.fitToHeightProperty().setValue(true);
            finalWorld.pannableProperty().set(true);
            ((GridPane)pane).getChildren().add(finalWorld);

        }

	/* END --- NEW FOR PROJECT 5
			rest is unchanged from Project 4 */

    /* Gets the package name.  This assumes that Critter and its
     * subclasses are all in the same package. */
    private static String myPackage;

    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    private static Random rand = new Random();

    public static int getRandomInt(int max) {
        return rand.nextInt(max);
    }

    public static void setSeed(long new_seed) {
        rand = new Random(new_seed);
    }

    /**
     * create and initialize a Critter subclass.
     * critter_class_name must be the qualified name of a concrete
     * subclass of Critter, if not, an InvalidCritterException must be
     * thrown.
     *
     * @param critter_class_name
     * @throws InvalidCritterException
     */
    public static void createCritter(String critter_class_name)
            throws InvalidCritterException {
        Class<?> newCritter = null;

        try {
            newCritter = Class.forName(myPackage + "." + critter_class_name);
        } catch (ClassNotFoundException e) {
            throw new InvalidCritterException(critter_class_name);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Object critter = newCritter.getConstructor().newInstance();

            // Set the new critter's position by using casting to bypass editing private fields
            ((Critter) critter).x_coord = Critter.getRandomInt(Params.WORLD_WIDTH);
            ((Critter) critter).y_coord = Critter.getRandomInt(Params.WORLD_HEIGHT);
            ((Critter) critter).energy = Params.START_ENERGY;

            // Add the critter to the total population
            population.add((Critter) critter);

        } catch (InstantiationException e) {
            throw new InvalidCritterException("");
        } catch (InvocationTargetException e) {
            throw new InvalidCritterException("");
        } catch (NoSuchMethodException e) {
            throw new InvalidCritterException("");
        } catch (IllegalAccessException e) {
            throw new InvalidCritterException("");
        } catch (IllegalArgumentException e) {
            throw new InvalidCritterException("");
        } catch (SecurityException e) {
            throw new InvalidCritterException("");
        }

    }

    /**
     * Gets a list of critters of a specific type.
     *
     * @param critter_class_name What kind of Critter is to be listed.
     *        Unqualified class name.
     * @return List of Critters.
     * @throws InvalidCritterException
     */
    public static List<Critter> getInstances(String critter_class_name)
            throws InvalidCritterException {
        List<Critter> allInstances = new ArrayList<Critter>();
        Class<?> critter = null;
        try {
            critter = Class.forName(myPackage + "." + critter_class_name);
        } catch (ClassNotFoundException e) {
            throw new InvalidCritterException("");
        }

        for (Critter crit : population) {
            if (critter.isInstance(crit)) {
                allInstances.add(crit);
            }
        }
        return allInstances;
    }

    /**
     * Clear the world of all critters, dead and alive
     */
    public static void clearWorld() {
        population.clear();
        babies.clear();
    }

    public static void worldTimeStep() {
        // Do specific time steps for all members of the population
        for (int i = 0; i < population.size(); i++) {
            population.get(i).doTimeStep();
        }

        // removing all dead critters after a time step
        for (int i = 0; i < population.size(); i++) {
            if (population.get(i).getEnergy() <= 0) {
                population.remove(population.get(i));
            }
        }

        doEncounters();

        // Make all critters rest for the night
        for (int j = 0; j < population.size(); j++) {
            population.get(j).energy -= Params.REST_ENERGY_COST;
            population.get(j).hasMoved = false;
        }

        for (int i = 0; i < Params.REFRESH_CLOVER_COUNT; i++) {         // Make clovers
            TestCritter clover = new Clover();
            clover.setEnergy(Params.START_ENERGY);
            clover.setX_coord(getRandomInt(Params.WORLD_WIDTH));
            clover.setY_coord(getRandomInt(Params.WORLD_HEIGHT));
            population.add(clover);
        }
        population.addAll(babies);                                  // adding all babies to general population
        babies.clear();

    }

    private int stepX(int x) {                               // helper functions to step the X and Y coordinates
        x_coord += x;
        x_coord = (x_coord + Params.WORLD_WIDTH) % Params.WORLD_WIDTH;
        return x_coord;
    }

    private int stepY(int y) {
        y_coord += y;
        y_coord = (y_coord + Params.WORLD_HEIGHT) % Params.WORLD_HEIGHT;
        return y_coord;
    }

    public static void displayWorld() {
        String[][] grid = new String[Params.WORLD_WIDTH][Params.WORLD_HEIGHT];
        //fill the grid out by searching for matching coord of population with each point on the grid
        for(int i = 0; i < Params.WORLD_WIDTH; i++) {
            for(int j = 0; j < Params.WORLD_HEIGHT; j++) {
                grid[i][j] = " ";
                for(int k = 0; k < population.size(); k++) {
                    if(population.get(k).x_coord == i && population.get(k).y_coord == j) {
                        grid[i][j] = population.get(k).toString();
                    }
                }
            }
        }

        //print out the grid with the border
        System.out.print("+");
        for(int i = 0; i < Params.WORLD_WIDTH; i++)
            System.out.print("-");
        System.out.print("+\n");
        //print out the actual contents of the grid with the vertical line border, going one row at a time
        for(int i = 0; i < Params.WORLD_HEIGHT; i++) {
            System.out.print("|");
            for(int j = 0; j < Params.WORLD_WIDTH; j++) {
                System.out.print(grid[j][i]);
            }
            System.out.print("|\n");
        }
        //print out the bottom of the border
        System.out.print("+");
        for(int i = 0; i < Params.WORLD_WIDTH; i++)
            System.out.print("-");
        System.out.print("+\n");

    }

    /**
     * Prints out how many Critters of each type there are on the
     * board.
     *
     * @param critters List of Critters.
     */

    public abstract void doTimeStep();

    public abstract boolean fight(String opponent);

    /* a one-character long string that visually depicts your critter
     * in the ASCII interface */
    public String toString() {
        return "";
    }

    protected int getEnergy() {
        return energy;
    }

    protected final void run(int direction) {
        energy -= Params.RUN_ENERGY_COST;
        if (!hasMoved) {
            switch (direction) {
                case 0:
                    x_coord = stepX(2);
                    break;
                case 1:
                    x_coord = stepX(2);
                    y_coord = stepY(2);
                    break;
                case 2:
                    y_coord = stepY(2);
                    break;
                case 3:
                    x_coord = stepX(-2);
                    y_coord = stepY(2);
                    break;
                case 4:
                    x_coord = stepX(-2);
                    break;
                case 5:
                    x_coord = stepX(-2);
                    y_coord = stepY(-2);
                    break;
                case 6:
                    y_coord = stepY(-2);
                    break;
                case 7:
                    x_coord = stepX(2);
                    y_coord = stepY(-2);
                    break;
            }
            hasMoved = true;
        }
    }

    protected final void walk(int direction) {
        energy -= Params.WALK_ENERGY_COST;
        if (!hasMoved) {

            switch (direction) {
                case 0:
                    x_coord = stepX(1);
                    break;
                case 1:
                    x_coord = stepX(1);
                    y_coord = stepY(1);
                    break;
                case 2:
                    y_coord = stepY(1);
                    break;
                case 3:
                    x_coord = stepX(-1);
                    y_coord = stepY(1);
                    break;
                case 4:
                    x_coord = stepX(-1);
                    break;
                case 5:
                    x_coord = stepX(-1);
                    y_coord = stepY(-1);
                    break;
                case 6:
                    y_coord = stepY(-1);
                    break;
                case 7:
                    x_coord = stepX(1);
                    y_coord = stepY(-1);
                    break;
            }
            hasMoved = true;
        }
    }

    protected final void reproduce(Critter offspring, int direction) {
        if (energy < Params.MIN_REPRODUCE_ENERGY) {
            return;
        } else {
            offspring.energy = energy / 2;                      // set offspring energy to half of parents
            energy = (energy + 1) / 2;                          // half parents energy

            switch (direction) {                                // this for creating the child adjacently to the parent
                case 0:
                    offspring.x_coord = x_coord + 1;
                    break;
                case 1:
                    offspring.x_coord = x_coord + 1;
                    offspring.y_coord = y_coord + 1;
                    break;
                case 2:
                    offspring.y_coord = y_coord + 1;
                    break;
                case 3:
                    offspring.x_coord = x_coord - 1;
                    offspring.y_coord = y_coord + 1;
                    break;
                case 4:
                    offspring.x_coord = x_coord - 1;
                    break;
                case 5:
                    offspring.x_coord = x_coord - 1;
                    offspring.y_coord = y_coord - 1;
                    break;
                case 6:
                    offspring.y_coord = y_coord - 1;
                    break;
                case 7:
                    offspring.x_coord = x_coord + 1;
                    offspring.y_coord = y_coord - 1;
                    break;
            }
            // Make sure x and y coordinates wrap around
            offspring.x_coord = (x_coord + Params.WORLD_WIDTH) % Params.WORLD_WIDTH;
            offspring.y_coord = (y_coord + Params.WORLD_HEIGHT) % Params.WORLD_HEIGHT;
            // Add any babies to general population
            babies.add(offspring);
        }
    }

    /**
     * The TestCritter class allows some critters to "cheat". If you
     * want to create tests of your Critter model, you can create
     * subclasses of this class and then use the setter functions
     * contained here.
     * <p>
     * NOTE: you must make sure that the setter functions work with
     * your implementation of Critter. That means, if you're recording
     * the positions of your critters using some sort of external grid
     * or some other data structure in addition to the x_coord and
     * y_coord functions, then you MUST update these setter functions
     * so that they correctly update your grid/data structure.
     */
    static abstract class TestCritter extends Critter {

        protected void setEnergy(int new_energy_value) {
            super.energy = new_energy_value;
        }

        protected void setX_coord(int new_x_coord) {
            super.x_coord = new_x_coord;
        }

        protected void setY_coord(int new_y_coord) {
            super.y_coord = new_y_coord;
        }

        protected int getX_coord() {
            return super.x_coord;
        }

        protected int getY_coord() {
            return super.y_coord;
        }

        /**
         * This method getPopulation has to be modified by you if you
         * are not using the population ArrayList that has been
         * provided in the starter code.  In any case, it has to be
         * implemented for grading tests to work.
         */
        protected static List<Critter> getPopulation() {
            return population;
        }

        /**
         * This method getBabies has to be modified by you if you are
         * not using the babies ArrayList that has been provided in
         * the starter code.  In any case, it has to be implemented
         * for grading tests to work.  Babies should be added to the
         * general population at either the beginning OR the end of
         * every timestep.
         */
        protected static List<Critter> getBabies() {
            return babies;
        }
    }

    private static void doEncounters() {
        for (int i = 0; i < population.size() - 1; i++) {
            Critter jimbo = population.get(i);
            if (jimbo.energy < 0)
                continue;
            int x = jimbo.x_coord;
            int y = jimbo.y_coord;
            for (int j = i+1; j < population.size(); j++) {
                Critter slimBo = population.get(j);
                if (slimBo.energy < 0)
                    continue;
                int x_1 = slimBo.x_coord;
                int y_1 = slimBo.y_coord;

                if (jimbo.x_coord == x && jimbo.y_coord == y && slimBo.x_coord == x_1 && slimBo.y_coord == y_1) {
                    int attackOfJimbo = 0;
                    int attackOfSlimBo = 0;

                    if (jimbo.fight(slimBo.toString())) {
                        if (jimbo.energy == 0) {
                            attackOfJimbo = 0;
                        } else
                            attackOfJimbo = getRandomInt(jimbo.energy);
                    }
                    if (slimBo.fight(jimbo.toString())){
                        if (slimBo.energy == 0) {
                            attackOfSlimBo = 0;
                        } else {
                            attackOfSlimBo = getRandomInt(slimBo.energy);
                        }
                    }

                    if (jimbo.x_coord != x || jimbo.y_coord != y) {
                        break;
                    } else if (slimBo.x_coord != x_1 || slimBo.y_coord != y_1) {
                        continue;
                    }

                    if(jimbo.x_coord == x && slimBo.x_coord == x && jimbo.y_coord == y
                            && slimBo.y_coord == y && slimBo.energy > 0 && jimbo.energy > 0) {

                        if (attackOfJimbo >= attackOfSlimBo) {
                            jimbo.energy += slimBo.energy / 2;
                            slimBo.energy = 0;
                        } else {
                            slimBo.energy += jimbo.energy / 2;
                            jimbo.energy = 0;
                        }
                    }
                }
            }
        }

    }
}