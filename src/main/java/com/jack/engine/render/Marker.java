/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jack.engine.render;

import com.jack.engine.GPSCoord;
import com.jack.engine.Pos3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Aurelien
 */
public class Marker {
    @Getter @Setter protected GPSCoord gpsCoord;
    private Group mesh;
    
    public Marker(GPSCoord gpsCoord) {
        this.gpsCoord = gpsCoord;
        mesh = new Group();
    }
    
    public void render(Group root, double radius)
    {
        Sphere sphere = new Sphere(0.2);
        Pos3D pos = gpsCoord.toPos3D(radius);
        
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.RED);

        sphere.setMaterial(
                material
        );
        
        sphere.setTranslateX(pos.getX());
        sphere.setTranslateY(pos.getY());
        sphere.setTranslateZ(-pos.getZ());
        
        mesh.getChildren().clear();
        
        mesh.getChildren().add(sphere);
        
        root.getChildren().add(mesh);
    }
    
    public void scale(double ratio) {
        mesh.setScaleX(ratio);
        mesh.setScaleY(ratio);
        mesh.setScaleZ(ratio);
    }
}
