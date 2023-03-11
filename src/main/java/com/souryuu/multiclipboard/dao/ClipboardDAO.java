package com.souryuu.multiclipboard.dao;

import javafx.scene.input.Clipboard;

import java.awt.*;
import java.awt.datatransfer.StringSelection;

public class ClipboardDAO {

    public static String getClipboardContent() {
        return Clipboard.getSystemClipboard().getString();
    }

    public static void setClipboardContent(String clipboardContent) {
        StringSelection selection = new StringSelection(clipboardContent);
        java.awt.datatransfer.Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, null);
    }


}
