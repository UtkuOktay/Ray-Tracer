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
        ArrayList<Surface> surfaces = new ArrayList<>();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
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

                else if (elements[0].equals("f")) {
                    if (elements.length < 4)
                        continue;

                    int indexA = Integer.parseInt(elements[1].split("/")[0].split("//")[0]);
                    int indexB = Integer.parseInt(elements[2].split("/")[0].split("//")[0]);
                    int indexC = Integer.parseInt(elements[3].split("/")[0].split("//")[0]);

                    int numberOfVertices = vertices.size();
                    if (indexA < 1 || indexB < 1 || indexC < 1 || indexA > numberOfVertices || indexB > numberOfVertices || indexC > numberOfVertices)
                        continue;

                    Vector3 a = vertices.get(indexA - 1);
                    Vector3 b = vertices.get(indexB - 1);
                    Vector3 c = vertices.get(indexC - 1);

                    surfaces.add(new Triangle(a, b, c, material));
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
