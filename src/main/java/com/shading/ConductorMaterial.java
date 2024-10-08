package main.java.com.shading;

import main.java.com.math.Vector3;
import main.java.com.render.Ray;
import main.java.com.texture.Texture;

public class ConductorMaterial extends Material{
    private double reflectivity;

    public ConductorMaterial() {
        super();
    }

    public ConductorMaterial(Texture diffuseTexture, Vector3 specularColor, double specularHardness, double reflectivity) {
        super(diffuseTexture, specularColor, specularHardness);
        this.reflectivity = Math.min(Math.max(reflectivity, 0), 1);
    }

    @Override
    public double getReflectivity(double airIor, Ray ray, Vector3 surfaceNormal) {
        return reflectivity;
    }

    @Override
    public double getTransmission() {
        //Fully opaque.
        return 0;
    }

    @Override
    public double getIor() {
        //Not 1 indeed, but not used anyway.
        return 1;
    }

    public void setReflectivity(double reflectivity) {
        this.reflectivity = Math.min(Math.max(reflectivity, 0), 1);
    }
}
