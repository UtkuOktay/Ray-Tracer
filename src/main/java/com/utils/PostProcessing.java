package main.java.com.utils;

import main.java.com.math.Vector3;

public class PostProcessing {
    private static final double gammaCorrectionFactor = 1 / 2.2;

    public static Vector3[][] gammaCorrection(Vector3[][] image) {
        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[i].length; j++) {
                double r = Math.pow(image[i][j].getX(), gammaCorrectionFactor);
                double g = Math.pow(image[i][j].getY(), gammaCorrectionFactor);
                double b = Math.pow(image[i][j].getZ(), gammaCorrectionFactor);
                image[i][j] = new Vector3(r, g, b);
            }
        }
        return image;
    }
}
