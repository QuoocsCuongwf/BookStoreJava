package com.example.demo.spring_controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class test extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/TacGia.fxml"));

        Pane root = fxmlLoader.load(); // Tải giao diện từ FXML

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/asset/css/main.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("TacGia");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}