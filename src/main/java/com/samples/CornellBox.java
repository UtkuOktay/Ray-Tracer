package main.java.com.samples;

import main.java.com.geometry.Object3D;
import main.java.com.geometry.Sphere;
import main.java.com.geometry.Triangle;
import main.java.com.math.Vector3;
import main.java.com.render.Camera;
import main.java.com.render.PointLight;
import main.java.com.render.Scene;
import main.java.com.shading.DielectricMaterial;
import main.java.com.shading.Material;

public class CornellBox {
    private static final int imageWidth = 1920;
    private static final int imageHeight = 1920;

    public static Scene getScene() {
        Camera camera = new Camera(new Vector3(0, 0, 2.5), new Vector3(0, 0, -1), new Vector3(0, 1, 0), imageWidth, imageHeight, 2, 4);
        PointLight light = new PointLight(new Vector3(0, 2.95, 0), new Vector3(1, 1, 1), 10);

        //Cornell Box
        Material floorMaterial = new DielectricMaterial(new Vector3(0.8, 0.8, 0.8), new Vector3(0.1, 0.1, 0.1), 10, 1, 0);
        Material leftWallMaterial = new DielectricMaterial(new Vector3(0.9, 0.1, 0.1), new Vector3(0.1, 0.1, 0.1), 10, 1, 0);
        Material rightWallMaterial = new DielectricMaterial(new Vector3(0.1, 0.9, 0.1), new Vector3(0.1, 0.1, 0.1), 10, 1, 0);

        Vector3 pointA = new Vector3(-3, -3, 0);
        Vector3 pointB = new Vector3(3, -3, 0);
        Vector3 pointC = new Vector3(3, -3, -3);
        Vector3 pointD = new Vector3(-3, -3, -3);
        Vector3 pointE = new Vector3(-3, 3, 0);
        Vector3 pointF = new Vector3(-3, 3, -3);
        Vector3 pointG = new Vector3(3, 3, -3);
        Vector3 pointH = new Vector3(3, 3, 0);

        //Floor
        Triangle triangle1 = new Triangle(pointA, pointB, pointC, floorMaterial);
        Triangle triangle2 = new Triangle(pointC, pointD, pointA, floorMaterial);

        //Left Wall
        Triangle triangle3 = new Triangle(pointA, pointD, pointE, leftWallMaterial);
        Triangle triangle4 = new Triangle(pointD, pointF, pointE, leftWallMaterial);

        //Opposite Wall
        Triangle triangle5 = new Triangle(pointD, pointC, pointG, floorMaterial);
        Triangle triangle6 = new Triangle(pointG, pointF, pointD, floorMaterial);

        //Right Wall
        Triangle triangle7 = new Triangle(pointC, pointB, pointH, rightWallMaterial);
        Triangle triangle8 = new Triangle(pointH, pointG, pointC, rightWallMaterial);

        //Ceiling
        Triangle triangle9 = new Triangle(pointE, pointF, pointG, floorMaterial);
        Triangle triangle10 = new Triangle(pointE, pointG, pointH, floorMaterial);

        Object3D cornellBox = new Object3D();

        cornellBox.addSurface(triangle1);
        cornellBox.addSurface(triangle2);
        cornellBox.addSurface(triangle3);
        cornellBox.addSurface(triangle4);
        cornellBox.addSurface(triangle5);
        cornellBox.addSurface(triangle6);
        cornellBox.addSurface(triangle7);
        cornellBox.addSurface(triangle8);
        cornellBox.addSurface(triangle9);
        cornellBox.addSurface(triangle10);


        //Spheres
        Material sphereMaterial1 = new DielectricMaterial(new Vector3(1, 0, 0), new Vector3(0.2, 0.2, 0.2), 100, 1, 0);
        Material sphereMaterial2 = new DielectricMaterial(new Vector3(0, 0.8, 0.8), new Vector3(0.2, 0.2, 0.2), 100, 5, 0);

        Sphere sphere1 = new Sphere(new Vector3(-1.5, -2, -2), 1, sphereMaterial1);
        Sphere sphere2 = new Sphere(new Vector3(1.5, -2, -2), 1, sphereMaterial2);

        Scene scene = new Scene(camera, new Vector3(0.025, 0.025, 0.025), new Vector3(0.2, 0.4, 0.6));
        scene.addLight(light);
        scene.addObject3D(new Object3D(sphere1));
        scene.addObject3D(new Object3D(sphere2));

        scene.addObject3D(cornellBox);

        return scene;
    }
}
