package com.souryuu.multiclipboard;

import com.souryuu.multiclipboard.dao.ClipboardDAO;
import com.souryuu.multiclipboard.dao.QueueDAO;
import com.souryuu.multiclipboard.entity.ClipboardData;
import com.souryuu.multiclipboard.entity.ContentExtension;
import com.souryuu.multiclipboard.entity.ContentType;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

import java.io.File;
import java.io.FileNotFoundException;

public class MultiClipboardDataModel {

    // Static Variables
    private static MultiClipboardDataModel instance;

    // Content List
    private final ObservableList<ClipboardData> contentList;

    // Properties Variables
    private IntegerProperty indexProperty;
    private final IntegerProperty contentSizeProperty;
    private final StringProperty currentContentTextProperty;
    private final BooleanProperty processingStartedProperty;

    // Variables
    private ClipboardData currentContent;
    private final String DEFAULT_SEPARATOR = "#-#";
    private String customSeparator = "";
    private boolean defaultSeparatorUsed;

    // Initializer Block
    {
        contentList = FXCollections.observableArrayList();
        indexProperty = new SimpleIntegerProperty(0);
        contentSizeProperty = new SimpleIntegerProperty(0);
        currentContentTextProperty = new SimpleStringProperty("");
        processingStartedProperty = new SimpleBooleanProperty(false);
        currentContent = new ClipboardData("", ContentType.TextContent);
    }

    // Singleton Pattern Private Constructor
    private MultiClipboardDataModel() {
        // Fields Initialization
        this.defaultSeparatorUsed = true;
        // Creation Of Bindings And Listeners
        bindProperties();
        bindListeners();
    }

    public static MultiClipboardDataModel getInstance() {
        if(instance == null) {
            instance = new MultiClipboardDataModel();
        }
        return instance;
    }

    public ObservableList<ClipboardData> getContentList() {
        return this.contentList;
    }

    public IntegerProperty getIndexProperty() {
        return this.indexProperty;
    }

    public int getIndexValue() {
        return getIndexProperty().get();
    }

    public void setIndexValue(int indexValue) {
        getIndexProperty().set(indexValue);
    }

    public IntegerProperty getContentSizeProperty() {
        return this.contentSizeProperty;
    }

    public int getContentSizeValue() {
        return getContentSizeProperty().get();
    }

    public StringProperty getCurrentContentTextProperty() {
        return this.currentContentTextProperty;
    }

    public String getCurrentContentTextValue() {
        return getCurrentContentTextProperty().get();
    }

    public void setCurrentContentTextValue(String currentContentTextValue) {
        getCurrentContentTextProperty().set(currentContentTextValue);
    }

    public ClipboardData getCurrentContent() {
        return this.currentContent;
    }

    public void setCurrentContent(ClipboardData currentContent) {
        this.currentContent.setContentValue(currentContent.getContentValue());
        this.currentContent.setContentType(currentContent.getContentType());
    }

    public void updateCurrentContent(int contentIndex) {
        setCurrentContent(getContentList().get(contentIndex));
    }

    public String getDefaultSeparator() {
        return this.DEFAULT_SEPARATOR;
    }

    public String getCustomSeparator() {
        return this.customSeparator;
    }

    public void setCustomSeparator(String customSeparator) {
        this.customSeparator = customSeparator;
    }

    public boolean isDefaultSeparatorUsed() {
        return this.defaultSeparatorUsed;
    }

    public void setDefaultSeparatorUsed(boolean defaultSeparatorUsed) {
        this.defaultSeparatorUsed = defaultSeparatorUsed;
    }

    public BooleanProperty getProcessingStartedProperty() {
        return this.processingStartedProperty;
    }

    public boolean isProcessingStarted() {
        return getProcessingStartedProperty().get();
    }

    public void setProcessingStarted(boolean processingStarted) {
        getProcessingStartedProperty().set(processingStarted);
    }

    private void bindProperties() {
        contentSizeProperty.bind(Bindings.size(contentList));
        currentContentTextProperty.bind(currentContent.getContentProperty());
    }

    private void bindListeners() {
        getContentList().addListener((ListChangeListener<ClipboardData>) change -> {
            ObservableList<ClipboardData> list = getContentList();
            if(list != null && !list.isEmpty()) {
                updateCurrentContent(getIndexValue());
            }
        });
        getIndexProperty().addListener((ChangeListener<? super Number>) (observableValue, oldValue, newValue) -> {
            if(newValue.intValue() <= getContentSizeValue()) {
                oldValue = newValue;
            }
            setCurrentContent(getContentList().get(newValue.intValue()));
        });
    }

    public void addNewContentToQueue() {
        // Reading Of Current Clipboard Content
        String clipboardContent = ClipboardDAO.getClipboardContent();
        // Verification Of Returned Value
        if(clipboardContent != null && !clipboardContent.equals("")) {
            // New Element Creation
            ClipboardData newContent = new ClipboardData(clipboardContent, ContentType.TextContent);
            // Add To Queue
            getContentList().add(newContent);
            // Clearing Current Clipboard Contents
            Clipboard.getSystemClipboard().setContent(new ClipboardContent());
            // Setting Of Current Content To Last Element Of List
            setCurrentContent(getContentList().get(getContentSizeValue()-1));
        } else {
            // TODO: Create Logging For Invalid Clipboard Content
        }
    }

    public void clearModel() {
        // Clearing All Data Model Variables
        getContentList().clear();
        setCurrentContent(new ClipboardData("", ContentType.TextContent));
        indexProperty = new SimpleIntegerProperty(0);
    }

    public void readQueue(File inputFile, ContentExtension extension) {
        // Clearing Current Queue Data
        clearModel();
        // Adding All Read Queue Elements
        if(extension == ContentExtension.TXT) {
            getContentList().addAll(QueueDAO.readQueueFromTxt(inputFile));
        } else if(extension == ContentExtension.DAT) {
            getContentList().addAll(QueueDAO.readQueueFromDat(inputFile));
        }
        // Setting Current Content To First Element Of Queue
        if(getContentList() != null && !getContentList().isEmpty()) {
            setCurrentContent(getContentList().get(0));
        } else {
            // TODO: Adding Exception For Null Result List Or Empty List
        }
    }

    public boolean saveQueue(File output, ContentExtension extension) {
        // Return Value
        boolean result = false;
        // Saving Queue To File
        try {
            // Obtain Used Separator
            String separator = isDefaultSeparatorUsed() ? getDefaultSeparator() : getCustomSeparator();
            // Selection Of Output File Extension
            if(extension == ContentExtension.TXT) {
                result = QueueDAO.saveQueueToTxt(output, getContentList(), separator);
            } else if(extension == ContentExtension.DAT) {
                result = QueueDAO.saveQueueToDat(output, getContentList(), separator);
            }
        } catch (FileNotFoundException e) {
            // Display Error Stack Trace
            e.printStackTrace();
            return false;
        }
        return result;
    }

}
