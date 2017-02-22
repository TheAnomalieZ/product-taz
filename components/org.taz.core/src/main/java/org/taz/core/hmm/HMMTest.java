package org.taz.core.hmm;

import org.taz.core.autoencoder.AE;

import java.util.ArrayList;

/**
 * Created by suve on 21/02/17.
 */
public class HMMTest {
    public static void main(String[] args) {
        HMM hmm = new HMM();

        ArrayList<Double> scorelist  = hmm.generateScoreSeries("/home/suve/FYP/anomaly.jfr","App1");

    }
}
