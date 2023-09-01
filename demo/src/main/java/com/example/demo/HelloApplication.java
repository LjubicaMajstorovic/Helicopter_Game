package com.example.demo;

import javafx.animation.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.util.Duration;

public class HelloApplication extends Application {

    private static final double WINDOW_WIDTH = 750.0;
    private static final double WINDOW_HEIGHT = 750.0;
    private static final double HELICOPTER_WIDTH = 16.875;
    private static final double HELICOPTER_HEIGHT = 39.37500000000001;
    private static final double HELICOPTER_SPEED_STEP = 5.0;
    private static final double HELICOPTER_DIRECTION_STEP = 5.0;
    private static final double HELICOPTER_DAMP = 0.995;
    private static final double HELIPAD_WIDTH = 75.0;
    private static final double HELIPAD_HEIGHT = 75.0;
    private static final double PACKAGE_WIDTH = 15.0;
    private static final double PACKAGE_HEIGHT = 15.0;
    private static final double SPEEDMETER_WIDTH = 7.5;
    private static final double SPEEDMETER_HEIGHT = 675.0;
    private static final double FUELMETER_WIDTH = 75.0;
    private static final double FUELMETER_HEIGHT = 75.0;
    private static final double HEIGHTMETER_HEIGHT = 540;

    private static final double OBSTACLE_WIDTH = 110;
    private static final double OBSTACLE_HEIGHT = 8;

    private int packagesToCollect;

    private boolean isHelicopterPicked = false;
    private boolean isFieldPicked = false;
    private boolean isGameFinished = false;
    private boolean parking;

    private boolean helicopterMove = true;
    private Helicopter helicopter = null;
    private HeightMeter heightMeter = null;
    private Helipad helipad1 = null;
    private Helipad helipad2 = null;
    private Package[] packages = null;
    private Obstacle[] obstacles = null;
    private Water[] waters = null;

    private Group fld;

    public HelloApplication() {
    }

    public void start(Stage stage) {
        Group root = new Group();
        Helicopter helicopter1 = new Helicopter(HELICOPTER_WIDTH, HELICOPTER_HEIGHT, Color.DARKORANGE, Color.BLUE, 300.0);
        Helicopter helicopter2 = new Helicopter(HELICOPTER_WIDTH, HELICOPTER_HEIGHT, Color.DARKGRAY, Color.WHITE, 350.0);
        Helicopter helicopter3 = new Helicopter(HELICOPTER_WIDTH, HELICOPTER_HEIGHT, Color.YELLOWGREEN, Color.GREEN, 250.0);

        helicopter2.getTransforms().addAll(
                new Translate(WINDOW_WIDTH/4, 0)
        );
        helicopter3.getTransforms().addAll(
                new Translate(-WINDOW_WIDTH/4, 0)
        );
        Text text1 = new Text("300");
        Text text2 = new Text("350");
        Text text3 = new Text("250");

        text1.getTransforms().addAll(new Translate(-WINDOW_WIDTH*0.02, WINDOW_HEIGHT*0.1));
        text2.getTransforms().addAll(
                new Translate(WINDOW_WIDTH/4.3, WINDOW_HEIGHT*0.1)
        );
        text3.getTransforms().addAll(
                new Translate(-WINDOW_WIDTH/3.7, WINDOW_HEIGHT*0.1)
        );

        Font font = Font.font("Arial", FontWeight.BOLD, 18);

        text1.setFont(font);
        text2.setFont(font);
        text3.setFont(font);

        text1.setFill(Color.BLACK);
        text2.setFill(Color.BLACK);
        text3.setFill(Color.BLACK);




        root.getChildren().addAll(helicopter1, helicopter2, helicopter3, text1, text2, text3);
        root.getTransforms().addAll(new Translate(WINDOW_WIDTH/2, WINDOW_HEIGHT/2));
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        Image grass = new Image("C:\\Users\\core I7\\IdeaProjects\\HelikopterSkelet\\src\\main\\java\\com\\example\\helikopterskelet\\grass.jpg");
        ImagePattern background = new ImagePattern(grass, 0.0, 0.0, 1.0, 1.0, true);
        scene.setFill(background);
        stage.setTitle("Helicopter");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        Group newRoot = new Group();
        newRoot.getTransforms().addAll(new Translate(375.0, 375.0));
        helicopter1.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!isHelicopterPicked){
                    isHelicopterPicked = true;
                    helicopter = helicopter1;
                    pickField(scene);
                }
            }
        });

        helicopter2.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!isHelicopterPicked){
                    isHelicopterPicked = true;
                    helicopter = helicopter2;
                    helicopter.getTransforms().addAll(new Translate(-WINDOW_WIDTH/4, 0));
                    pickField(scene);
                }
            }
        });

        helicopter3.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!isHelicopterPicked){
                    isHelicopterPicked = true;
                    helicopter = helicopter3;
                    helicopter.getTransforms().addAll(new Translate(WINDOW_WIDTH/4, 0));
                    pickField(scene);
                }
            }
        });

    }

    private void pickField(Scene scene){
        Group root = new Group();
        Line line1 = new Line(0, -WINDOW_HEIGHT/2, 0, WINDOW_HEIGHT/2);
        Line line2 = new Line(WINDOW_WIDTH/2, 0, -WINDOW_WIDTH/2, 0);
        line1.setStrokeWidth(2);
        line2.setStrokeWidth(2);

        Field1 field1 = new Field1(null);
        Field2 field2 = new Field2(null);
        Field3 field3 = new Field3(null);
        Field4 field4 = new Field4(null);

        field1.getTransforms().addAll(new Translate(WINDOW_WIDTH/4, WINDOW_HEIGHT/4), new Scale(0.25, 0.25));
        field2.getTransforms().addAll(new Translate(-WINDOW_WIDTH/4, WINDOW_HEIGHT/4), new Scale(0.25, 0.25));
        field3.getTransforms().addAll(new Translate(-WINDOW_WIDTH/4, -WINDOW_HEIGHT/4), new Scale(0.25, 0.25));
        field4.getTransforms().addAll(new Translate(WINDOW_WIDTH/4, -WINDOW_HEIGHT/4), new Scale(0.25, 0.25));

        root.getChildren().addAll(field1, field2, field3, field4, line1, line2);
        root.getTransforms().addAll(new Translate(375.0, 375.0));
        scene.setRoot(root);

        Group newRoot = new Group();
        field1.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!isFieldPicked){
                    Field1 field = new Field1(helicopter);
                    field.getTransforms().addAll(new Translate(WINDOW_WIDTH/2, WINDOW_HEIGHT/2));
                    newRoot.getChildren().addAll(field);
                    scene.setRoot(newRoot);
                    helipad1 = field.getHelipad1();
                    helipad2 = field.getHelipad2();
                    packages = field.getPackages();
                    packagesToCollect = packages.length;
                    obstacles = field.getObstacles();
                    helicopter.setHelipad(helipad2);
                    waters = field.getWaters();
                    fld = field;
                    playGame(scene);

                }
            }
        });

        field2.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!isFieldPicked){
                    Field2 field = new Field2(helicopter);
                    field.getTransforms().addAll(new Translate(WINDOW_WIDTH/2, WINDOW_HEIGHT/2));
                    newRoot.getChildren().addAll(field);
                    scene.setRoot(newRoot);
                    helipad1 = field.getHelipad1();
                    helipad2 = field.getHelipad2();
                    packages = field.getPackages();
                    packagesToCollect = packages.length;
                    obstacles = field.getObstacles();
                    helicopter.setHelipad(helipad2);
                    waters = field.getWaters();
                    fld = field;
                    playGame(scene);
                }
            }
        });

        field3.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!isFieldPicked){
                    Field3 field = new Field3(helicopter);
                    field.getTransforms().addAll(new Translate(WINDOW_WIDTH/2, WINDOW_HEIGHT/2));
                    newRoot.getChildren().addAll(field);
                    scene.setRoot(newRoot);
                    helipad1 = field.getHelipad1();
                    helipad2 = field.getHelipad2();
                    packages = field.getPackages();
                    packagesToCollect = packages.length;
                    obstacles = field.getObstacles();
                    helicopter.setHelipad(helipad2);
                    waters = field.getWaters();
                    fld = field;
                    playGame(scene);
                }
            }
        });

        field4.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!isFieldPicked){
                    Field4 field = new Field4(helicopter);
                    field.getTransforms().addAll(new Translate(WINDOW_WIDTH/2, WINDOW_HEIGHT/2));
                    newRoot.getChildren().addAll(field);
                    scene.setRoot(newRoot);
                    helipad1 = field.getHelipad1();
                    helipad2 = field.getHelipad2();
                    packages = field.getPackages();
                    packagesToCollect = packages.length;
                    obstacles = field.getObstacles();
                    helicopter.setHelipad(helipad2);
                    waters = field.getWaters();
                    fld = field;
                    playGame(scene);
                }
            }
        });

    }

    public void playGame(Scene scene){
        Group root = (Group) scene.getRoot();
        RootController rc = new RootController(fld, helicopter);

        this.helicopter.getTransforms().addAll(new Translate(WINDOW_WIDTH/2, WINDOW_HEIGHT/2));
        SpeedMeter speedMeter = new SpeedMeter(helicopter.getMaxSpeed(), 7.5, 675.0);
        speedMeter.getTransforms().addAll(new Translate(365.625, 0.0), new Translate(WINDOW_WIDTH/2, WINDOW_HEIGHT/2));

        FuelMeter fuelMeter = new FuelMeter(75.0, helicopter);
        fuelMeter.getTransforms().addAll(new Translate(-332.4375, -332.4375), new Translate(WINDOW_WIDTH/2, WINDOW_HEIGHT/2));

        HeightMeter heightMeter = new HeightMeter();
        heightMeter.getTransforms().addAll( new Translate((WINDOW_WIDTH/2)*0.01, (WINDOW_HEIGHT)*0.9),
                new Rotate(180));


        Label label = new Label("00:00");
        label.setStyle("-fx-text-fill: red; -fx-font-size: 20px;");

        root.getChildren().addAll(label);
        label.getTransforms().addAll(new Translate(-WINDOW_WIDTH/2.1, WINDOW_HEIGHT/2.19), new Translate(WINDOW_WIDTH/2, WINDOW_HEIGHT/2));
        Timer clock = new Timer(label);
        clock.start();
        root.getChildren().addAll(speedMeter, fuelMeter, heightMeter);

        scene.addEventHandler(KeyEvent.KEY_PRESSED, (event) -> {
            if(isGameFinished) return;
            if (event.getCode().equals(KeyCode.UP)) {

                helicopter.changeSpeed(5.0);

            } else if (event.getCode().equals(KeyCode.DOWN)) {

                helicopter.changeSpeed(-5.0);

            } else if (event.getCode().equals(KeyCode.LEFT)) {
                if (helicopterMove) {
                    helicopter.rotate(-5.0, 0.0, 750.0, 0.0, 750.0, obstacles);
                } else {
                    rc.rotate(-5.0, 0.0, 750.0, 0.0, 750.0, obstacles);
                }

            } else if (event.getCode().equals(KeyCode.RIGHT)) {
                if(helicopterMove) {
                    helicopter.rotate(5.0, 0.0, 750.0, 0.0, 750.0, obstacles);
                } else {
                    rc.rotate(5.0, 0.0, 750.0, 0.0, 750.0, obstacles);
                }
            } else if (event.getCode().equals(KeyCode.SPACE)) {
                if(!helicopter.isParkingRunning()){
                    for(int i = 0; i < waters.length; i++){
                        if(waters[i].isHelicopterAboveWater(helicopter)){
                            return;
                        }
                    }
                    helicopter.setParkingRunning(true);
                    heightMeter.updateHeight();
                    helicopter.setParked();

                }
            } else if (event.getCode().equals(KeyCode.DIGIT1)){
                rc.reset();
                helicopterMove = true;
                helicopter.setHelicopterMove(true);
            } else if (event.getCode().equals(KeyCode.DIGIT2)){
                rc.prepare();
                helicopterMove = false;
                helicopter.setHelicopterMove(false);
            }

        });
        MyTimer.IUpdatable helicopterWrapper = (ds) -> {
            if(isGameFinished) return;
            if(fuelMeter.getFuel() == 0){
                isGameFinished = true;
                clock.stopTimer();
                Label msgLost = lostMessage();
                msgLost.getTransforms().addAll(
                        new Translate(WINDOW_WIDTH/4.7, WINDOW_HEIGHT/3)
                );
                root.getChildren().addAll(msgLost);

            }
            helicopter.update(ds,  0.995, 0.0, 750.0, 0.0, 750.0, obstacles);
            if(!helicopterMove) rc.update(ds,  0.995, 0.0, 750.0, 0.0, 750.0, obstacles);

            for(int i = 0; i < packages.length; ++i) {
                if (packages[i] != null && packages[i].handleCollision(helicopter.getBoundsInParent())) {
                    for (Node node : root.getChildren()) {
                        if (node instanceof Field) {
                            ((Group) node).getChildren().remove(packages[i]);
                            break;
                        }
                    }
                    packagesToCollect--;
                    packages[i] = null;
                    if(packagesToCollect==0){
                        isGameFinished=true;
                        clock.stopTimer();
                        Label msg = successMessage();
                        msg.getTransforms().addAll(
                                new Translate(WINDOW_WIDTH/3.8, WINDOW_HEIGHT/3)
                        );
                        root.getChildren().addAll(msg);
                    }
                }
            }

            for(int i = 0; i < waters.length; i++){
                if(waters[i].isHelicopterAboveWater(helicopter) && helicopter.getSpeed() < 0.7){
                    isGameFinished = true;
                    clock.stopTimer();
                    helicopter.sinkHelicopter();
                    Label msgLost = lostMessage();
                    msgLost.getTransforms().addAll(
                            new Translate(WINDOW_WIDTH/4.7, WINDOW_HEIGHT/3)
                    );
                    root.getChildren().addAll(msgLost);

                }
            }


            speedMeter.changeSpeed(helicopter.getSpeed());
            fuelMeter.updateFuel(helicopter.getSpeed());
        };
        MyTimer myTimer = new MyTimer(helicopterWrapper);
        myTimer.start();
    }

    public Label successMessage(){
        Label label = new Label("YOU WON!");
        label.setFont(Font.font("Times New Roman", FontWeight.BOLD,70));
        label.setTextFill(Color.RED);
        return label;
    }

    public  Label lostMessage(){
        Label label = new Label("GAME OVER");
        label.setFont(Font.font("Times New Roman", FontWeight.BOLD,70));
        label.setTextFill(Color.RED);
        return label;
    }



    public static void main(String[] args) {
        launch(new String[0]);
    }
}
