package org.taz.core.autoencoder;

/**
 * Created by  Maninesan on 11/26/16.
 */

import java.io.IOException;
import java.nio.file.Path;


import org.deeplearning4j.api.storage.StatsStorage;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.GradientNormalization;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration.ListBuilder;
import org.deeplearning4j.nn.conf.Updater;
import org.deeplearning4j.nn.conf.layers.*;
import org.deeplearning4j.nn.conf.layers.RBM.HiddenUnit;
import org.deeplearning4j.nn.conf.layers.RBM.VisibleUnit;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.api.IterationListener;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;

import org.deeplearning4j.ui.stats.StatsListener;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.nd4j.linalg.lossfunctions.LossFunctions.LossFunction;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

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


        MultiLayerNetwork model = ModelPersistence.loadModel( target ) ;
        if( model == null ) {

            log.info("Build model....");
            ListBuilder lb = new NeuralNetConfiguration.Builder()
                    .seed(seed)
                    .iterations(iterations)
                    .learningRate( .001 )
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
                .lossFunction(LossFunction.MEAN_ABSOLUTE_ERROR)
                .activation("relu")
                .weightInit(WeightInit.RELU)
                .nIn( n[1] )
                .nOut( n[0] )
                .build();

        return rc ;
    }

    private RBM makeLayer(int in, int out ) {
        log.info( "Making layer {} -> {}", in, out );
        return new RBM.Builder()
                .nIn(in)
                .nOut(out)
                .activation("relu")
                .weightInit(WeightInit.RELU)
                .lossFunction(LossFunctions.LossFunction.RECONSTRUCTION_CROSSENTROPY).k(3)
                .hiddenUnit(HiddenUnit.RECTIFIED).visibleUnit(VisibleUnit.GAUSSIAN)
                .updater(Updater.NESTEROVS).gradientNormalization(GradientNormalization.ClipL2PerLayer)
                .build();
    }

    public void train( DataSetIterator iter ) {

        for( int i=0 ; i<40 ; i++ ) {
            log.info("Train model : Loop "+i);
            while(iter.hasNext()) {
                synchronized( this ) {
                    DataSet next = iter.next();
                    model.fit(new DataSet(next.getFeatureMatrix(),next.getFeatureMatrix()));
                }
            }
            iter.reset();
        }


    }

    public void trainAndTest( DataSetIterator trainIter,  DataSetIterator testIter){
        // ----- Train the network, evaluating the test set performance at each epoch -----
        int nEpochs = 3;
        String str = "Test set evaluation at epoch %d: Accuracy = %.2f, F1 = %.2f";
        for (int i = 0; i < nEpochs; i++) {
            model.fit(trainIter);

            //Evaluate on the test set:
            Evaluation evaluation = model.evaluate(testIter);
            log.warn(String.format(str, i, evaluation.accuracy(), evaluation.f1()));

            trainIter.reset();
            testIter.reset();
        }
    }

    public void test(DataSetIterator iter){
        log.info("Evaluate the model ...");
        String str = "Test set evaluation: Accuracy = %.2f, F1 = %.2f";
        Evaluation evaluation = model.evaluate(iter);
        log.warn(String.format(str, evaluation.accuracy(), evaluation.f1()));
        System.out.println(evaluation.stats());
    }



    public void save( Path target ) throws IOException {
        ModelPersistence.saveModel( model, target ) ;
    }

    public void setListener(StatsStorage statsStorage){
        model.setListeners(new StatsListener(statsStorage));
    }
}