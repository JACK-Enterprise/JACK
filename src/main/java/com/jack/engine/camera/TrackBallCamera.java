/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jack.engine.camera;

import com.jack.engine.CartographyTextureManager;
import com.jack.engine.EmpriseCoord;
import com.jack.engine.GPSCoord;
import com.jack.engine.Planet;
import static  com.jack.core.JackMath.*;
import com.jack.wms.WMSImageryProvider;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import java.io.IOException;

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
    @Getter private double totalXAngle;
    @Getter private double totalYAngle;
    @Getter @Setter private double fov;
    @Getter private double startFov;
    private double zoomSensitivity;
    private double moveSensitivity;
    private Planet planet;
    private Group root;
    private Group tile;
    private StackPane stackPane;
    private Stage stage;
    @Setter private TrackBallCamera camera;

    public TrackBallCamera(double x, double y, double z, Scene scene, Group root) {
        super(true);
        this.x = x;
        this.y = y;
        this.z = z;
        this.fov = 35;
        startFov = this.fov;
        this.scene = scene;
        this.root = root;
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

    public TrackBallCamera(double x, double y, double z, Scene scene, Group root, Group tile) {
        super(true);
        this.x = x;
        this.y = y;
        this.z = z;
        this.fov = 35;
        startFov = this.fov;
        this.scene = scene;
        this.root = root;
        this.tile = tile;
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

    public void setRoot(Group root) {
        this.root = root;
    }

    public void setTile(Group tile) {
        this.tile = tile;
    }

    public void setStackPane(StackPane stackPane) {
        this.stackPane = stackPane;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
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


                EmpriseCoord emprise = xPosbyFov(x, z, fov, planet.getPlanetRadius(), totalXAngle, totalYAngle);
                WMSImageryProvider wms = new WMSImageryProvider("http://geoservices.brgm.fr/geologie", "SCAN_F_GEOL250");
                try {
                    wms.getMap(emprise);
                }
                catch (IOException e){

                }
                GPSCoord camCoord = new GPSCoord();
                camCoord.setLongitude(-totalXAngle);
                camCoord.setLatitude(-totalYAngle * 1.5);

                System.out.println();
                System.out.println("CAM COORD : " + camCoord.getLongitude() + "  " + camCoord.getLatitude());

                CartographyTextureManager manager = new CartographyTextureManager(planet);

                Box box = manager.bindTexturesFromGPSCoord(scene, camCoord.getLongitude(), camCoord.getLatitude());
                tile.getChildren().add(box);


                System.out.println("######################################################");
                System.out.println(root.getChildren().toString());
                System.out.println("######################################################");

                stage.show();

            }
        };
    }
    private EventHandler <MouseEvent> bindMouseReleasedEvent(){
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                scene.setCursor(javafx.scene.Cursor.DEFAULT);

                EmpriseCoord emprise = xPosbyFov(x, z, fov, planet.getPlanetRadius(), totalXAngle, totalYAngle);
                WMSImageryProvider wms = new WMSImageryProvider("http://geoservices.brgm.fr/geologie", "SCAN_F_GEOL250");
                try {
                    wms.getMap(emprise);
                }
                catch (IOException e){

                }

            }
        };
    }
}