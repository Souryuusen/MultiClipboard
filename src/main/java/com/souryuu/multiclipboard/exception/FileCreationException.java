package com.souryuu.multiclipboard.exception;

public class FileCreationException extends RuntimeException{

    public FileCreationException(String message) {
        super(message);
    }

    public FileCreationException(String message, Throwable cause) {
        super(message, cause);
    }

}
