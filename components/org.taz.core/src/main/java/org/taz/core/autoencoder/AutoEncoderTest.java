package org.taz.core.autoencoder;

/**
 * Created by  Maninesan on 11/26/16.
 */

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.deeplearning4j.api.storage.StatsStorage;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.ui.api.UIServer;
import org.deeplearning4j.ui.storage.FileStatsStorage;
import org.deeplearning4j.ui.storage.InMemoryStatsStorage;
import org.deeplearning4j.ui.storage.mapdb.MapDBStatsStorage;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.SplitTestAndTrain;
import org.nd4j.linalg.dataset.api.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.factory.Nd4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutoEncoderTest {
    private static Logger log = LoggerFactory.getLogger(AutoEncoderTest.class);

    public static void main(String[] args) throws Exception {

        int batchSize = 100 ;
        int chunkSize = 50 ;
        int layers[] = { chunkSize, 100, 50, 30 } ;


        Path target = Paths.get( "/home/mani/jfr/data" ) ;
        if( !Files.exists(target) ) {
            log.info( "Creating {}", target ) ;
            Files.createDirectories( target ) ;
        }

        System.out.println();

        AE ae = new AE( target, layers ) ;



        //Load the training data:
        RecordReader trainRecordReader = new CSVRecordReader(0,",");
        trainRecordReader.initialize(new FileSplit(new File("/home/mani/jfr/dada.csv")));
        DataSetIterator trainIter = new RecordReaderDataSetIterator(trainRecordReader,80);


        //Load the test data
        RecordReader testRecordReader = new CSVRecordReader(0,",");
        testRecordReader.initialize(new FileSplit(new File("/home/mani/jfr/test.csv")));
        DataSetIterator testIter = new RecordReaderDataSetIterator(testRecordReader,1);



//        System.out.println(testIter.next().getFeatures());


        List<INDArray> featuresTrain = new ArrayList<>();
        List<INDArray> featuresTest = new ArrayList<>();
        List<INDArray> labelsTest = new ArrayList<>();

//        Random r = new Random(12345);
//        while(trainIter.hasNext()){
//            DataSet ds = trainIter.next();
//            SplitTestAndTrain split = ds.splitTestAndTrain(60, r);  //60/20 split (from miniBatch = 80)
//            featuresTrain.add(split.getTrain().getFeatureMatrix());
//            DataSet dsTest = split.getTest();
//            featuresTest.add( split.getTest().getFeatureMatrix());
//            INDArray indexes = Nd4j.argMax(dsTest.getLabels(),1); //Convert from one-hot representation -> index
//            labelsTest.add(indexes);
//        }
//        for (INDArray a:
//                labelsTest) {
//            System.out.println(a);
//        }


        //Train model:
//        int nEpochs = 30;
//        for( int epoch=0; epoch<nEpochs; epoch++ ){
//            for(INDArray data : featuresTrain){
//                net.fit(data,data);
//            }
//            System.out.println("Epoch " + epoch + " complete");
//        }
//


/**

        //Initialize the user interface backend
        UIServer uiServer = UIServer.getInstance();

        //Configure where the network information (gradients, activations, score vs. time etc) is to be stored
        //Then add the StatsListener to collect this information from the network, as it trains
        StatsStorage statsStorage = new  InMemoryStatsStorage();

        ae.setListener(statsStorage);


        //Attach the StatsStorage instance to the UI: this allows the contents of the StatsStorage to be visualized
        uiServer.attach(statsStorage);
**/

        log.info("Load datasets ... ");
        ae.train(trainIter);
        ae.test(testIter);



        log.info("Saving params ...");
        ae.save( target );

    }



}
