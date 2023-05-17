package com.example.demo;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;

public class Obstacle extends Rectangle {
    public Obstacle(double width, double height, Translate position){
        super(width, height);
        super.getTransforms().addAll(position);

        Image image = new Image("C:/Users/core I7/Desktop/RG_Domaci_1/Helicopter_Game/demo/images/wood.jpg");
        super.setFill(Color.TRANSPARENT);

        ImagePattern imagePattern= new ImagePattern(image, 0.0, 0.0, 1.0, 1.0, true);
        super.setFill(imagePattern);


    }

    public boolean handleCollision(Bounds helicopterBounds) {
        return super.getBoundsInParent().intersects(helicopterBounds);
    }

}
