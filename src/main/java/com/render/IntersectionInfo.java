package main.java.com.render;

import main.java.com.math.Vector3;
import main.java.com.shading.Material;

public class IntersectionInfo {
    private double t;
    private Ray ray;
    private Vector3 position;
    private Vector3 normal;
    private Material material;
    private double u, v;
    private boolean isInsideObject = false;

    public IntersectionInfo(double t, Ray ray, Vector3 position, Vector3 normal, Material material, double u, double v) {
        this.t = t;
        this.ray = ray;
        this.position = position;
        this.normal = normal;
        this.material = material;
        this.u = u;
        this.v = v;
        updateIfInsideObject();
    }

    public double getT() {
        return t;
    }

    public Ray getRay() {
        return ray;
    }

    public Vector3 getPosition() {
        return position;
    }

    public Vector3 getNormal() {
        return normal;
    }

    public Material getMaterial() {
        return material;
    }

    public double getU() {
        return u;
    }

    public void setU(double u) {
        this.u = u;
    }

    public double getV() {
        return v;
    }

    public void setV(double v) {
        this.v = v;
    }

    public boolean isInsideObject() {
        return isInsideObject;
    }

    private void updateIfInsideObject() {
        if (Vector3.dot(ray.getDirection(), normal) <= 0) // Ray is outside the object
            return;

        // Ray is inside the object
        normal = Vector3.multiply(normal, -1);
        isInsideObject = true;
    }
}
