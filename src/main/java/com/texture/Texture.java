package main.java.com.texture;

import main.java.com.math.Vector3;

public interface Texture {
    Vector3 getColor(double u, double v);
}
