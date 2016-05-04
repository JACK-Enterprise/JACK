package com.jack.address;/**
 * Created by Maxime on 21/04/2016.
 */

import java.io.IOException;
import java.util.ResourceBundle;

import com.jack.address.controller.AboutController;
import com.jack.address.controller.MenuBarController;
import com.jack.address.controller.SettingsLayoutController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import com.jack.wms.*;

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

        initRootLayout();

        WMSImageryProvider wms = new WMSImageryProvider("http://www.geosignal.org/cgi-bin/wmsmap", "layer");
        try {
            wms.GetCapabilities();
        } catch (IOException e) {

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
            primaryStage.setScene(scene);
            primaryStage.show();

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

    public void cancelRequest(){
        Platform.exit();
   }

}