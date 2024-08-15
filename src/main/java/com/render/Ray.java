package main.java.com.render;

import main.java.com.math.Vector3;

public class Ray {
    private Vector3 origin;
    private Vector3 direction;

    public Ray(Vector3 origin, Vector3 direction) {
        this.origin = origin;
        this.direction = direction.normalized();
    }

    public Vector3 getOrigin() {
        return origin;
    }

    public void setOrigin(Vector3 origin) {
        this.origin = origin;
    }

    public Vector3 getDirection() {
        return direction;
    }

    public void setDirection(Vector3 direction) {
        this.direction = direction.normalized();
    }

    public Vector3 pointAt(double t) {
        return Vector3.add(getOrigin(), Vector3.multiply(getDirection(), t));
    }
}
