package org.taz.core.autoencoder;

/**
 * Created by  Maninesan on 11/26/16.
 */

import java.io.IOException;
import java.nio.file.Path;


import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.deeplearning4j.api.storage.StatsStorage;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.GradientNormalization;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration.ListBuilder;
import org.deeplearning4j.nn.conf.Updater;
import org.deeplearning4j.nn.conf.layers.*;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.api.IterationListener;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;

import org.deeplearning4j.ui.stats.StatsListener;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.lossfunctions.LossFunctions;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class AE {
    private static Logger log = LoggerFactory.getLogger(AE.class);

    private final MultiLayerNetwork model ;
    private final int chunk = 50;
    private final int layers[] ;



    public AE(Path target, int layerSizes[]) throws Exception {
        int seed = 123;
        int iterations = 3 ;
        int listenerFreq = 1 ;
        this.layers = layerSizes ;
        double lr = 1e-4;
        double mu = 0.9;
        double l2 = 5e-6;


        MultiLayerNetwork model = ModelPersistence.loadModel( target ) ;
        if( model == null ) {

            log.info("Build model....");
            ListBuilder lb = new NeuralNetConfiguration.Builder()
                    .seed(seed)
                    .iterations(iterations)
                    .learningRate( lr )
                    .momentum(mu)
                    .optimizationAlgo(OptimizationAlgorithm.LBFGS )
                    .regularization(true).l2(1e-4)
                    .list();

            Layer[] layers = makeLayers(layerSizes) ;
            for( int l=0 ; l<layers.length ; l++ ) {
                lb.layer(l, layers[l] ) ;
            }

            MultiLayerConfiguration conf = lb
                    .pretrain(true)
                    .backprop(true)
                    .build() ;

            model = new MultiLayerNetwork(conf);
            model.init();
        }

        this.model = model ;
        model.setListeners(Collections.singletonList((IterationListener) new ScoreIterationListener(listenerFreq)));    }



    private Layer[] makeLayers( int ... n ) {
        if( n.length<2 ) {
            throw new RuntimeException( "There must be more than 2 hidden layers in an RBM" ) ;
        }

        Layer[] rc = new Layer[ 2 * (n.length - 1)  ] ;

        for( int i=0 ; i<n.length-1 ; i++ ) {
            rc[i] = makeLayer( n[i], n[i+1] ) ;
            rc[rc.length-i-1] = makeLayer( n[i+1], n[i] ) ;
        }

        rc[rc.length-1] = new OutputLayer.Builder()
                .lossFunction(LossFunctions.LossFunction.RECONSTRUCTION_CROSSENTROPY)
                .activation("relu")
                .weightInit(WeightInit.RELU)
                .nIn( n[1] )
                .nOut( n[0] )
                .build();

        return rc ;
    }

    private AutoEncoder makeLayer(int in, int out ) {
        log.info( "Making layer {} -> {}", in, out );
        return new AutoEncoder.Builder()
                .nIn(in)
                .nOut(out)
                .activation("relu")
                .weightInit(WeightInit.RELU)
                .lossFunction(LossFunctions.LossFunction.RECONSTRUCTION_CROSSENTROPY)
                .updater(Updater.NESTEROVS).gradientNormalization(GradientNormalization.ClipL2PerLayer)
                .build();
    }

    public void train( DataSetIterator iter ) {

        for( int i=0 ; i<40 ; i++ ) {
            log.info("Train model : Loop "+i);
            while(iter.hasNext()) {
                    DataSet next = iter.next();
                    model.fit(new DataSet(next.getFeatureMatrix(),next.getFeatureMatrix()));
            }
            iter.reset();
        }


    }


    public void test(DataSetIterator iter){
        log.info("Evaluate the model ...");
        String str = "Test set evaluation: Accuracy = %.2f, F1 = %.2f";
        Evaluation eval = new Evaluation(2);

        while(iter.hasNext()) {
            DataSet next = iter.next();
            INDArray predicted = model.output(next.getFeatureMatrix());
            INDArray actual = next.getFeatures();
            eval.eval(actual,predicted);

        }

        log.info(eval.stats());
        log.info("****************Example finished********************");

        /**
        Evaluation evaluation = model.evaluate(iter);
        log.warn(String.format(str, evaluation.accuracy(), evaluation.f1()));
        System.out.println(evaluation.stats());
    **/
       }

    //Evaluate the model on test data
    //Score each example in test set separately
    //Then add triple (score, digit, and INDArray data) to lists and sort by score
    //This allows us to get best N and worst N digits for each type
    public  ArrayList<ImmutableTriple<Double,Integer,INDArray>> calScore(List<INDArray> featuresTest){
        ArrayList<ImmutableTriple<Double,Integer,INDArray>> scorelist = new ArrayList<>();


        int count = 0;
        for( int i=0; i<featuresTest.size(); i++ ){
            INDArray testData = featuresTest.get(i);
                double score = model.score(new DataSet(testData,testData));
                HashMap<Double,INDArray> m = new HashMap<>();
                m.put(score,testData);
                scorelist.add(new ImmutableTriple<Double,Integer,INDArray>(score, count++, testData));

        }


         Collections.sort(scorelist, c);


        //Select the 5 best and 5 worst numbers (by reconstruction error) for each digit
        List<INDArray> best = new ArrayList<>(50);
        List<INDArray> worst = new ArrayList<>(50);
        for( ImmutableTriple<Double, Integer, INDArray> m:scorelist){
                best.add(m.getRight());
//                worst.add(list.get(list.size()-j-1).getRight());

        }

        return scorelist;
    }
    //Sort data by score, separately for each digit
    Comparator<ImmutableTriple<Double, Integer, INDArray>> c = new Comparator<ImmutableTriple<Double, Integer, INDArray>>() {
        @Override
        public int compare(ImmutableTriple<Double, Integer, INDArray> o1, ImmutableTriple<Double, Integer, INDArray> o2) {
            return Double.compare(o1.getLeft(),o2.getLeft());
        }
    };


    public MultiLayerNetwork getModel(){
        return model;


    }


    public void save( Path target ) throws IOException {
        ModelPersistence.saveModel( model, target ) ;
    }

    public void setListener(StatsStorage statsStorage){
        model.setListeners(new StatsListener(statsStorage));
    }
}
