package com.example.demo;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

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

    public HelloApplication() {
    }

    public void start(Stage stage) {
        Group root = new Group();
        Helicopter helicopter = new Helicopter(16.875, 39.37500000000001);

        Helipad helipad = new Helipad(75.0, 75.0);
        helipad.getTransforms().addAll(new Transform[]{new Translate(-37.5, -37.5)});

        Translate package0position = new Translate(242.5, -257.5);
        Translate package1position = new Translate(-257.5, -257.5);
        Translate package2position = new Translate(242.5, 257.5);
        Translate package3position = new Translate(-257.5, 257.5);
        Package[] packages = new Package[]{new Package(15.0, 15.0, package0position), new Package(15.0, 15.0, package1position), new Package(15.0, 15.0, package2position), new Package(15.0, 15.0, package3position)};

        SpeedMeter speedMeter = new SpeedMeter(helicopter.getMaxSpeed(), 7.5, 675.0);
        speedMeter.getTransforms().addAll(new Translate(365.625, 0.0));

        FuelMeter fuelMeter = new FuelMeter(75.0, helicopter);
        fuelMeter.getTransforms().addAll(new Translate(-332.4375, -332.4375));

        root.getChildren().addAll(helipad, speedMeter, fuelMeter, helicopter);
        root.getChildren().addAll(packages);
        root.getTransforms().addAll(new Translate(375.0, 375.0));

        Scene scene = new Scene(root, 750.0, 750.0);
        Boolean pakred = true;

        scene.addEventHandler(KeyEvent.KEY_PRESSED, (event) -> {
            if (event.getCode().equals(KeyCode.UP)) {
                helicopter.changeSpeed(5.0);
            } else if (event.getCode().equals(KeyCode.DOWN)) {
                helicopter.changeSpeed(-5.0);
            } else if (event.getCode().equals(KeyCode.LEFT)) {
                helicopter.rotate(-5.0, 0.0, 750.0, 0.0, 750.0);

            } else if (event.getCode().equals(KeyCode.RIGHT)) {
                helicopter.rotate(5.0, 0.0, 750.0, 0.0, 750.0);
            } else if (event.getCode().equals(KeyCode.SPACE)) {
                helicopter.setParked();
            }

        });
        MyTimer.IUpdatable helicopterWrapper = (ds) -> {
            helicopter.update(ds,  0.995, 0.0, 750.0, 0.0, 750.0);


            for(int i = 0; i < packages.length; ++i) {
                if (packages[i] != null && packages[i].handleCollision(helicopter.getBoundsInParent())) {
                    root.getChildren().remove(packages[i]);
                    packages[i] = null;
                }
            }

            speedMeter.changeSpeed(helicopter.getSpeed());
            fuelMeter.takeFuel(helicopter.getSpeed());
        };
        MyTimer myTimer = new MyTimer(helicopterWrapper);
        myTimer.start();
        Image grass = new Image("C:\\Users\\core I7\\IdeaProjects\\HelikopterSkelet\\src\\main\\java\\com\\example\\helikopterskelet\\grass.jpg");
        ImagePattern background = new ImagePattern(grass, 0.0, 0.0, 1.0, 1.0, true);
        scene.setFill(background);
        stage.setTitle("Helicopter");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(new String[0]);
    }
}
