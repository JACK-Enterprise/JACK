package com.jack.engine;



import com.jack.engine.camera.TrackBallCamera;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.LightBase;
import javafx.scene.PointLight;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Sphere;



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


    private TrackBallCamera camera;
    private Sphere earth;
    private Skybox skybox;
    private LightBase sunLight;
    private LightBase ambientLight;
    private Sphere blueAtmosphere;
    private Sphere whiteAtmosphere;

    private PhongMaterial blueMaterial;
    private PhongMaterial whiteMaterial;

    private Box box;
    private StackPane stackPane;
    private SubScene subScene;
    private double lastMouseX;
    private double lastMouseY;
    private double totalXAngle;
    private double totalYAngle;

    public SimpleEngine() {

        angleZ = 0;
        angleY = 0;
        motionSensitivity = 0.003;

        camera = new TrackBallCamera(0, 0, -20);
        skybox = setSkybox();
        earth = new Sphere(5, 120);

        // Set lights
        sunLight = new PointLight();
        ambientLight = new AmbientLight();
        
        sunLight.setColor(Color.color(0.8, 0.8, 0.8, 0.1));
        sunLight.setTranslateX(-20);
        sunLight.setTranslateZ(-20);
        
        ambientLight.setColor(Color.color(0.4, 0.4, 0.4));
        ambientLight.setTranslateX(20);
        ambientLight.setTranslateZ(20);

        // Set Material
        blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.color(0, 0.2, 0.5, 0.2));

        whiteMaterial = new PhongMaterial();
        whiteMaterial.setDiffuseColor(Color.color(1, 1, 1, 0.2));

        // Set Atmospheres
        blueAtmosphere = new Sphere(5.1, 200);
        blueAtmosphere.setDrawMode(DrawMode.FILL);
        blueAtmosphere.setMaterial(blueMaterial);

        whiteAtmosphere = new Sphere(5.05, 200);
        whiteAtmosphere.setDrawMode(DrawMode.FILL);
        whiteAtmosphere.setMaterial(whiteMaterial);

        // Set Earth
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

        root.getChildren().add(camera);
        root.getChildren().add(skybox);
        root.getChildren().add(blueAtmosphere);
        root.getChildren().add(whiteAtmosphere);
        root.getChildren().add(earth);
        root.getChildren().add(sunLight);
        root.getChildren().add(ambientLight);


        subScene = new SubScene(root, 200, 200);
        subScene.setManaged(false);
        subScene.setFill(Color.WHITE);
        subScene.setCamera(camera);
        Group group = new Group(subScene);


        camera.bindOn(group);

        return group;

    }

    public void setSize(){

        subScene.heightProperty().bind(stackPane.heightProperty());
        subScene.widthProperty().bind(stackPane.widthProperty());
    }

    private Skybox setSkybox(){

        Image top = new Image("SkyBox/Cesium_ny.jpg");
        Image front = new Image("SkyBox/Cesium_pz.jpg");
        Image left = new Image("SkyBox/Cesium_nx.jpg");
        Image bottom = new Image("SkyBox/Cesium_py.jpg");
        Image back = new Image("SkyBox/Cesium_nz.jpg");
        Image right = new Image("SkyBox/Cesium_px.jpg");

        return new Skybox(top, bottom, left, right, front, back, 100, camera);

    }



}
