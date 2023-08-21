package com.example.demo;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
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

        Group field1 = field1();
        Group field2 = field2();
        Group field3 = field3();
        Group field4 = field4();

        field1.getTransforms().addAll(new Translate(WINDOW_WIDTH/4, WINDOW_HEIGHT/4), new Scale(0.25, 0.25));
        field2.getTransforms().addAll(new Translate(-WINDOW_WIDTH/4, WINDOW_HEIGHT/4), new Scale(0.25, 0.25));
        field3.getTransforms().addAll(new Translate(-WINDOW_WIDTH/4, -WINDOW_HEIGHT/4), new Scale(0.25, 0.25));
        field4.getTransforms().addAll(new Translate(WINDOW_WIDTH/4, -WINDOW_HEIGHT/4), new Scale(0.25, 0.25));

        root.getChildren().addAll(field1, field2, field3, field4, line1, line2);
        root.getTransforms().addAll(new Translate(375.0, 375.0));
        scene.setRoot(root);
    }

    private Group field1(){
        Group group = new Group();
        Helipad helipad1 = new Helipad(HELIPAD_WIDTH, HELIPAD_HEIGHT);
        Helipad helipad2 = new Helipad(HELIPAD_WIDTH, HELIPAD_HEIGHT);

        helipad1.getTransforms().addAll(new Translate(-HELIPAD_WIDTH/2, -HELIPAD_HEIGHT/2));
        helipad2.getTransforms().addAll(new Translate(WINDOW_WIDTH/2.5, -HELIPAD_HEIGHT/2));

        Translate package0position = new Translate(242.5, -257.5);
        Translate package1position = new Translate(-257.5, -257.5);
        Translate package2position = new Translate(242.5, 257.5);
        Translate package3position = new Translate(-257.5, 257.5);
        Translate package4position = new Translate(WINDOW_WIDTH/2.3, WINDOW_HEIGHT/2.3);
        Translate package5position = new Translate(WINDOW_WIDTH/2.3, -WINDOW_HEIGHT/2.3);
        Translate package6position = new Translate(-WINDOW_WIDTH/2.3, WINDOW_HEIGHT/2.3);
        Translate package7position = new Translate(-WINDOW_WIDTH/2/3, WINDOW_HEIGHT/2.3);
        Package[] packages = new Package[]{new Package(15.0, 15.0, package0position), new Package(15.0, 15.0, package1position), new Package(15.0, 15.0, package2position), new Package(15.0, 15.0, package3position),
        new Package(PACKAGE_WIDTH, PACKAGE_HEIGHT, package4position), new Package(PACKAGE_WIDTH, PACKAGE_HEIGHT, package5position), new Package(PACKAGE_WIDTH, PACKAGE_HEIGHT, package6position), new Package(PACKAGE_WIDTH, PACKAGE_HEIGHT, package7position)};

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

        group.getChildren().addAll(helipad1, helipad2);
        group.getChildren().addAll(packages);
        group.getChildren().addAll(obstacles);

        return group;
    }

    private Group field2(){
        Group group = new Group();
        Helipad helipad1 = new Helipad(HELIPAD_WIDTH, HELIPAD_HEIGHT);
        Helipad helipad2 = new Helipad(HELIPAD_WIDTH, HELIPAD_HEIGHT);

        helipad1.getTransforms().addAll(new Translate(-HELIPAD_WIDTH/2, -HELIPAD_HEIGHT/2));
        helipad2.getTransforms().addAll(new Translate(-HELIPAD_WIDTH/2.5, -WINDOW_HEIGHT/2));

        Translate package0position = new Translate(242.5, -257.5);
        Translate package1position = new Translate(-257.5, -257.5);
        Translate package2position = new Translate(242.5, 257.5);
        Translate package3position = new Translate(-257.5, 257.5);
        Translate package4position = new Translate(WINDOW_WIDTH/2.3, 0);
        Translate package5position = new Translate(WINDOW_WIDTH/2.3, -WINDOW_HEIGHT/2.3);
        Translate package6position = new Translate(-WINDOW_WIDTH/2.3, 0);
        Translate package7position = new Translate(0, WINDOW_HEIGHT/2.1);
        Package[] packages = new Package[]{new Package(15.0, 15.0, package0position), new Package(15.0, 15.0, package1position), new Package(15.0, 15.0, package2position), new Package(15.0, 15.0, package3position),
                new Package(PACKAGE_WIDTH, PACKAGE_HEIGHT, package4position), new Package(PACKAGE_WIDTH, PACKAGE_HEIGHT, package5position), new Package(PACKAGE_WIDTH, PACKAGE_HEIGHT, package6position), new Package(PACKAGE_WIDTH, PACKAGE_HEIGHT, package7position)};

        Translate obstacle0position = new Translate(-OBSTACLE_WIDTH/2, -WINDOW_HEIGHT/4);
        Translate obstacle1position = new Translate(-WINDOW_WIDTH/4, -WINDOW_WIDTH/2.5);
        Translate obstacle2position = new Translate(-OBSTACLE_WIDTH/2, WINDOW_HEIGHT/4);
        Translate obstacle3position = new Translate(WINDOW_WIDTH/4, -WINDOW_WIDTH/2.5);


        Obstacle[] obstacles = new Obstacle[]{
                new Obstacle(OBSTACLE_WIDTH, OBSTACLE_HEIGHT, obstacle0position),
                new Obstacle(OBSTACLE_HEIGHT, OBSTACLE_WIDTH, obstacle1position),
                new Obstacle(OBSTACLE_WIDTH, OBSTACLE_HEIGHT, obstacle2position),
                new Obstacle(OBSTACLE_HEIGHT, OBSTACLE_WIDTH, obstacle3position)
        };

        group.getChildren().addAll(helipad1, helipad2);
        group.getChildren().addAll(packages);
        group.getChildren().addAll(obstacles);

        return group;
    }


    private Group field3(){
        Group group = new Group();
        Helipad helipad1 = new Helipad(HELIPAD_WIDTH, HELIPAD_HEIGHT);
        Helipad helipad2 = new Helipad(HELIPAD_WIDTH, HELIPAD_HEIGHT);

        helipad1.getTransforms().addAll(new Translate(-HELIPAD_WIDTH/2, -HELIPAD_HEIGHT/2));
        helipad2.getTransforms().addAll(new Translate(-WINDOW_WIDTH/3, -WINDOW_HEIGHT/2));

        Translate package0position = new Translate(242.5, -257.5);
        Translate package1position = new Translate(-WINDOW_WIDTH/2, -257.5);
        Translate package2position = new Translate(242.5, 257.5);
        Translate package3position = new Translate(-257.5, 257.5);
        Translate package4position = new Translate(WINDOW_WIDTH/2.3, WINDOW_HEIGHT/2.3);
        Translate package5position = new Translate(WINDOW_WIDTH/2.3, 0);
        Translate package6position = new Translate(-WINDOW_WIDTH/2.3, WINDOW_HEIGHT/2.3);
        Translate package7position = new Translate(-WINDOW_WIDTH/2/3, WINDOW_HEIGHT/2.3);
        Package[] packages = new Package[]{new Package(15.0, 15.0, package0position), new Package(15.0, 15.0, package1position), new Package(15.0, 15.0, package2position), new Package(15.0, 15.0, package3position),
                new Package(PACKAGE_WIDTH, PACKAGE_HEIGHT, package4position), new Package(PACKAGE_WIDTH, PACKAGE_HEIGHT, package5position), new Package(PACKAGE_WIDTH, PACKAGE_HEIGHT, package6position), new Package(PACKAGE_WIDTH, PACKAGE_HEIGHT, package7position)};

        Translate obstacle0position = new Translate(-WINDOW_WIDTH/4, -WINDOW_HEIGHT/4);
        Translate obstacle1position = new Translate(-WINDOW_WIDTH/4, WINDOW_HEIGHT/4);
        Translate obstacle2position = new Translate(WINDOW_WIDTH/10, WINDOW_HEIGHT/4);
        Translate obstacle3position = new Translate(WINDOW_WIDTH/4, -OBSTACLE_WIDTH/1.7);


        Obstacle[] obstacles = new Obstacle[]{
                new Obstacle(OBSTACLE_WIDTH, OBSTACLE_HEIGHT, obstacle0position),
                new Obstacle(OBSTACLE_HEIGHT, OBSTACLE_WIDTH, obstacle1position),
                new Obstacle(OBSTACLE_WIDTH, OBSTACLE_HEIGHT, obstacle2position),
                new Obstacle(OBSTACLE_HEIGHT, OBSTACLE_WIDTH, obstacle3position)
        };
        obstacles[1].getTransforms().addAll(new Rotate(150));
        obstacles[3].getTransforms().addAll(new Rotate(150));

        group.getChildren().addAll(helipad1, helipad2);
        group.getChildren().addAll(packages);
        group.getChildren().addAll(obstacles);

        return group;
    }

    private Group field4(){
        Group group = new Group();
        Helipad helipad1 = new Helipad(HELIPAD_WIDTH, HELIPAD_HEIGHT);
        Helipad helipad2 = new Helipad(HELIPAD_WIDTH, HELIPAD_HEIGHT);

        helipad1.getTransforms().addAll(new Translate(-HELIPAD_WIDTH/2, -HELIPAD_HEIGHT/2));
        helipad2.getTransforms().addAll(new Translate(-HELIPAD_WIDTH/2.5, WINDOW_WIDTH/3));

        Translate package0position = new Translate(242.5, -257.5);
        Translate package1position = new Translate(-257.5, -257.5);
        Translate package2position = new Translate(242.5, 257.5);
        Translate package3position = new Translate(-257.5, 257.5);
        Translate package4position = new Translate(0, -WINDOW_HEIGHT/2.3);
        Translate package5position = new Translate(WINDOW_WIDTH/2.3, -WINDOW_HEIGHT/2.3);
        Translate package6position = new Translate(-WINDOW_WIDTH/2.3, WINDOW_HEIGHT/2.3);
        Translate package7position = new Translate(-WINDOW_WIDTH/2/3, WINDOW_HEIGHT/2.3);
        Package[] packages = new Package[]{new Package(15.0, 15.0, package0position), new Package(15.0, 15.0, package1position), new Package(15.0, 15.0, package2position), new Package(15.0, 15.0, package3position),
                new Package(PACKAGE_WIDTH, PACKAGE_HEIGHT, package4position), new Package(PACKAGE_WIDTH, PACKAGE_HEIGHT, package5position), new Package(PACKAGE_WIDTH, PACKAGE_HEIGHT, package6position), new Package(PACKAGE_WIDTH, PACKAGE_HEIGHT, package7position)};

        Translate obstacle0position = new Translate(-OBSTACLE_WIDTH/2, -WINDOW_HEIGHT/4);
        Translate obstacle1position = new Translate(-WINDOW_WIDTH/4, WINDOW_HEIGHT/4);
        Translate obstacle2position = new Translate(-OBSTACLE_WIDTH/2, WINDOW_HEIGHT/4);
        Translate obstacle3position = new Translate(WINDOW_WIDTH/4, -WINDOW_HEIGHT/4);


        Obstacle[] obstacles = new Obstacle[]{
                new Obstacle(OBSTACLE_WIDTH, OBSTACLE_HEIGHT, obstacle0position),
                new Obstacle(OBSTACLE_HEIGHT, OBSTACLE_WIDTH, obstacle1position),
                new Obstacle(OBSTACLE_WIDTH, OBSTACLE_HEIGHT, obstacle2position),
                new Obstacle(OBSTACLE_HEIGHT, OBSTACLE_WIDTH, obstacle3position)
        };

        group.getChildren().addAll(helipad1, helipad2);
        group.getChildren().addAll(packages);
        group.getChildren().addAll(obstacles);

        return group;
    }


    public static void main(String[] args) {
        launch(new String[0]);
    }
}
