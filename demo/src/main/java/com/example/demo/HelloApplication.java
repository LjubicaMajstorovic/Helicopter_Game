package com.example.demo;

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

public class HelloApplication extends Application {
    private static int a = 1;
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

    private boolean isHelicopterPicked = false;
    private boolean isFieldPicked = false;

    private boolean parking;
     private Helicopter helicopter = null;
     private HeightMeter heightMeter = null;

     private Helipad helipad1 = null;
     private Helipad helipad2 = null;
     private Package[] packages = null;
     private Obstacle[] obstacles = null;

     private Group root;

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
        root.getTransforms().addAll(new Translate(375.0, 375.0));
        Scene scene = new Scene(root, 750.0, 750.0);
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

        /*Group root = new Group();

        Helipad helipad = new Helipad(75.0, 75.0);
        helipad.getTransforms().addAll(new Translate(-37.5, -37.5));

        Helicopter helicopter = new Helicopter(16.875, 39.37500000000001, helipad, Color.DARKORANGE, Color.BLUE, 300.00);

        Translate package0position = new Translate(242.5, -257.5);
        Translate package1position = new Translate(-257.5, -257.5);
        Translate package2position = new Translate(242.5, 257.5);
        Translate package3position = new Translate(-257.5, 257.5);
        Package[] packages = new Package[]{new Package(15.0, 15.0, package0position), new Package(15.0, 15.0, package1position), new Package(15.0, 15.0, package2position), new Package(15.0, 15.0, package3position)};

        Translate obstacle0position = new Translate(-OBSTACLE_WIDTH/2, -WINDOW_HEIGHT/4);
        Translate obstacle1position = new Translate(-WINDOW_WIDTH/4, -OBSTACLE_WIDTH/2);
        Translate obstacle2position = new Translate(-OBSTACLE_WIDTH/2, WINDOW_HEIGHT/4);
        Translate obstacle3position = new Translate(WINDOW_WIDTH/4, -OBSTACLE_WIDTH/2);

        Obstacle[] obstacles = new Obstacle[]{
                new Obstacle(OBSTACLE_WIDTH, OBSTACLE_HEIGHT, obstacle0position),
                new Obstacle(OBSTACLE_HEIGHT, OBSTACLE_WIDTH, obstacle1position),
                new Obstacle(OBSTACLE_WIDTH, OBSTACLE_HEIGHT, obstacle2position),
                new Obstacle(OBSTACLE_HEIGHT, OBSTACLE_WIDTH, obstacle3position)
        };


        SpeedMeter speedMeter = new SpeedMeter(helicopter.getMaxSpeed(), 7.5, 675.0);
        speedMeter.getTransforms().addAll(new Translate(365.625, 0.0));

        FuelMeter fuelMeter = new FuelMeter(75.0, helicopter);
        fuelMeter.getTransforms().addAll(new Translate(-332.4375, -332.4375));

        HeightMeter heightMeter = new HeightMeter();
        heightMeter.getTransforms().addAll(new Translate(-(WINDOW_WIDTH/2)*0.99, (WINDOW_HEIGHT/2)*0.8),
                new Rotate(180));


        Label label = new Label("00:00");
        label.setStyle("-fx-text-fill: red; -fx-font-size: 20px;");

        root.getChildren().addAll(label);
        label.getTransforms().addAll(new Translate(-WINDOW_WIDTH/2.1, WINDOW_HEIGHT/2.19));
        Timer clock = new Timer(label);
        clock.start();
        root.getChildren().addAll(helipad, speedMeter, fuelMeter, heightMeter, helicopter);
        root.getChildren().addAll(packages);
        root.getChildren().addAll(obstacles);
        root.getTransforms().addAll(new Translate(375.0, 375.0));

        Scene scene = new Scene(root, 750.0, 750.0);




        scene.addEventHandler(KeyEvent.KEY_PRESSED, (event) -> {
            if (event.getCode().equals(KeyCode.UP)) {
                helicopter.changeSpeed(5.0);
            } else if (event.getCode().equals(KeyCode.DOWN)) {
                helicopter.changeSpeed(-5.0);
            } else if (event.getCode().equals(KeyCode.LEFT)) {
                helicopter.rotate(-5.0, 0.0, 750.0, 0.0, 750.0, obstacles);

            } else if (event.getCode().equals(KeyCode.RIGHT)) {
                helicopter.rotate(5.0, 0.0, 750.0, 0.0, 750.0, obstacles);
            } else if (event.getCode().equals(KeyCode.SPACE)) {
                if(!helicopter.isParkingRunning()){
                    helicopter.setParkingRunning(true);
                    heightMeter.updateHeight();
                    helicopter.setParked();

                }
            }

        });
        MyTimer.IUpdatable helicopterWrapper = (ds) -> {
            helicopter.update(ds,  0.995, 0.0, 750.0, 0.0, 750.0, obstacles);


            for(int i = 0; i < packages.length; ++i) {
                if (packages[i] != null && packages[i].handleCollision(helicopter.getBoundsInParent())) {
                    root.getChildren().remove(packages[i]);
                    packages[i] = null;
                }
            }


            speedMeter.changeSpeed(helicopter.getSpeed());
            fuelMeter.updateFuel(helicopter.getSpeed());
        };
        MyTimer myTimer = new MyTimer(helicopterWrapper);
        myTimer.start();
        Image grass = new Image("C:\\Users\\core I7\\IdeaProjects\\HelikopterSkelet\\src\\main\\java\\com\\example\\helikopterskelet\\grass.jpg");
        ImagePattern background = new ImagePattern(grass, 0.0, 0.0, 1.0, 1.0, true);
        scene.setFill(background);
        stage.setTitle("Helicopter");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();*/
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
                    obstacles = field.getObstacles();
                    helicopter.setHelipad(helipad2);
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
                    obstacles = field.getObstacles();
                    helicopter.setHelipad(helipad2);
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
                    obstacles = field.getObstacles();
                    helicopter.setHelipad(helipad2);
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
                    obstacles = field.getObstacles();
                    helicopter.setHelipad(helipad2);
                    playGame(scene);
                }
            }
        });

    }

    public void playGame(Scene scene){
        Group root = (Group) scene.getRoot();
        this.helicopter.getTransforms().addAll(new Translate(WINDOW_WIDTH/2, WINDOW_HEIGHT/2));
        SpeedMeter speedMeter = new SpeedMeter(helicopter.getMaxSpeed(), 7.5, 675.0);
        speedMeter.getTransforms().addAll(new Translate(365.625, 0.0), new Translate(WINDOW_WIDTH/2, WINDOW_HEIGHT/2));

        FuelMeter fuelMeter = new FuelMeter(75.0, helicopter);
        fuelMeter.getTransforms().addAll(new Translate(-332.4375, -332.4375), new Translate(WINDOW_WIDTH/2, WINDOW_HEIGHT/2));

        HeightMeter heightMeter = new HeightMeter();
        heightMeter.getTransforms().addAll(new Translate(-(WINDOW_WIDTH/2)*0.99, (WINDOW_HEIGHT/2)*0.8),
                new Rotate(180), new Translate(WINDOW_WIDTH/2, WINDOW_HEIGHT/2));


        Label label = new Label("00:00");
        label.setStyle("-fx-text-fill: red; -fx-font-size: 20px;");

        root.getChildren().addAll(label);
        label.getTransforms().addAll(new Translate(-WINDOW_WIDTH/2.1, WINDOW_HEIGHT/2.19), new Translate(WINDOW_WIDTH/2, WINDOW_HEIGHT/2));
        Timer clock = new Timer(label);
        clock.start();
        root.getChildren().addAll(speedMeter, fuelMeter, heightMeter);

        scene.addEventHandler(KeyEvent.KEY_PRESSED, (event) -> {
            if (event.getCode().equals(KeyCode.UP)) {
                helicopter.changeSpeed(5.0);
            } else if (event.getCode().equals(KeyCode.DOWN)) {
                helicopter.changeSpeed(-5.0);
            } else if (event.getCode().equals(KeyCode.LEFT)) {
                helicopter.rotate(-5.0, 0.0, 750.0, 0.0, 750.0, obstacles);

            } else if (event.getCode().equals(KeyCode.RIGHT)) {
                helicopter.rotate(5.0, 0.0, 750.0, 0.0, 750.0, obstacles);
            } else if (event.getCode().equals(KeyCode.SPACE)) {
                if(!helicopter.isParkingRunning()){
                    helicopter.setParkingRunning(true);
                    heightMeter.updateHeight();
                    helicopter.setParked();

                }
            }

        });
        MyTimer.IUpdatable helicopterWrapper = (ds) -> {
            helicopter.update(ds,  0.995, 0.0, 750.0, 0.0, 750.0, obstacles);
            for(int i = 0; i < packages.length; ++i) {
                if (packages[i] != null && packages[i].handleCollision(helicopter.getBoundsInParent())) {
                    for (Node node : root.getChildren()) {
                        if (node instanceof Field) {
                            ((Group) node).getChildren().remove(packages[i]); // Call interface methods
                        }
                    }
                    packages[i] = null;
                }
            }


            speedMeter.changeSpeed(helicopter.getSpeed());
            fuelMeter.updateFuel(helicopter.getSpeed());
        };
        MyTimer myTimer = new MyTimer(helicopterWrapper);
        myTimer.start();
    }

    public static void main(String[] args) {
        launch(new String[0]);
    }
}
