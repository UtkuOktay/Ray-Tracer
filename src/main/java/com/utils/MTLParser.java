package main.java.com.utils;

import main.java.com.math.Vector3;
import main.java.com.shading.ConductorMaterial;
import main.java.com.shading.DielectricMaterial;
import main.java.com.shading.Material;
import main.java.com.texture.ImageTexture;
import main.java.com.texture.SolidColorTexture;
import main.java.com.texture.Texture;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class MTLParser {
    public static Map<String, Material> parseMTLFile(String filePath) {
        File file = new File(filePath);

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;

            Map<String, Material> materials = new HashMap<>();

            String currentMaterialName = null;
            Map<String, Object> currentMaterialProperties = new HashMap<>();

            while ((line = bufferedReader.readLine()) != null) {
                String[] elements = line.split(" ");
                if (elements.length < 1)
                    continue;

                if (elements[0].equals("newmtl")) {
                    if (currentMaterialName != null)
                        materials.put(currentMaterialName, createMaterialFromMap(currentMaterialProperties));

                    currentMaterialName = elements[1];
                    currentMaterialProperties = new HashMap<>();
                }

                else if (elements[0].equals("Kd") || elements[0].equals("Ks"))
                    currentMaterialProperties.put(elements[0], parseColorString(elements[1], elements[2], elements[3]));

                else if (elements[0].equals("Ns") || elements[0].equals("Ni") || elements[0].equals("d"))
                    currentMaterialProperties.put(elements[0], Double.parseDouble(elements[1]));

                else if (elements[0].equals("map_Kd")) {
                    String texturePath = filePath.substring(0, filePath.lastIndexOf(File.separator) + 1) + elements[1];
                    currentMaterialProperties.put("map_Kd", texturePath);
                }

                else if (elements[0].equals("illum")) {
                    currentMaterialProperties.put("illum", Integer.parseInt(elements[1]));
                }
            }

            if (currentMaterialName != null)
                materials.put(currentMaterialName, createMaterialFromMap(currentMaterialProperties));
            return materials;
        }
        catch (FileNotFoundException e) {
            System.out.println("MTL file cannot be loaded.\n" + e.getMessage());
            return null;
        }
        catch (IOException e) {
            System.out.println("MTL file cannot be read.\n" + e.getMessage());
            return null;
        }
    }

    private static Vector3 parseColorString(String rStr, String gStr, String bStr) {
        double r = Double.parseDouble(rStr);
        double g = Double.parseDouble(gStr);
        double b = Double.parseDouble(bStr);
        return new Vector3(r, g, b);
    }

    private static Material createMaterialFromMap(Map<String, Object> materialProperties) {
        Texture diffuseTexture;
        if (materialProperties.containsKey("map_Kd")) {
            Vector3 kd = materialProperties.containsKey("Kd") ? (Vector3) materialProperties.get("Kd") : new Vector3(1, 1, 1);
            diffuseTexture = new ImageTexture((String) materialProperties.get("map_Kd"), kd);
        }
        else
            diffuseTexture = new SolidColorTexture((Vector3) materialProperties.get("Kd"));

        Vector3 specularColor = (Vector3) materialProperties.getOrDefault("Ks", new Vector3(0, 0, 0));
        double specularHardness = (Double) materialProperties.getOrDefault("d", 0.0);
        double ior = (Double) materialProperties.getOrDefault("Ni", 0.0);
        double transmission = 1 - (Double) materialProperties.getOrDefault("d", 0.0); //Invert as it is defined as dissolve

        int illumMode = (int) materialProperties.getOrDefault("illum", 2);

        //This is not a completely correct approach and should be updated later.
        if (illumMode == 0 || illumMode == 1)
            return new ConductorMaterial(diffuseTexture, new Vector3(), specularHardness, 0);

        return new DielectricMaterial(diffuseTexture, specularColor, specularHardness, ior, transmission);
    }
}
