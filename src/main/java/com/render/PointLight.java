package main.java.com.render;

import main.java.com.math.Vector3;

public class PointLight {
    private Vector3 position;
    private Vector3 color;
    private double power;

    public PointLight(Vector3 position, Vector3 color, double power) {
        this.position = position;
        this.color = color;
        this.power = power;
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public Vector3 getColor() {
        return color;
    }

    public void setColor(Vector3 color) {
        this.color = color;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }
}
