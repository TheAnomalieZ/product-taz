package org.taz.core.util;

import com.opencsv.CSVReader;
import org.taz.core.autoencoder.AE;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Maninesan on 2/18/17.
 */
public class AETest {
    public static void main(String[] args) {
        AE ae = new AE();

        ArrayList<Double> scorelist  = ae.generateScoreSeries("/home/FYP/anomaly.jfr","App2_gctime");

        ArrayList<Integer> labellist = ae.getAnomalyLabels(scorelist);

        ArrayList<Double[]> times    = ae.getAnomlayTimes(labellist);

        double t = ae.getThreshold();


//        String scorefilePath = "/home/garth/FYP/product-taz/components/org.taz.core/src/main/resources/K6pJ3BDB/App1_gctime_ae_score.csv";
//        CSVReader reader = null;
//        ArrayList<Double> scorelist = new ArrayList<Double>();
//        try {
//            reader = new CSVReader(new FileReader( scorefilePath));
//            String [] nextLine;
//            Double score;
//            while ((nextLine = reader.readNext()) != null) {
//                // nextLine[] is an array of values from the line
//                score = Double.parseDouble(nextLine[0]);
//                scorelist.add(score);
//            }
//        } catch (FileNotFoundException e1) {
//            e1.printStackTrace();
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }
//        scorelist.add(36.2);
//        ae.getAnomlayTimes(ae.getAnomalyLabels(scorelist));
    }
}
