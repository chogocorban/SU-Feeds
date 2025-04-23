package com.chogo.sufeeds.sufeeds;

//Corban Chogo, ICS 1.2 Group B, 165558, 13/11/2024

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class SceneSwitch {
    public SceneSwitch(VBox currentVBox, String fxml) throws Exception{
    VBox nextVBox = FXMLLoader.load(Objects.requireNonNull(App.class.getResource(fxml)));
    currentVBox.getChildren().removeAll();
    currentVBox.getChildren().setAll(nextVBox);

    }
}
