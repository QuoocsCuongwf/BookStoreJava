package com.example.demo.model;

import javafx.scene.control.Button;
import lombok.Setter;
import lombok.Getter;

@Getter
@Setter

public class ButtonStorage {
    private Button button = new Button();
    private String linkFXML="";

    public ButtonStorage(Button button, String linkFXML) {
        this.button = button;
        this.linkFXML = linkFXML;
    }
    public void showButtonStorage() {
        System.out.println(button);
        System.out.println(linkFXML);
    }
}
