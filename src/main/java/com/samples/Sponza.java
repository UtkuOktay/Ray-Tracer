package main.java.com.samples;

import main.java.com.geometry.Object3D;
import main.java.com.geometry.Triangle;
import main.java.com.math.Vector3;
import main.java.com.render.Camera;
import main.java.com.render.PointLight;
import main.java.com.render.Scene;
import main.java.com.shading.DielectricMaterial;
import main.java.com.shading.Material;
import main.java.com.texture.SolidColorTexture;
import main.java.com.utils.OBJParser;

public class Sponza {
    private static final int imageWidth = 1920;
    private static final int imageHeight = 1080;

    public static Scene getScene() {
        Camera camera = new Camera(new Vector3(50, 2, -7), new Vector3(-1, 0.5, 0.5), new Vector3(0, 1, 0), imageWidth, imageHeight, 2, 4);
        PointLight light = new PointLight(new Vector3(30, 100, -15), new Vector3(1, 1, 1), 10000);

        Material defaultMaterial = new DielectricMaterial(new SolidColorTexture(new Vector3(0.8, 0.8, 0.8)), new Vector3(0, 0, 0), 1, 1, 0);

        Object3D sponzaObject = OBJParser.parseOBJFile("res\\models\\sponza\\sponza.obj", defaultMaterial);

        Scene scene = new Scene(camera, new Vector3(0.05, 0.05, 0.05), new Vector3(0.2, 0.4, 0.6));
        scene.addLight(light);
        scene.addObject3D(sponzaObject);
        return scene;
    }
}
