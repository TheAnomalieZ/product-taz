package org.taz.commons.util;

/**
 * Created by  Maninesan on 12/06/16.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class GUI extends JFrame {

    private static JButton uploadAFileButton = new JButton();
    private static JPanel panel1;
    private JButton memoryStateButton = new JButton();
    private JButton cpuAnalyzeButton = new JButton();
    private JButton pauseTimeButton = new JButton();
    private JButton gcEventButton = new JButton();

    private JTextArea textArea = new JTextArea();
    private Map<String,String> filepaths = new LinkedHashMap<String,String>();
    private JButton refreshFileListButton = new JButton();
    private JFileChooser jfrChooser = new JFileChooser();
    private  JFileChooser chooser;

    private ArrayList<Integer> GCSequence;
    private Map<Long,Long> pauseTimeSeries;
    private Map<Long,Long> cpuTimeSeries;
    private ArrayList<ArrayList<String>> gcAttributes;

    private CSVWriter csvWriter;
    private JFRReader jfrReader;


    String fileName = "";

    public GUI() {
        jfrReader = JFRReader.getInstance();
        csvWriter = CSVWriter.getInstance();
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(20,40,600,250);

        // Buttons setbounds
        uploadAFileButton.setBounds(650, 50, 200,100);
        uploadAFileButton.setText("Upload JFRs");
        refreshFileListButton.setBounds(650, 150, 200,100);
        refreshFileListButton.setText("Refresh");
        memoryStateButton.setBounds(40, 300, 200,80);
        memoryStateButton.setText("Memory sequence");
        pauseTimeButton.setBounds(40,400,200,80);
        pauseTimeButton.setText("pause Time series");
        cpuAnalyzeButton.setBounds(260, 300, 200,80);
        cpuAnalyzeButton.setText("CPU");
        gcEventButton.setBounds(260,400,200,80);
        gcEventButton.setText("All gc attributes");



        panel1 = new JPanel(new BorderLayout());
        memoryStateButton.setEnabled(false);
        cpuAnalyzeButton.setEnabled(false);
        pauseTimeButton.setEnabled(false);
        gcEventButton.setEnabled(false);

        // JPanel bounds
        panel1.setBounds(0, 0, 1000, 600);

        //JFrame layout
        this.setLayout(null);

        //JPanel layout
        panel1.setLayout(null);
        // Adding to JFrame
        panel1.add(uploadAFileButton);
        panel1.add(memoryStateButton);
        panel1.add(cpuAnalyzeButton);
        panel1.add(pauseTimeButton);
        panel1.add(refreshFileListButton);
        panel1.add(gcEventButton);

        panel1.add(scrollPane);
        add(panel1);

        // JFrame properties
        setSize(1000, 600);
        setBackground(Color.BLACK);
        setTitle("TAZ JFR Parser");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        jfrChooser.setDialogTitle("Choose JFR files");
        jfrChooser.setMultiSelectionEnabled(true);

        uploadAFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                System.out.println(e.getActionCommand());
                jfrChooser.showOpenDialog(panel1);
                File[] files = jfrChooser.getSelectedFiles();
                for (File file : files)
                    try {
                        textArea.append(file.getCanonicalPath()+" \n");
                        filepaths.put(file.getName(),file.getCanonicalPath());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                if(jfrReader != null)
                    jfrReader.readJFR(filepaths);
                memoryStateButton.setEnabled(true);
                cpuAnalyzeButton.setEnabled(true);
                pauseTimeButton.setEnabled(true);
                gcEventButton.setEnabled(true);

            }
        });

        memoryStateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(e.getActionCommand());
                getGCStates();

            }
        });

        pauseTimeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(e.getActionCommand());
                getPauseTimeSeries();

            }
        });

        gcEventButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(e.getActionCommand());
                getGCAttributes();

            }
        });

        cpuAnalyzeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                System.out.println(e.getActionCommand());

            }
        });


        refreshFileListButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                System.out.println(e.getActionCommand());
                textArea.setText("");
                filepaths = new LinkedHashMap<String,String>();
                if(jfrReader != null)
                    jfrReader.refreshViewList();
                memoryStateButton.setEnabled(false);
                cpuAnalyzeButton.setEnabled(false);
                gcEventButton.setEnabled(false);
                pauseTimeButton.setEnabled(false);

            }
        });

    }

    public void getGCStates(){
        if (jfrReader != null) {
            jfrReader.getGCStates();
        }
    }

    public void getGCAttributes(){
        if (jfrReader != null) {
            jfrReader.getGCAttributes();
        }
    }

    public void getPauseTimeSeries(){
        if (jfrReader != null) {
            jfrReader.getPauseTimeSeries();
        }

    }

    public static void main(String[] args) {

        new GUI();


    }
}
