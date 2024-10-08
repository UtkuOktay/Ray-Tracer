package main.java.com;

import main.java.com.math.Vector3;
import main.java.com.render.Renderer;
import main.java.com.render.Scene;
import main.java.com.samples.CornellBox;
import main.java.com.utils.ImageFileIO;
import main.java.com.utils.PostProcessing;


public class Main {
    private static final int pixelSamples = 20;

    public static void main(String[] args) {
        Renderer renderer = new Renderer();
        Scene scene = CornellBox.getScene();
        Vector3[][] image = renderer.render(scene, pixelSamples);
        image = PostProcessing.gammaCorrection(image);
        image = ImageFileIO.clampPixelValues(image, 1);
        ImageFileIO.saveImage(image, "renders\\cornell-box_render.png");
    }
}