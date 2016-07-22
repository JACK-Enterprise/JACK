/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jack.engine;

import java.nio.file.Paths;
import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import java.nio.file.Path;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Sphere;
import lombok.Getter;

/**
 *
 * @author Aurelien
 */
public class Planet extends Sphere {
    
    private Path diffuseMap;
    private Path specularMap;
    private Path bumpMap;
    private double radius;
    private static final double MAP_WIDTH = 8192 / 2d;
    private static final double MAP_HEIGHT = 4096 / 2d;
   
    public Planet(String diffuseMap, String specularMap, String bumpMap, double radius) {
        super(radius);
        this.diffuseMap = Paths.get(diffuseMap);
        this.specularMap = Paths.get(specularMap);
        this.bumpMap = Paths.get(bumpMap);
        this.radius = radius;
    }
    
    public double getPlanetRadius() {
        return radius;
    }


    public void setPlanetRadius(double radius) {
        this.radius = radius;
    }

    public void setDiffuseMap(Path diffuseMap) {
        this.diffuseMap = diffuseMap;
    }

    public void init() {
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(
                new Image(
                        diffuseMap.toString(),
                        MAP_WIDTH,
                        MAP_HEIGHT,
                        true,
                        true
                )
        );
        material.setBumpMap(
                new Image(
                        bumpMap.toString(),
                        MAP_WIDTH,
                        MAP_HEIGHT,
                        true,
                        true
                )
        );
        material.setSpecularMap(
                new Image(
                        specularMap.toString(),
                        MAP_WIDTH,
                        MAP_HEIGHT,
                        true,
                        true
                )
        );

        this.setMaterial(
                material
        );
    }

    public void initWithoutBumpMap(){
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(
                new Image(
                        diffuseMap.toString(),
                        MAP_WIDTH,
                        MAP_HEIGHT,
                        true,
                        true
                )
        );
        material.setSpecularMap(
                new Image(
                        specularMap.toString(),
                        MAP_WIDTH,
                        MAP_HEIGHT,
                        true,
                        true
                )
        );

        this.setMaterial(
                material
        );
    }
    
    public void addToContainer(Group container) {
        setAtmosphere(container);
        container.getChildren().add(this);
    }
    
    private void setAtmosphere(Group container) {
        Sphere blueAtmosphere = new Sphere(5.1, 200);
        Sphere whiteAtmosphere = new Sphere(5.05, 200);
        
        PhongMaterial blueMaterial = new PhongMaterial();
        PhongMaterial whiteMaterial = new PhongMaterial();
        
        blueMaterial.setDiffuseColor(Color.color(0, 0.2, 0.5, 0.2));
        whiteMaterial.setDiffuseColor(Color.color(1, 1, 1, 0.2));        
        
        
        blueAtmosphere.setDrawMode(DrawMode.FILL);
        blueAtmosphere.setMaterial(blueMaterial);

        whiteAtmosphere.setDrawMode(DrawMode.FILL);
        whiteAtmosphere.setMaterial(whiteMaterial);
        
        container.getChildren().addAll(blueAtmosphere, whiteAtmosphere);
    }
}
