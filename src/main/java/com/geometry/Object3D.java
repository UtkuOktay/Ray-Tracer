package main.java.com.geometry;

import main.java.com.shading.Material;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Object3D {
    private List<Surface> surfaces = new ArrayList<>();

    public Object3D() {

    }

    public Object3D(Surface surface) {
        this.surfaces.add(surface);
    }

    public List<Surface> getSurfaces() {
        return Collections.unmodifiableList(surfaces);
    }

    public void addSurface(Surface surface) {
        surfaces.add(surface);
    }

    public void removeSurface(Surface surface) {
        surfaces.remove(surface);
    }

    public void clearSurfaces() {
        surfaces.clear();
    }
}
