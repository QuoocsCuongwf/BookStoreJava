package com.example.demo.GuiController;

import com.example.demo.model.ButtonStorage;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LeftMenuController {
    static List<ButtonStorage> buttonStorageList = new ArrayList<>();

    public LeftMenuController() {
    }
    public void addButtonStorageList(Button button , String linkFXML) {
        buttonStorageList.add(new ButtonStorage(button, linkFXML));
    }
    public void linkButtonStorageToFxml(Button button) {

        buttonStorageList.stream().filter(buttonStorage -> buttonStorage.getButton().equals(button))
                .findFirst().ifPresent(buttonStorage -> {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(buttonStorage.getLinkFXML()));
                        if (button.getScene() != null) {
                            button.getScene().setRoot(fxmlLoader.load());
                        }

                } catch (IOException e) {
                        e.printStackTrace();
                    }
        });
    }
}
