package com.souryuu.multiclipboard.entity;

import com.souryuu.multiclipboard.exception.CorruptedContentException;

import java.io.File;
import java.io.IOException;

public class ClipboardContent {

    Object contentValue;
    ContentType contentType;

    public ClipboardContent(Object contentValue, ContentType contentType) {
        this.contentType = contentType;
        this.contentValue = contentValue;
    }

    public Object getContentValue() {
        if(getContentType() == ContentType.TextContent) {
            return (String) this.contentValue;
        } else if(getContentType() == ContentType.FileContent) {
            return (File) this.contentValue;
        } else {
            return null;
        }
    }

    public void setContentValue(Object contentValue) {
        this.contentValue = contentValue;
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
            return (String) getContentValue();
        } else if(getContentType() == ContentType.FileContent) {
            try {
                return "Filepath to stored content:\n" + ((File)getContentValue()).getCanonicalPath().toString();
            } catch (IOException e) {
                throw new CorruptedContentException("Error During Obtaining Canonical Path Of File.", e);
            }
        } else {
            throw new CorruptedContentException("Improper Content Type Detected");
        }
    }
}
