package org.taz.core.autoencoder;

/**
 * Created by  Maninesan on 11/30/16.
 */

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.deeplearning4j.api.storage.StatsStorage;
import org.deeplearning4j.ui.api.UIServer;
import org.deeplearning4j.ui.stats.StatsListener;
import org.deeplearning4j.ui.storage.InMemoryStatsStorage;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.SequenceRecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.records.reader.impl.csv.CSVSequenceRecordReader;
import org.datavec.api.split.FileSplit;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;

public class UIExample {
    private static Logger log = LoggerFactory.getLogger(AutoEncoderTest.class);
    public static void main(String[] args)throws Exception {

        //Get our network and training data
        int chunkSize = 100 ;
        int layers[] = { chunkSize, 100, 50, 30 } ;


        Path target = Paths.get( "/home/mani/jfr/data" ) ;
        if( !Files.exists(target) ) {
            log.info( "Creating {}", target ) ;
            Files.createDirectories( target ) ;
        }

        LSTMAutoEncoder ae = new LSTMAutoEncoder(target, layers) ;

        //Load the training data:
        RecordReader recordReader = new CSVRecordReader(0,",");
        recordReader.initialize(new FileSplit(new File("/home/mani/jfr/GCStates0.csv")));
        DataSetIterator trainIter = new RecordReaderDataSetIterator(recordReader,1);



        //Initialize the user interface backend
        UIServer uiServer = UIServer.getInstance();

        //Configure where the network information (gradients, activations, score vs. time etc) is to be stored
        //Then add the StatsListener to collect this information from the network, as it trains
        StatsStorage statsStorage = new InMemoryStatsStorage();


        ae.setListener(new StatsListener(statsStorage));

        //Attach the StatsStorage instance to the UI: this allows the contents of the StatsStorage to be visualized
        uiServer.attach(statsStorage);

        //Start training:
        log.info("Load datasets ... ");
        ae.train(trainIter);
        log.info("Saving params ...");

        ae.save( target );


        //go to http://localhost:9000/train
    }
}

