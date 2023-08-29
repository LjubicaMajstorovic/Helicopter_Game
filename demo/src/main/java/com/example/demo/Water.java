package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;

import java.util.Random;

public class Water extends Path {
    private static double WATER_WIDTH = 100;
    private static double WATER_HEIGHT = 150;

    public Water(){
        super();
        Random random = new Random();

        super.getElements().addAll(new MoveTo(0,0), new QuadCurveTo((random.nextDouble() + 0.001)*WATER_WIDTH/5, (random.nextDouble() + 0.0001)*(WATER_HEIGHT*0.5), WATER_WIDTH/3, WATER_HEIGHT*random.nextDouble()),
        new QuadCurveTo(((random.nextDouble() + 0.001)*WATER_WIDTH/5) + WATER_WIDTH/3, (random.nextDouble() + 0.001)*(WATER_HEIGHT*0.5), WATER_WIDTH*2/3, WATER_HEIGHT*random.nextDouble()),
                new QuadCurveTo(((random.nextDouble() + 0.001)*WATER_WIDTH/5) + WATER_WIDTH*2/3, (random.nextDouble() + 0.001)*(WATER_HEIGHT*0.5), WATER_WIDTH*2/3, WATER_HEIGHT*random.nextDouble()),
                new QuadCurveTo(((random.nextDouble() + 0.001)*WATER_WIDTH/5) + WATER_WIDTH*2/3, (random.nextDouble() + 0.001)*(-WATER_HEIGHT*0.5), WATER_WIDTH*2/3, -WATER_HEIGHT*random.nextDouble()),
                new QuadCurveTo(((random.nextDouble() + 0.001)*WATER_WIDTH/5) + WATER_WIDTH/3, (random.nextDouble() + 0.001)*(-WATER_HEIGHT*0.5), WATER_WIDTH*2/3, -WATER_HEIGHT*random.nextDouble()),
                new QuadCurveTo((random.nextDouble() + 0.001)*WATER_WIDTH/5, (random.nextDouble() + 0.0001)*(-WATER_HEIGHT*0.5), WATER_WIDTH/3, -WATER_HEIGHT*random.nextDouble()));

        Image image = new Image("C:\\Users\\core I7\\Documents\\GitHub_Grafika\\Helicopter_Game\\demo\\images\\water.jpg");
        ImagePattern imagePattern = new ImagePattern(image);
        super.setFill(imagePattern);
        super.setStroke(Color.TRANSPARENT);
    }
}
