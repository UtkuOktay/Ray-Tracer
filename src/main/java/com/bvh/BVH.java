package main.java.com.bvh;

import main.java.com.geometry.Surface;
import main.java.com.math.Vector3;
import main.java.com.render.IntersectionInfo;
import main.java.com.render.Ray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BVH {
    private BVHNode root;

    public BVH(List<Surface> surfaces) {
        buildBVH(surfaces);
    }

    private void buildBVH(List<Surface> surfaces) {
        setRoot(buildBVHRec(new BVHNode(new AABB(surfaces), null, null, surfaces), surfaces, 1));
    }

    private BVHNode buildBVHRec(BVHNode parent,  List<Surface> surfaces, int currentDepth) {
        if (surfaces.isEmpty())
            return null;

        BVHNode node = new BVHNode(new AABB(surfaces), null, null, null);

        if (surfaces.size() == 1) {
            node.setSurfaces(surfaces);
            return node;
        }

        List<Surface> leftSurfaces = new ArrayList<>();
        List<Surface> rightSurfaces = new ArrayList<>();

        splitSurfaces(parent.getBoundingBox(), surfaces, leftSurfaces, rightSurfaces);

        //If can't split anymore, make this node a leaf.
        if (leftSurfaces.isEmpty() || rightSurfaces.isEmpty()) {
            node.setSurfaces(surfaces);
            return node;
        }

        node.setLeft(buildBVHRec(node, leftSurfaces, currentDepth + 1));
        node.setRight(buildBVHRec(node, rightSurfaces, currentDepth + 1));

        return node;

    }

    private void splitSurfaces(AABB boundingBox, List<Surface> surfaces, List<Surface> leftSurfaces, List<Surface> rightSurfaces) {
        double xLength = boundingBox.getMax().getX() - boundingBox.getMin().getX();
        double yLength = boundingBox.getMax().getY() - boundingBox.getMin().getY();
        double zLength = boundingBox.getMax().getZ() - boundingBox.getMin().getZ();

        int largestAxis = 0;

        if (yLength > xLength && yLength > zLength)
            largestAxis = 1;
        else if (zLength > xLength && zLength > yLength)
            largestAxis = 2;

        double midpoint = findMedianOfCenters(surfaces, largestAxis);

        for (Surface surface : surfaces) {
            if (surface.getCenter().getComponentByIndex(largestAxis) < midpoint)
                leftSurfaces.add(surface);
            else
                rightSurfaces.add(surface);
        }
    }

    private double findMedianOfCenters(List<Surface> surfaces, int axis) {
        double[] centers = new double[surfaces.size()];
        int i = 0;
        for (Surface surface : surfaces) {
            centers[i++] = surface.getCenter().getComponentByIndex(axis);
        }

        Arrays.sort(centers);
        if (centers.length % 2 == 0)
            return (centers[centers.length / 2 - 1] + centers[centers.length / 2]) / 2;

        return centers[centers.length / 2];
    }

    private BVHNode getRoot() {
        return root;
    }

    private void setRoot(BVHNode root) {
        this.root = root;
    }

    public IntersectionInfo hit(Ray ray) {
        IntersectionInfo dummyIntersectionInfo = new IntersectionInfo(Double.MAX_VALUE, ray, new Vector3(), new Vector3(), null);
        IntersectionInfo closestIntersection = hitRec(ray, getRoot(), dummyIntersectionInfo);

        if (closestIntersection == dummyIntersectionInfo)
            return null;

        return closestIntersection;
    }

    private IntersectionInfo hitRec(Ray ray, BVHNode node, IntersectionInfo closestIntersection) {
        if (!node.getBoundingBox().hit(ray))
            return closestIntersection;

        if (node.isLeafNode()) {
            double smallestT = closestIntersection.getT();

            for (Surface surface : node.getSurfaces()) {
                IntersectionInfo surfaceIntersection = surface.hit(ray);
                if (surfaceIntersection == null)
                    continue;

                double t = surfaceIntersection.getT();
                if (t >= 0 && t < smallestT) {
                    closestIntersection = surfaceIntersection;
                    smallestT = t;
                }
            }
        }
        else {
            closestIntersection = hitRec(ray, node.getLeft(), closestIntersection);
            closestIntersection = hitRec(ray, node.getRight(), closestIntersection);
        }

        return closestIntersection;
    }
}
