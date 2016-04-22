package com.jack.address;/**
 * Created by Maxime on 21/04/2016.
 */

import java.io.IOException;
import java.util.ResourceBundle;

import com.jack.address.view.SettingsLayoutController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
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

        initRootLayout();

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
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
          //  showSettingsWindow();

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
            loader.setLocation(MainApp.class.getResource("view/SettingsLayout.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the settings stage
            Stage settingsStage = new Stage();
            settingsStage.setTitle(messages.getString("key.settings"));
            settingsStage.initModality(Modality.WINDOW_MODAL);
            settingsStage.initOwner(primaryStage);
            Scene scene = new Scene(page);

            //Set the settings into the controller.
            SettingsLayoutController controller = loader.getController();
            controller.setSettingsStage(settingsStage);

            // Show the window settings and wait util the user closes it
            settingsStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

}