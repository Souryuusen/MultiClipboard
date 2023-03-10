package com.souryuu.multiclipboard.dao;

import com.souryuu.multiclipboard.MultiClipboardDataModel;
import com.souryuu.multiclipboard.entity.ClipboardData;
import com.souryuu.multiclipboard.entity.ContentType;
import com.souryuu.multiclipboard.exception.FileCreationException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.NoSuchFileException;
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

    public static List<ClipboardData> readQueueFromDat(File input) {
        // Returned Queue
        List<ClipboardData> result = new ArrayList<>();
        // Data Model Instance
        MultiClipboardDataModel dataModel = MultiClipboardDataModel.getInstance();
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(input))) {
            byte[] data;
            String dataString = new String(bis.readAllBytes());
            // Splitting Of Read Data
            String[] contentValues;
            if(dataModel.isDefaultSeparatorUsed()) {
                contentValues = dataString.split(dataModel.getDefaultSeparator());
            } else {
                contentValues = dataString.split(dataModel.getCustomSeparator());
            }
            // Filling Content List Based On Read Data
            for(String content : contentValues) {
                // Creation Of New ClipboardData
                ClipboardData clipboardData = new ClipboardData(content, ContentType.TextContent);
                // Add New Data To Queue
                result.add(clipboardData);
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

    public static boolean saveQueueToTxt(File output, List<ClipboardData> queue, String separator) throws FileNotFoundException {
        // Verification Of Parameters
        if(output == null || !output.exists()) {
            throw new FileNotFoundException("Cannot Save Queue To Non-Existing File!");
        }
        if(queue.isEmpty()) {
            throw new IllegalArgumentException("Cannot Save Empty Queue!");
        }
        if(separator.equalsIgnoreCase("")) {
            throw new IllegalArgumentException("Content Separator Cannot Be Blank!");
        }
        // Building Text File Content Based On Queue
        StringBuilder sb = new StringBuilder();
        for(ClipboardData data : queue) {
            // Line Feed Before Adding New Content
            if(sb.length() > 0) sb.append(System.lineSeparator());
            // Parsing ClipboardData Content To Txt
            sb.append(data.getContentValue()).append(System.lineSeparator()).append(separator);
        }
        // Saving Text Data To File
        try (BufferedWriter br = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output), StandardCharsets.UTF_8))) {
            br.write(sb.toString());
        } catch (IOException e) {
            throw new FileCreationException("Cannot Write Content To File!", e);
        }
        return true;
    }

    public static boolean saveQueueToDat(File output, List<ClipboardData> queue, String separator) throws FileNotFoundException {
        // Verification Of Parameters
        if(output == null || !output.exists()) {
            throw new FileNotFoundException("Cannot Save Queue To Non-Existing File!");
        }
        if(queue.isEmpty()) {
            throw new IllegalArgumentException("Cannot Save Empty Queue!");
        }
        if(separator.equalsIgnoreCase("")) {
            throw new IllegalArgumentException("Content Separator Cannot Be Blank!");
        }
        // Building Text File Content Based On Queue
        StringBuilder sb = new StringBuilder();
        for(ClipboardData data : queue) {
            // Line Feed Before Adding New Content
            if(sb.length() > 0) sb.append(System.lineSeparator());
            // Parsing ClipboardData Content To Txt
            sb.append(data.getContentValue()).append(System.lineSeparator()).append(separator);
        }
        // Saving Text Data To File
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(output))) {
            bos.write(sb.toString().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new FileCreationException("Cannot Write Content To File!", e);
        }
        return true;
    }

}
