package main.java.com.geometry;

import main.java.com.math.Vector3;
import main.java.com.render.IntersectionInfo;
import main.java.com.render.Ray;
import main.java.com.shading.Material;

public class Triangle implements Surface {
    private final double eps = 1e-6;

    private Vector3 pointA, pointB, pointC;
    private Vector3 normal;
    private Material material;

    public Triangle(Vector3 pointA, Vector3 pointB, Vector3 pointC, Material material) {
        this.pointA = pointA;
        this.pointB = pointB;
        this.pointC = pointC;
        this.material = material;
        updateNormalVector();
    }

    private void updateNormalVector() {
        Vector3 ab = Vector3.subtract(getPointB(), getPointA());
        Vector3 ac = Vector3.subtract(getPointC(), getPointA());
        normal = Vector3.cross(ab, ac).normalized();
    }

    public Vector3 getPointA() {
        return pointA;
    }

    public void setPointA(Vector3 pointA) {
        this.pointA = pointA;
        updateNormalVector();
    }

    public Vector3 getPointB() {
        return pointB;
    }

    public void setPointB(Vector3 pointB) {
        this.pointB = pointB;
        updateNormalVector();
    }

    public Vector3 getPointC() {
        return pointC;
    }

    public void setPointC(Vector3 pointC) {
        this.pointC = pointC;
        updateNormalVector();
    }

    @Override
    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public double getArea() {
        Vector3 ab = Vector3.subtract(getPointB(), getPointA());
        Vector3 ac = Vector3.subtract(getPointC(), getPointA());
        return Vector3.cross(ab, ac).magnitude() / 2;
    }

    public Vector3 getNormal() {
        return normal;
    }

    public IntersectionInfo hit(Ray ray) {
        Vector3 n = getNormal();
        double denum = Vector3.dot(ray.getDirection(), n);
        if (Math.abs(denum) < eps)
            return null; //Avoid division by zero

        double num = Vector3.dot(Vector3.subtract(getPointA(), ray.getOrigin()), n);
        double t = num / denum;
        Vector3 hitPosition = ray.pointAt(t);
        if (!isInside(hitPosition))
            return null;

        return new IntersectionInfo(t, ray, hitPosition, n, getMaterial());
    }

    private static double getAreaOfTriangle(Vector3 a, Vector3 b, Vector3 c) {
        Vector3 ab = Vector3.subtract(b, a);
        Vector3 ac = Vector3.subtract(c, a);
        return Vector3.cross(ab, ac).magnitude() / 2;
    }

    private boolean isInside(Vector3 point) {
        double area = getArea();
        if (area < eps)
            return false;

        double alpha = getAreaOfTriangle(point, getPointB(), getPointC());
        double beta = getAreaOfTriangle(getPointA(), point, getPointC());
        double gamma = getAreaOfTriangle(getPointA(), getPointB(), point);

        return alpha > 0 && beta > 0 && gamma > 0 && alpha + beta + gamma < area + eps;
    }
}
