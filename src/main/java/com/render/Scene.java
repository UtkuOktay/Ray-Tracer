package main.java.com.render;

import main.java.com.geometry.Object3D;
import main.java.com.geometry.Surface;
import main.java.com.math.Vector3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Scene {
    private Camera camera;
    private Vector3 ambientLight;
    private Vector3 backgroundColor;
    private final List<PointLight> lights;
    private final List<Object3D> objects3D;
    private final List<Surface> surfaces; // To accelerate the access when all surfaces are requested.

    public Scene(Camera camera, Vector3 ambientLight, Vector3 backgroundColor) {
        this.camera = camera;
        this.ambientLight = ambientLight;
        this.backgroundColor = backgroundColor;
        lights = new ArrayList<PointLight>();
        objects3D = new ArrayList<>();
        surfaces = new ArrayList<>();
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public Vector3 getAmbientLight() {
        return ambientLight;
    }

    public void setAmbientLight(Vector3 ambientLight) {
        this.ambientLight = ambientLight;
    }

    public Vector3 getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Vector3 backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public List<PointLight> getLights() {
        return Collections.unmodifiableList(lights);
    }

    public List<Object3D> getObjects3D() {
        return Collections.unmodifiableList(objects3D);
    }

    public List<Surface> getSurfaces() {
        return Collections.unmodifiableList(surfaces);
    }

    public void addLight(PointLight light) {
        lights.add(light);
    }

    public void removeLight(PointLight light) {
        lights.remove(light);
    }

    public void clearLights() {
        lights.clear();
    }

    public void addObject3D(Object3D object3D) {
        objects3D.add(object3D);
        for (Surface surface : object3D.getSurfaces()) {
            surfaces.add(surface);
        }
    }

    public void removeObject3D(Object3D object3D) {
        objects3D.remove(object3D);
        for (Surface surface : object3D.getSurfaces()) {
            surfaces.remove(surface);
        }
    }

    public void clearObjects3D() {
        objects3D.clear();
        surfaces.clear();
    }
}
