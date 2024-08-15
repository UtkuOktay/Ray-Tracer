package main.java.com.shading;

import main.java.com.math.Vector3;

public class Material {
    private Vector3 diffuseColor;
    private Vector3 specularColor;
    private double specularHardness;
    private double ior;
    private double transmission;

    public Material(Vector3 diffuseColor, Vector3 specularColor, double specularHardness, double ior, double transmission) {
        this.diffuseColor = diffuseColor;
        this.specularColor = specularColor;
        this.specularHardness = specularHardness;
        this.ior = ior;
        this.transmission = Math.max(Math.min(transmission, 1), 0);
    }

    public Vector3 getDiffuseColor() {
        return diffuseColor;
    }

    public void setDiffuseColor(Vector3 diffuseColor) {
        this.diffuseColor = diffuseColor;
    }

    public Vector3 getSpecularColor() {
        return specularColor;
    }

    public void setSpecularColor(Vector3 specularColor) {
        this.specularColor = specularColor;
    }

    public double getSpecularHardness() {
        return specularHardness;
    }

    public void setSpecularHardness(double specularHardness) {
        this.specularHardness = specularHardness;
    }

    public double getIor() {
        return ior;
    }

    public void setIor(double ior) {
        this.ior = ior;
    }

    public double getTransmission() {
        return transmission;
    }

    public void setTransmission(double transmission) {
        this.transmission = Math.max(Math.min(transmission, 1), 0);
    }
}
