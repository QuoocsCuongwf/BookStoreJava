package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class test extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/KhachHang.fxml"));

        AnchorPane root = fxmlLoader.load(); // Tải giao diện từ FXML

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("KhachHang");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
