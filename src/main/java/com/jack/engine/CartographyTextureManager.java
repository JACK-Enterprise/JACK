/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jack.engine;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Rectangle;
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
        double yAngle = Math.toDegrees(Math.acos(pos.getY() / (radius - pos.getZ()))); // SEEMS OK

        Box box = createPlane("file:./cartography/wms2.png", pos, w, h);
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

        Box box = createPlane("file:./cartography/wms2.png", pos, w, h);

        /*
        box.setRotationAxis(new Point3D(1, 0,  0));
        box.setRotate(Math.toDegrees(xAngle));
        box.setRotationAxis(new Point3D(0, 1,  0));
        box.setRotate(Math.toDegrees(yAngle));
        */

        //System.out.println(xAngle + " / " + yAngle);

        return box;
    }

    public Box bindTexturesFromGPSCoord(Scene scene, double x, double y) {
        double radius = planet.getPlanetRadius();
        GPSCoord camCoord = new GPSCoord(x, y);
        Pos3D pos = camCoord.toPos3D(radius);

        System.out.println("----------------------------------------------------");
        System.out.println("Cartography Texture Manager System Informations");
        System.out.println("Cam Coord in degree :");
        System.out.println("    -Longitude : " + camCoord.getLongitude());
        System.out.println("    -Latitude : " + camCoord.getLatitude());
        System.out.println();
        System.out.println("Cam Coord in 3D Pos :");
        System.out.println("    -X : " + pos.getX());
        System.out.println("    -Y : " + pos.getY());
        System.out.println("    -Z : " + pos.getZ());
        System.out.println();
        System.out.println("Cam Coord 3D Pos To Degree:");
        GPSCoord coord = pos.toGPSCoord();
        System.out.println("    -Longitude : " + coord.getLongitude());
        System.out.println("    -Latitude : " + coord.getLatitude());
        System.out.println("----------------------------------------------------");


        double w = 0.1;
        double h = 0.1;
        double xAngle = Math.toDegrees(Math.acos(-pos.getX() / radius)); // NOT CHECKED
        double yAngle = Math.toDegrees(Math.acos(pos.getY() / radius)); // SEEMS OK

        double radian = Math.toRadians(camCoord.getLongitude());

        System.out.println("Radian : " + radian);
        System.out.println("TEst : " + Math.acos(radian));
        System.out.println("Y Angle : " + yAngle);
        System.out.println("X Angle : " + xAngle);

        Box box = createPlane("file:./tmp/wms.png", pos, w, h);

        box.setRotationAxis(new Point3D(1, 0,  0));
        box.setRotate(Math.toDegrees(xAngle));
        box.setRotationAxis(new Point3D(0, 1,  0));
        box.setRotate(Math.toDegrees(yAngle));

        System.out.println(xAngle + " / " + yAngle);

        return box;
    }
    
    private Box createPlane(String texturePath, Pos3D pos, double w, double h){
        Box box = new Box(0.1, h, w);
        PhongMaterial material = new PhongMaterial();

        System.out.println("-------------- CUBE POS ---------------");
        System.out.println("Cube Coord in 3D Pos :");
        System.out.println("    -X : " + pos.getX());
        System.out.println("    -Y : " + pos.getY());
        System.out.println("    -Z : " + pos.getZ());
        System.out.println();

        box.setTranslateX(pos.getX());
        box.setTranslateY(pos.getY());
        box.setTranslateZ(-pos.getZ());
        
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
