package main.java.com.bvh;

import main.java.com.geometry.Surface;

import java.util.List;

public class BVHNode {
    private AABB boundingBox;
    private BVHNode left;
    private BVHNode right;
    private List<Surface> surfaces;

    public BVHNode(AABB boundingBox, BVHNode left, BVHNode right, List<Surface> surfaces) {
        this.boundingBox = boundingBox;
        this.left = left;
        this.right = right;
        this.surfaces = surfaces;
    }

    public AABB getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(AABB boundingBox) {
        this.boundingBox = boundingBox;
    }

    public BVHNode getLeft() {
        return left;
    }

    public void setLeft(BVHNode left) {
        this.left = left;
    }

    public BVHNode getRight() {
        return right;
    }

    public void setRight(BVHNode right) {
        this.right = right;
    }

    public List<Surface> getSurfaces() {
        return surfaces;
    }

    public void setSurfaces(List<Surface> surfaces) {
        this.surfaces = surfaces;
    }

    public boolean isLeafNode() {
        return left == null && right == null;
    }
}
