package com.jack.address;/**
 * Created by Maxime on 21/04/2016.
 */

import java.io.IOException;
import java.util.ResourceBundle;

import com.jack.address.controller.*;
import com.jack.configuration.IniManager;
import com.jack.engine.SimpleEngine;
import com.jack.wms.WMSImageryProvider;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Main class for loading the Jack app
 */
public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private ResourceBundle messages;

    /**
     *
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("JACK");

        IniManager ini = new IniManager();

        initRootLayout();

        WMSImageryProvider wms = new WMSImageryProvider("http://geoservices.brgm.fr/geologie", "SCAN_F_GEOL250");
        try {
            wms.getMap();
        }
        catch (IOException e){

        }
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load ResourceBundle for i18n

            messages = ResourceBundle.getBundle("WindowBundle");
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(messages);
            loader.setLocation(MainApp.class.getResource("/view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();


            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            SimpleEngine se = new SimpleEngine(scene);
            StackPane stackPane = new StackPane(se.initScene());
            se.setStackPane(stackPane);
            se.setSize();
            rootLayout.setCenter(stackPane);

            primaryStage.setScene(scene);
            primaryStage.show();
            //earthViewer.rotateAroundYAxis(stackPane).play();

            // Get the controller for the menu
            MenuBarController menuBarController = loader.getController();
            menuBarController.setMainApp(this);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main stage.
     * @return Returns the stage
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public boolean showSettingsWindow(){
        try {
            // Load fxml file and create anew stage for the window setting
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(messages);
            loader.setLocation(MainApp.class.getResource("/view/SettingsLayout.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage appSettingStage = new Stage();
            appSettingStage.setTitle(messages.getString("key.settings"));
            appSettingStage.initModality(Modality.WINDOW_MODAL);
            appSettingStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            appSettingStage.setScene(scene);

            SettingsLayoutController controller = loader.getController();
            controller.setSettingsStage(appSettingStage);

            appSettingStage.showAndWait();


            return controller.isOkClicked();
        } catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Load and display the About window
     * @return
     */
    public boolean showAboutWindow(){
        try{
            // Load FXML file
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(messages);
            loader.setLocation(MainApp.class.getResource("/view/AboutLayout.fxml"));
            AnchorPane page = loader.load();

            // Creating the Stage
            Stage aboutStage = new Stage();
            aboutStage.initModality(Modality.WINDOW_MODAL);
            aboutStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            aboutStage.setScene(scene);

            AboutController aboutController = loader.getController();
            aboutController.setAboutStage(aboutStage);
            aboutStage.showAndWait();

            return aboutController.isClicked();
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean showImageViewerWindow(){
        try{
            // Load XML File
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(messages);
            loader.setLocation(MainApp.class.getResource("/view/ImageViewer.fxml"));
            AnchorPane pane = loader.load();

            // Creating the Stage
            Stage imageStage = new Stage();
            imageStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(pane);
            imageStage.setScene(scene);

            ImageViewerController imageViewerController = loader.getController();
            imageViewerController.setImageStage(imageStage);
            imageStage.showAndWait();

            return imageViewerController.isClicked();
        } catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean showCapabilitiesWindow(){
        try{
            // Load XML File
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(messages);
            loader.setLocation(MainApp.class.getResource("/view/GetCapabilitiesLayout.fxml"));
            AnchorPane page = loader.load();

            // Create the Stage
            Scene scene = new Scene(page);
            Stage stage = new Stage();
            stage.initOwner(primaryStage);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setTitle(messages.getString("key.getCapabilities"));
            stage.setScene(scene);

            System.out.println(stage.toString());

            GetCapabilitiesController controller = loader.getController();
            controller.setStage(stage);
            stage.showAndWait();


            return controller.isClick();
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    public void cancelRequest(){
        Platform.exit();
   }

}
