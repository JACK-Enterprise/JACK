/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jack.engine.camera;

import com.jack.annotations.RenderGPS;
import com.jack.configuration.IniManager;
import com.jack.engine.CartographyTextureManager;
import com.jack.engine.EmpriseCoord;
import com.jack.engine.GPSCoord;
import com.jack.engine.Planet;
import static  com.jack.core.JackMath.*;
import static com.jack.core.StdDevLib.*;
import com.jack.engine.Pos3D;
import com.jack.plugins.manager.Plugin;
import com.jack.wms.WMSImageryProvider;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseButton;
import javafx.scene.transform.Transform;

/**
 *
 * @author Aurelien
 */
public class TrackBallCamera extends PerspectiveCamera {
    
    @Getter @Setter private double x;
    @Getter @Setter private double y;
    @Getter @Setter private double z;
    private final ArrayList<Plugin> plugins;
    private double zInit;
    private Scene scene;
    private double lastMouseX;
    private double lastMouseY;
    @Getter private double totalXAngle;
    @Getter private double totalYAngle;
    @Getter @Setter private double fov;
    @Getter private final double startFov;
    private final double zoomSensitivity;
    private double moveSensitivity;
    private Planet planet;
    private Group root;
    private Group tile;
    private StackPane stackPane;
    private Stage stage;
    private final WMSImageryProvider wms;
    @Setter private TrackBallCamera camera;
    private CartographyTextureManager manager;

    public TrackBallCamera(double x, double y, double z, Scene scene, Group root, ArrayList<Plugin> plugins) {
        super(true);
        this.x = x;
        this.y = y;
        this.z = z;
        this.zInit = z;
        this.fov = 35;
        startFov = this.fov;
        this.scene = scene;
        this.root = root;
        moveSensitivity = 0.4;
        zoomSensitivity = 0.003;
        IniManager iniManager = new IniManager();
        String url = iniManager.getStringValue("imagery", "server");
        String layer = iniManager.getStringValue("imagery", "layers");
        wms = new WMSImageryProvider(url, layer);
        this.plugins = plugins;
        
        setFieldOfView(fov);
        setFarClip(10000);
        setNearClip(0.1);
        getTransforms().addAll(
                new Rotate(0, Rotate.Y_AXIS),
                new Rotate(0, Rotate.X_AXIS),
                new Translate(x, y, z));

    }

    public TrackBallCamera(double x, double y, double z, Scene scene, Group root, Group tile, ArrayList<Plugin> plugins) {
        super(true);
        this.x = x;
        this.y = y;
        this.z = z;
        this.fov = 35;
        startFov = this.fov;
        this.scene = scene;
        this.root = root;
        this.tile = tile;
        this.plugins = plugins;
        moveSensitivity = 0.4;
        zoomSensitivity = 0.003;
        IniManager iniManager = new IniManager();
        String url = iniManager.getStringValue("imagery", "server");
        String layer = iniManager.getStringValue("imagery", "layers");
        wms = new WMSImageryProvider(url, layer);

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
        manager = new CartographyTextureManager(planet);
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
        return (MouseEvent event) -> {
            if(event.getButton() != MouseButton.PRIMARY)
            {
                return;
            }
            scene.setCursor(javafx.scene.Cursor.CLOSED_HAND);
            double mouseX = event.getScreenX();
            double mouseY = event.getScreenY();
            double xrel = -(lastMouseX - mouseX) * moveSensitivity;
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
            
            if(totalXAngle >= 180)
            {
                totalXAngle -= 360;
            }
            else if(totalXAngle <= -180)
            {
                totalXAngle += 360;
            }
            ObservableList<Transform> transforms = TrackBallCamera.this.getTransforms();
            transforms.clear();
            transforms.add(new Rotate(totalXAngle, new Point3D(0, 1, 0)));
            transforms.add(new Rotate(totalYAngle, new Point3D(1, 0, 0)));
            transforms.add(new Translate(x, y, z));
            lastMouseX = mouseX;
            lastMouseY = mouseY;
            updateTiles();
        };
    }
    
    private EventHandler <MouseEvent> bindMousePressedEvent() {
       
        return (MouseEvent event) -> {
            double mx = event.getScreenX();
            double my = event.getScreenY();
            if(event.getButton() == MouseButton.PRIMARY)
            {
                scene.setCursor(javafx.scene.Cursor.CLOSED_HAND);
                lastMouseX = mx;
                lastMouseY = my;
            }
            
            else if(event.getButton() == MouseButton.SECONDARY)
            {
                planet.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if(event.getButton() != MouseButton.SECONDARY)
                        {
                            return;
                        }
                        double xTmp = event.getX();
                        double yTmp = event.getY();
                        double zTmp = event.getZ();
                        GPSCoord coord = new Pos3D(-xTmp, yTmp, zTmp).toGPSCoord();
                        GPSCoord coordCam = new Pos3D(x, y, z).toGPSCoord();
                        
                        coord.setLongitude(-coordCam.getLongitude() + coord.getLongitude());
                        coord.setLatitude(-coordCam.getLatitude() + coord.getLatitude());
                        
                        plugins.stream().filter((plugin) -> (plugin.getPlugin().getClass().getAnnotation(RenderGPS.class) != null)).forEach((plugin) -> {
                            plugin.run(coord, root, planet.getRadius());
                        });
                    }
                });
            }
        };
    }
    private EventHandler <ScrollEvent> bindScrollMouseEvent(){
        return (ScrollEvent event) -> {
            fov -= fov * event.getDeltaY()*zoomSensitivity;
            
            if(fov > 35){
                fov = 35;
            }
            
            if(fov < 0.1){
                fov = 0.1;
            }
            
            setFieldOfView(fov);
            moveSensitivity -= moveSensitivity * event.getDeltaY()*zoomSensitivity;
            
            if(moveSensitivity > 0.4)
            {
                moveSensitivity = 0.4;
            }
            else if(moveSensitivity < 0.001)
            {
                moveSensitivity = 0.001;
            }
            updateTiles();
        };
    }
    
    private void updateTiles()
    {
        EmpriseCoord emprise = xPosbyFov(x, zInit, fov, planet.getPlanetRadius(), totalXAngle, totalYAngle);

        wms.getTiledMap(emprise, fov, planet.getPlanetRadius());

        GPSCoord camCoord = new GPSCoord();
        camCoord.setLongitude(-totalXAngle);
        camCoord.setLatitude(-totalYAngle * 1.5);

        writeCameraOnFile();

        manager.bindTexturesFromGPSCoord(scene, camCoord.getLongitude(), camCoord.getLatitude(), emprise, fov, tile);

        stage.show();

        readTBCFile("./cartography/camera.tbc");
                
    }

    private void writeCameraOnFile(){
        double[] data = {-totalXAngle, -totalYAngle * 1.5};
        String folderPath = "./cartography";
        String filePath = "/camera.tbc";

        File camFile = new File(folderPath + filePath);

        if (!new File(folderPath).exists()) {
            new File(folderPath).mkdirs();
        }

        try (DataOutputStream writer = new DataOutputStream(new FileOutputStream(camFile))) {
            writer.writeInt(data.length);
            for (double d : data) {
                writer.writeDouble(d);
            }
        } catch (IOException ex) {
            Logger.getLogger(TrackBallCamera.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    private EventHandler <MouseEvent> bindMouseReleasedEvent(){
        return (MouseEvent event) -> {
            scene.setCursor(javafx.scene.Cursor.DEFAULT);
        };
    }
}