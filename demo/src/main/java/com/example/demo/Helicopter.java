package com.example.demo;

import javafx.animation.*;
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

    private Circle boundCircleObstacle;
    private Rectangle[] elis;
    private Group elisGroup;
    private Rotate elisRotation;
    private double ugaonaBrzina;
    private Group helicopterBody;
    private boolean noFuel;
    private boolean parked;
    private boolean elisRotate;
    private Scale scaleHelicopter;
    private Scale[] scaleElis;
    private Helipad helipad = null;
    private boolean parkingRunning = false;
    private boolean parkedOnHelipad = false;

    private boolean helicopterMove = true;


    public Helicopter(double width, double height, Color color1, Color color2, double maxSpeed) {
        this.scaleHelicopter = new Scale();
        super.getTransforms().addAll(this.scaleHelicopter);
        this.parked = true;
        this.maxSpeed = maxSpeed;
        this.elisRotate = false;
        this.noFuel = false;
        this.helicopterBody = new Group();
        this.ugaonaBrzina = 343.77467707849394;
        this.speed = 0.0;
        this.cockpit = new Ellipse(width / 2.0, 1.3 * width / 2.0);
        Stop[] stops = new Stop[]{new Stop(0.0, color1), new Stop(1.0, color2)};
        LinearGradient linearGradient = new LinearGradient(1.0, 0.0, 1.0, 1.0, true, CycleMethod.NO_CYCLE, stops);
        this.cockpit.setFill(linearGradient);
        this.cockpit.setStroke(linearGradient);
        double tailWidth = 0.15 * width;
        double tailHeight = height - width / 2.0;
        this.tail = new Rectangle(tailWidth, tailHeight);
        this.tail.setFill(color2);
        this.tail.setStroke(color2);
        this.tail.getTransforms().addAll(new Translate(-tailWidth / 2.0, 0.0));
        this.horizontalPartOfTail = new Rectangle(3.0 * width / 4.0, 1.5 * tailWidth);
        this.horizontalPartOfTail.setFill(color2);
        this.horizontalPartOfTail.getTransforms().addAll(new Translate(-3.0 * width / 8.0, 10.0 * height / 18.0));
        this.tailGroup = new Group();
        this.tailGroup.getChildren().addAll(this.tail, this.horizontalPartOfTail);
        double elisHeight = 1.32 * width;
        this.boundCircle = new Circle(elisHeight);
        this.boundCircleObstacle = new Circle(elisHeight*1.17);
        this.boundCircleObstacle.setFill(Color.TRANSPARENT);
        this.boundCircleObstacle.setStroke(null);
        this.boundCircle.setFill(null);
        this.boundCircle.setStroke(null);
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

        this.helicopterBody.getChildren().addAll(this.tailGroup, this.cockpit, this.boundCircle, this.boundCircleObstacle);
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

    public boolean isParkingRunning(){
        return parkingRunning;
    }

    public void setParkingRunning(boolean running){
        parkingRunning = running;
    }

    public boolean isWallOrObstaclesHit(double left, double right, double up, double down, Obstacle[] obstacles) {
        Bounds cockpitBounds = this.boundCircle.localToScene ( this.boundCircle.getBoundsInLocal ( ) );
        Bounds tailBounds    = this.tail.localToScene ( this.tail.getBoundsInLocal ( ) );
        Bounds horizontalPartBounds = this.horizontalPartOfTail.localToScene(this.horizontalPartOfTail.getBoundsInLocal());

        double cockpitMinX = cockpitBounds.getCenterX ( ) - this.boundCircle.getRadius ( );
        double cockpitMaxX = cockpitBounds.getCenterX ( ) + this.boundCircle.getRadius ( );
        double cockpitMinY = cockpitBounds.getCenterY ( ) - this.boundCircle.getRadius ( );
        double cockpitMaxY = cockpitBounds.getCenterY ( ) + this.boundCircle.getRadius ( );

        boolean cockpitWallHit = cockpitMinX <= left || cockpitMaxX >= right || cockpitMinY <= up || cockpitMaxY >= down;

        double tailMinX = horizontalPartBounds.getMinX ( );
        double tailMaxX = horizontalPartBounds.getMaxX ( );
        double tailMinY = tailBounds.getMinY ( );
        double tailMaxY = tailBounds.getMaxY ( );

        boolean tailWallHit = tailMinX <= left || tailMaxX >= right || tailMinY <= up || tailMaxY >= down;

        boolean obstacleCollision;


        for(Obstacle obstacle: obstacles){
            obstacleCollision = obstacle.handleCollision(this.getBoundsInParent());
            if(obstacleCollision) return true;
        }


        return cockpitWallHit || tailWallHit;
    }


    public void rotate(double dAngle, double left, double right, double up, double down, Obstacle[] obstacles) {
        if (!this.parked && helicopterMove) {
            double oldAngle = this.rotate.getAngle();
            double newAngle = oldAngle + dAngle;
            this.rotate.setAngle(newAngle);
            if (this.isWallOrObstaclesHit(left, right, up, down, obstacles)) {
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


    public void setNoFuel(boolean fuel) {
        this.noFuel = fuel;
    }

    public void setParked() {
        double oldScale;
        double newScale;
        this.elisRotate = true;

        if (!parked){
            oldScale = 1.33;
            newScale = 1;
            this.speed = 0;
            this.elisRotate = true;

        }
        else{
            oldScale = 1;
            newScale = 1.33;
            if(parkedOnHelipad){
                this.parkedOnHelipad = false;
                this.helipad.removeFuelTank();
            }

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
                        Duration.seconds(2),
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
            this.parked = !this.parked;
            parkingRunning = false;

            if(parked) {
                this.elisRotate = !elisRotate;
                if(helipad.helicopterParked(this.getBoundsInParent())){
                    parkedOnHelipad = true;
                }
            } else{
                parkedOnHelipad = false;
            }

        });


    }

    public void setSpeed(double speed) { this.speed = speed; }

    public boolean isParked(){
        return parked;
    }
    public boolean isNoFuel() { return noFuel; }

    public void setHelicopterMove(boolean move) { this.helicopterMove = move; }


    public void update(double ds, double speedDamp, double left, double right, double up, double down, Obstacle[] obstacles) {
        if(elisRotate){
            double odlAngle = this.elisRotation.getAngle();
            this.elisRotation.setAngle(odlAngle + ds * this.ugaonaBrzina);
        }

        if (!this.parked && !noFuel && helicopterMove) {

            double oldX = this.position.getX();
            double oldY = this.position.getY();
            double newX = oldX + ds * this.speed * this.direction.getX();
            double newY = oldY + ds * this.speed * this.direction.getY();


            this.position.setX(newX);
            this.position.setY(newY);


            if (this.isWallOrObstaclesHit(left, right, up, down, obstacles)) {
                this.speed = 0.0;
                this.position.setX(oldX);
                this.position.setY(oldY);
            } else {
                this.speed *= speedDamp;
            }

        }
    }
    public void setHelicopterParkedOnHelipad(boolean isIt){
        parkedOnHelipad = isIt&&parked;
    }

    public boolean isHelicopterParkedOnHelipad(){
        return parkedOnHelipad;
    }

    public Translate getPosition() { return position; }
    public Rotate getRotation() { return rotate; }

    public void setHelipad(Helipad helipad){
        this.helipad = helipad;
    }

    public void sinkHelicopter(){

        double oldScale = 1.33;
        double newScale = 1;

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
                        new KeyValue(scaleElis[2].yProperty(), oldScale),
                        new KeyValue(this.opacityProperty(), 1, Interpolator.LINEAR)
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
                        new KeyValue(scaleElis[2].yProperty(), newScale),
                        new KeyValue(this.opacityProperty(), 1, Interpolator.LINEAR)
                ),

                new KeyFrame(
                        Duration.seconds(2),
                        new KeyValue(scaleHelicopter.xProperty(), 0),
                        new KeyValue(scaleHelicopter.yProperty(), 0),
                        new KeyValue(scaleElis[0].xProperty(), 0),
                        new KeyValue(scaleElis[0].yProperty(), 0),
                        new KeyValue(scaleElis[1].xProperty(), 0),
                        new KeyValue(scaleElis[1].yProperty(), 0),
                        new KeyValue(scaleElis[2].xProperty(), 0),
                        new KeyValue(scaleElis[2].yProperty(), 0),
                        new KeyValue(this.opacityProperty(), 0)
                )
        );

        timeline.play();
    }


}
