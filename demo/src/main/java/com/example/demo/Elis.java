package com.example.demo;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

public class Elis extends Group {
    private Group coreGroup;
    private Rectangle[] elis;
    private Rotate elisRotation;
    private Circle boundCircle;
    private boolean parked;
    private double speed;
    private boolean noFuel;
    private double ugaonaBrzina = 343.77467707849394;
    private double maxSpeed = 400.0;
    private Translate position;
    private Point2D direction = new Point2D(0.0, -1.0);
    private ScaleTransition scaleTransition;
    private double scale = 0.75;
    private double oldScale;
    private boolean rotateElis = false;

    private Scale[] scaleElis;

    private Helicopter myHelicopter;

    private Rotate rotate;

    public Elis(double width, double height) {
        //this.myHelicopter = helicopter;
        coreGroup = new Group();

        position = new Translate();
        rotate = new Rotate(0.0);

        this.parked = false;
        this.speed = 0.0;
        double elisHeight = 1.32 * width;
        this.boundCircle = new Circle(elisHeight);
        this.boundCircle.setFill((Paint)null);
        this.boundCircle.setStroke((Paint)null);
        this.elis = new Rectangle[3];
        this.elisRotation = new Rotate(0.0);
        this.position = new Translate();
        this.getTransforms().addAll( this.rotate, this.position);
        this.coreGroup.getTransforms().addAll(this.elisRotation);

        scaleElis = new Scale[3];

        for(int i = 0; i < 3; i++){
            scaleElis[i] = new Scale();
        }

        for(int i = 0; i < 3; ++i) {
            this.elis[i] = new Rectangle(0.14 * width, elisHeight);
            this.elis[i].getTransforms().addAll(scaleElis[i]);
            if (i == 0) {
                this.elis[i].getTransforms().addAll(new Transform[]{new Rotate(180.0), new Translate(-0.07 * width, 0.0)});
            } else {
                this.elis[i].getTransforms().addAll(new Transform[]{new Rotate(Math.pow(-1.0, (double)i) * 60.0), new Translate(-0.07 * width, -0.07 * width * Math.pow(1.0, (double)(i - 1)))});
            }

            this.elis[i].setFill(Color.BLACK);
            coreGroup.getChildren().addAll(new Node[]{this.elis[i]});
        }
        this.getChildren().addAll(coreGroup);

    }

    void setPosition(double x, double y){
        this.position.setX(x);
        this.position.setY(y);
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

    public void update(double ds, double speedDamp, double left, double right, double up, double down) {
        //this.position.setX(myHelicopter.getPosition().getX());
        //this.position.setY(myHelicopter.getPosition().getY());
        if (!this.parked) {
            double odlAngle = this.elisRotation.getAngle();
            this.elisRotation.setAngle(odlAngle + ds * this.ugaonaBrzina);
            double oldX = this.position.getX();
            double oldY = this.position.getY();
            double newX = oldX + ds * this.speed * this.direction.getX();
            double newY = oldY + ds * this.speed * this.direction.getY();
            this.position.setX(newX);
            this.position.setY(newY);
            this.speed *= speedDamp;
        }
    }

    public void rotate(double dAngle, double left, double right, double up, double down) {
        if (!this.parked) {
            double oldAngle = this.rotate.getAngle();
            double newAngle = oldAngle + dAngle;
            this.rotate.setAngle(newAngle);
            /*if (this.isWallHit(left, right, up, down)) {
                this.rotate.setAngle(oldAngle);
            } else {*/
            double magnitude = this.direction.magnitude();
            this.direction = new Point2D(magnitude * Math.sin(Math.toRadians(newAngle)), -magnitude * Math.cos(Math.toRadians(newAngle)));


        }
    }

    public void setParked(boolean parked) {
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        new KeyValue(scaleElis[0].xProperty(), 1),
                        new KeyValue(scaleElis[0].yProperty(), 1),
                        new KeyValue(scaleElis[1].xProperty(), 1),
                        new KeyValue(scaleElis[1].yProperty(), 1),
                        new KeyValue(scaleElis[2].xProperty(), 1),
                        new KeyValue(scaleElis[2].yProperty(), 1)

                ),
                new KeyFrame(
                        Duration.seconds(1),
                        new KeyValue(scaleElis[0].xProperty(), 1.33),
                        new KeyValue(scaleElis[0].yProperty(), 1.33),
                        new KeyValue(scaleElis[1].xProperty(), 1.33),
                        new KeyValue(scaleElis[1].yProperty(), 1.33),
                        new KeyValue(scaleElis[2].xProperty(), 1.33),
                        new KeyValue(scaleElis[2].yProperty(), 1.33)

                )
        );

        timeline.play();
    }
}
