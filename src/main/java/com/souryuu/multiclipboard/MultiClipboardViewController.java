package com.souryuu.multiclipboard;

import com.souryuu.multiclipboard.entity.ClipboardContent;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.converter.NumberStringConverter;

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
    MultiClipboardViewController instance;
    MultiClipboardApplication application;

    public MultiClipboardViewController() {

    }

    public MultiClipboardViewController getInstance() {
        if(instance == null) {
            instance = this;
        }
        return instance;
    }

    @FXML
    public void initialize() {
        // Binding Of Variables Values To GUI Elements
//        ClipboardContent currentContent = MultiClipboardApplication.getInstance().getCurrentContent();
        fieldPosition.textProperty().bindBidirectional(MultiClipboardApplication.getInstance().getCurrentIndex(), new NumberStringConverter());
    }

    @FXML
    public void onMenuItemNewClick() {
        System.out.println("Menu Item \"New\" Clicked!");
        MultiClipboardApplication.getInstance().increaseIndex();
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
        MultiClipboardApplication.getInstance().increaseIndex();
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
