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

public class VariousMaterials {
    private static final int imageWidth = 1920;
    private static final int imageHeight = 1080;

    public static Scene getScene() {
        Camera camera = new Camera(new Vector3(0, 5, 12), new Vector3(0, -3, -10), new Vector3(0, 1, 0), imageWidth, imageHeight, 2, 4);
        PointLight light = new PointLight(new Vector3(2, 10, 7), new Vector3(1, 1, 1), 100);

        Material planeMaterial = new DielectricMaterial(new Vector3(0.1, 0.1, 0.15), new Vector3(0.2, 0.2, 0.2), 10, 3, 0);

        Vector3 pointA = new Vector3(-10, 0, -10);
        Vector3 pointB = new Vector3(-10, 0, 10);
        Vector3 pointC = new Vector3(10, 0, 10);
        Vector3 pointD = new Vector3(10, 0, -10);

        Triangle triangle1 = new Triangle(pointA, pointB, pointC, planeMaterial);
        Triangle triangle2 = new Triangle(pointC, pointD, pointA, planeMaterial);


        Material sphereMaterial1 = new DielectricMaterial(new Vector3(1, 0.25, 0), new Vector3(0.2, 0.2, 0.2), 10, 1, 0);
        Material sphereMaterial2 = new DielectricMaterial(new Vector3(0.4, 0.0, 0.75), new Vector3(0.8, 0.8, 0.8), 100, 1, 0);
        Material sphereMaterial3 = new DielectricMaterial(new Vector3(0.3, 0.1, 0), new Vector3(0.2, 0.2, 0.2), 100, 8, 0);
        Material sphereMaterial4 = new DielectricMaterial(new Vector3(0, 0.0, 0.0), new Vector3(0.2, 0.2, 0.2), 100, 1.3, 1);
        Material sphereMaterial5 = new DielectricMaterial(new Vector3(0.9, 0.1, 0.1), new Vector3(0.2, 0.2, 0.2), 10, 1, 0);
        Material sphereMaterial6 = new DielectricMaterial(new Vector3(0.1, 0.9, 0.1), new Vector3(0.8, 0.8, 0.8), 100, 1, 0);
        Material sphereMaterial7 = new DielectricMaterial(new Vector3(0.1, 0.1, 0.9), new Vector3(0.8, 0.8, 0.8), 100, 8, 0);

        Sphere sphere1 = new Sphere(new Vector3(-6, 2, -3), 2, sphereMaterial1);
        Sphere sphere2 = new Sphere(new Vector3(0, 2, -3), 2, sphereMaterial2);
        Sphere sphere3 = new Sphere(new Vector3(6, 2, -3), 2, sphereMaterial3);
        Sphere sphere4 = new Sphere(new Vector3(-4.5, 1, 3), 1, sphereMaterial4);
        Sphere sphere5 = new Sphere(new Vector3(-1.5, 1, 3), 1, sphereMaterial5);
        Sphere sphere6 = new Sphere(new Vector3(1.5, 1, 3), 1, sphereMaterial6);
        Sphere sphere7 = new Sphere(new Vector3(4.5, 1, 3), 1, sphereMaterial7);

        Scene scene = new Scene(camera, new Vector3(0.025, 0.025, 0.025), new Vector3(0.2, 0.4, 0.6));
        scene.addLight(light);
        scene.addObject3D(new Object3D(sphere1));
        scene.addObject3D(new Object3D(sphere2));
        scene.addObject3D(new Object3D(sphere3));
        scene.addObject3D(new Object3D(sphere4));
        scene.addObject3D(new Object3D(sphere5));
        scene.addObject3D(new Object3D(sphere6));
        scene.addObject3D(new Object3D(sphere7));

        scene.addObject3D(new Object3D(triangle1));
        scene.addObject3D(new Object3D(triangle2));

        return scene;
    }
}
