/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jack.engine.camera;

import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Aurelien
 */
public class TrackBallCamera extends PerspectiveCamera {
    
    @Getter @Setter private double x;
    @Getter @Setter private double y;
    @Getter @Setter private double z;
    private double lastMouseX;
    private double lastMouseY;
    private double totalXAngle;
    private double totalYAngle;
    
    public TrackBallCamera(double x, double y, double z) {
        super(true);
        this.x = x;
        this.y = y;
        this.z = z;
        
        setFieldOfView(35);
        setFarClip(10000);
        setNearClip(0.1);
        getTransforms().addAll(
                new Rotate(0, Rotate.Y_AXIS),
                new Rotate(0, Rotate.X_AXIS),
                new Translate(x, y, z));
    }
    
    public void bindOn(Node element) {
        if(element != null)
        {
            element.setOnMousePressed(bindMousePressedEvent());
            element.setOnMouseDragged(bindMouseDraggedEvent());
        }
    }
    
    private EventHandler<MouseEvent> bindMouseDraggedEvent() {
        TrackBallCamera self = this;
        EventHandler<MouseEvent> ev = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double xrel = (lastMouseX - event.getX()) * 0.3;
                double yrel = (lastMouseY - event.getY())* 0.3;
                
                totalXAngle += xrel;
                totalYAngle += yrel;
                
                if(totalYAngle > 90)
                {
                    totalYAngle = 90;
                }
                else if(totalYAngle < -90)
                {
                    totalYAngle = -90;
                }
                
                self.getTransforms().clear();
                self.getTransforms().add(new Rotate(totalXAngle, new Point3D(0, 1, 0)));
                self.getTransforms().add(new Rotate(totalYAngle, new Point3D(1, 0, 0)));
                self.getTransforms().add(new Translate(x, y, z));
                lastMouseX = event.getX();
                lastMouseY = event.getY();
            }
        };
        return ev;
    }
    
    private EventHandler <MouseEvent> bindMousePressedEvent() {
       
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lastMouseX = event.getX();
                lastMouseY = event.getY();
            }
        };
    }
}