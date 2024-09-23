package main.java.com.shading;

import main.java.com.math.Vector3;
import main.java.com.render.IntersectionInfo;
import main.java.com.render.PointLight;
import main.java.com.render.Ray;

import java.util.List;

public class BlinnPhongShading {
    public static Vector3 shade(IntersectionInfo intersectionInfo, List<PointLight> lights, Vector3 ambientLight) {
        Material material = intersectionInfo.getMaterial();
        Ray ray = intersectionInfo.getRay();

        double u = intersectionInfo.getU();
        double v = intersectionInfo.getV();

        // Ambient Lighting
        Vector3 color = Vector3.componentWiseMultiply(material.getDiffuseTexture().getColor(u, v), ambientLight);

        for (PointLight light : lights) {
            Vector3 lightVector = Vector3.subtract(light.getPosition(), intersectionInfo.getPosition());
            Vector3 lightColor = Vector3.divide(light.getColor(), (Math.pow(lightVector.magnitude(), 2) / light.getPower()));

            // Diffuse Component
            double diffuseDotProduct = Math.max(0, Vector3.dot(intersectionInfo.getNormal(), lightVector.normalized()));
            Vector3 kd = material.getDiffuseTexture().getColor(u, v);
            color = Vector3.add(color, Vector3.multiply(Vector3.componentWiseMultiply(kd, lightColor), diffuseDotProduct));

            // Specular Component
            Vector3 ks = material.getSpecularColor();
            double p = material.getSpecularHardness();
            Vector3 halfwayVector = Vector3.add(Vector3.multiply(ray.getDirection(), -1), lightVector).normalized();
            double specularDotProduct = Math.max(0, Vector3.dot(intersectionInfo.getNormal(), halfwayVector));
            color = Vector3.add(color, Vector3.multiply(Vector3.componentWiseMultiply(ks, lightColor), Math.pow(specularDotProduct, p)));
        }

        return color;
    }
}
