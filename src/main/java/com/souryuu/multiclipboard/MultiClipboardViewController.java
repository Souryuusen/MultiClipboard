package com.souryuu.multiclipboard;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.converter.NumberStringConverter;

import java.util.Optional;

public class MultiClipboardViewController {
    // Root Element
    @FXML AnchorPane paneRoot;
    // Menu Bar Variables Declaration
    @FXML MenuBar menuBar;
    @FXML Menu menuFile, menuEdit, menuOptions;
    @FXML MenuItem menuItemNew, menuItemReadFromTxt, menuItemSave, menuItemLoad, menuItemExit;
    @FXML MenuItem menuItemAdd, menuItemStart, menuItemStop, menuItemReset;
    @FXML Menu menuFileExtensions, menuElementSeparator;
    @FXML RadioMenuItem menuRadioSaveAsTxt, menuRadioSaveAsDat, menuRadioSeparatorDefault, menuRadioSeparatorCustom;

    // Control Section Area Variable Declaration
    @FXML VBox paneControlButtonVBox;
    @FXML Button btnAddContent, btnSaveContent, btnLoadContent, btnStartProcessing, btnStopProcessing, btnSetPosition;
    @FXML AnchorPane paneControlFieldAnchorPane, paneControlCurrentSizeAnchorPane;
    @FXML TextField fieldPosition, fieldCurrentSize;
    @FXML Label labelCurrentSize;

    // Current Content Area Variables Declaration
    @FXML TextArea areaCurrentContent;

    // Variable Initialization
    private MultiClipboardDataModel dataModel;

    {
        dataModel = MultiClipboardDataModel.getInstance();
    }

    @FXML
    public void initialize() {
        // Binding Of Variables Values To GUI Elements
        fieldCurrentSize.textProperty().bindBidirectional(dataModel.getContentSizeProperty(), new NumberStringConverter());
        areaCurrentContent.textProperty().bindBidirectional(dataModel.getCurrentContentTextProperty());
    }

    @FXML
    public void onMenuItemNewClick() {
        Optional<ButtonType> userChoice = showConfirmationDialog("Are you sure?", "New queue creation...", "Creation of new queue will result in clearing all data! Are You sure you want to create new queue?");
        if(userChoice.get() == ButtonType.OK) {
            dataModel.clearModel();
        } else {
            // TODO: Add Debuging Information After Selecting Cancel Option
        }
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
        dataModel.addNewContentToQueue();
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
        dataModel.addNewContentToQueue();
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

    private Optional<ButtonType> showConfirmationDialog(String title, String header, String text) {
        // Creation Of New Alert Pop-Up
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);
        // Show Pop-Up
        Optional<ButtonType> result = alert.showAndWait();

        return result;
    }
}
