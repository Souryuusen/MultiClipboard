package com.souryuu.multiclipboard;

import com.souryuu.multiclipboard.entity.ClipboardContent;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MultiClipboardApplication extends Application {

    private static MultiClipboardApplication instance;

    private ObservableList<ClipboardContent> contentList;

    protected static SimpleIntegerProperty currentIndex;

    public static void main(String[] args) {
        launch();
    }

    public static MultiClipboardApplication getInstance() {
        if(instance == null) {
            instance = new MultiClipboardApplication();
        }
        return instance;
    }

    public ObservableList<ClipboardContent> getContentList() {
        return contentList;
    }

    public void setContentList(ObservableList<ClipboardContent> contentList) {
        this.contentList = contentList;
    }

    public ClipboardContent getCurrentContent() {
        return getContentList().get(currentIndex.get());
    }

    protected SimpleIntegerProperty getCurrentIndex() {
        return this.currentIndex;
    }

    protected void increaseIndex() {
        this.currentIndex.set(getCurrentIndex().get()+1);
        System.out.println(getCurrentIndex().get());
    }

    @Override
    public void start(Stage stage) throws IOException {
        currentIndex = new SimpleIntegerProperty(0);
        // Loading FXML
        FXMLLoader loader = new FXMLLoader(MultiClipboardApplication.class.getResource("multiclipboard-view.fxml"));
        // Scene Setup
        Scene appScene = new Scene(loader.load());
        stage.setTitle("Hello!");
        stage.setScene(appScene);
        stage.sizeToScene();
        // Create Bindings

        // Show Application
        stage.show();
    }
}
