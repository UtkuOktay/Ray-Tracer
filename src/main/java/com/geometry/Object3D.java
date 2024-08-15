package main.java.com.geometry;

import main.java.com.shading.Material;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Object3D {
    private List<Surface> surfaces = new ArrayList<>();
    private Material material;

    public Object3D() {

    }

    public Object3D(Surface surface) {
        this.surfaces.add(surface);
    }

    public Object3D(Material material) {
        this.material = material;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public List<Surface> getSurfaces() {
        return Collections.unmodifiableList(surfaces);
    }

    public void addSurface(Surface surface) {
        if (getMaterial() != null)
            surface.setMaterial(material);

        surfaces.add(surface);
    }

    public void removeSurface(Surface surface) {
        surfaces.remove(surface);
    }

    public void clearSurfaces() {
        surfaces.clear();
    }
}
