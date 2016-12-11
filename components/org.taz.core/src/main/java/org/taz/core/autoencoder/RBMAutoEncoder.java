package org.taz.core.autoencoder;

import org.deeplearning4j.nn.conf.GradientNormalization;
import org.deeplearning4j.nn.conf.Updater;
import org.deeplearning4j.nn.conf.layers.RBM;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

/**
 * Created by  Maninesan on 12/10/16.
 */
public class RBMAutoEncoder extends AE {
    private static Logger log = LoggerFactory.getLogger(RBMAutoEncoder.class);

    public RBMAutoEncoder(Path target, int[] layerSizes) throws Exception {
        super(target, layerSizes);
    }


    private RBM makeLayer(int in, int out ) {
        log.info( "Making layer {} -> {}", in, out );
        return new RBM.Builder()
                .nIn(in)
                .nOut(out)
                .activation("relu")
                .weightInit(WeightInit.RELU)
                .lossFunction(LossFunctions.LossFunction.RECONSTRUCTION_CROSSENTROPY).k(3)
                .hiddenUnit(RBM.HiddenUnit.RECTIFIED).visibleUnit(RBM.VisibleUnit.GAUSSIAN)
                .updater(Updater.NESTEROVS).gradientNormalization(GradientNormalization.ClipL2PerLayer)
                .build();
    }


}
