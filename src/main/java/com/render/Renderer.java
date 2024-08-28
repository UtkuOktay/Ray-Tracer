package main.java.com.render;

import main.java.com.bvh.BVH;
import main.java.com.geometry.Surface;
import main.java.com.math.Vector3;
import main.java.com.shading.BlinnPhongShading;
import main.java.com.shading.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Renderer {
    private final double eps = 1e-3;

    public Vector3[][] render(Scene scene, int pixelSamples) {
        BVH bvh = new BVH(scene.getSurfaces());
        long startTime = System.nanoTime();

        Camera camera = scene.getCamera();

        int imageWidth = camera.getResolutionX();
        int imageHeight = camera.getResolutionY();

        Vector3[][] image = new Vector3[imageHeight][imageWidth];

        AtomicInteger progress = new AtomicInteger(0);

        //for (int i = 0; i < imageHeight; i++) {
        IntStream.range(0, imageHeight).parallel().forEach(i -> {
            for (int j = 0; j < imageWidth; j++) {
                Vector3 pixelColor = new Vector3();
                for (int k = 0; k < pixelSamples; k++) {
                    Ray ray = camera.createRay(j, i);
                    pixelColor = Vector3.add(pixelColor, traceRay(ray, scene, bvh, 4));
                }
                image[i][j] = Vector3.divide(pixelColor, pixelSamples);
            }
            int currentProgress = progress.incrementAndGet();
            double timeElapsed = (System.nanoTime() - startTime) / 1e9;
            double timeRemaining = ((imageHeight * timeElapsed) / currentProgress) - timeElapsed;
            System.out.printf("Rendered Rows: %d/%d. Elapsed Time: %ds. Estimated Time Remaining: %ds.\n", currentProgress, imageHeight, (int) timeElapsed, (int) timeRemaining);
        });

        long finishTime = System.nanoTime();
        System.out.printf("Rendering was completed in %d seconds.\n", (int) ((finishTime - startTime) / 1e9));
        return image;
    }

    private Vector3 traceRay(Ray ray, Scene scene, BVH bvh, int depth) {
        Vector3 backgroundColor = scene.getBackgroundColor();

        Vector3 color = new Vector3(0, 0, 0);

        //  Check if the ray intersects with any object.
        IntersectionInfo intersectionInfo = bvh.hit(ray);
        if (intersectionInfo == null || intersectionInfo.getT() < 0)
            return backgroundColor;

        // Find the visible lights through shadow rays.
        ArrayList<PointLight> visibleLights = new ArrayList<>();
        for (PointLight light : scene.getLights()) {
            Vector3 shadowRayDirection = Vector3.subtract(light.getPosition(), intersectionInfo.getPosition()).normalized();
            Vector3 shadowRayOrigin = Vector3.add(intersectionInfo.getPosition(), Vector3.multiply(shadowRayDirection, eps));

            double tAtPointLight = (light.getPosition().getX() - shadowRayOrigin.getX()) / shadowRayDirection.getX() + eps;

            Ray shadowRay = new Ray(shadowRayOrigin, shadowRayDirection);

            IntersectionInfo shadowRayIntersectionInfo = bvh.hit(shadowRay);
            if (shadowRayIntersectionInfo == null || shadowRayIntersectionInfo.getT() > tAtPointLight)
                visibleLights.add(light);
        }

        color = Vector3.add(color, BlinnPhongShading.shade(intersectionInfo, visibleLights, scene.getAmbientLight()));

        // Recursive ray tracing
        if (depth > 0) {
            Vector3 surfaceNormal = intersectionInfo.getNormal();
            Material material = intersectionInfo.getMaterial();

            // Fresnel Effect / Schlick's Approximation
            double air_ior = 1;
            double material_ior = material.getIor();

            double r0 = Math.pow((air_ior - material_ior) / (air_ior + material_ior), 2);
            double cosTheta = Vector3.dot(Vector3.multiply(ray.getDirection(), -1), surfaceNormal)
                    / ray.getDirection().magnitude() * surfaceNormal.magnitude();

            double reflectivity = r0 + (1 - r0) * Math.pow((1 - cosTheta), 5);

            // Reflection Ray
            Vector3 reflectionRayDirection = Vector3.subtract(ray.getDirection(),
                    Vector3.multiply(surfaceNormal, 2 * Vector3.dot(ray.getDirection(), surfaceNormal))).normalized();
            Vector3 reflectionRayOrigin = Vector3.add(intersectionInfo.getPosition(), Vector3.multiply(reflectionRayDirection, eps));
            Ray reflectionRay = new Ray(reflectionRayOrigin, reflectionRayDirection);

            Vector3 reflectionColor = traceRay(reflectionRay, scene, bvh, depth - 1);
            color = Vector3.add(color, Vector3.multiply(reflectionColor, reflectivity));

            // Transmission Ray
            if (material.getTransmission() > 0) {
                double n1 = air_ior;
                double n2 = material_ior;

                double ior_ratio = n1 / n2;
                if (intersectionInfo.isInsideObject())
                    ior_ratio = n2 / n1;

                double squareRootTermContent = 1 - Math.pow(ior_ratio, 2) * (1 - Math.pow(Vector3.dot(ray.getDirection(), surfaceNormal), 2));
                if (squareRootTermContent >= 0) {
                    Vector3 transmissionRayDirection = Vector3.subtract(Vector3.multiply(ray.getDirection(), ior_ratio),
                            Vector3.multiply(surfaceNormal,
                                    ior_ratio * Vector3.dot(ray.getDirection(), surfaceNormal) + Math.sqrt(squareRootTermContent))).normalized();
                    Vector3 transmissionRayOrigin = Vector3.add(intersectionInfo.getPosition(), Vector3.multiply(transmissionRayDirection, eps));
                    Ray transmissionRay = new Ray(transmissionRayOrigin, transmissionRayDirection);
                    Vector3 transmissionColor = traceRay(transmissionRay, scene, bvh, depth - 1);
                    color = Vector3.add(color, Vector3.multiply(transmissionColor, (1 - reflectivity) * material.getTransmission()));
                }
            }
        }

        return color;
    }
}
