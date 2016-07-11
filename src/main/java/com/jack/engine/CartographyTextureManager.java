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
        double w = 0.1;
        double h = 0.1;
        double xAngle = Math.toDegrees(Math.acos(-pos.getX() / (radius - pos.getZ()))); // NOT CHECKED
        double yAngle = Math.toDegrees(Math.acos(pos.getY() / (radius - pos.getZ()))); // SEEMS OK
        
        Box box = createPlane("file:./tmp/imgTest.png", pos, w, h);
        
        box.setRotationAxis(new Point3D(1, 0,  0));
        box.setRotate(Math.toDegrees(xAngle));
        box.setRotationAxis(new Point3D(0, 1,  0));
        box.setRotate(Math.toDegrees(yAngle));
        
        System.out.println(xAngle + " / " + yAngle);
        
        Group group = new Group();
        
        group.getChildren().add(box);
        return group;
    }
    
    private Box createPlane(String texturePath, Pos3D pos, double w, double h){
        Box box = new Box(0.1, h, w);
        PhongMaterial material = new PhongMaterial();
        
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
