package main.java.com.samples;

import main.java.com.geometry.Object3D;
import main.java.com.geometry.Triangle;
import main.java.com.math.Vector3;
import main.java.com.render.Camera;
import main.java.com.render.PointLight;
import main.java.com.render.Scene;
import main.java.com.shading.Material;
import main.java.com.utils.OBJParser;

public class Bunny {
    private static final int imageWidth = 1280;
    private static final int imageHeight = 720;;

    public static Scene getScene() {
        Camera camera = new Camera(new Vector3(0, 4, 5), new Vector3(0, -0.5, -1), new Vector3(0, 1, 0), imageWidth, imageHeight, 2, 4);
        PointLight light = new PointLight(new Vector3(0, 5, 5), new Vector3(1, 1, 1), 10);

        Material bunnyMaterial = new Material(new Vector3(0.9, 0.9, 0.9), new Vector3(0.2, 0.2, 0.2), 10, 1, 0);

        Object3D bunnyObject = OBJParser.parseOBJFile("res\\models\\bunny.obj", bunnyMaterial);

        Vector3 pointA = new Vector3(-5, 0, -5);
        Vector3 pointB = new Vector3(-5, 0, 5);
        Vector3 pointC = new Vector3(5, 0, 5);
        Vector3 pointD = new Vector3(5, 0, -5);

        Material floorMaterial = new Material(new Vector3(0.2, 0.2, 0.25), new Vector3(0.2, 0.2, 0.2), 10, 2, 0);

        Triangle triangle1 = new Triangle(pointA, pointB, pointC, floorMaterial);
        Triangle triangle2 = new Triangle(pointC, pointD, pointA, floorMaterial);

        Object3D floor = new Object3D();
        floor.addSurface(triangle1);
        floor.addSurface(triangle2);

        Scene scene = new Scene(camera, new Vector3(0.025, 0.025, 0.025), new Vector3(0.2, 0.4, 0.6));
        scene.addLight(light);
        scene.addObject3D(bunnyObject);
        scene.addObject3D(floor);
        return scene;
    }

}
