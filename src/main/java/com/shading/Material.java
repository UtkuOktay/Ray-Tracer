package main.java.com.shading;

import main.java.com.math.Vector3;
import main.java.com.render.Ray;

public abstract class Material {
    private Vector3 diffuseColor;
    private Vector3 specularColor;
    private double specularHardness;

    public Material(Vector3 diffuseColor, Vector3 specularColor, double specularHardness) {
        this.diffuseColor = diffuseColor;
        this.specularColor = specularColor;
        this.specularHardness = specularHardness;
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

    public abstract double getReflectivity(double airIor, Ray ray, Vector3 surfaceNormal);
    public abstract double getTransmission();
    public abstract double getIor();
}
