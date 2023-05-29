package assignment5;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

//all shapes are drawn with the intent of an even number for the height and width
    public abstract class Shapes {
    public static void drawCircle(int x, int y, int width, int height, Color o, Color f, GraphicsContext gc) {

        //set the colors
        gc.setStroke(o);
        gc.setFill(f);

        //draws the outline
        gc.strokeArc(x, y, width, height, 0, 360, ArcType.ROUND);

        //fills the outline
        gc.fillArc(x, y, width, height, 0, 360, ArcType.ROUND);
    }

    public static void drawSquare(int x, int y, int width, int height, Color o, Color f, GraphicsContext gc) {

        //set the colors
        gc.setStroke(o);
        gc.setFill(f);

        //draws the outline
        gc.strokeRect(x, y, width, height);

        //fills the outline
        gc.fillRect(x, y, width, height);
    }

    public static void drawTriangle(int x, int y, int width, int height, Color o, Color f, GraphicsContext gc) {

        //set the colors
        gc.setStroke(o);
        gc.setFill(f);

        //draws the outline
        gc.strokeLine(x, y + height, x + (width / 2), y);
        gc.strokeLine(x + (width / 2), y, x + width, y + height);
        gc.strokeLine(x + width, y + height, x, y + height);

        //fills the outline
        gc.beginPath();
        gc.moveTo(x, y + height);
        gc.lineTo(x + (width / 2), y);
        gc.lineTo(x + width, y + height);
        gc.lineTo(x, y + height);
        gc.closePath();
        gc.fill();
    }

    public static void drawDiamond(int x, int y, int width, int height, Color o, Color f, GraphicsContext gc) {

        //set the colors
        gc.setStroke(o);
        gc.setFill(f);

        //draws the outline
        gc.strokeLine(x + width / 2, y, x, y + height / 2);
        gc.strokeLine(x, y + height / 2, x + width / 2, y + height);
        gc.strokeLine(x + width / 2, y + height, x + width, y + height / 2);
        gc.strokeLine(x + width, y + height / 2, x + width / 2, y);

        //fills the outline
        gc.beginPath();
        gc.moveTo(x + width / 2, y);
        gc.lineTo(x, y + height / 2);
        gc.lineTo(x + width / 2, y + height);
        gc.lineTo(x + width, y + height / 2);
        gc.lineTo(x + width / 2, y);
        gc.closePath();
        gc.fill();


    }

    public static void drawStar(int x, int y, int width, int height, Color o, Color f, GraphicsContext gc) {
        //set the colors
        gc.setStroke(o);
        gc.setFill(f);

        //draws the outline
        gc.strokeLine(x, y + height, x + width / 2, y);
        gc.strokeLine(x + width / 2, y, x + width, y + height);
        // gc.strokeLine(x + width / 2, x, x + width, y + height);              either this method or the line above is correct
        gc.strokeLine(x + width, y + height, x, y + height / 4);
        gc.strokeLine(x, y + height / 4, x + width, y + height / 4);
        gc.strokeLine(x + width, y + height / 4, x, y + height);

        //fills the outline
        gc.beginPath();
        gc.moveTo(x, y + height);
        gc.lineTo(x + width / 2, y);
        gc.lineTo(x + width, y + height);
        gc.lineTo(x, y + height / 4);
        gc.lineTo(x + width, y + height / 4);
        gc.lineTo(x, y + height);
        gc.closePath();
        gc.fill();
    }
}
