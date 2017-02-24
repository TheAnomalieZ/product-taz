package org.taz.core.hmm;

/**
 * Created by suve on 23/02/17.
 */
public class HMMTrainTest {
    public static void main(String[] args){
        HMMTrain train = new HMMTrain();
        train.startHMMTraining("/home/suve/FYP/HMMLast/App1/no_big.jfr","App2");
    }
}
