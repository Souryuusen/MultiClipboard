package com.souryuu.multiclipboard.dao;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

public class ClipboardDAO {

    public static String getClipboardContent() {
        return Clipboard.getSystemClipboard().getString();
    }

    public static void setClipboardContent(String clipboardContent) {
        ClipboardContent cc = new ClipboardContent();
        cc.putString(clipboardContent);
        Clipboard.getSystemClipboard().setContent(cc);
    }


}
