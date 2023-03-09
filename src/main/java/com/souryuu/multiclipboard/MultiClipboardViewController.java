package com.souryuu.multiclipboard;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.converter.NumberStringConverter;

import java.io.File;
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
    private final MultiClipboardDataModel dataModel;

    {
        dataModel = MultiClipboardDataModel.getInstance();
    }

    @FXML
    public void initialize() {
        // Binding Of Variables Values To GUI Elements
        bindVariables();
    }

    private void bindVariables() {
        fieldCurrentSize.textProperty().bindBidirectional(dataModel.getContentSizeProperty(), new NumberStringConverter());
        areaCurrentContent.textProperty().bindBidirectional(dataModel.getCurrentContentTextProperty());
    }

    @FXML
    public void onMenuItemNewClick() {
        Optional<ButtonType> userChoice = showConfirmationDialog("Are you sure?", "New queue creation...", "Creation of new queue will result in clearing all data! Are You sure you want to create new queue?");
        // Verification Of Received Input
        if(userChoice.isPresent()) {
            if (userChoice.get() == ButtonType.OK) {
                dataModel.clearModel();
            } else {
                // TODO: Add Debuging Information After Selecting Cancel Option
            }
        }
    }

    @FXML
    public void onMenuItemReadFromTxtClick() {
        System.out.println("Menu Item \"Read From Txt\" Clicked!");
        Optional<ButtonType> userChoice = showConfirmationDialog("Are you sure?", "Loading New Queue From File...", "Reading new queue from text file will result in overwriting current queue. Are you sure you want to proceed?");
        if(userChoice.isPresent()) {
            if (userChoice.get() == ButtonType.OK) {
                // Setting Of Selectable File Extensions
                String[] extensions = {"txt"};
                String[] extensionsDetails = {"Text Files"};
                // Selection Of File
                File selectedFile = showFileChooser(extensions, extensionsDetails);
                // Reading Selected File Content Into Application Data Model
                dataModel.readQueueFromTxt(selectedFile);
            } else {
                // TODO: Add Debuging Information After Selecting Cancel Option
            }
        }
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

    private File showFileChooser(String[] extensions, String[] extensionsText) {
        // Verification Of Received Parameters
        if(extensions == null || extensionsText == null) {
            throw new IllegalArgumentException("Input Extensions Cannot Be Null!!");
        }
        if(extensions.length != extensionsText.length) {
            throw new IllegalArgumentException("Size Of Input Extensions Arrays Have To Be Same!!");
        }
        // Creation Of File Chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select New Queue File...");
        // Addition Of File Extensions Filters From Parameters
        for(int i = 0; i < extensions.length; i++) {
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(extensionsText[i], "*." + extensions[i]));
        }
        // File Selection
        File selectedFile = fileChooser.showOpenDialog(paneRoot.getScene().getWindow());
        if(selectedFile != null && selectedFile.isFile() && !selectedFile.isDirectory()) {
            return selectedFile;
        } else {
            throw new IllegalArgumentException("Selected File Cannot Be Read!!");
        }
    }
}
