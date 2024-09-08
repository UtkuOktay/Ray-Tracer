package main.java.com.samples;

import main.java.com.geometry.Object3D;
import main.java.com.geometry.Triangle;
import main.java.com.math.Vector3;
import main.java.com.render.Camera;
import main.java.com.render.PointLight;
import main.java.com.render.Scene;
import main.java.com.shading.DielectricMaterial;
import main.java.com.shading.Material;
import main.java.com.utils.OBJParser;

public class Dragon {
    private static final int imageWidth = 1920;
    private static final int imageHeight = 1080;

    public static Scene getScene() {
        Camera camera = new Camera(new Vector3(-1.75, 1.5, 3.5), new Vector3(0.25, 0, -1), new Vector3(0, 1, 0), imageWidth, imageHeight, 2, 4);
        PointLight light = new PointLight(new Vector3(0, 5, 5), new Vector3(1, 1, 1), 30);

        Material dragonMaterial = new DielectricMaterial(new Vector3(0.1, 0.35, 0.8), new Vector3(0.8, 0.8, 0.8), 100, 1.5, 0);

        Object3D dragonObject = OBJParser.parseOBJFile("res\\models\\dragon.obj", dragonMaterial);

        Vector3 pointA = new Vector3(-10, 0, -10);
        Vector3 pointB = new Vector3(-10, 0, 10);
        Vector3 pointC = new Vector3(10, 0, 10);
        Vector3 pointD = new Vector3(10, 0, -10);
        Vector3 pointE = new Vector3(-10, 10, -10);
        Vector3 pointF = new Vector3(10, 10, -10);
        Vector3 pointG = new Vector3(10, 0, 10);
        Vector3 pointH = new Vector3(10, 10, 10);

        Material floorMaterial = new DielectricMaterial(new Vector3(0.9, 0.85, 0.75), new Vector3(0.1, 0.1, 0.1), 1, 1, 0);
        Material wallMaterial = new DielectricMaterial(new Vector3(0.95, 0.8, 0.5), new Vector3(0.1, 0.1, 0.1), 1, 1, 0);

        Triangle triangle1 = new Triangle(pointA, pointB, pointC, floorMaterial);
        Triangle triangle2 = new Triangle(pointC, pointD, pointA, floorMaterial);

        Triangle triangle3 = new Triangle(pointA, pointD, pointE, wallMaterial);
        Triangle triangle4 = new Triangle(pointE, pointD, pointF, wallMaterial);

        Triangle triangle5 = new Triangle(pointD, pointG, pointF, wallMaterial);
        Triangle triangle6 = new Triangle(pointG, pointH, pointF, wallMaterial);

        Object3D floor = new Object3D();
        Object3D walls = new Object3D();
        floor.addSurface(triangle1);
        floor.addSurface(triangle2);
        walls.addSurface(triangle3);
        walls.addSurface(triangle4);
        walls.addSurface(triangle5);
        walls.addSurface(triangle6);

        Scene scene = new Scene(camera, new Vector3(0.025, 0.025, 0.025), new Vector3(0.2, 0.4, 0.6));
        scene.addLight(light);
        scene.addObject3D(dragonObject);
        scene.addObject3D(floor);
        scene.addObject3D(walls);
        return scene;
    }
}
