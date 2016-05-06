package com.jack.address.controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sun.security.tools.policytool.Resources;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Maxime on 06/05/2016.
 */
public class GetCapabilitiesWindow {

    private Stage stage;
    private Scene scene;
    private ResourceBundle rb;
    private AnchorPane anchorPane;
    private TextField textUrl;

    public GetCapabilitiesWindow(){
        stage = new Stage();
        rb = ResourceBundle.getBundle("WindowBundle");
        stage.initModality(Modality.WINDOW_MODAL);

    }

    public void initView(){

        stage.setTitle("GetCapabilities");

        Button submit = new Button(rb.getString("key.submit"));
        Button cancel = new Button(rb.getString("key.cancel"));
        HBox buttonBox = new HBox(submit, cancel);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(12);
        textUrl  = new TextField();
        textUrl.appendText("Enter url");
        VBox vBox = new VBox(textUrl, buttonBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(14);

        submit.setOnAction(event);

        anchorPane = new AnchorPane(vBox);
        scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.showAndWait();
    }

    private void submitHandler() throws IOException{
        URL url = new URL(textUrl.getText());
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();

        InputStream xml = connection.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(xml));
        String sLine;
        String sXml = "";

        while((sLine = reader.readLine()) != null){
            sXml += sLine + '\n';
        }
        System.out.print(sXml);
        connection.disconnect();

    }

    EventHandler event = new EventHandler() {
        @Override
        public void handle(Event event) {
            try {

                submitHandler();
            } catch (IOException e) {

            }
        }
    };

}
