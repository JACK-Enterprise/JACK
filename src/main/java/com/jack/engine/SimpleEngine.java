package com.jack.engine;



import com.jack.engine.camera.TrackBallCamera;
import com.jack.engine.geometry.SphereCoordinates;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.LightBase;
import javafx.scene.PointLight;
import static javafx.scene.SceneAntialiasing.BALANCED;
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

    private static final String DIFFUSE_MAP = "XTREM.jpg";
    private static final String NORMAL_MAP = "earth_normalmap_flat_8192x4096.jpg";
    private static final String SPECULAR_MAP = "earth_specularmap_flat_8192x4096.jpg";
    
    private static final double MAP_WIDTH = 21600;
    private static final double MAP_HEIGHT = 10800;
    private double i = 0;

    private double angleZ;
    private double angleY;
    private double motionSensitivity;

    private TrackBallCamera camera;
    private Planet earth;
    private Skybox skybox;
    private LightBase sunLight;
    private LightBase ambientLight;
    private GPSCoord gpsCoord = new GPSCoord(2.333333, 48.866667);
    private GPSCoord gpsCoord2 = new GPSCoord(-74.00, 40.43);
    private SphereCoordinates coords = new SphereCoordinates(gpsCoord);
    private SphereCoordinates coords2 = new SphereCoordinates(gpsCoord2);
    
    private Box box;
    private StackPane stackPane;
    private SubScene subScene;
    private double lastMouseX;
    private double lastMouseY;
    private double totalXAngle;
    private double totalYAngle;
    
    private Pos3D pos;
    private Pos3D pos2;
    private double dist;
    private Sphere sphere = new Sphere(0.01);

    public SimpleEngine() {

        
        angleZ = 0;
        angleY = 0;
        motionSensitivity = 0.003;

        camera = new TrackBallCamera(0, 0, -30);
        skybox = setSkybox();
        earth = new Planet(DIFFUSE_MAP, SPECULAR_MAP, NORMAL_MAP, 6.371);

        // Set lights
        sunLight = new PointLight();
        ambientLight = new AmbientLight();
        
        sunLight.setColor(Color.color(0.8, 0.8, 0.8, 0.1));
        sunLight.setTranslateX(-30);
        sunLight.setTranslateZ(-30);
        
        ambientLight.setColor(Color.color(0.4, 0.4, 0.4));
        ambientLight.setTranslateX(30);
        ambientLight.setTranslateZ(30);

        earth.init();
        
        pos = coords.get3DPosUsingDegrees(earth.getPlanetRadius());
        pos2 = coords2.get3DPosUsingDegrees(earth.getPlanetRadius());
        dist = gpsCoord.getSphericalDistance(gpsCoord2, earth.getPlanetRadius()) * 1000;
        System.out.println("Pos is : {" + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + "}");
        System.out.println("Pos is : {" + pos2.getX() + ", " + pos2.getY() + ", " + pos2.getZ() + "}");
        System.out.println("Distance is : {" + dist + "}");
    
        sphere.setTranslateX(pos.getX());
        sphere.setTranslateY(pos.getY());
        sphere.setTranslateZ(-pos.getZ());  
    }

    public void setStackPane(StackPane stackPane) {
        this.stackPane = stackPane;
    }

    public SubScene getSubScene() {
        return subScene;
    }

    public Group initScene(){
        Group root = new Group();
        root.getChildren().add(skybox);
        root.getChildren().add(sunLight);
        root.getChildren().add(ambientLight);
        earth.addToContainer(root);
        root.getChildren().add(sphere);

        subScene = new SubScene(root, 200, 200, true, BALANCED);
        subScene.setManaged(true);
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
