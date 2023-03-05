package com.souryuu.multiclipboard;

import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;

public class MultiClipboardController {

    @FXML
    MenuBar menuBar;

    @FXML
    public void initialize() {

    }

    @FXML
    public void onMenuItemNewClick() {
        System.out.println("Menu Item \"New\" Clicked!");
    }

    @FXML
    public void onMenuItemReadFromTxtClick() {
        System.out.println("Menu Item \"Read From Txt\" Clicked!");
    }

    @FXML
    public void onMenuItemSaveClick() {
        System.out.println("Menu Item \"Save\" Clicked!");
    }

    @FXML
    public void onMenuItemLoadClick() {
        System.out.println("Menu Item \"Load\" Clicked!");
    }

    @FXML
    public void onMenuItemExitClick() {
        System.out.println("Menu Item \"Exit\" Clicked!");
    }

    @FXML
    public void onMenuItemAddClick() {
        System.out.println("Menu Item \"Add\" Clicked!");
    }

    @FXML
    public void onMenuItemStartClick() {
        System.out.println("Menu Item \"Start\" Clicked!");
    }

    @FXML
    public void onMenuItemStopClick() {
        System.out.println("Menu Item \"Stop\" Clicked!");
    }

    @FXML
    public void onMenuItemResetClick() {
        System.out.println("Menu Item \"Reset\" Clicked!");
    }

    @FXML
    public void onMenuRadioExtensionClick() {
        System.out.println("Menu Item \"Save Extension Change\" Clicked!");
    }

    @FXML
    public void onMenuRadioSeparatorClick() {
        System.out.println("Menu Item \"Element Separator Change\" Clicked!");
    }

    @FXML
    public void onBtnAddContentClick() {
        System.out.println("Button \"Add\" Clicked!");
    }

    @FXML
    public void onBtnSaveContentClick() {
        System.out.println("Button \"Save Content\" Clicked!");
    }

    @FXML
    public void onBtnLoadContentClick() {
        System.out.println("Button \"Load Content\" Clicked!");
    }

    @FXML
    public void onBtnStartProcessingClicked() {
        System.out.println("Button \"Start Processing\" Clicked!");
    }

    @FXML
    public void onBtnStopProcessingClicked() {
        System.out.println("Button \"Stop Processing\" Clicked!");
    }

    @FXML
    public void onBtnSetPositionClick() {
        System.out.println("Button \"Set Position\" Clicked!");
    }
}
