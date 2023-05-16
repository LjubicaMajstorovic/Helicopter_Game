package com.example.demo;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;

public class SpeedMeter extends Group {
    private Rectangle rectangle;
    Circle circle;
    private double maxValue;
    private Translate positionCircle;
    private double height;

    public SpeedMeter(double maxValue, double width, double height) {
        this.maxValue = maxValue;
        this.height = height / 2.0;
        this.rectangle = new Rectangle(width, height);
        this.circle = new Circle(width / 2.0);
        this.rectangle.setFill((Paint)null);
        this.rectangle.setStroke(Color.BLACK);
        this.circle.setFill(Color.RED);
        this.circle.getTransforms().addAll(new Transform[]{new Translate(width / 2.0, height / 2.0)});
        this.positionCircle = new Translate();
        this.circle.getTransforms().addAll(new Transform[]{this.positionCircle});
        super.getChildren().addAll(new Node[]{this.rectangle, this.circle});
        super.getTransforms().addAll(new Transform[]{new Translate(0.0, -height / 2.0)});
    }

    public void changeSpeed(double curSpeed) {
        double ratio = curSpeed / this.maxValue;
        this.positionCircle.setY(-ratio * this.height);
    }
}
