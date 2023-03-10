package com.souryuu.multiclipboard;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
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
        FXMLLoader loader = new FXMLLoader(MultiClipboardApplication.class.getResource("multiclipboard-view.fxml"));
        // Scene Setup
        Scene appScene = new Scene(loader.load());
        stage.setTitle("MultiClipboard Application V1.0.0!");
        stage.setScene(appScene);
        stage.sizeToScene();
        // Show Application
        stage.show();
    }
}
