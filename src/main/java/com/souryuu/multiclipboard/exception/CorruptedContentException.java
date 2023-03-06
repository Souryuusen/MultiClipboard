package com.souryuu.multiclipboard.exception;

public class CorruptedContentException extends RuntimeException{

    public CorruptedContentException(String message) {
        super(message);
    }

    public CorruptedContentException(String message, Throwable cause) {
        super(message, cause);
    }
}
