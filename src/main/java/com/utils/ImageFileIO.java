package main.java.com.utils;

import main.java.com.math.Vector3;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageFileIO {
    public static BufferedImage readImage(String filePath) {
        try {
            File imageFile = new File(filePath);
            return ImageIO.read(imageFile);
        }
        catch (IOException e) {
            System.out.println("Image cannot be read:\n" + e.getMessage());
            return null;
        }
    }

    public static void saveImage(Vector3[][] image, String filePath) {
        BufferedImage bufferedImage = new BufferedImage(image[0].length, image.length, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[0].length; j++) {
                Vector3 color = image[i][j];
                int r = (int) (color.getX() * 255);
                int g = (int) (color.getY() * 255);
                int b = (int) (color.getZ() * 255);

                int rgb = (r << 16) | (g << 8) | b;
                bufferedImage.setRGB(j, i, rgb);
            }
        }

        try {
            File outputfile = new File(filePath);
            ImageIO.write(bufferedImage, "png", outputfile);
        }
        catch (IOException e) {
            System.out.println("Render could not be saved:\n" + e.getMessage());
        }
    }

    public static Vector3[][] clampPixelValues(Vector3[][] image, int max) {
        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[0].length; j++) {
                double r = Math.min(image[i][j].getX(), max);
                double g = Math.min(image[i][j].getY(), max);
                double b = Math.min(image[i][j].getZ(), max);

                image[i][j] = new Vector3(r, g, b);
            }
        }
        return image;
    }
}
