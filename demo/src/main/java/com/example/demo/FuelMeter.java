package com.example.demo;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;

public class FuelMeter extends Group {
    private Arc fuelMeter;
    private Arc criticalSection;
    private Line pointer;
    private Rotate pointerRotate;
    private double fuelInTank;
    private double maxFuel = 500.0;
    private static double decrement = 0.001;
    Helicopter helicopter;

    public FuelMeter(double width, Helicopter helicopter) {
        this.helicopter = helicopter;
        this.fuelMeter = new Arc(0.0, 0.0, width / 2.0, width / 2.0, 0.0, 180.0);
        this.fuelInTank = this.maxFuel;
        this.criticalSection = new Arc(0.0, 0.0, width / 2.0, width / 2.0, 120.0, 60.0);
        this.fuelMeter.setStrokeWidth(5.0);
        this.fuelMeter.setFill((Paint)null);
        this.fuelMeter.setStroke(Color.BLACK);
        this.criticalSection.setStrokeWidth(5.0);
        this.criticalSection.setStroke(Color.RED);
        this.criticalSection.setFill((Paint)null);
        this.pointer = new Line(width * 0.03, 0.0, width / 2.0, 0.0);
        this.pointerRotate = new Rotate(0.0);
        this.pointer.getTransforms().addAll(new Transform[]{this.pointerRotate});
        this.pointer.setStrokeWidth(4.0);
        super.getChildren().addAll(new Node[]{this.fuelMeter, this.criticalSection, this.pointer});
    }

    public void takeFuel(double speed) {
        this.fuelInTank = Math.max(this.fuelInTank - Math.abs(speed) * decrement, 0.0);
        this.pointerRotate.setAngle(-(this.maxFuel - this.fuelInTank) / this.maxFuel * 180.0);
        if (this.fuelInTank == 0.0) {
            this.helicopter.setNoFuel(true);
            this.helicopter.changeSpeed(-this.helicopter.getSpeed());
        }

    }

    public void putInFuel(double ds) {
        this.fuelInTank = Math.min(this.maxFuel, this.fuelInTank + ds * decrement);
    }
}
