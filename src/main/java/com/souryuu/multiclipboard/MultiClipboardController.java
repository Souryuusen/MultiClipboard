package com.souryuu.multiclipboard;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MultiClipboardController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
