package com.example.demo;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import javafx.util.Duration;

public class HeightMeter extends Rectangle{

    private static final double width = 1.5;
    private static final double maxHeight = 600;

    private boolean parked;
    HeightMeter(){
        super(width, 0, Color.BLUE);
        parked = true;


    }

    void updateHeight(){
        double height;
        double oldHeight;
        if(parked){
            height = maxHeight;
            oldHeight = 0;;
        } else{
            height = 0;
            oldHeight = maxHeight;
        }
        parked = !parked;
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        new KeyValue(super.heightProperty(), oldHeight, Interpolator.LINEAR)

                ),
                new KeyFrame(
                        Duration.seconds(2),
                        new KeyValue(super.heightProperty(), height, Interpolator.LINEAR)
                )
        );

        timeline.play();
    }
}
