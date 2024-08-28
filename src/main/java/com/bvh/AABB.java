package main.java.com.bvh;

import main.java.com.geometry.Surface;
import main.java.com.math.Vector3;
import main.java.com.render.Ray;

import java.util.List;

public class AABB {
    private Vector3 min = new Vector3();
    private Vector3 max = new Vector3();

    public AABB(List<Surface> surfaces) {
        setBoundaries(surfaces);
    }

    public final void setBoundaries(List<Surface> surfaces) {
        if (surfaces.isEmpty()) {
            setMin(new Vector3());
            setMax(new Vector3());
            return;
        }

        setMin(new Vector3(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE));
        setMax(new Vector3(-Double.MAX_VALUE, -Double.MAX_VALUE, -Double.MAX_VALUE));

        for (Surface surface : surfaces) {
            setMin(Vector3.componentWiseMin(getMin(), surface.getMinPoint()));
            setMax(Vector3.componentWiseMax(getMax(), surface.getMaxPoint()));
        }
    }

    public final Vector3 getMin() {
        return min;
    }

    private void setMin(Vector3 min) {
        this.min = min;
    }

    public final Vector3 getMax() {
        return max;
    }

    private void setMax(Vector3 max) {
        this.max = max;
    }
    
    private double[] findTOnAxis(Ray ray, int axis) {
        double inverseDirectionValue = 1 / ray.getDirection().getComponentByIndex(axis);
        double tMin = (getMin().getComponentByIndex(axis) - ray.getOrigin().getComponentByIndex(axis)) * inverseDirectionValue;
        double tMax = (getMax().getComponentByIndex(axis) - ray.getOrigin().getComponentByIndex(axis)) * inverseDirectionValue;


        if (tMin > tMax) {
            double temp = tMax;
            tMax = tMin;
            tMin = temp;
        }

        return new double[] {tMin, tMax};
    }

    public boolean hit(Ray ray) {
        double[] tx = findTOnAxis(ray, 0);
        double txMin = tx[0];
        double txMax = tx[1];

        double[] ty = findTOnAxis(ray, 1);
        double tyMin = ty[0];
        double tyMax = ty[1];

        if (txMin > tyMax || tyMin > txMax)
            return false;

        double tMin = Math.max(txMin, tyMin);
        double tMax = Math.min(txMax, tyMax);

        double[] tz = findTOnAxis(ray, 2);
        double tzMin = tz[0];
        double tzMax = tz[1];

        if (tMin > tzMax || tzMin > tMax)
            return false;

        tMax = Math.min(tMax, tzMax);

        return tMax >= 0;
    }
}
