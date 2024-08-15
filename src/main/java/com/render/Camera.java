package main.java.com.render;

import main.java.com.math.Vector3;

import java.util.Random;

public class Camera {
    private Vector3 position;
    private Vector3 lookAt;
    private Vector3 up;

    private int resolutionX;
    private int resolutionY;
    private double focalLength;

    private double viewportWidth;
    private double viewportHeight;

    private final Random random = new Random();

    public Camera(Vector3 position, Vector3 lookAt, Vector3 up, int resolutionX, int resolutionY, double focalLength, double viewportWidth) {
        this.position = position;
        this.lookAt = lookAt.normalized();
        this.up = up.normalized();
        this.resolutionX = resolutionX;
        this.resolutionY = resolutionY;
        this.focalLength = focalLength;
        this.viewportWidth = viewportWidth;
        updateViewportHeight();
    }

    private void updateViewportHeight() {
        viewportHeight = viewportWidth / getAspectRatio();
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public Vector3 getLookAt() {
        return lookAt;
    }

    public void setLookAt(Vector3 lookAt) {
        this.lookAt = lookAt.normalized();
    }

    public Vector3 getUp() {
        return up;
    }

    public void setUp(Vector3 up) {
        this.up = up;
    }

    public int getResolutionX() {
        return resolutionX;
    }

    public void setResolutionX(int resolutionX) {
        this.resolutionX = resolutionX;
        updateViewportHeight();
    }

    public int getResolutionY() {
        return resolutionY;
    }

    public void setResolutionY(int resolutionY) {
        this.resolutionY = resolutionY;
        updateViewportHeight();
    }

    public double getFocalLength() {
        return focalLength;
    }

    public void setFocalLength(double focalLength) {
        this.focalLength = focalLength;
    }

    public double getViewportWidth() {
        return viewportWidth;
    }

    public void setViewportWidth(double viewportWidth) {
        this.viewportWidth = viewportWidth;
        updateViewportHeight();
    }

    public double getViewportHeight() {
        return viewportHeight;
    }

    public double getAspectRatio() {
        return (double) resolutionX / resolutionY;
    }

    private Vector3 getPixelPosition(double x, double y) {
        Vector3 directionVector = Vector3.subtract(getLookAt(), getPosition()).normalized();
        Vector3 rightVector = Vector3.cross(directionVector, getUp()).normalized();
        Vector3 realUpVector = Vector3.cross(rightVector, directionVector).normalized();

        Vector3 viewportCenter = Vector3.add(getPosition(), Vector3.multiply(getLookAt(), focalLength));

        Vector3 horizontalVector = Vector3.multiply(rightVector, (x / getResolutionX() - 0.5) * getViewportWidth());
        Vector3 verticalVector = Vector3.multiply(realUpVector, (y / getResolutionY() - 0.5) * getViewportHeight());

        Vector3 pixelPosition = Vector3.subtract(viewportCenter, verticalVector);
        pixelPosition = Vector3.add(pixelPosition, horizontalVector);

        return pixelPosition;
    }

    public Ray createRay(int x, int y) {
        double offset1 = random.nextDouble() - 0.5;
        double offset2 = random.nextDouble() - 0.5;
        Vector3 pixelPosition = getPixelPosition(x + offset1, y + offset2);

        Vector3 rayOrigin = getPosition();
        Vector3 rayDirection = Vector3.subtract(pixelPosition, getPosition());
        return new Ray(rayOrigin, rayDirection);
    }

}
