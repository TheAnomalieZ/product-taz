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

public class GUI extends JFrame {

    private static JButton uploadAFileButton = new JButton();
    private static JPanel panel1;
    private JButton memoryAnalyzeButton = new JButton();
    private JButton cpuAnalyzeButton = new JButton();
    private JTextArea textArea = new JTextArea();
    private ArrayList<String> filepaths = new ArrayList<String>();
    private JButton refreshFileListButton = new JButton();
    private JFileChooser jfrChooser = new JFileChooser();
    private  JFileChooser chooser;

    private ArrayList<Integer> GCSequence;
    private CSVWriter csvWriter;


    private JFRReader jfrReader;

    public GUI() {
        jfrReader = JFRReader.getInstance();
        csvWriter = CSVWriter.getInstance();
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(20,50,450,200);

        // Buttons setbounds
        uploadAFileButton.setBounds(500, 50, 200,100);
        uploadAFileButton.setText("Upload JFRs");
        refreshFileListButton.setBounds(500, 150, 200,100);
        refreshFileListButton.setText("Refresh");
        memoryAnalyzeButton.setBounds(150, 300, 200,100);
        memoryAnalyzeButton.setText("Memory sequence");
        cpuAnalyzeButton.setBounds(400, 300, 200,100);
        cpuAnalyzeButton.setText("CPU");

        panel1 = new JPanel(new BorderLayout());
        memoryAnalyzeButton.setEnabled(false);
        cpuAnalyzeButton.setEnabled(false);

        // JPanel bounds
        panel1.setBounds(0, 0, 800, 400);

        //JFrame layout
        this.setLayout(null);

        //JPanel layout
        panel1.setLayout(null);
        // Adding to JFrame
        panel1.add(uploadAFileButton);
        panel1.add(memoryAnalyzeButton);
//        panel1.add(cpuAnalyzeButton);
        panel1.add(refreshFileListButton);
//        panel1.add(textArea);
        panel1.add(scrollPane);
        add(panel1);

        // JFrame properties
        setSize(750, 450);
        setBackground(Color.BLACK);
        setTitle("TAZ GUI");
//        setLocationRelativeTo(null);
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
                        filepaths.add(file.getCanonicalPath());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                if(jfrReader != null)
                    jfrReader.readJFR(filepaths);
                memoryAnalyzeButton.setEnabled(true);
                cpuAnalyzeButton.setEnabled(true);
            }
        });

        memoryAnalyzeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(e.getActionCommand());

                String fileName = "/home/mani/jfr/save.csv";

                if (jfrReader != null)
                    GCSequence = jfrReader.getGCStates();

                for(Integer state:GCSequence)
                    textArea.append(state.toString()+"\n");

                chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                if( chooser.showOpenDialog( panel1 ) == JFileChooser.APPROVE_OPTION )
                {
                     fileName=chooser.getSelectedFile().getAbsolutePath();
                }

                File file = new File(fileName);
                csvWriter.generateGCStates(GCSequence, file);
            }
        });

        cpuAnalyzeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                System.out.println(e.getActionCommand());
                if(jfrReader != null)
                    jfrReader.getGCStates();
            }
        });



        refreshFileListButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                System.out.println(e.getActionCommand());
                textArea.setText("");
                filepaths = new ArrayList<String>();
                if(jfrReader != null)
                    jfrReader.refreshViewList();
                memoryAnalyzeButton.setEnabled(false);
                cpuAnalyzeButton.setEnabled(false);
            }
        });

    }

    public static void main(String[] args) {

        new GUI();


    }
}
