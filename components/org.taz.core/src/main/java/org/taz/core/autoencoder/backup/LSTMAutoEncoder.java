package org.taz.core.autoencoder.backup;

import org.deeplearning4j.nn.conf.layers.GravesLSTM;
import org.deeplearning4j.nn.weights.WeightInit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

/**
 * Created by  Maninesan on 12/4/16.
 */
public class LSTMAutoEncoder extends AE {

    private static Logger log = LoggerFactory.getLogger(LSTMAutoEncoder.class);

    public LSTMAutoEncoder(Path target, int[] layerSizes) throws Exception {
        super(target, layerSizes);
    }


    private GravesLSTM makeLayer(int in, int out ) {
        log.info( "Making layer {} -> {}", in, out );
        return new GravesLSTM.Builder()
                .nIn(in)
                .nOut(out)
                .activation("relu")
                .weightInit(WeightInit.RELU)
                .build();
    }




}
