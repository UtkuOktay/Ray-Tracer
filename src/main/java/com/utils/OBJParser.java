package main.java.com.utils;

import main.java.com.geometry.Object3D;
import main.java.com.geometry.Surface;
import main.java.com.geometry.Triangle;
import main.java.com.math.Vector3;
import main.java.com.shading.Material;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OBJParser {
    public static Object3D parseOBJFile(String filePath, Material defaultMaterial) {
        File file = new File(filePath);

        ArrayList<Vector3> vertices = new ArrayList<>();
        ArrayList<Vector3> vertexNormals = new ArrayList<>();
        ArrayList<double[]> textureCoordinates = new ArrayList<>();
        ArrayList<Surface> surfaces = new ArrayList<>();

        Material currentMaterial = defaultMaterial;
        Map<String, Material> materials = new HashMap<>();

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

                else if (elements[0].equals("vt")) {
                    if (elements.length < 3)
                        continue;

                    double u = Double.parseDouble(elements[1]);
                    double v = Double.parseDouble(elements[2]);

                    textureCoordinates.add(new double[] {u, v});
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

                    double[] uvA = new double[2], uvB = new double[2], uvC = new double[2];
                    if (!v1[1].isEmpty() && !v2[1].isEmpty() && !v3[1].isEmpty()) {
                        uvA = textureCoordinates.get(Integer.parseInt(v1[1]) - 1);
                        uvB = textureCoordinates.get(Integer.parseInt(v2[1]) - 1);
                        uvC = textureCoordinates.get(Integer.parseInt(v3[1]) - 1);
                    }

                    Vector3 vnA = null, vnB = null, vnC = null;
                    if (!v1[2].isEmpty() && !v2[2].isEmpty() && !v3[2].isEmpty()) {
                        vnA = vertexNormals.get(Integer.parseInt(v1[2]) - 1);
                        vnB = vertexNormals.get(Integer.parseInt(v2[2]) - 1);
                        vnC = vertexNormals.get(Integer.parseInt(v3[2]) - 1);
                    }

                    surfaces.add(new Triangle(vA, vB, vC, vnA, vnB, vnC, currentMaterial, uvA, uvB, uvC));
                }

                else if (elements[0].equals("mtllib")) {
                    String mtlFilePath = filePath.substring(0, filePath.lastIndexOf(File.separator) + 1) + elements[1];
                    Map<String, Material> newMaterials = MTLParser.parseMTLFile(mtlFilePath);
                    if (newMaterials != null)
                        materials.putAll(newMaterials);
                }
                else if (elements[0].equals("usemtl")) {
                    currentMaterial = materials.getOrDefault(elements[1], defaultMaterial);
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
        for (Surface surface : surfaces) {
            object3D.addSurface(surface);
        }

        return object3D;
    }
}
