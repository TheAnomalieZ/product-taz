package org.taz.core.autoencoder;

/**
 * Created by  Maninesan on 11/26/16.
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.api.Layer.TrainingMode;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.GradientNormalization;
import org.deeplearning4j.nn.conf.LearningRatePolicy;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration.ListBuilder;
import org.deeplearning4j.nn.conf.Updater;
import org.deeplearning4j.nn.conf.layers.Layer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.conf.layers.RBM;
import org.deeplearning4j.nn.conf.layers.RBM.HiddenUnit;
import org.deeplearning4j.nn.conf.layers.RBM.VisibleUnit;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.api.IterationListener;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.nd4j.linalg.lossfunctions.LossFunctions.LossFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

public class AutoEncoder {
    private static Logger log = LoggerFactory.getLogger(AutoEncoder.class);

    private final MultiLayerNetwork model ;
    private final int chunk = 50;
    private final int layers[] ;



    public AutoEncoder(Path target,int layerSizes[] ) throws Exception {
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
                        .list() ;

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
            model.setListeners(Collections.singletonList((IterationListener) new ScoreIterationListener(listenerFreq)));
        }



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
                .build() ;

        return rc ;
    }

    private RBM makeLayer( int in, int out ) {
        log.info( "Making layer {} -> {}", in, out );
        return new RBM.Builder()
                .nIn(in)
                .nOut(out)
                .activation("relu")
                .weightInit(WeightInit.RELU)
                .lossFunction(LossFunctions.LossFunction.RECONSTRUCTION_CROSSENTROPY).k(3)
                .hiddenUnit(HiddenUnit.RECTIFIED).visibleUnit(VisibleUnit.GAUSSIAN)
                .updater(Updater.NESTEROVS).gradientNormalization(GradientNormalization.ClipL2PerLayer)
                .build() ;
    }

    public void train( DataSetIterator iter ) {

        for( int i=0 ; i<15 ; i++ ) {
            log.info("Train model ...");
            while(iter.hasNext()) {
                synchronized( this ) {
                    DataSet next = iter.next();
                    model.fit(new DataSet(next.getFeatureMatrix(),next.getFeatureMatrix()));
                }
            }
            iter.reset();
        }
    }

    public byte [] classify( int [] data ) {

        long cs = 0 ;
        INDArray input = Nd4j.create( chunk ) ;
        for( int i=0 ; i<data.length ; i++ ) {
            cs <<= 1 ;
            cs += data[i] ;
            input.putScalar(i, data[i] ) ;
        }
        log.info("Classify data requested. checksum: {}", cs );

        //  model.feedForward( input )
        INDArray output ;
        List<INDArray> outputs ;
        synchronized( this ) {
            log.info("Lock acquired. Classify data sent." );
            //			output = model.output(input, TrainingMode.TEST ) ;

            outputs = model.feedForward( input, false ) ;
        }
        //		byte rc[] = new byte[ data.length ] ;
        //		for( int i=0 ; i<rc.length ; i++ ) {
        //			rc[i] = (byte)( output.getFloat( i ) * 255.0 );
        //		}

        int bytesToSend = 0 ;
        for( int i=0 ; i<outputs.size() ; i++ ) {
            bytesToSend += outputs.get(i).length() ;
        }

        byte rc[] = new byte[ bytesToSend ] ;

        for( int i=0, ix=0 ; i<outputs.size() ; i++ ) {
            for( int j=0 ; j<outputs.get(i).length() ; j++, ix++ ) {
                rc[ix] = (byte)( outputs.get(i).getFloat( j ) * 250.0 );
            }
        }

        return rc ;
    }

    public void save( Path target ) throws IOException {
        ModelPersistence.saveModel( model, target ) ;
    }

}
