package com.jack.engine;



import com.jack.annotationparser.AnnotationFunctionBinder;
import com.jack.engine.camera.TrackBallCamera;
import com.jack.plugins.manager.Plugin;
import com.jack.plugins.manager.PluginLoader;
import java.io.File;
import java.util.ArrayList;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.LightBase;
import javafx.scene.PointLight;
import static javafx.scene.SceneAntialiasing.BALANCED;
import javafx.scene.SubScene;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.stage.Stage;

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
    
    private Box box;
    private StackPane stackPane;
    private SubScene subScene;
    private double lastMouseX;
    private double lastMouseY;
    private double totalXAngle;
    private double totalYAngle;
    
    private Pos3D pos;
    private Pos3D pos2;
    private Pos3D pos3;
    private double dist;
    private CartographyTextureManager manager;
    private Scene scene;
    private Group root;
    private Group tile;
    private Stage stage;
    private ArrayList<Plugin> plugins;

    public SimpleEngine(Scene scene) {

        this.scene = scene;
        angleZ = 0;
        angleY = 0;
        motionSensitivity = 0.003;

        plugins = new ArrayList<Plugin>();        
        ArrayList<Plugin> tmpPlugins;
        File folder = new File("plugins");        
        
        if(folder.exists()) {
            File[] files = folder.listFiles();
            if(files!=null) {
                for(File f: files) {
                    tmpPlugins = (new PluginLoader(f.getPath(), new ArrayList<AnnotationFunctionBinder>())).load();
                    if(tmpPlugins != null)
                    {
                        plugins.addAll(tmpPlugins);
                    }
                }
            }
        }
        camera = new TrackBallCamera(0, 0, -30, scene, root, tile, plugins);

        
        skybox = setSkybox();
        earth = new Planet(DIFFUSE_MAP, SPECULAR_MAP, NORMAL_MAP, 6.371);
        manager = new CartographyTextureManager(earth);
        // Set lights
        sunLight = new PointLight();
        ambientLight = new AmbientLight();
        
        sunLight.setColor(Color.color(0.8, 0.8, 0.8, 0.1));
        sunLight.setTranslateX(-30);
        sunLight.setTranslateZ(-30);
        
        ambientLight.setColor(Color.color(0.4, 0.4, 0.4));
        ambientLight.setTranslateX(30);
        ambientLight.setTranslateZ(-30);

        earth.initWithoutBumpMap();
        camera.setPlanet(earth);
    }

    public void setStackPane(StackPane stackPane) {
        this.stackPane = stackPane;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public SubScene getSubScene() {
        return subScene;
    }

    public Group initScene(){
        root = new Group();
        tile = new Group();
        Group markerGroup = new Group();

        root.getChildren().add(tile);
        root.getChildren().add(skybox);
        root.getChildren().add(sunLight);
        root.getChildren().add(ambientLight);

        earth.addToContainer(root);

        root.getChildren().add(markerGroup);

        camera.setRoot(root);

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

    public void initCameraConfig(){
        camera.setStackPane(stackPane);
        camera.setCamera(camera);
        camera.setStage(stage);
        camera.setTile(tile);

    }


    private Box createPlane(String texturePath, Pos3D pos, double w, double h){
        Box box = new Box(0.1, h, w);
        PhongMaterial material = new PhongMaterial();

//        System.out.println("-------------- CUBE POS ---------------");
//        System.out.println("Cube Coord in 3D Pos :");
//        System.out.println("    -X : " + pos.getX());
//        System.out.println("    -Y : " + pos.getY());
//        System.out.println("    -Z : " + pos.getZ());
//        System.out.println();

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
