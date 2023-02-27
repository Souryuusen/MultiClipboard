package com.souryuu.multiclipboard;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MultiClipboardApplication extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        // Loading FXML
        FXMLLoader loader = new FXMLLoader(MultiClipboardApplication.class.getResource("multiclipboard-view2.fxml"));
        // Scene Setup
        Scene appScene = new Scene(loader.load());
        stage.setTitle("Hello!");
        stage.setScene(appScene);
        stage.sizeToScene();
        stage.show();
    }
}
