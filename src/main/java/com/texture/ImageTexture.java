package main.java.com.texture;

import main.java.com.math.Vector3;
import main.java.com.utils.ImageFileIO;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ImageTexture implements Texture{
    private BufferedImage image;
    private Vector3 colorCoefficients = new Vector3(1, 1, 1);
    private boolean repeat = true;
    private double scale = 1;

    public ImageTexture(BufferedImage image) {
        this.image = image;
    }

    public ImageTexture(String imagePath) {
        this.image = ImageFileIO.readImage(imagePath);
    }

    public ImageTexture(String imagePath, Vector3 colorCoefficients) {
        this.image = ImageFileIO.readImage(imagePath);
        this.colorCoefficients = colorCoefficients;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public Vector3 getColorCoefficients() {
        return colorCoefficients;
    }

    public void setColorCoefficients(Vector3 colorCoefficients) {
        this.colorCoefficients = colorCoefficients;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    @Override
    public Vector3 getColor(double u, double v) {
        u *= getScale();
        v *= getScale();

        if (repeat) {
            u = Math.abs(u - Math.floor(u));
            v = Math.abs(v - Math.floor(v));
        }
        else {
            u = Math.min(Math.max(u, 0), 1);
            v = Math.min(Math.max(v, 0), 1);
        }

        BufferedImage image = getImage();

        double x = u * (image.getWidth() - 1);
        double y = (1 - v) * (image.getHeight() - 1);

        return Vector3.componentWiseMultiply(findColorBilinear(image, x, y), getColorCoefficients());
    }

    private Vector3 findColorBilinear(BufferedImage image, double x, double y) {
        int floorX = (int) Math.floor(x);
        int ceilingX = (int) Math.ceil(x);
        double s = x - floorX;

        int floorY = (int) Math.floor(y);
        int ceilingY = (int) Math.ceil(y);
        double t = y - floorY;

        Color c0 = new Color(image.getRGB(floorX, floorY));
        Color c1 = new Color(image.getRGB(ceilingX, floorY));
        Color c2 = new Color(image.getRGB(floorX, ceilingY));
        Color c3 = new Color(image.getRGB(ceilingX, ceilingY));

        Vector3 c0Vec = Vector3.divide(new Vector3(c0.getRed(), c0.getGreen(), c0.getBlue()), 255.0);
        Vector3 c1Vec = Vector3.divide(new Vector3(c1.getRed(), c1.getGreen(), c1.getBlue()), 255.0);
        Vector3 c2Vec = Vector3.divide(new Vector3(c2.getRed(), c2.getGreen(), c2.getBlue()), 255.0);
        Vector3 c3Vec = Vector3.divide(new Vector3(c3.getRed(), c3.getGreen(), c3.getBlue()), 255.0);

        Vector3 c01 = Vector3.add(Vector3.multiply(c0Vec, 1 - s), Vector3.multiply(c1Vec, s));
        Vector3 c23 = Vector3.add(Vector3.multiply(c2Vec, 1 - s), Vector3.multiply(c3Vec, s));
        return Vector3.add(Vector3.multiply(c01, 1 - t), Vector3.multiply(c23, t));
    }
}
