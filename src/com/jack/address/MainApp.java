package com.jack.address;/**
 * Created by Maxime on 21/04/2016.
 */

import java.io.IOException;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Main class for loading the Jack app
 */
public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

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
            ResourceBundle messages;
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
}