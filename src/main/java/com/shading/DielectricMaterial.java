package main.java.com.shading;

import main.java.com.math.Vector3;
import main.java.com.render.Ray;

public class DielectricMaterial extends Material{
    private double ior;
    private double transmission;

    public DielectricMaterial(Vector3 diffuseColor, Vector3 specularColor, double specularHardness, double ior, double transmission) {
        super(diffuseColor, specularColor, specularHardness);
        this.ior = ior;
        this.transmission = Math.max(Math.min(transmission, 1), 0);
    }

    @Override
    public double getReflectivity(double airIor, Ray ray, Vector3 surfaceNormal) {
        //Fresnel Effect / Schlick's Approximation
        double materialIor = getIor();

        double r0 = Math.pow((airIor - materialIor) / (airIor + materialIor), 2);
        double cosTheta = Vector3.dot(Vector3.multiply(ray.getDirection(), -1), surfaceNormal)
                / ray.getDirection().magnitude() * surfaceNormal.magnitude();

        return r0 + (1 - r0) * Math.pow((1 - cosTheta), 5);
    }

    @Override
    public double getTransmission() {
        return transmission;
    }

    @Override
    public double getIor() {
        return ior;
    }

    public void setIor(double ior) {
        this.ior = ior;
    }

    public void setTransmission(double transmission) {
        this.transmission = Math.max(Math.min(transmission, 1), 0);
    }
}
