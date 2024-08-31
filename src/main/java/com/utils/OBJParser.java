package main.java.com.utils;

import main.java.com.geometry.Object3D;
import main.java.com.geometry.Surface;
import main.java.com.geometry.Triangle;
import main.java.com.math.Vector3;
import main.java.com.shading.Material;

import java.io.*;
import java.util.ArrayList;

public class OBJParser {
    public static Object3D parseOBJFile(String filePath, Material material) {
        File file = new File(filePath);

        ArrayList<Vector3> vertices = new ArrayList<>();
        ArrayList<Vector3> vertexNormals = new ArrayList<>();
        ArrayList<Surface> surfaces = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] elements = line.split(" ");
                if (elements.length < 1)
                    continue;

                if (elements[0].equals("v")) {
                    if (elements.length < 4)
                        continue;

                    double x = Double.parseDouble(elements[1]);
                    double y = Double.parseDouble(elements[2]);
                    double z = Double.parseDouble(elements[3]);

                    vertices.add(new Vector3(x, y, z));
                }

                else if (elements[0].equals("vn")) {
                    if (elements.length < 4)
                        continue;

                    double x = Double.parseDouble(elements[1]);
                    double y = Double.parseDouble(elements[2]);
                    double z = Double.parseDouble(elements[3]);

                    vertexNormals.add(new Vector3(x, y, z));
                }

                else if (elements[0].equals("f")) {
                    if (elements.length < 4)
                        continue;

                    String[] v1 = elements[1].split("/");
                    String[] v2 = elements[2].split("/");
                    String[] v3 = elements[3].split("/");

                    int indexA = Integer.parseInt(v1[0]);
                    int indexB = Integer.parseInt(v2[0]);
                    int indexC = Integer.parseInt(v3[0]);

                    int numberOfVertices = vertices.size();
                    if (indexA < 1 || indexB < 1 || indexC < 1 || indexA > numberOfVertices || indexB > numberOfVertices || indexC > numberOfVertices)
                        continue;

                    Vector3 vA = vertices.get(indexA - 1);
                    Vector3 vB = vertices.get(indexB - 1);
                    Vector3 vC = vertices.get(indexC - 1);

                    if (v1.length < 2 || v2.length < 2 || v3.length < 2) {
                        surfaces.add(new Triangle(vA, vB, vC, material));
                        continue;
                    }

                    int indexVnA = Integer.parseInt(v1[2]);
                    int indexVnB = Integer.parseInt(v2[2]);
                    int indexVnC = Integer.parseInt(v3[2]);

                    Vector3 vnA = vertexNormals.get(indexVnA - 1);
                    Vector3 vnB = vertexNormals.get(indexVnB - 1);
                    Vector3 vnC = vertexNormals.get(indexVnC - 1);

                    surfaces.add(new Triangle(vA, vB, vC, vnA, vnB, vnC, material));
                }
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("OBJ file cannot be loaded.\n" + e.getMessage());
            return null;
        }
        catch (IOException e) {
            System.out.println("OBJ file cannot be read.\n" + e.getMessage());
            return null;
        }

        Object3D object3D = new Object3D();
        object3D.setMaterial(material);
        for (Surface surface : surfaces) {
            object3D.addSurface(surface);
        }

        return object3D;
    }
}
