package main.java.com.geometry;

import main.java.com.math.Vector3;
import main.java.com.render.IntersectionInfo;
import main.java.com.render.Ray;
import main.java.com.shading.Material;

public class Sphere implements Surface {
    private Vector3 center;
    private double radius;
    private Material material;

    public Sphere(Vector3 center, double radius, Material material) {
        this.center = center;
        this.radius = radius;
        this.material = material;
    }

    public Vector3 getCenter() {
        return center;
    }

    public void setCenter(Vector3 center) {
        this.center = center;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public IntersectionInfo hit(Ray ray) {
        Vector3 d = ray.getDirection();
        Vector3 ce = Vector3.subtract(ray.getOrigin(), getCenter());

        double a = Vector3.dot(d, d);
        double b = 2 * Vector3.dot(d, ce);
        double c = Vector3.dot(ce, ce) - Math.pow(getRadius(), 2);

        double discriminant = Math.pow(b, 2) - 4 * a * c;

        if (discriminant < 0)
            return null;

        double t1 = (-b + Math.sqrt(discriminant)) / (2 * a);
        double t2 = (-b - Math.sqrt(discriminant)) / (2 * a);

        double t = t2;

        //If the ray origin is inside the sphere, use t1.
        if (Vector3.subtract(getCenter(), ray.getOrigin()).magnitude() < getRadius())
            t = t1;

        Vector3 hitPosition = ray.pointAt(t);

        Vector3 normalVector = getNormal(hitPosition);

        return new IntersectionInfo(t, ray, hitPosition, normalVector, material);
    }

    @Override
    public Vector3 getMinPoint() {
        double radius = getRadius();
        return Vector3.subtract(getCenter(), new Vector3(radius, radius, radius));
    }

    @Override
    public Vector3 getMaxPoint() {
        double radius = getRadius();
        return Vector3.add(getCenter(), new Vector3(radius, radius, radius));
    }

    public Vector3 getNormal(Vector3 point) {
        return Vector3.subtract(point, getCenter()).normalized();
    }
}
