package org.taz.core.hmm;

import org.taz.core.autoencoder.AE;

import java.util.ArrayList;

/**
 * Created by suve on 21/02/17.
 */
public class HMMTest {
    public static void main(String[] args) {
        HMM hmm = new HMM();

        //ArrayList<Double> scorelist1  = hmm.generateScoreSeries("/home/suve/FYP/HMMLast/App1/normal_big.jfr","App1");
        ArrayList<Double> scorelist  = hmm.generateScoreSeries("/home/suve/FYP/HMMLast/App1/anomaly_validation_2.jfr","val1");
        //ArrayList<Double> scorelist  = hmm.generateScoreSeries("/home/suve/FYP/HMMLast/App1/anomaly_final.jfr","App1");

        double threshold = hmm.getPercentileValue(scorelist, 5);
        //double threshold = hmm.generateThreshold(scorelist1, 30);
        ArrayList<Integer> labels = hmm.generateAnomalyLabels(scorelist, threshold);
        ArrayList<Double []> anomalyregion = hmm.getAnomlayTimes(labels, 10);

    }
}
