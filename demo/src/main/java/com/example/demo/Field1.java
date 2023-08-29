package com.example.demo;

import javafx.scene.Group;
import javafx.scene.transform.Translate;

public class Field1 extends Group implements Field {
    private Helipad helipad1;
    private Helipad helipad2;
    private Obstacle[] obstacles;
    private Package[] packages;

    private static final double HELIPAD_WIDTH = 75.0;
    private static final double HELIPAD_HEIGHT = 75.0;
    private static final double PACKAGE_WIDTH = 15.0;
    private static final double PACKAGE_HEIGHT = 15.0;

    private static final double WINDOW_WIDTH = 750.0;
    private static final double WINDOW_HEIGHT = 750.0;

    private static final double OBSTACLE_WIDTH = 110;
    private static final double OBSTACLE_HEIGHT = 8;
    public Helipad getHelipad1() {
        return helipad1;
    }

    public Helipad getHelipad2() {
        return helipad2;
    }

    public Obstacle[] getObstacles() {
        return obstacles;
    }

    public Package[] getPackages() {
        return packages;
    }

    public Field1(){
        helipad1 = new Helipad(HELIPAD_WIDTH, HELIPAD_HEIGHT);
        helipad2 = new Helipad(HELIPAD_WIDTH, HELIPAD_HEIGHT);

        helipad1.getTransforms().addAll(new Translate(-HELIPAD_WIDTH/2, -HELIPAD_HEIGHT/2));
        helipad2.getTransforms().addAll(new Translate(WINDOW_WIDTH/3, -HELIPAD_HEIGHT/2));

        Translate package0position = new Translate(242.5, -257.5);
        Translate package1position = new Translate(-257.5, -257.5);
        Translate package2position = new Translate(242.5, 257.5);
        Translate package3position = new Translate(-257.5, 257.5);
        Translate package4position = new Translate(WINDOW_WIDTH/2.3, WINDOW_HEIGHT/2.3);
        Translate package5position = new Translate(WINDOW_WIDTH/2.3, -WINDOW_HEIGHT/2.3);
        Translate package6position = new Translate(-WINDOW_WIDTH/2.3, WINDOW_HEIGHT/2.3);
        Translate package7position = new Translate(-WINDOW_WIDTH/2/3, WINDOW_HEIGHT/2.3);
        packages = new Package[]{new Package(15.0, 15.0, package0position), new Package(15.0, 15.0, package1position), new Package(15.0, 15.0, package2position), new Package(15.0, 15.0, package3position),
                new Package(PACKAGE_WIDTH, PACKAGE_HEIGHT, package4position), new Package(PACKAGE_WIDTH, PACKAGE_HEIGHT, package5position), new Package(PACKAGE_WIDTH, PACKAGE_HEIGHT, package6position), new Package(PACKAGE_WIDTH, PACKAGE_HEIGHT, package7position)};

        Translate obstacle0position = new Translate(-OBSTACLE_WIDTH/2, -WINDOW_HEIGHT/4);
        Translate obstacle1position = new Translate(-WINDOW_WIDTH/4, -OBSTACLE_WIDTH/2);
        Translate obstacle2position = new Translate(-OBSTACLE_WIDTH/2, WINDOW_HEIGHT/4);
        Translate obstacle3position = new Translate(WINDOW_WIDTH/4, -OBSTACLE_WIDTH/2);


        obstacles = new Obstacle[]{
                new Obstacle(OBSTACLE_WIDTH, OBSTACLE_HEIGHT, obstacle0position),
                new Obstacle(OBSTACLE_HEIGHT, OBSTACLE_WIDTH, obstacle1position),
                new Obstacle(OBSTACLE_WIDTH, OBSTACLE_HEIGHT, obstacle2position),
                new Obstacle(OBSTACLE_HEIGHT, OBSTACLE_WIDTH, obstacle3position)
        };

        super.getChildren().addAll(helipad1, helipad2);
        super.getChildren().addAll(packages);
        super.getChildren().addAll(obstacles);


    }
}
