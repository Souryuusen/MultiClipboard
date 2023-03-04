package com.souryuu.multiclipboard;


import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import java.awt.SystemColor;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import com.github.kwhat.jnativehook.GlobalScreen;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.ButtonGroup;
import javax.swing.JScrollPane;

/*
 * @author Grzegorz Lach
 * @version 1.1.7
 */
public class OldApp implements NativeKeyListener, WindowListener
{
    private static Toolkit toolkit = Toolkit.getDefaultToolkit();
    private static Clipboard clipboard = toolkit.getSystemClipboard();
    private static StringSelection strSel;
    private static File file;
    private static Font clipboardContentFont;
    private static final Object[] exitButtons = {"Save", "Don't save", "Cancel"};
    private static String temp = new String();
    private static String path = new String();
    private static String extension = new String();
    private static String readTemp = new String();
    private static String separator = new String();
    private static StringBuilder strBuilder = new StringBuilder();
    private JFrame frame;
    private static JTextField posField;
    private static ArrayList<String> queue ;
    private static int index = 0;
    private static int startingIndex = 0;
    private static int loadIndex = 0;
    private static int returnVal;
    private static int selection;
    private static boolean setPosFlag = false;
    private static boolean startFlag = false;
    private static boolean newFlag = true;
    private static boolean saveFlag = true;
    private static boolean saveAsDatFlag = true;
    private static boolean saveAsTxtFlag = !saveAsDatFlag;
    private static JTextField errorField;
    private static JTextArea contentArea;
    private static ObjectOutputStream out;
    private static ObjectInputStream in;
    private static JTextField arraySize;
    private static JFileChooser chooser;
    private static FileNameExtensionFilter filter;
    private static Scanner scanner;
    private final ButtonGroup buttonGroup = new ButtonGroup();
    private final ButtonGroup buttonGroup_1 = new ButtonGroup();

    public static void main(String[] args)
    {
        try
        {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    OldApp window = new OldApp();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public OldApp() {
        initialize();
    }

    public void doSave()
    {
        returnVal = chooser.showSaveDialog(frame);
        if(returnVal == JFileChooser.APPROVE_OPTION)
        {
            path = chooser.getSelectedFile().getAbsolutePath();
            if(extension.equals(null) || (!extension.equals(".dat") && !extension.equals(".txt")))
            {
                extension = ".dat";
            }
            try
            {
                if(path.contains(extension))
                {
                    out = new ObjectOutputStream(new FileOutputStream(new File(path)));
                }
                else
                {
                    out = new ObjectOutputStream(new FileOutputStream(new File(path+extension)));
                }
                out.writeObject(queue);
                out.close();
                errorField.setText("File saved!!");
            } catch (IOException e1)
            {
                errorField.setText("Savefile didn't created due to an error!!");
            }
        }
        saveFlag = true;
    }

    public void doRead()
    {
        returnVal = chooser.showOpenDialog(frame);
        if(returnVal == JFileChooser.APPROVE_OPTION)
        {
            path = chooser.getSelectedFile().getAbsolutePath();
        }
        try {
            file = new File(path);
            if(file.exists())
            {
                scanner = new Scanner(file);
            }
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        if(newFlag)
        {
            index = 0;
            if(file.exists())
                while(scanner.hasNextLine())
                {
                    readTemp = scanner.nextLine();
                    if(readTemp.equalsIgnoreCase(separator))
                    {
                        if(!strBuilder.equals(null))
                        {
                            strBuilder.append("\n");
                            readTemp = strBuilder.toString();
                            strBuilder.setLength(0);
                            queue.add(index, readTemp);
                            index++;
                        }
                    }
                    else
                    {
                        strBuilder.append(readTemp + "/n");
                    }
                }
        }
        else
        {
            errorField.setText("Cannot create queue from file - make new queue and try again!");
        }

        arraySize.setText(Integer.toString(queue.size()));
    }

    @SuppressWarnings("unchecked")
    public void doLoad()
    {
        returnVal = chooser.showOpenDialog(frame);
        if(returnVal == JFileChooser.APPROVE_OPTION)
        {
            path = chooser.getSelectedFile().getAbsolutePath();

            try
            {
                in = new ObjectInputStream(new FileInputStream(path));
                queue = (ArrayList<String>) in.readObject();
                arraySize.setText(Integer.toString(queue.size()));
                errorField.setText("File loaded!!");
                in.close();
            } catch (FileNotFoundException e3)
            {
                errorField.setText("File not found!");
            } catch (IOException e3)
            {
                errorField.setText("Error occured durning loading");
            } catch (ClassNotFoundException e1)
            {
                errorField.setText("Class not found!");
            }
        }
    }

    private void initialize()
    {
        queue = new ArrayList<String>();
        frame = new JFrame();
        frame.getContentPane().setBackground(SystemColor.menu);
        frame.setResizable(false);
        frame.setAlwaysOnTop(true);
        frame.setBounds(100, 100, 450, 330);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setTitle("MultiClipboard v1.1.6");
        frame.addWindowListener(this);

        clipboardContentFont = new Font("Serif", Font.PLAIN, 11);

        Action addToQueue = new AddToQueue();
        Action save = new Save();
        Action setPos = new SetPosition();
        Action load = new Load();
        Action reset = new Reset();
        Action start = new Start();
        Action stop = new Stop();
        Action newAction = new New();
        Action exit = new Exit();
        Action saveAsDat = new SaveAsDat();
        Action saveAsTxt = new SaveAsTxt();
        Action read = new Reader();
        Action textSepDefault = new DefaultTextSep();
        Action textSepCustom = new CustomTextSep();

        chooser = new JFileChooser();
        filter = new FileNameExtensionFilter("Data or text file", "dat", "txt");
        chooser.setFileFilter(filter);

        JButton addButton = new JButton(addToQueue);
        addButton.setBounds(10, 15, 89, 23);
        frame.getContentPane().add(addButton);

        JButton saveButton = new JButton(save);
        saveButton.setBounds(10, 49, 89, 23);
        frame.getContentPane().add(saveButton);

        JButton loadButton = new JButton(load);
        loadButton.setBounds(10, 83, 89, 23);
        frame.getContentPane().add(loadButton);

        JButton startButton = new JButton(start);
        startButton.setBounds(10, 117, 89, 23);
        frame.getContentPane().add(startButton);

        JButton stopButton = new JButton(stop);
        stopButton.setBounds(10, 151, 89, 23);
        frame.getContentPane().add(stopButton);

        JButton resetButton = new JButton(read);
        resetButton.setBounds(10, 248, 161, 23);
        frame.getContentPane().add(resetButton);

        JButton setPosButton = new JButton(setPos);
        setPosButton.setBounds(10, 215, 116, 23);
        frame.getContentPane().add(setPosButton);

        posField = new JTextField();
        posField.setBounds(136, 216, 73, 20);
        frame.getContentPane().add(posField);
        posField.setColumns(10);
        posField.setText("1");

        JLabel clipboardLabel = new JLabel("Clipboard content:");
        clipboardLabel.setBounds(109, 0, 161, 14);
        frame.getContentPane().add(clipboardLabel);

        errorField = new JTextField();
        errorField.setBounds(181, 249, 253, 20);
        frame.getContentPane().add(errorField);
        errorField.setColumns(10);

        arraySize = new JTextField();
        arraySize.setBounds(348, 216, 86, 20);
        frame.getContentPane().add(arraySize);
        arraySize.setColumns(10);

        JLabel labelQueueSize = new JLabel("Queue size:");
        labelQueueSize.setBounds(265, 219, 73, 14);
        frame.getContentPane().add(labelQueueSize);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(109, 15, 325, 189);
        frame.getContentPane().add(scrollPane);

        contentArea = new JTextArea();
        scrollPane.setViewportView(contentArea);
        contentArea.setFont(clipboardContentFont);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu menuFile = new JMenu("File");
        menuBar.add(menuFile);

        JMenuItem menuItemNew = new JMenuItem(newAction);
        menuItemNew.setAccelerator(KeyStroke.getKeyStroke("ctrl N"));
        menuFile.add(menuItemNew);

        JMenuItem menuItemReadFromtxt = new JMenuItem(read);
        menuItemReadFromtxt.setAccelerator(KeyStroke.getKeyStroke("ctrl R"));
        menuFile.add(menuItemReadFromtxt);

        JMenuItem menuItemSave = new JMenuItem(save);
        menuItemSave.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
        menuFile.add(menuItemSave);

        JMenuItem menuItemLoad = new JMenuItem(load);
        menuItemLoad.setAccelerator(KeyStroke.getKeyStroke("ctrl L"));
        menuFile.add(menuItemLoad);

        JMenuItem menuItemExit = new JMenuItem(exit);
        menuFile.add(menuItemExit);

        JMenu menuEdit = new JMenu("Edit");
        menuBar.add(menuEdit);

        JMenuItem menuItemAddToQueue = new JMenuItem(addToQueue);
        menuEdit.add(menuItemAddToQueue);

        JMenuItem menuItemStartQueue = new JMenuItem(start);
        menuEdit.add(menuItemStartQueue);

        JMenuItem menuItemStopQueue = new JMenuItem(stop);
        menuEdit.add(menuItemStopQueue);

        JMenuItem menuItemResetQueue = new JMenuItem(reset);
        menuEdit.add(menuItemResetQueue);

        JMenu menuOptions = new JMenu("Options");
        menuBar.add(menuOptions);

        JMenu menuSaveFile = new JMenu("Save file extension");
        menuOptions.add(menuSaveFile);

        JRadioButtonMenuItem menuRadioSaveAsdat = new JRadioButtonMenuItem(saveAsDat);
        menuRadioSaveAsdat.setSelected(saveAsDatFlag);
        menuSaveFile.add(menuRadioSaveAsdat);
        buttonGroup.add(menuRadioSaveAsdat);

        JRadioButtonMenuItem menuRadioSaveAstxt = new JRadioButtonMenuItem(saveAsTxt);
        menuRadioSaveAstxt.setSelected(saveAsTxtFlag);
        menuSaveFile.add(menuRadioSaveAstxt);
        buttonGroup.add(menuRadioSaveAstxt);

        JMenu mnReaderSeparatorSetting = new JMenu("Text separator");
        menuOptions.add(mnReaderSeparatorSetting);

        JRadioButtonMenuItem menuRadioDefault = new JRadioButtonMenuItem(textSepDefault);
        menuRadioDefault.setSelected(true);
        buttonGroup_1.add(menuRadioDefault);
        mnReaderSeparatorSetting.add(menuRadioDefault);

        JRadioButtonMenuItem menuRadioCustom = new JRadioButtonMenuItem(textSepCustom);
        buttonGroup_1.add(menuRadioCustom);
        mnReaderSeparatorSetting.add(menuRadioCustom);

        //JMenuItem mntmNewMenuItem = new JMenuItem("Set file extension to...");
        //mnOptions.add(mntmNewMenuItem);
    }

    @SuppressWarnings("serial")
    private class AddToQueue extends AbstractAction
    {
        public AddToQueue()
        {
            putValue(Action.NAME, "Add");
            putValue(Action.SHORT_DESCRIPTION, "Add copied text to clipboard queue");
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            try
            {
                temp = (String) clipboard.getData(DataFlavor.stringFlavor);
                queue.add(index, temp);
                contentArea.setText("Index of element: "+ (index+1) +"\n\n"+ queue.get(index));
                errorField.setText("Added to queue at index "+(index+1));
                ++index;
            } catch (UnsupportedFlavorException | IOException e1)
            {
                errorField.setText("Incorrect data type to add");
            }

            arraySize.setText(Integer.toString(queue.size()));
            newFlag = false;
            saveFlag = false;
        }
    }

    @SuppressWarnings("serial")
    private class Save extends AbstractAction
    {
        public Save()
        {
            putValue(Action.NAME, "Save");
            putValue(Action.SHORT_DESCRIPTION, "Saves current queue");
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            doSave();
        }

    }

    @SuppressWarnings("serial")
    private class SetPosition extends AbstractAction
    {
        public SetPosition()
        {
            putValue(Action.NAME, "Set position");
            putValue(Action.SHORT_DESCRIPTION, "Sets element that will be copied/pasted next");
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(queue.size()>0)
            {
                startingIndex = Integer.parseInt(posField.getText());
                if(startingIndex > 0)
                {
                    if(startingIndex <= queue.size())
                    {
                        setPosFlag = true;
                        index = startingIndex-1;
                        loadIndex = startingIndex - 1;
                        contentArea.setText("Index of element: "+ (index+1) +"\n\n"+ queue.get(index));
                        strSel = new StringSelection(queue.get(loadIndex));
                        clipboard.setContents(strSel, null);
                        errorField.setText("");
                    }
                    else
                    {
                        errorField.setText("Position cannot be greater than queue size!!");
                    }
                }
                else
                {
                    errorField.setText("Starting position has to be greater than 0!!");
                }
                startFlag = false;
            }
            else
            {
                errorField.setText("Cannot set position of empty queue!!");
            }
        }
    }

    @SuppressWarnings("serial")
    private class Load extends AbstractAction
    {
        public Load()
        {
            putValue(Action.NAME, "Load");
            putValue(Action.SHORT_DESCRIPTION, "Loads saved queue");
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            doLoad();
        }
    }

    @SuppressWarnings("serial")
    private class Start extends AbstractAction
    {
        public Start()
        {
            putValue(Action.NAME, "Start");
            putValue(Action.SHORT_DESCRIPTION, "Enables queue");
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(queue.size()>0)
            {
                startFlag = true;
                if(setPosFlag)
                {
                    loadIndex = startingIndex-1;
                }
                else
                {
                    temp = queue.get(loadIndex);
                    contentArea.setText(temp);
                    strSel = new StringSelection(temp);
                    clipboard.setContents(strSel, null);
                }
            }
            else
            {
                selection = JOptionPane.showConfirmDialog(frame, "Your queue is empty.\nDo you want to load queue from a file?", "?",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if(selection == JOptionPane.YES_OPTION)
                {
                    doLoad();
                }
                if(selection == JOptionPane.CLOSED_OPTION)
                {
                    errorField.setText("Cannot start empty queue!");
                }
            }
        }


    }

    @SuppressWarnings("serial")
    private class Reset extends AbstractAction
    {
        public Reset()
        {
            putValue(Action.NAME, "Reset");
            putValue(Action.SHORT_DESCRIPTION, "Resets starting position, fields, options and index");
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            startingIndex = 1;
            posField.setText("1");
            setPosFlag = false;

            contentArea.setText("");

            index = 0;

            queue.clear();

            loadIndex = 0;

            errorField.setText("Reset completed!");

        }
    }

    @SuppressWarnings("serial")
    private class Stop extends AbstractAction
    {
        public Stop()
        {
            putValue(Action.NAME, "Stop");
            putValue(Action.SHORT_DESCRIPTION, "Disables queue");
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            startFlag = false;
        }
    }

    @SuppressWarnings("serial")
    private class New extends AbstractAction
    {
        public New()
        {
            putValue(Action.NAME, "New");
            putValue(Action.SHORT_DESCRIPTION, "Creates new queue");
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            selection = JOptionPane.showConfirmDialog(frame, "Do you want to create new queue?", "?",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(selection == JOptionPane.YES_OPTION)
            {
                queue.clear();
                startingIndex = 0;
                posField.setText("1");
                setPosFlag = false;
                contentArea.setText("");
                index = 0;
                loadIndex = 0;
                newFlag = true;
                arraySize.setText("0");
                readTemp = "";
                strBuilder.setLength(0);
                errorField.setText("New queue created");
            }
        }
    }

    @SuppressWarnings("serial")
    private class Exit extends AbstractAction
    {
        public Exit()
        {
            putValue(Action.NAME, "Exit");
            putValue(Action.SHORT_DESCRIPTION, "Exit from program");
        }

        @Override
        public void actionPerformed(ActionEvent arg0)
        {
            if(!saveFlag)
            {
                selection = JOptionPane.showOptionDialog(frame, "Do you want to save before exit?", "Save your progress??", JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, exitButtons, exitButtons[1]);

                if (selection == JOptionPane.OK_OPTION)
                {
                    doSave();
                    try {
                        GlobalScreen.unregisterNativeHook();
                    } catch (NativeHookException e) {
                        e.printStackTrace();
                    }
                    System.runFinalization();
                    System.exit(0);
                }
                else if(selection == JOptionPane.NO_OPTION)
                {
                    try {
                        GlobalScreen.unregisterNativeHook();
                    } catch (NativeHookException e) {
                        e.printStackTrace();
                    }
                    System.runFinalization();
                    System.exit(0);
                }
            }
            else
            {
                try {
                    GlobalScreen.unregisterNativeHook();
                } catch (NativeHookException e) {
                    e.printStackTrace();
                }
                System.runFinalization();
                System.exit(0);
            }
        }

    }

    @SuppressWarnings("serial")
    private class SaveAsDat extends AbstractAction
    {
        public SaveAsDat()
        {
            putValue(Action.NAME, "Save as *.dat");
            putValue(Action.SHORT_DESCRIPTION, "Saved file will have *.dat extension");
        }

        @Override
        public void actionPerformed(ActionEvent arg0)
        {
            extension = ".dat";
            saveAsDatFlag = true;
            saveAsTxtFlag = false;
        }
    }

    @SuppressWarnings("serial")
    private class SaveAsTxt extends AbstractAction
    {
        public SaveAsTxt()
        {
            putValue(Action.NAME, "Save as *.txt");
            putValue(Action.SHORT_DESCRIPTION, "Saved file will have *.txt extension");
        }

        @Override
        public void actionPerformed(ActionEvent arg0)
        {
            extension = ".txt";
            saveAsDatFlag = false;
            saveAsTxtFlag = true;
        }
    }

    @SuppressWarnings("serial")
    private class Reader extends AbstractAction
    {
        public Reader()
        {
            putValue(Action.NAME, "Read from *.txt");
            putValue(Action.SHORT_DESCRIPTION, "");
        }

        @Override
        public void actionPerformed(ActionEvent arg0)
        {
            if(queue.size() == 0)
            {
                doRead();
            }
            else
            {
                selection = JOptionPane.showConfirmDialog(frame, "You will lose current queue.\nDo you want to continue?", "?",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                if(selection == JOptionPane.YES_OPTION)
                {
                    doRead();
                }
            }
        }
    }

    @SuppressWarnings("serial")
    private class DefaultTextSep extends AbstractAction
    {
        public DefaultTextSep()
        {
            putValue(Action.NAME, "Default");
            putValue(Action.SHORT_DESCRIPTION, "Set separator to '?-----?'");
        }

        @Override
        public void actionPerformed(ActionEvent arg0)
        {
            separator = "----------";
        }
    }

    @SuppressWarnings("serial")
    private class CustomTextSep extends AbstractAction
    {
        public CustomTextSep()
        {
            putValue(Action.NAME, "Custom");
            putValue(Action.SHORT_DESCRIPTION, "Set your own separator");
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            separator = JOptionPane.showInputDialog(frame, "Enter your separator");
        }
    }

    public void windowOpened(WindowEvent e)
    {
        try
        {
            GlobalScreen.registerNativeHook();
        }catch (NativeHookException ex)
        {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            ex.printStackTrace();

            System.exit(1);
        }
        GlobalScreen.addNativeKeyListener(this);
    }

    public void windowClosed(WindowEvent e)
    {
    }

    public void windowClosing(WindowEvent e)
    {
        if(!saveFlag)
        {
            selection = JOptionPane.showOptionDialog(frame, "Do you want to save before exit?", "Save your progress??", JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, exitButtons, exitButtons[1]);

            if (selection == JOptionPane.OK_OPTION)
            {
                doSave();
                try {
                    GlobalScreen.unregisterNativeHook();
                } catch (NativeHookException ex) {
                    ex.printStackTrace();
                }
                System.runFinalization();
                System.exit(0);
            }
            else if(selection == JOptionPane.NO_OPTION)
            {
                try {
                    GlobalScreen.unregisterNativeHook();
                } catch (NativeHookException ex) {
                    ex.printStackTrace();
                }
                System.runFinalization();
                System.exit(0);
            }
        }
        else
        {
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException ex) {
                ex.printStackTrace();
            }
            System.runFinalization();
            System.exit(0);
        }
    }

    public void windowIconified(WindowEvent e) {}

    public void windowDeiconified(WindowEvent e) {}

    public void windowActivated(WindowEvent e) {}

    public void windowDeactivated(WindowEvent e) {}

    public void nativeKeyReleased(NativeKeyEvent e)
    {
        if (startFlag && e.getKeyCode() == NativeKeyEvent.VC_ALT && e.getModifiers() == NativeKeyEvent.CTRL_MASK)
        {
            ++loadIndex;
            temp = queue.get(loadIndex);
            contentArea.setText(temp);
            strSel = new StringSelection(temp);
            clipboard.setContents(strSel, null);
        }
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent arg0)
    {

    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent arg0)
    {

    }
}
