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
import javafx.scene.shape.Rectangle;
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
    private HashMap<String, Box> prevBoxesList;
    
    public CartographyTextureManager(Planet planet)
    {
        this.planet = planet;
        prevBoxesList = new HashMap<String, Box>();
    }
    

    public void bindTexturesFromGPSCoord(Scene scene, double x, double y, EmpriseCoord emprise, double fov, Group group) {
        
        double radius = planet.getPlanetRadius();
        GPSCoord minCoord = emprise.getMinCoord();
        GPSCoord maxCoord = emprise.getMaxCoord();
        double stepX = 2;
        double stepY = 2;
        double minLg = Math.floor(minCoord.getLongitude() / stepX) * stepX;
        double minLat = Math.floor(minCoord.getLatitude() / stepY) * stepY;
        double maxLg = Math.ceil(maxCoord.getLongitude() / stepX) * stepX;
        double maxLat = Math.ceil(maxCoord.getLatitude() / stepY) * stepY;
        ArrayList boxes = new ArrayList<Box>();
        double w = stepX * 2 * PI * radius / 360;
        double h = stepY * 2 * PI * radius / 360;        
        int res = fov > 40 ? 256 : (fov > 20 ? 512 : (fov > 10 ? 1024 : 2048));
        
        HashMap<String, Box> tmpMap = new HashMap<String, Box>();
        
        for(double i = minLg ; i < maxLg ; i+=stepX)
        {
            for(double j = minLat ; j < maxLat ; j+=stepY)
            {
                String filename = "file:./tmp/" + i + "_" + j + "_" + res + ".png";
                
                if(!prevBoxesList.containsKey(filename))
                {
                    GPSCoord coordTmp = new GPSCoord(i, j);

                    Box box = createPlane(filename, w, h, res);
                    box.getTransforms().add(new Rotate(-coordTmp.getLongitude(), 0, 0, 0, new Point3D(0, 1, 0)));
                    box.getTransforms().add(new Rotate(-coordTmp.getLatitude() / 1.5, 0, 0, 0, new Point3D(1, 0, 0)));
                    box.getTransforms().add(new Translate(0, 0, -radius));

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
        
        for(String k : remover)
        {
            group.getChildren().removeAll(prevBoxesList.get(k));
            prevBoxesList.remove(k);
        }
        
        for(String k : tmpMap.keySet())
        {
            Box b = tmpMap.get(k);
            if(b != null)
            {
                prevBoxesList.put(k, b);
            }
        }
        group.getChildren().addAll(boxes);
    }
    
    private Box createPlane(String texturePath, double w, double h, double res){
        Box box = new Box(w, h, 0.001);
        PhongMaterial material = new PhongMaterial();
        
        material.setDiffuseMap(
                new Image(
                        texturePath,
                        res,
                        res,
                        true,
                        true
                )
        );

        box.setMaterial(material);
        
        return box;
    }
    
}
