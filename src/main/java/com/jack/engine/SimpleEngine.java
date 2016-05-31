package com.jack.engine;



import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;



/**
 * Created by Maxime on 18/05/2016.
 */
public class SimpleEngine {

    private static final String DIFFUSE_MAP = "earth_gebco8_texture_8192x4096.jpg";
    private static final String NORMAL_MAP = "earth_normalmap_flat_8192x4096.jpg";
    private static final String SPECULAR_MAP = "earth_specularmap_flat_8192x4096.jpg";

    private static final double MAP_WIDTH = 8192 / 2d;
    private static final double MAP_HEIGHT = 4092 / 2d;
    private double i = 0;

    private double angleZ;
    private double angleY;
    private double motionSensitivity;


    private Camera camera;
    private Sphere earth;

    private Box box;
    private StackPane stackPane;
    private SubScene subScene;
    private PhongMaterial blueMaterial;
    private double lastMouseX;
    private double lastMouseY;
    private double totalXAngle;
    private double totalYAngle;

    public SimpleEngine() {

        angleZ = 0;
        angleY = 0;
        motionSensitivity = 0.003;

        camera = new PerspectiveCamera(true);
        earth = new Sphere(5);

        blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.BLUE);
       // earth.setDrawMode(DrawMode.LINE);
       // blueMaterial.setSpecularColor(Color.LIGHTBLUE);
;


        PhongMaterial earthMaterial = new PhongMaterial();
        earthMaterial.setDiffuseMap(
                new Image(
                        DIFFUSE_MAP,
                        MAP_WIDTH,
                        MAP_HEIGHT,
                        true,
                        true
                )
        );
        earthMaterial.setBumpMap(
                new Image(
                        NORMAL_MAP,
                        MAP_WIDTH,
                        MAP_HEIGHT,
                        true,
                        true
                )
        );
        earthMaterial.setSpecularMap(
                new Image(
                        SPECULAR_MAP,
                        MAP_WIDTH,
                        MAP_HEIGHT,
                        true,
                        true
                )
        );

        earth.setMaterial(
                earthMaterial
        );


    }

    public void setStackPane(StackPane stackPane) {
        this.stackPane = stackPane;
    }

    public SubScene getSubScene() {
        return subScene;
    }

    public Group initScene(){
        Group root = new Group();
        camera.setFarClip(10000);
        camera.setNearClip(0.1);
        camera.getTransforms().addAll(
                new Rotate(0, Rotate.Y_AXIS),
                new Rotate(0, Rotate.X_AXIS),
                new Translate(0, 0, -20));


        root.getChildren().add(camera);
        root.getChildren().add(earth);

        subScene = new SubScene(root, 200, 200);
        subScene.setManaged(false);
        subScene.setFill(Color.WHITE);
        subScene.setCamera(camera);
        Group group = new Group(subScene);

        group.setOnMouseDragged(new EventHandler<MouseEvent>() {
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
                
                earth.getTransforms().clear();
                earth.getTransforms().add(new Rotate(totalYAngle, new Point3D(1, 0, 0)));
                earth.getTransforms().add(new Rotate(totalXAngle, new Point3D(0, 1, 0)));
                lastMouseX = event.getX();
                lastMouseY = event.getY();
            }
        });
        
        group.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lastMouseX = event.getX();
                lastMouseY = event.getY();
            }
        });

        return group;

    }

    public void setSize(){

        subScene.heightProperty().bind(stackPane.heightProperty());
        subScene.widthProperty().bind(stackPane.widthProperty());
    }

    public void handleRotation() {
        
    }

}
