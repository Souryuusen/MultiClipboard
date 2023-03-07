package com.souryuu.multiclipboard;

import com.souryuu.multiclipboard.dao.ClipboardDAO;
import com.souryuu.multiclipboard.entity.ClipboardData;
import com.souryuu.multiclipboard.entity.ContentType;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

public class MultiClipboardDataModel {

    // Static Variables
    private static MultiClipboardDataModel instance;

    // Content List
    private ObservableList<ClipboardData> contentList;

    // Properties Variables
    private IntegerProperty indexProperty;
    private IntegerProperty contentSizeProperty;
    private StringProperty currentContentTextProperty;

    // Variables
    private ClipboardData currentContent;

    // Initializer Block
    {
        contentList = FXCollections.observableArrayList();
        indexProperty = new SimpleIntegerProperty(0);
        contentSizeProperty = new SimpleIntegerProperty(0);
        currentContentTextProperty = new SimpleStringProperty("");
        currentContent = new ClipboardData("", ContentType.TextContent);
    }

    // Singleton Pattern Private Constructor
    private MultiClipboardDataModel() {
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

    private void bindProperties() {
        contentSizeProperty.bind(Bindings.size(contentList));
        currentContentTextProperty.bind(currentContent.getContentProperty());
    }

    private void bindListeners() {
        getContentList().addListener(new ListChangeListener<ClipboardData>() {
            @Override
            public void onChanged(Change<? extends ClipboardData> change) {
                ObservableList<ClipboardData> list = getContentList();
                if(list != null && !list.isEmpty()) {
                    updateCurrentContent(getIndexValue());
                }
            }
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

}
