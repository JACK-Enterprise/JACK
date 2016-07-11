/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jack.engine.camera;

import com.jack.engine.Planet;
import static  com.jack.core.JackMath.*;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
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
    private Scene scene;
    private double lastMouseX;
    private double lastMouseY;
    private double totalXAngle;
    private double totalYAngle;
    @Getter @Setter private double fov;
    @Getter private double startFov;
    private double zoomSensitivity;
    private double moveSensitivity;
    private Planet planet;
    
    public TrackBallCamera(double x, double y, double z, Scene scene) {
        super(true);
        this.x = x;
        this.y = y;
        this.z = z;
        this.fov = 35;
        startFov = this.fov;
        this.scene = scene;
        moveSensitivity = 0.4;
        zoomSensitivity = 0.003;
        
        setFieldOfView(fov);
        setFarClip(10000);
        setNearClip(0.1);
        getTransforms().addAll(
                new Rotate(0, Rotate.Y_AXIS),
                new Rotate(0, Rotate.X_AXIS),
                new Translate(x, y, z));


    }

    public void setPlanet(Planet planet) {
        this.planet = planet;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void bindOn(Node element) {
        if(element != null)
        {
            element.setOnMousePressed(bindMousePressedEvent());
            element.setOnMouseDragged(bindMouseDraggedEvent());
            element.setOnScroll(bindScrollMouseEvent());
            element.setOnMouseReleased(bindMouseReleasedEvent());
        }
    }
    
    private EventHandler<MouseEvent> bindMouseDraggedEvent() {
        TrackBallCamera self = this;
        EventHandler<MouseEvent> ev = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                scene.setCursor(javafx.scene.Cursor.CLOSED_HAND);
                double mouseX = event.getScreenX();
                double mouseY = event.getScreenY();
                double xrel = (lastMouseX - mouseX) * moveSensitivity;
                double yrel = (lastMouseY - mouseY)* moveSensitivity;
                
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
                lastMouseX = mouseX;
                lastMouseY = mouseY;
            }
        };
        return ev;
    }
    
    private EventHandler <MouseEvent> bindMousePressedEvent() {
       
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                scene.setCursor(javafx.scene.Cursor.CLOSED_HAND);
                lastMouseX = event.getScreenX();
                lastMouseY = event.getScreenY();

                xPosbyFov(z, fov, planet.getPlanetRadius());
            }
        };
    }

    private EventHandler <ScrollEvent> bindScrollMouseEvent(){
        return new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {

                fov -= fov * event.getDeltaY()*zoomSensitivity;

                if(fov > 35){
                    fov = 35;
                }

                if(fov < 0.0001){
                    fov = 0.0001;
                }

                setFieldOfView(fov);
                moveSensitivity -= moveSensitivity * event.getDeltaY()*zoomSensitivity;
                
                if(moveSensitivity > 0.4)
                {
                    moveSensitivity = 0.4;
                }
                else if(moveSensitivity < 0.0001)
                {
                    moveSensitivity = 0.0001;
                }

                xPosbyFov(z, fov, planet.getPlanetRadius());
            }
        };
    }
    private EventHandler <MouseEvent> bindMouseReleasedEvent(){
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                scene.setCursor(javafx.scene.Cursor.DEFAULT);
            }

        };
    }
}