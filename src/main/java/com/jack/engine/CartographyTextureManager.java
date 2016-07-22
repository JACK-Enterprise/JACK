/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jack.engine;

import static java.lang.Math.PI;
import java.util.ArrayList;
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
@AllArgsConstructor
public class CartographyTextureManager {
    
    @Getter @Setter private Planet planet;
    
    public void bindTextures(Scene scene, double fov, double xCenter, double yCenter) {
        
    }
    
    public Group bindTextures(Scene scene, double x1, double y1, double x2, double y2) {
        double radius = planet.getPlanetRadius();
        Pos3D pos = new GPSCoord(-74.00, 40.43).toPos3D(radius);
        double w = 0.5;
        double h = 0.5;
        double xAngle = Math.toDegrees(Math.acos(-pos.getX() / (radius - pos.getZ()))); // NOT CHECKED
        double yAngle = 90 + Math.toDegrees(Math.acos(pos.getY() / (radius - pos.getZ()))); // SEEMS OK

        Box box = createPlane("file:./cartography/wms2.png", w, h);
        /*
        box.setRotationAxis(new Point3D(1, 0,  0));
        box.setRotate(Math.toDegrees(xAngle));
        box.setRotationAxis(new Point3D(0, 1,  0));
        box.setRotate(Math.toDegrees(yAngle));
        */
        System.out.println(xAngle + " / " + yAngle);

        Group group = new Group();

        group.getChildren().add(box);
        return group;
    }

    public Box bindTexturesPositiv(Scene scene) {
        double radius = planet.getPlanetRadius();
        GPSCoord coordGPS = new GPSCoord(3.45, 48.43);
        Pos3D pos = coordGPS.toPos3D(radius);
        //Pos3D pos2 = new Pos3D(0.0, -3.47, 5.335);
        double w = 0.5;
        double h = 0.5;
        //double xAngle = Math.toDegrees(Math.acos(-pos.getX() / (radius - pos.getZ()))); // NOT CHECKED
        //double yAngle = Math.toDegrees(Math.acos(pos.getY() / (radius - pos.getZ()))); // SEEMS OK

        Box box = createPlane("file:./cartography/wms2.png", w, h);

        /*
        box.setRotationAxis(new Point3D(1, 0,  0));
        box.setRotate(Math.toDegrees(xAngle));
        box.setRotationAxis(new Point3D(0, 1,  0));
        box.setRotate(Math.toDegrees(yAngle));
        */

        //System.out.println(xAngle + " / " + yAngle);

        return box;
    }

    public Box[] bindTexturesFromGPSCoord(Scene scene, double x, double y, EmpriseCoord emprise, double fov) {
        
        double radius = planet.getPlanetRadius();
        GPSCoord minCoord = emprise.getMinCoord();
        GPSCoord maxCoord = emprise.getMaxCoord();
        Box[] out;
        double ifov = 10;
        double minLg = ((double)(Math.ceil(minCoord.getLongitude() * ifov))) / ifov;
        double minLat = ((double)(Math.ceil(minCoord.getLatitude() * ifov))) / ifov;
        double maxLg = ((double)(Math.floor(maxCoord.getLongitude() * ifov))) / ifov;
        double maxLat = ((double)(Math.floor(maxCoord.getLatitude() * ifov))) / ifov;
        ArrayList boxes = new ArrayList<Box>();
        double stepX = 0.25 * fov, stepY = 0.25 * fov;
        double w = stepX * 2 * PI * radius / 360;
        double h = stepY * 2 * PI * radius / 360;        
        
        for(double i = minLg ; i < maxLg ; i+= stepX)
        {
            for(double j = minLat ; j < maxLat ; j+= stepY)
            {
                GPSCoord coordTmp = new GPSCoord(i, j);

                Box box = createPlane("file:./tmp/wms.png", w, h); // SET PATH NAME DYNAMICALLY HERE
                box.getTransforms().add(new Rotate(-coordTmp.getLongitude(), 0, 0, 0, new Point3D(0, 1, 0)));
                box.getTransforms().add(new Rotate(-coordTmp.getLatitude() / 1.5, 0, 0, 0, new Point3D(1, 0, 0)));
                box.getTransforms().add(new Translate(0, 0, -radius));
                
                boxes.add(box);
            }
        }
        out = new Box[boxes.size()];
        boxes.toArray(out);
        return out; 
    }
    
    private Box createPlane(String texturePath, double w, double h){
        Box box = new Box(w, h, 0.1);
        PhongMaterial material = new PhongMaterial();
        
        material.setDiffuseMap(
                new Image(
                        texturePath,
                        256,
                        256,
                        true,
                        true
                )
        );

        box.setMaterial(material);
        
        return box;
    }
    
}
