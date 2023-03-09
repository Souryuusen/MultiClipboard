package com.souryuu.multiclipboard.dao;

import com.souryuu.multiclipboard.MultiClipboardDataModel;
import com.souryuu.multiclipboard.entity.ClipboardData;
import com.souryuu.multiclipboard.entity.ContentType;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class QueueDAO {

    public static List<ClipboardData> readQueueFromTxt(File input) {
        // Returned Queue
        List<ClipboardData> result = new ArrayList<>();
        // Data Model Instance
        MultiClipboardDataModel dataModel = MultiClipboardDataModel.getInstance();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(input), StandardCharsets.UTF_8))) {
            String line;
            StringBuilder sb = new StringBuilder();
            while((line = br.readLine()) != null) {
                // Separator Detected
                if(line.equalsIgnoreCase(dataModel.getDefaultSeparator()) && dataModel.isDefaultSeparatorUsed()
                        || line.equalsIgnoreCase(dataModel.getCustomSeparator()) && !dataModel.isDefaultSeparatorUsed()) {
                    // Content String Creation
                    String content = sb.toString();
                    // Reset Of StringBuilder Content
                    sb.setLength(0);
                    // Creation Of New ClipboardData
                    ClipboardData clipboardData = new ClipboardData(content, ContentType.TextContent);
                    // Add New Data To Queue
                    result.add(clipboardData);
                } else {
                    sb.append(line).append(System.lineSeparator());
                }
            }
        } catch (FileNotFoundException e) {
            // TODO: Fill In Proper Manner
            e.printStackTrace();
        } catch (IOException e) {
            // TODO: Fill In Proper Manner
            e.printStackTrace();
        }
        return result;
    }

}
