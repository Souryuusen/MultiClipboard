package com.souryuu.multiclipboard.entity;

import com.souryuu.multiclipboard.exception.CorruptedContentException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ClipboardData {

    private StringProperty contentProperty;
    private ContentType contentType;

    // Block Initializer
    {
        contentProperty = new SimpleStringProperty();
    }

    public ClipboardData(String contentValue, ContentType contentType) {
        getContentProperty().set(contentValue);
        setContentType(contentType);
    }

    public StringProperty getContentProperty() {
        return this.contentProperty;
    }

    public String getContentValue() {
        return getContentProperty().getValue();
    }

    public void setContentValue(String contentValue) {
        getContentProperty().setValue(contentValue);
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    @Override
    public String toString() throws CorruptedContentException {
        if(getContentType() == ContentType.TextContent) {
            return getContentValue();
        }else {
            throw new CorruptedContentException("Invalid Content Type Detected");
        }
    }
}
