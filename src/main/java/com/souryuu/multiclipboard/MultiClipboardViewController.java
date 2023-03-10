package com.souryuu.multiclipboard;

import com.souryuu.multiclipboard.entity.ContentExtension;
import com.souryuu.multiclipboard.exception.FileCreationException;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import javafx.scene.control.Alert.AlertType;

import java.io.File;
import java.io.IOException;
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

    // Block Initializer
    {
        dataModel = MultiClipboardDataModel.getInstance();
    }

    @FXML
    public void initialize() {
        // Binding Of Variables Values To GUI Elements
        bindVariables();
        bindListeners();
    }

    private void bindVariables() {
        fieldCurrentSize.textProperty().bindBidirectional(dataModel.getContentSizeProperty(), new NumberStringConverter());
        areaCurrentContent.textProperty().bindBidirectional(dataModel.getCurrentContentTextProperty());
        btnStartProcessing.disableProperty().bind(dataModel.getProcessingStartedProperty());
        btnStopProcessing.disableProperty().bind(Bindings.not(dataModel.getProcessingStartedProperty()));
    }

    private void bindListeners() {
        fieldPosition.textProperty().addListener((observable, oldValue, newValue) -> {
            fieldPosition.setText(newValue.replaceAll("[^\\d]", ""));
        });
    }

    @FXML
    public void onMenuItemNewClick() {
        Optional<ButtonType> userChoice = showDialog(AlertType.CONFIRMATION, "Are you sure?", "New queue creation...", "Creation of new queue will result in clearing all data! Are You sure you want to create new queue?");
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
        // Display Confirmation Dialog
        Optional<ButtonType> userChoice = showDialog(AlertType.CONFIRMATION, "Are you sure?", "Loading New Queue From File...", "Reading new queue from text file will result in overwriting current queue. Are you sure you want to proceed?");
        // On User Accept - Load Queue From File
        if(userChoice.isPresent()) {
            if (userChoice.get() == ButtonType.OK) {
                // Setting Of Selectable File Extensions
                String[] extensions = {"txt"};
                String[] extensionsDetails = {"Text Files"};
                // Selection Of File
                File selectedFile = showFileChooser(extensions, extensionsDetails, false);
                // Reading Selected File Content Into Application Data Model
                dataModel.readQueue(selectedFile, ContentExtension.TXT);
            } else {
                // TODO: Add Debuging Information After Selecting Cancel Option
            }
        }
    }

    @FXML
    public void onMenuItemSaveClick() {
        guiSaveAction();
    }

    @FXML
    public void onMenuItemLoadClick() {
        guiLoadAction();
    }

    @FXML
    public void onMenuItemExitClick() {
        // Clearing Of Data Model
        dataModel.clearModel();
        // Closing Of Application
        Stage stage = (Stage) paneRoot.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onMenuItemAddClick() {
        dataModel.addNewContentToQueue();
    }

    @FXML
    public void onMenuItemStartClick() {
        guiStartAction();
    }

    @FXML
    public void onMenuItemStopClick() {
        guiStopAction();
    }

    @FXML
    public void onMenuItemResetClick() {
        dataModel.clearModel();
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
        guiSaveAction();
    }

    @FXML
    public void onBtnLoadContentClick() {
        guiLoadAction();
    }

    @FXML
    public void onBtnStartProcessingClicked() {
        guiStartAction();
    }

    @FXML
    public void onBtnStopProcessingClicked() {
        guiStopAction();
    }

    @FXML
    public void onBtnSetPositionClick() {
        if(!fieldPosition.getText().isEmpty()) {
            dataModel.setIndexValue(Integer.parseInt(fieldPosition.getText().trim())-1);
        }
    }

    private Optional<ButtonType> showDialog(AlertType type, String title, String header, String text) {
        // Creation Of New Alert Pop-Up
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);
        // Show Pop-Up
        Optional<ButtonType> result = alert.showAndWait();
        // Return User Selection
        return result;
    }

    private File showFileChooser(String[] extensions, String[] extensionsText, boolean saveDialog) {
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
        File selectedFile;
        // Dialog Type Selection
        if(!saveDialog) {
            selectedFile = fileChooser.showOpenDialog(paneRoot.getScene().getWindow());
        } else {
            selectedFile = fileChooser.showSaveDialog(paneRoot.getScene().getWindow());
            if(selectedFile != null && !selectedFile.exists()) {
                try {
                    selectedFile.createNewFile();
                } catch (IOException e) {
                    throw new FileCreationException("Error During Creating File " + selectedFile.getPath(), e);
                }
            }
        }
        if(selectedFile != null && selectedFile.isFile() && !selectedFile.isDirectory()) {
            return selectedFile;
        } else {
            throw new IllegalArgumentException("Selected File Cannot Be Read!!");
        }
    }

    private void guiSaveAction() {
        // Setting Of Selectable File Extensions
        String[] extensions;
        String[] extensionsDetails;
        if(menuRadioSaveAsDat.isSelected()) {
            extensions = new String[]{"dat"};
            extensionsDetails = new String[]{"Data Files"};
        } else {
            extensions = new String[]{"txt"};
            extensionsDetails = new String[]{"Text Files"};
        }
        // Selection Of File
        File selectedFile = showFileChooser(extensions, extensionsDetails, true);
        // Reading Selected File Content Into Application Data Model
        boolean result = false;
        if(menuRadioSaveAsDat.isSelected()) {
            result = dataModel.saveQueue(selectedFile, ContentExtension.DAT);
        } else {
            result = dataModel.saveQueue(selectedFile, ContentExtension.TXT);
        }
        // Showing Dialog Based On Result
        if(result) {
            showDialog(AlertType.INFORMATION, "File Saved!", "File Saved!", "Queue Saved Successfully To Txt File!");
        } else {
            showDialog(AlertType.ERROR, "Error Detected!", "Error During File Saving", "Current Queue Has Not Been Saved Due To Error!");
        }
    }

    private void guiLoadAction() {
        Optional<ButtonType> userChoice = showDialog(AlertType.CONFIRMATION, "Are you sure?", "Loading New Queue From File...", "Reading new queue from file will result in overwriting current queue. Are you sure you want to proceed?");
        if(userChoice.isPresent()) {
            if (userChoice.get() == ButtonType.OK) {
                // Setting Of Selectable File Extensions
                String[] extensions = {"txt", "dat"};
                String[] extensionsDetails = {"Text Files", "Data Files"};
                // Selection Of File
                File selectedFile = showFileChooser(extensions, extensionsDetails, false);
                // Reading Selected File Content Into Application Data Model
                if(selectedFile.getPath().toLowerCase().endsWith(".txt")) {
                    dataModel.readQueue(selectedFile, ContentExtension.TXT);
                } else if(selectedFile.getPath().toLowerCase().endsWith(".dat")){
                    dataModel.readQueue(selectedFile, ContentExtension.DAT);
                }
            } else {
                // TODO: Add Debuging Information After Selecting Cancel Option
            }
        }
    }

    private void guiStartAction() {
        if(!dataModel.isProcessingStarted()) {
            dataModel.setProcessingStarted(true);
        }
    }

    private void guiStopAction() {
        if(dataModel.isProcessingStarted()) {
            dataModel.setProcessingStarted(false);
        }
    }
}
