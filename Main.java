package assignment5;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class Main extends Application {
    private final static int widthOfWorld = 500;
    public static GridPane grid = new GridPane();
    private int timeStep = 0;
    private TextArea gameStats = new TextArea();
    List<String> realCritters = new ArrayList<String>();
    private static String myPackage;

    public static void main(String[] args) {
        launch(args);
    }

    // setStats function to set current stats for the game
    public void setStats(String setStats) {
        gameStats.setText(setStats);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        final VBox buttons = new VBox();
        final GridPane secondaryGrid = new GridPane();
        // secondaryGrid.setGridLinesVisible(true);

        // Setting up myPackage statement
        myPackage = Critter.class.getPackage().toString().split(" ")[1];

        // Check if folder has extra types of critters
        Class[] arrayCrit;
        try {
            arrayCrit = validClasses(myPackage);
            for (int i = 0; i < arrayCrit.length; i++) {
                if ((Critter.class.isAssignableFrom(arrayCrit[i])) && (!Modifier.isAbstract(arrayCrit[i].getModifiers()))) {
                    realCritters.add(arrayCrit[i].getName().substring(myPackage.length() + 1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Title
        Label title = new Label("Welcome to Critters!");
        title.setFont(Font.font("Bradley Hand ITC", 50));
        title.setTextFill(Color.TOMATO);
        GridPane.setConstraints(title, 0, 0, 4, 4);

        // Setting up the world
        GridPane world = new GridPane();
        world.setGridLinesVisible(true);
        GridPane.setConstraints(world, 3, 23);
        Critter.displayWorld(world);

        // Show stats method
        Label showStats = new Label("Show stats for:");
        GridPane.setConstraints(showStats, 0, 15);
        CheckBox[] critters = new CheckBox[realCritters.size()];
        for (int i = 0; i < realCritters.size(); i++) {
            critters[i] = new CheckBox(realCritters.get(i));
            secondaryGrid.add(critters[i], 1, i + 15);
        }
        VBox stats = new VBox();
        stats.getChildren().add(gameStats);
        stats.setAlignment(Pos.CENTER);
        GridPane.setConstraints(stats, 1, 23);
        Label statsStart = new Label("Game stats");
        // GridPane.setConstraints(statsStart, 1, 24);
        statsStart.setFont(Font.font("Calibri", 40));
        stats.getChildren().add(statsStart);

        // Add Critter function
        Label add = new Label("Add a critter: ");
        GridPane.setConstraints(add, 0, 5);
        ChoiceBox<String> validCritters = new ChoiceBox<>();
        validCritters.getItems().addAll(realCritters);
        GridPane.setConstraints(validCritters, 2, 5);
        TextField numOfCrit = new TextField();
        numOfCrit.setPromptText("Enter number of critters");
        GridPane.setConstraints(numOfCrit, 1, 5);
        Button makeCrt = new Button("Create critters");
        GridPane.setConstraints(makeCrt, 3, 5);
        makeCrt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int n = 0;
                try {
                    n = Integer.parseInt(numOfCrit.getText());
                } catch (Exception e) {
                    System.out.println("Invalid");
                    return;
                }
                if (n <= 0) {
                    System.out.println("Invalid");
                    return;
                }
                for (int i = 0; i < n; i++) {
                    try {
                        Critter.createCritter(validCritters.getValue());
                    } catch (Exception e) {
                        return;
                    }
                }
                Critter.displayWorld(world);
                numOfCrit.clear();
            }
        });

        // Perform time steps function
        Label userTimeSteps = new Label("Enter number of time steps: ");
        GridPane.setConstraints(userTimeSteps, 0, 9);
        TextField numOfSteps = new TextField();
        numOfSteps.setPromptText("Default is 1 time step");
        GridPane.setConstraints(numOfSteps, 1, 9);

        Label timeSteps = new Label("Time step: " + timeStep);
        GridPane.setConstraints(timeSteps, 1, 10);

        Button stepTime = new Button("Simulate");
        GridPane.setConstraints(stepTime, 2, 9);
        stepTime.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int n = 0;
                String input = numOfSteps.getText();
                if (input.equals("")) {
                    n = 1;
                } else {
                    try {
                        n = Integer.parseInt(input);
                    } catch (Exception e) {
                        System.out.println("Invalid input");
                    }
                }
                    for (int i = 0; i < n; i++) {
                        Critter.worldTimeStep();
                    }
                    runStatsUpdater(critters);
                    timeStep += n;
                    timeSteps.setText("Time step: " + timeStep);

                    Critter.displayWorld(world);
                }

        });

        // Quit button
        Button quitBtn = new Button("Quit");
        quitBtn.setFont(Font.font("Calibri", FontWeight.BOLD, 12));
        GridPane.setConstraints(quitBtn, 5, 0);
        quitBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });

        // Clear world button
        Button clearWld = new Button("Clear the world");
        clearWld.setFont(Font.font("Calibri", FontWeight.BOLD, 12));
        GridPane.setConstraints(clearWld, 3, 21);
        GridPane.setHalignment(clearWld, HPos.RIGHT);
        clearWld.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Critter.clearWorld();
                Critter.displayWorld(world);
                timeStep = 0;
                timeSteps.setText("Time step: " + timeStep);
            }
        });

        // Random seed button
        Label randSeed = new Label("Set random seed: ");
        GridPane.setConstraints(randSeed, 0, 7);
        TextField seed = new TextField();
        seed.setPromptText("Enter the seed");
        GridPane.setConstraints(seed, 1, 7);
        Button random = new Button("Randomize");
        GridPane.setConstraints(random, 2, 7);
        random.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int n = 0;
                String input = seed.getText();
                try {
                    n = Integer.parseInt(input);
                } catch (Exception e) {
                    System.out.println("Invalid input");
                }
                Critter.setSeed(n);
                seed.clear();
                Critter.displayWorld(world);
            }
        });

        // Animation
        Label speedChoice = new Label("Slide to determine FPS (Frames Per Second)");
        GridPane.setConstraints(speedChoice, 3, 11);

        Slider FPS = new Slider(1, 15, 8);
        FPS.setShowTickMarks(true);
        FPS.setShowTickLabels(true);
        FPS.setBlockIncrement(1);
        GridPane.setConstraints(FPS, 3, 14);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double speed = FPS.getValue();
                speed = 1000000000 / speed;
            long begin = System.nanoTime();
            Critter.worldTimeStep();
            Critter.displayWorld(world);
            timeStep++;
            timeSteps.setText("Time step: " + timeStep);
            runStatsUpdater(critters);
            while (System.nanoTime() - begin < speed) {}
            }
        };

        Button stopAni = new Button("Stop");
        stopAni.setDisable(true);
        GridPane.setConstraints(stopAni, 4, 14);
        stopAni.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                timer.stop();
                quitBtn.setDisable(false);
                clearWld.setDisable(false);
                random.setDisable(false);
                stepTime.setDisable(false);
                makeCrt.setDisable(false);
            }
        });

        Button startAni = new Button("Start");
        GridPane.setConstraints(startAni, 4, 11);
        startAni.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                timer.start();
                stopAni.setDisable(false);
                quitBtn.setDisable(true);
                clearWld.setDisable(true);
                random.setDisable(true);
                stepTime.setDisable(true);
                makeCrt.setDisable(true);
            }
        });

        secondaryGrid.getChildren().addAll(title, buttons, userTimeSteps, numOfSteps,
                randSeed, seed, random, quitBtn, clearWld, stepTime, timeSteps, showStats, validCritters,
                add, numOfCrit, makeCrt, FPS, speedChoice, stopAni, startAni, world, stats);

        // Stage stats = new Stage();
        // stats.setTitle("Statistics");
        StackPane statInfo = new StackPane();
        Scene statScene = new Scene(statInfo);
        // stats.setScene(statScene);
        // stats.show();

        // Making the stage look pretty
        secondaryGrid.setHgap(5);
        secondaryGrid.setVgap(5);
        secondaryGrid.setPadding(new Insets(10, 10, 20, 10));

        Scene thisScene = new Scene(secondaryGrid);
        primaryStage.setScene(thisScene);
        primaryStage.setTitle("Critters Simulation");
        primaryStage.show();
    }




    private static Class[] validClasses(String nameOfPackage) throws IOException, ClassNotFoundException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = nameOfPackage.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();

        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }

        ArrayList<Class> classes = new ArrayList<Class>();

        for (File directory : dirs) {
            classes.addAll(findClasses(directory, nameOfPackage));
        }

        return classes.toArray(new Class[classes.size()]);
    }

    private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {

        List<Class> classes = new ArrayList<Class>();

        if (!directory.exists()) {
            return classes;
        }

        File[] files = directory.listFiles();

        for (File file : files) {

            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            }
            else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }

        return classes;
    }

    private void runStatsUpdater(CheckBox[] critters) {
        String result = "List of alive critters (denoted by their symbol): \n";
        gameStats.appendText(result);
        for (int i = 0; i < critters.length; i++) {
            String specific = realCritters.get(i);

            if (critters[i].isSelected()) {
                List<Critter> totalPop;
                try {
                    totalPop = Critter.getInstances(specific);
                } catch (Exception e) {
                    return;
                }

                Class<?> crit;
                try {
                    crit = Class.forName(myPackage + "." + specific);
                    Method meth = crit.getMethod("runStats", List.class);
                    gameStats.appendText(meth.invoke(null, totalPop) + "\n");
                } catch (Exception e) {
                    String statRun = Critter.runStats(totalPop);
                    gameStats.appendText(statRun);
                }
            }
        }
    }

}
