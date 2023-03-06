package com.souryuu.multiclipboard;

import com.souryuu.multiclipboard.entity.ClipboardContent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class MultiClipboardDataModel {

    // Static Variables
    private static MultiClipboardDataModel instance;

    // Content List
    ObservableList<ClipboardContent> contentList;

    // Properties Variables


    // Initializer Block
    {
        contentList = FXCollections.observableArrayList();
    }

    // Singleton Pattern Private Constructor
    private MultiClipboardDataModel() {}

    public MultiClipboardDataModel getInstance() {
        if(instance == null) {
            instance = new MultiClipboardDataModel();
        }
        return instance;
    }


}
