package com.example.demo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javax.swing.*;

public class GuiController {
        @FXML
        private TextField username;

        @FXML
        private PasswordField password;

        @FXML
        private Button loginButton;

        @FXML
        public void initialize() {
        // Setting an action for the login button
        loginButton.setOnAction(event -> handleLogin());
    }

        private void handleLogin() {
        String usernameInput = username.getText();
        String passwordInput = password.getText();

        // For now, just print the input to the console
        System.out.println("Username: " + usernameInput);
        System.out.println("Password: " + passwordInput);

        // You can add your authentication logic here
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public TextField getUsername() {
        System.out.println(username.getText());
        return username;
    }
}
