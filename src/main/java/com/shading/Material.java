package main.java.com.shading;

import main.java.com.math.Vector3;
import main.java.com.render.Ray;
import main.java.com.texture.Texture;

public abstract class Material {
    private Texture diffuseTexture;
    private Vector3 specularColor;
    private double specularHardness;

    public Material() {

    }

    public Material(Texture diffuseTexture, Vector3 specularColor, double specularHardness) {
        this.diffuseTexture = diffuseTexture;
        this.specularColor = specularColor;
        this.specularHardness = specularHardness;
    }

    public Texture getDiffuseTexture() {
        return diffuseTexture;
    }

    public void setDiffuseTexture(Texture diffuseTexture) {
        this.diffuseTexture = diffuseTexture;
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
