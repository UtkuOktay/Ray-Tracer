package main.java.com.texture;

import main.java.com.math.Vector3;

public class SolidColorTexture implements Texture{
    private Vector3 color;

    public SolidColorTexture(Vector3 color) {
        this.color = color;
    }

    @Override
    public Vector3 getColor(double u, double v) {
        return color;
    }

    public void setColor(Vector3 color) {
        this.color = color;
    }
}
