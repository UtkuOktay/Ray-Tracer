package main.java.com.math;

public class Vector3 {
    private double x, y, z;

    public Vector3() {
        x = y = z = 0;
    }

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(Vector3 v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double magnitude() {
        return Math.sqrt(Math.pow(getX(), 2) + Math.pow(getY(), 2) + Math.pow(getZ(), 2));
    }

    public Vector3 normalized() {
        return divide(this, magnitude());
    }

    public static Vector3 add(Vector3 v1, Vector3 v2) {
        return new Vector3(v1.getX() + v2.getX(), v1.getY() + v2.getY(), v1.getZ() + v2.getZ());
    }

    public static Vector3 subtract(Vector3 v1, Vector3 v2) {
        return new Vector3(v1.getX() - v2.getX(), v1.getY() - v2.getY(), v1.getZ() - v2.getZ());
    }

    public static Vector3 multiply(Vector3 v1, double scalar) {
        return new Vector3(v1.getX() * scalar, v1.getY() * scalar, v1.getZ() * scalar);
    }

    public static Vector3 componentWiseMultiply(Vector3 v1, Vector3 v2) {
        return new Vector3(v1.getX() * v2.getX(), v1.getY() * v2.getY(), v1.getZ() * v2.getZ());
    }

    public static Vector3 divide(Vector3 v1, double scalar) {
        return new Vector3(v1.getX() / scalar, v1.getY() / scalar, v1.getZ() / scalar);
    }

    public static double dot(Vector3 v1, Vector3 v2) {
        return v1.getX() * v2.getX() + v1.getY() * v2.getY() + v1.getZ() * v2.getZ();
    }

    public static Vector3 cross(Vector3 v1, Vector3 v2) {
        return new Vector3(
                v1.getY() * v2.getZ() - v1.getZ() * v2.getY(),
                v1.getZ() * v2.getX() - v1.getX() * v2.getZ(),
                v1.getX() * v2.getY() - v1.getY() * v2.getX());
    }

    public static double angleBetween(Vector3 v1, Vector3 v2) {
        return Math.acos(dot(v1, v2) / (v1.magnitude() * v2.magnitude()));
    }

    @Override
    public String toString() {
        return String.format("[%.4f, %.4f, %.4f]", getX(), getY(), getZ());
    }
}
