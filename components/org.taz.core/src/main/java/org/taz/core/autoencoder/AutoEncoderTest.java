package org.taz.core.autoencoder;

/**
 * Created by  Maninesan on 11/26/16.
 */

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.attribute.FileAttribute;
//import java.util.Collections;

import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutoEncoderTest {
    int batchSize = 100 ;



    private static Logger log = LoggerFactory.getLogger(AutoEncoderTest.class);

    public static void main(String[] args) throws Exception {

//		Nd4j.ENFORCE_NUMERICAL_STABILITY = true ;


        int batchSize = 100 ;
        int chunkSize = 50 ;
        int layers[] = { chunkSize, 100, 50, 30 } ;


        Path target = Paths.get( "/home/mani/jfr/data" ) ;
        if( !Files.exists(target) ) {
            log.info( "Creating {}", target ) ;
            Files.createDirectories( target ) ;
        }

        AutoEncoder ae = new AutoEncoder( target, layers ) ;



        //Load the training data:
        RecordReader recordReader = new CSVRecordReader(0,",");
        recordReader.initialize(new FileSplit(new File("/home/mani/jfr/GCStates0.csv")));
        DataSetIterator trainIter = new RecordReaderDataSetIterator(recordReader,1);



        log.info("Load datasets ... ");
        ae.train(trainIter);
        log.info("Saving params ...");

        ae.save( target );

    }



}
