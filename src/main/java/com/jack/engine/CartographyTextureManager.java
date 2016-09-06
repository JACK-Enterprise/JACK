/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jack.engine;

import static java.lang.Math.PI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Aurelien
 */
public class CartographyTextureManager {
    
    @Getter @Setter private Planet planet;
    private HashMap<String, MeshView> prevBoxesList;
    
    public CartographyTextureManager(Planet planet)
    {
        this.planet = planet;
        prevBoxesList = new HashMap<String, MeshView>();
    }
    

    public void bindTexturesFromGPSCoord(Scene scene, double x, double y, EmpriseCoord emprise, double fov, Group group) {
        
        double radius = planet.getPlanetRadius();
        GPSCoord minCoord = emprise.getMinCoord();
        GPSCoord maxCoord = emprise.getMaxCoord();
        double stepX = 1;
        double stepY = 1;
        double minLg = Math.floor(minCoord.getLongitude() / stepX) * stepX;
        double minLat = Math.floor(minCoord.getLatitude() / stepY) * stepY;
        double maxLg = Math.ceil(maxCoord.getLongitude() / stepX) * stepX;
        double maxLat = Math.ceil(maxCoord.getLatitude() / stepY) * stepY;
        ArrayList boxes = new ArrayList<MeshView>();
//        double w = stepX * 2 * PI * radius / 360;
//        double h = stepY * 2 * PI * radius / 360;        
        int res = fov > 20 ? 32 : (fov > 10 ? 64 : (fov > 1? 128 : 256));
        double nextI;
        double nextJ;
        
        HashMap<String, MeshView> tmpMap = new HashMap<String, MeshView>();
        
        for(double i = minLg ; i < maxLg ; i+=stepX)
        {
            nextI = i+stepX;
            
            for(double j = minLat ; j < maxLat ; j+=stepY)
            {
                nextJ = j+stepY;
                String filename = "file:./tmp/" + i + "_" + j + "_" + res + ".jpg";
                
                if(!prevBoxesList.containsKey(filename))
                {
                    GPSCoord coordTmp = new GPSCoord(i, j);
                    GPSCoord nextCoordI = new GPSCoord(nextI, j);
                    GPSCoord nextCoordJ = new GPSCoord(i, nextJ);
                    GPSCoord nextCoordIJ = new GPSCoord(nextI, nextJ);
                    MeshView box = createPlane(x, y, filename, // 0, 0
                                                nextCoordJ.toPos3D(planet.getRadius()), // 0, 1 
                                                coordTmp.toPos3D(planet.getRadius()), // 0, 0
                                                nextCoordIJ.toPos3D(planet.getRadius()), // 1, 1
                                                nextCoordI.toPos3D(planet.getRadius()), res, planet.getRadius()); // 1, 0

                    boxes.add(box);
                    tmpMap.put(filename, box);
                }
                else
                {
                    tmpMap.put(filename, null);
                }
            }
        }
        
        Set <String> remover = prevBoxesList.entrySet()
          .stream()
          .filter(entry -> group.getChildren().contains(entry.getValue()) && !tmpMap.containsKey(entry.getKey()))
          .map(Map.Entry::getKey)
          .collect(Collectors.toSet());        
        
        remover.stream().map((k) -> {
            group.getChildren().removeAll(prevBoxesList.get(k));
            return k;
        }).forEach((k) -> {
            prevBoxesList.remove(k);
        });
        
        tmpMap.keySet().stream().forEach((k) -> {
            MeshView b = tmpMap.get(k);
            if (b != null) {
                prevBoxesList.put(k, b);
            }
        });
        group.getChildren().addAll(boxes);
    }
    
    private MeshView createPlane(double x, double y, String texturePath, Pos3D bottomLeft, Pos3D topLeft,
            Pos3D bottomRight, Pos3D topRight, double res, double radius){
        
        MeshView box;        
        double smallMidX = (topLeft.getX() + topRight.getX()) / 2;
        double midY = (topLeft.getY() + bottomLeft.getY()) / 2;
        double bigMidX = (bottomLeft.getX() + bottomRight.getX()) / 2;
        double midZ = (topLeft.getZ() + bottomRight.getZ()) / 2;
        Pos3D midPoint = new Pos3D((bigMidX + smallMidX) / 2, midY, midZ);
        GPSCoord midCoord = midPoint.toGPSCoord();
        int[] faces = {
                    2, 3, 0, 2, 1, 0,
                    2, 3, 1, 0, 3, 1
            };
        TriangleMesh pyramidMesh = new TriangleMesh();
        float[] arr = { (float)topLeft.getX(), (float)topLeft.getY(), -(float)topLeft.getZ(),
                        (float)topRight.getX(), (float)topRight.getY(), -(float)topRight.getZ(),
                        (float)bottomLeft.getX(), (float)bottomLeft.getY(), -(float)bottomLeft.getZ(),                      
                        (float)bottomRight.getX(), (float)bottomRight.getY(), -(float)bottomRight.getZ(),
                        };
        float[] texCoords = {
                    1, 1, // idx t0
                    1, 0, // idx t1
                    0, 1, // idx t2
                    0, 0  // idx t3
            };
        PhongMaterial material = new PhongMaterial();
        pyramidMesh.getPoints().addAll(arr);
        pyramidMesh.getTexCoords().addAll(texCoords);
        pyramidMesh.getFaces().addAll(faces);
        material.setDiffuseMap(
                new Image(
                        texturePath,
                        res,
                        res,
                        true,
                        true
                )
        );
        
        box = new MeshView(pyramidMesh);
        box.setMaterial(material);
        box.setCullFace(CullFace.NONE);
        
        box.getTransforms().add(new Rotate(midCoord.getLongitude()- x, midPoint.getX(), midPoint.getY(), -midPoint.getZ(), new Point3D(0, 1, 0)));
        box.getTransforms().add(new Rotate((midCoord.getLatitude() - y) / 1.5, midPoint.getX(), midPoint.getY(), -midPoint.getZ(), new Point3D(1, 0, 0)));
        
        return box;
    }
    
}
