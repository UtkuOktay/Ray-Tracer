package main.java.com.geometry;

import main.java.com.math.Vector3;
import main.java.com.render.IntersectionInfo;
import main.java.com.render.Ray;
import main.java.com.shading.Material;

public class Triangle implements Surface {
    private final double eps = 1e-6;

    private Vector3 pointA, pointB, pointC;
    private Vector3 vertexNormalA, vertexNormalB, vertexNormalC;
    private Vector3 standardNormal;
    private Material material;

    public Triangle(Vector3 pointA, Vector3 pointB, Vector3 pointC, Material material) {
        this.pointA = pointA;
        this.pointB = pointB;
        this.pointC = pointC;
        this.material = material;
        updateNormalVector();
        vertexNormalA = standardNormal;
        vertexNormalB = standardNormal;
        vertexNormalC = standardNormal;
    }

    public Triangle(Vector3 pointA, Vector3 pointB, Vector3 pointC, Vector3 vertexNormalA, Vector3 vertexNormalB, Vector3 vertexNormalC, Material material) {
        this.pointA = pointA;
        this.pointB = pointB;
        this.pointC = pointC;
        this.vertexNormalA = vertexNormalA;
        this.vertexNormalB = vertexNormalB;
        this.vertexNormalC = vertexNormalC;
        this.material = material;
        updateNormalVector();
    }

    private void updateNormalVector() {
        Vector3 ab = Vector3.subtract(getPointB(), getPointA());
        Vector3 ac = Vector3.subtract(getPointC(), getPointA());
        standardNormal = Vector3.cross(ab, ac).normalized();
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

    public Vector3 getVertexNormalA() {
        return vertexNormalA;
    }

    public void setVertexNormalA(Vector3 vertexNormalA) {
        this.vertexNormalA = vertexNormalA;
    }

    public Vector3 getVertexNormalB() {
        return vertexNormalB;
    }

    public void setVertexNormalB(Vector3 vertexNormalB) {
        this.vertexNormalB = vertexNormalB;
    }

    public Vector3 getVertexNormalC() {
        return vertexNormalC;
    }

    public void setVertexNormalC(Vector3 vertexNormalC) {
        this.vertexNormalC = vertexNormalC;
    }

    @Override
    public Material getMaterial() {
        return material;
    }

    @Override
    public void setMaterial(Material material) {
        this.material = material;
    }

    public double getArea() {
        Vector3 ab = Vector3.subtract(getPointB(), getPointA());
        Vector3 ac = Vector3.subtract(getPointC(), getPointA());
        return Vector3.cross(ab, ac).magnitude() / 2;
    }

    public Vector3 getNormal(Vector3 point) {
        if (getVertexNormalA() == null || getVertexNormalB() == null || getVertexNormalC() == null)
            return standardNormal;

        double[] barycentricCoordinates = getBarycentricCoordinates(point);

        double alpha = barycentricCoordinates[0];
        double beta = barycentricCoordinates[1];
        double gamma = barycentricCoordinates[2];

        Vector3 n1 = Vector3.multiply(getVertexNormalA(), alpha);
        Vector3 n2 = Vector3.multiply(getVertexNormalB(), beta);
        Vector3 n3 = Vector3.multiply(getVertexNormalC(), gamma);

        return Vector3.add(n1, Vector3.add(n2, n3)).normalized();
    }

    @Override
    public IntersectionInfo hit(Ray ray) {
        Vector3 n = standardNormal;
        double denum = Vector3.dot(ray.getDirection(), n);
        if (Math.abs(denum) < eps)
            return null; //Avoid division by zero

        double num = Vector3.dot(Vector3.subtract(getPointA(), ray.getOrigin()), n);
        double t = num / denum;
        Vector3 hitPosition = ray.pointAt(t);
        if (!isInside(hitPosition))
            return null;

        return new IntersectionInfo(t, ray, hitPosition, getNormal(hitPosition), getMaterial());
    }

    @Override
    public Vector3 getCenter() {
        return Vector3.divide(Vector3.add(getPointA(), Vector3.add(getPointB(), getPointC())), 3);
    }

    @Override
    public Vector3 getMinPoint() {
        return Vector3.componentWiseMin(getPointA(), Vector3.componentWiseMin(getPointB(), getPointC()));
    }

    @Override
    public Vector3 getMaxPoint() {
        return Vector3.componentWiseMax(getPointA(), Vector3.componentWiseMax(getPointB(), getPointC()));
    }

    private static double getAreaOfTriangle(Vector3 a, Vector3 b, Vector3 c) {
        Vector3 ab = Vector3.subtract(b, a);
        Vector3 ac = Vector3.subtract(c, a);
        return Vector3.cross(ab, ac).magnitude() / 2;
    }

    private double[] getBarycentricCoordinates(Vector3 point) {
        double inverseArea = 1 / getArea();

        double alpha = getAreaOfTriangle(point, getPointB(), getPointC()) * inverseArea;
        double beta = getAreaOfTriangle(getPointA(), point, getPointC()) * inverseArea;
        double gamma = getAreaOfTriangle(getPointA(), getPointB(), point) * inverseArea;

        return new double[] {alpha, beta, gamma};
    }

    private boolean isInside(Vector3 point) {
        double[] barycentricCoordinates = getBarycentricCoordinates(point);
        double alpha = barycentricCoordinates[0];
        double beta = barycentricCoordinates[1];
        double gamma = barycentricCoordinates[2];

        return alpha > 0 && beta > 0 && gamma > 0 && alpha + beta + gamma < 1 + eps;
    }
}
