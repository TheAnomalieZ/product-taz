package org.taz.core.autoencoder;

/**Example: Anomaly Detection on JFR GC sequence using simple autoencoder without pretraining
 * The goal is to identify outliers sequences, i.e., those sequences that are unusual or
 * not like the typical sequences.
 * This is accomplished in this example by using reconstruction error: stereotypical
 * examples should have low reconstruction error, whereas outliers should have high
 * reconstruction error
 *
 * @author Maninesan
 */

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.SequenceRecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.records.reader.impl.csv.CSVSequenceRecordReader;
import org.datavec.api.split.FileSplit;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.datasets.datavec.SequenceRecordReaderDataSetIterator;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutoEncoderTest {
    private static Logger log = LoggerFactory.getLogger(AutoEncoderTest.class);

    public static void main(String[] args) throws Exception {

        int batchSize = 100 ;
        int chunkSize = 20 ;
        int layers[] = { 20, 20 } ;


        Path target = Paths.get( "/home/garth/FYP/Model/RBM" ) ;
        if( !Files.exists(target) ) {
            log.info( "Creating {}", target ) ;
            Files.createDirectories( target ) ;
        }

        System.out.println();

        AE ae = new RBMAutoEncoder( target, layers ) ;

        //Load the training data:
        RecordReader trainRecordReader = new CSVSequenceRecordReader(0);
        trainRecordReader.initialize(new FileSplit(new File("/home/garth/FYP/JFR_CSV/pausetime.csv")));
        DataSetIterator trainIter = new RecordReaderDataSetIterator(trainRecordReader,20);
//        DataSetIterator trainIter = new SequenceRecordReaderDataSetIterator(Iterator(trainRecordReader,20);


        while(trainIter.hasNext()) {
            DataSet s= trainIter.next();
            System.out.println(s);
        }

        //Load the test data
        RecordReader testRecordReader = new CSVRecordReader(0);
        testRecordReader.initialize(new FileSplit(new File("/home/garth/FYP/JFR_CSV/anomaly.csv")));
        DataSetIterator testIter = new RecordReaderDataSetIterator(testRecordReader,1);
//
//        SequenceRecordReader trainFeatures = new CSVSequenceRecordReader();
//        trainFeatures.initialize(new FileSplit(new File("/home/mani/jfr/mama.csv")));
//        DataSetIterator trainData = new SequenceRecordReaderDataSetIterator(trainFeatures, trainLabels, miniBatchSize, numLabelClasses,
//                false, SequenceRecordReaderDataSetIterator.AlignmentMode.ALIGN_END);

/**

        //Initialize the user interface backend
        UIServer uiServer = UIServer.getInstance();

        //Configure where the network information (gradients, activations, score vs. time etc) is to be stored
        //Then add the StatsListener to collect this information from the network, as it trains
        StatsStorage statsStorage = new FileStatsStorage(new File("/home/mani/jfr/data/rbm.txt"));
//                InMemoryStatsStorage();


        ae.setListener(statsStorage);


        //Attach the StatsStorage instance to the UI: this allows the contents of the StatsStorage to be visualized
        uiServer.attach(statsStorage);

**/

        log.info("Load datasets ... ");
         ae.train(trainIter);


        List<INDArray> featuresTest = new ArrayList<>();

        Random r = new Random(12345);
        while(testIter.hasNext()){
            DataSet ds = testIter.next();
            featuresTest.add(ds.getFeatures());
        }

        ArrayList<ImmutableTriple<Double,Integer,INDArray>> l = ae.calScore(featuresTest);

        for(ImmutableTriple<Double,Integer,INDArray> m :l){
            System.out.println(m);
        }


        System.out.println(ae.getModel().score());

        ae.test(testIter);

        log.info("Saving params ...");
        ae.save( target );

    }



}
