package com.example.demo;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class RootController {
    private Group root;


    private Rotate rotate;
    private Translate position;
    private Point2D direction;
    private double ugaonaBrzina = 343.77467707849394;



    private Helicopter helicopter;

    RootController(Group root, Helicopter helicopter){
        this.root = root;
        this.helicopter = helicopter;

        this.direction = new Point2D(0.0, -1.0);
        this.position = new Translate();
        this.rotate = new Rotate(0.0);

        this.root.getTransforms().addAll(rotate);
        this.root.getTransforms().addAll(position);

    }

    public void rotate(double dAngle, double left, double right, double up, double down, Obstacle[] obstacles) {
        if (!helicopter.isParked()) {
            double oldAngle = this.rotate.getAngle();
            double newAngle = oldAngle + dAngle;
            double oldHelicAngle = this.helicopter.getRotation().getAngle();
            this.rotate.setAngle(newAngle);
            this.helicopter.getRotation().setAngle(-newAngle);
            if (helicopter.isWallOrObstaclesHit(left, right, up, down, obstacles)) {
                this.rotate.setAngle(oldAngle);
                this.helicopter.getRotation().setAngle(oldHelicAngle);
            } else {
                double magnitude = this.direction.magnitude();
                this.direction = new Point2D(magnitude * Math.sin(Math.toRadians(newAngle)), -magnitude * Math.cos(Math.toRadians(newAngle)));
            }

        }
    }

    public void update(double ds, double speedDamp, double left, double right, double up, double down, Obstacle[] obstacles) {

        if (!helicopter.isParked() && !helicopter.isNoFuel()) {

            double oldX = this.position.getX();
            double oldY = this.position.getY();


            helicopter.getPosition().getY();
            double newX = oldX + ds * helicopter.getSpeed() * this.direction.getX();
            double newY = oldY + ds * helicopter.getSpeed() * this.direction.getY();

            this.position.setX(newX);
            this.position.setY(newY);

            this.helicopter.getPosition().setX(-newX);
            this.helicopter.getPosition().setY(-newY);

            if (helicopter.isWallOrObstaclesHit(left, right, up, down, obstacles)) {
                helicopter.setSpeed(0);
                this.position.setX(oldX);
                this.position.setY(oldY);
            } else {
                helicopter.setSpeed(helicopter.getSpeed()*speedDamp);
            }

        }
    }

    public void prepare(){
        this.helicopter.getPosition().setX(0);
        this.helicopter.getPosition().setY(0);
        this.helicopter.getRotation().setAngle(0);
        this.helicopter.setSpeed(0);
    }


    public void reset(){
        this.position.setX(0);
        this.position.setY(0);
        this.rotate.setAngle(0);
        this.helicopter.getRotation().setAngle(0);
        this.helicopter.getPosition().setX(0);
        this.helicopter.getPosition().setY(0);
        this.helicopter.setSpeed(0);
    }


}
