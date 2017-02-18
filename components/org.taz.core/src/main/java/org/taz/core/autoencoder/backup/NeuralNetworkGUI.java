package org.taz.core.autoencoder.backup;

import org.deeplearning4j.api.storage.StatsStorage;
import org.deeplearning4j.ui.api.UIServer;
import org.deeplearning4j.ui.storage.InMemoryStatsStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.taz.core.autoencoder.backup.AE;
import org.taz.core.autoencoder.backup.LSTMAutoEncoder;
import org.taz.core.autoencoder.backup.RBMAutoEncoder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by  Maninesan on 12/10/16.
 */
public class NeuralNetworkGUI {
    private static Logger log = LoggerFactory.getLogger(NeuralNetworkGUI.class);


    private JPanel panel1;
    private JRadioButton autoEncoderRadioButton;
    private JRadioButton preditionModelRadioButton;
    private JTextField txtepoch;
    private JTextField txtbatch;
    private JComboBox comboBox1;
    private JButton trainButton;
    private JTextArea textArea1;
    private JButton testButton;
    private ArrayList<String> filepaths = new ArrayList<String>();

    private String model ="AutoEncoderModel";
    private String layer ="LSTM";
    private int epcohs;
    private int batch;
    private AE aeModel;

    private JFileChooser fileChooser;

    public NeuralNetworkGUI(){
        ButtonGroup bG = new ButtonGroup();
        bG.add(autoEncoderRadioButton);
        bG.add(preditionModelRadioButton);
        trainButton.setEnabled(false);
        testButton.setEnabled(false);
        comboBox1.setEnabled(false);

        autoEncoderRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model = "AutoEncoderModel";
                trainButton.setEnabled(true);
                testButton.setEnabled(true);
                comboBox1.setEnabled(true);
            }
        });

        preditionModelRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model = "PredictionModel";
                trainButton.setEnabled(true);
                testButton.setEnabled(true);
                comboBox1.setEnabled(true);
            }
        });

        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                layer = (String)comboBox1.getSelectedItem();
            }
        });
        trainButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                System.out.println(e.getActionCommand());
                switch (model){
                    case "AutoEncoderModel":
                        try{
                            aeModel = getModel();

                        }catch (Exception ex){

                        }
                        break;
                    case "PredictionModel":

                }

            }
        });

        testButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(e.getActionCommand());

            }
        });


    }


    public void showUIServer(AE ae){
        //Initialize the user interface backend
        UIServer uiServer = UIServer.getInstance();

        //Configure where the network information (gradients, activations, score vs. time etc) is to be stored
        //Then add the StatsListener to collect this information from the network, as it trains
        StatsStorage statsStorage = new InMemoryStatsStorage();

        ae.setListener(statsStorage);


        //Attach the StatsStorage instance to the UI: this allows the contents of the StatsStorage to be visualized
        uiServer.attach(statsStorage);

    }

    public AE getModel() throws Exception{
        int layers[] = { 50, 100, 70, 50 } ;
        Path target;
        AE model;

        switch (layer){
            case "LSTM":
                target = Paths.get( "/home/mani/model/LSTM/" ) ;
                if( !Files.exists(target) ) {
                    log.info( "Creating {}", target ) ;
                    Files.createDirectories( target ) ;
                }

                model = new LSTMAutoEncoder( target, layers ) ;
                break;
            case "RBM":
                target = Paths.get( "/home/mani/model/RBM/" ) ;
                if( !Files.exists(target) ) {
                    log.info( "Creating {}", target ) ;
                    Files.createDirectories( target ) ;
                }
                model = new RBMAutoEncoder( target, layers ) ;
                break;
            case "AE":
                target = Paths.get( "/home/mani/model/AE/" ) ;
                if( !Files.exists(target) ) {
                    log.info( "Creating {}", target ) ;
                    Files.createDirectories( target ) ;
                }
                model = new AE( target, layers ) ;
                break;
            default:
                target = Paths.get( "/home/mani/model/RBM/" ) ;
                if( !Files.exists(target) ) {
                    log.info( "Creating {}", target ) ;
                    Files.createDirectories( target ) ;
                }
                model = new RBMAutoEncoder( target, layers ) ;
        }

        return model;

    }

    public static void main(String[] args){
        JFrame frame = new JFrame("NeuralNetworkGUI");
        frame.setContentPane(new NeuralNetworkGUI().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


    }
}
