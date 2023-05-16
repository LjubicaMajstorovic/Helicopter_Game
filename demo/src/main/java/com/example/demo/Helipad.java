package com.example.demo;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;

public class Helipad extends Group {
    private Rectangle pad;
    private Circle circle;
    private Line diagonal1;
    private Line diagonal2;
    private Rectangle whitePad;

    public Helipad(double width, double height) {
        this.pad = new Rectangle(width, height, Color.GREY);
        this.whitePad = new Rectangle(width, height, Color.WHITE);
        this.circle = new Circle(width / 2.0);
        this.circle.getTransforms().addAll(new Transform[]{new Translate(width / 2.0, width / 2.0)});
        this.circle.setFill((Paint)null);
        this.circle.setStroke(Color.WHITE);
        this.circle.setStrokeWidth(3.0);
        this.diagonal1 = new Line(0.0, 0.0, width, height);
        this.diagonal1.setStrokeWidth(3.0);
        this.diagonal2 = new Line(width, 0.0, 0.0, height);
        this.diagonal2.setStrokeWidth(3.0);
        Shape finalPad = Shape.subtract(this.pad, this.circle);
        finalPad = Shape.subtract(finalPad, this.diagonal1);
        finalPad = Shape.subtract(finalPad, this.diagonal2);
        finalPad.setFill(Color.GREY);
        super.getChildren().addAll(new Node[]{this.whitePad, finalPad});
    }
}
