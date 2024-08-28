package main.java.com.geometry;

import main.java.com.math.Vector3;
import main.java.com.render.IntersectionInfo;
import main.java.com.render.Ray;
import main.java.com.shading.Material;

public interface Surface {
    Material getMaterial();
    void setMaterial(Material material);
    IntersectionInfo hit(Ray ray);
    Vector3 getCenter();
    Vector3 getMinPoint();
    Vector3 getMaxPoint();
}
