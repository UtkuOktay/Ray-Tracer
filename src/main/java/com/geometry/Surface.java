package main.java.com.geometry;

import main.java.com.render.IntersectionInfo;
import main.java.com.render.Ray;
import main.java.com.shading.Material;

public interface Surface {
    Material getMaterial();
    void setMaterial(Material material);
    IntersectionInfo hit(Ray ray);
}
