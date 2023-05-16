package com.example.demo;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

public class Helicopter extends Group {
    private Point2D direction;
    private Rotate rotate;
    private Translate position;
    private double speed;
    private double maxSpeed = 300.0;
    private Ellipse cockpit;
    private Rectangle tail;
    private Rectangle horizontalPartOfTail;
    private Group tailGroup;
    private Circle boundCircle;
    private Rectangle[] elis;
    private Group elisGroup;
    private Rotate elisRotation;
    private double ugaonaBrzina;
    private Group helicopterBody;
    private boolean noFuel;
    private boolean parked;
    private boolean elisRotate;
    private Scale scaleHelicopter;

    private double width;
    private double height;
    private Scale[] scaleElis;


    public Helicopter(double width, double height) {

        this.width = width;
        this.height = height;
        this.scaleHelicopter = new Scale();
        super.getTransforms().addAll(this.scaleHelicopter);
        this.parked = true;
        this.elisRotate = false;
        this.noFuel = false;
        this.helicopterBody = new Group();
        this.ugaonaBrzina = 343.77467707849394;
        this.speed = 0.0;
        this.cockpit = new Ellipse(width / 2.0, 1.3 * width / 2.0);
        Stop[] stops = new Stop[]{new Stop(0.0, Color.DARKORANGE), new Stop(1.0, Color.BLUE)};
        LinearGradient linearGradient = new LinearGradient(1.0, 0.0, 1.0, 1.0, true, CycleMethod.NO_CYCLE, stops);
        this.cockpit.setFill(linearGradient);
        this.cockpit.setStroke(linearGradient);
        double tailWidth = 0.15 * width;
        double tailHeight = height - width / 2.0;
        this.tail = new Rectangle(tailWidth, tailHeight);
        this.tail.setFill(Color.BLUE);
        this.tail.setStroke(Color.BLUE);
        this.tail.getTransforms().addAll(new Translate(-tailWidth / 2.0, 0.0));
        this.horizontalPartOfTail = new Rectangle(3.0 * width / 4.0, 1.5 * tailWidth);
        this.horizontalPartOfTail.setFill(Color.BLUE);
        this.horizontalPartOfTail.getTransforms().addAll(new Translate(-3.0 * width / 8.0, 10.0 * height / 18.0));
        this.tailGroup = new Group();
        this.tailGroup.getChildren().addAll(this.tail, this.horizontalPartOfTail);
        double elisHeight = 1.32 * width;
        this.boundCircle = new Circle(elisHeight);
        this.boundCircle.setFill((Paint)null);
        this.boundCircle.setStroke((Paint)null);
        this.elis = new Rectangle[3];
        this.elisRotation = new Rotate(0.0);
        this.elisGroup = new Group();
        scaleElis = new Scale[3];

        for(int i = 0; i < 3; i++){
            scaleElis[i] = new Scale();
        }

        for(int i = 0; i < 3; ++i) {
            this.elis[i] = new Rectangle(0.14 * width, elisHeight);
            this.elis[i].getTransforms().addAll(scaleElis[i]);
            if (i == 0) {
                this.elis[i].getTransforms().addAll(new Rotate(180.0), new Translate(-0.07 * width, 0.0));
            } else {
                this.elis[i].getTransforms().addAll(new Rotate(Math.pow(-1.0, (double)i) * 60.0), new Translate(-0.07 * width, -0.07 * width * Math.pow(1.0, (double)(i - 1))));
            }

            this.elis[i].setFill(Color.BLACK);
            elisGroup.getChildren().addAll(this.elis[i]);
        }

        this.helicopterBody.getChildren().addAll(this.tailGroup, this.cockpit);
        this.rotate = new Rotate(0.0);
        this.scaleHelicopter = new Scale();
        this.helicopterBody.getTransforms().addAll(rotate, scaleHelicopter);
        super.getChildren().addAll( helicopterBody);
        this.getChildren().addAll(elisGroup);
        this.elisGroup.getTransforms().addAll(this.elisRotation);
        this.direction = new Point2D(0.0, -1.0);
        this.position = new Translate();
        super.getTransforms().addAll(this.position);

    }

    private boolean isWallHit(double left, double right, double up, double down) {
        Bounds cockpitBounds = this.cockpit.localToScene ( this.cockpit.getBoundsInLocal ( ) );
        Bounds tailBounds    = this.tail.localToScene ( this.tail.getBoundsInLocal ( ) );
        Bounds horizontalPartBounds = this.horizontalPartOfTail.localToScene(this.horizontalPartOfTail.getBoundsInLocal());

        double cockpitMinX = cockpitBounds.getCenterX ( ) - this.cockpit.getRadiusX ( );
        double cockpitMaxX = cockpitBounds.getCenterX ( ) + this.cockpit.getRadiusX ( );
        double cockpitMinY = cockpitBounds.getCenterY ( ) - this.cockpit.getRadiusY ( );
        double cockpitMaxY = cockpitBounds.getCenterY ( ) + this.cockpit.getRadiusY ( );

        boolean cockpitWallHit = cockpitMinX <= left || cockpitMaxX >= right || cockpitMinY <= up || cockpitMaxY >= down;

        double tailMinX = horizontalPartBounds.getMinX ( );
        double tailMaxX = horizontalPartBounds.getMaxX ( );
        double tailMinY = tailBounds.getMinY ( );
        double tailMaxY = tailBounds.getMaxY ( );

        boolean tailWallHit = tailMinX <= left || tailMaxX >= right || tailMinY <= up || tailMaxY >= down;


        return cockpitWallHit || tailWallHit;
    }

    Translate getPosition(){
        return position;
    }

    public void rotate(double dAngle, double left, double right, double up, double down) {
        if (!this.parked) {
            double oldAngle = this.rotate.getAngle();
            double newAngle = oldAngle + dAngle;
            this.rotate.setAngle(newAngle);
            if (this.isWallHit(left, right, up, down)) {
                this.rotate.setAngle(oldAngle);
            } else {
                double magnitude = this.direction.magnitude();
                this.direction = new Point2D(magnitude * Math.sin(Math.toRadians(newAngle)), -magnitude * Math.cos(Math.toRadians(newAngle)));
            }

        }
    }

    public void changeSpeed(double dSpeed) {
        if (!this.parked) {
            double tempSpeed = this.speed + dSpeed;
            if (this.noFuel) {
                this.speed = 0.0;
            }

            if (tempSpeed > 0.0) {
                this.speed = Math.min(tempSpeed, this.maxSpeed);
            } else {
                this.speed = Math.max(tempSpeed, -this.maxSpeed);
            }

        }
    }

    public double getSpeed() {
        return this.speed;
    }

    public double getMaxSpeed() {
        return this.maxSpeed;
    }

    public boolean isThereNoFuel() {
        return this.noFuel;
    }

    public void setNoFuel(boolean fuel) {
        this.noFuel = fuel;
    }

    public void setParked() {
        double oldScale;
        double newScale;
        this.parked = !this.parked;
        if (parked){
            oldScale = 1.33;
            newScale = 1;
            this.speed = 0;
        }
        else{
            this.elisRotate = true;
            oldScale = 1;
            newScale = 1.33;
        }
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        new KeyValue(scaleHelicopter.xProperty(), oldScale),
                        new KeyValue(scaleHelicopter.yProperty(), oldScale),
                        new KeyValue(scaleElis[0].xProperty(), oldScale),
                        new KeyValue(scaleElis[0].yProperty(), oldScale),
                        new KeyValue(scaleElis[1].xProperty(), oldScale),
                        new KeyValue(scaleElis[1].yProperty(), oldScale),
                        new KeyValue(scaleElis[2].xProperty(), oldScale),
                        new KeyValue(scaleElis[2].yProperty(), oldScale)
                ),

                new KeyFrame(
                        Duration.seconds(1),
                        new KeyValue(scaleHelicopter.xProperty(), newScale),
                        new KeyValue(scaleHelicopter.yProperty(), newScale),
                        new KeyValue(scaleElis[0].xProperty(), newScale),
                        new KeyValue(scaleElis[0].yProperty(), newScale),
                        new KeyValue(scaleElis[1].xProperty(), newScale),
                        new KeyValue(scaleElis[1].yProperty(), newScale),
                        new KeyValue(scaleElis[2].xProperty(), newScale),
                        new KeyValue(scaleElis[2].yProperty(), newScale)
                )
        );
        timeline.play();
        timeline.setOnFinished(event->{
            if(parked) {
                this.elisRotate = !elisRotate;
            }
        });


    }



    public boolean isParked() {
        return this.parked;
    }

    public void update(double ds, double speedDamp, double left, double right, double up, double down) {
        if(elisRotate){
            double odlAngle = this.elisRotation.getAngle();
            this.elisRotation.setAngle(odlAngle + ds * this.ugaonaBrzina);
        }

        if (!this.parked && !noFuel) {

            double oldX = this.position.getX();
            double oldY = this.position.getY();
            double newX = oldX + ds * this.speed * this.direction.getX();
            double newY = oldY + ds * this.speed * this.direction.getY();




            this.position.setX(newX);
            this.position.setY(newY);
            if (this.isWallHit(left, right, up, down)) {
                this.speed = 0.0;
                this.position.setX(oldX);
                this.position.setY(oldY);
            } else {
                this.speed *= speedDamp;
            }


        }
    }


}
