package org.taz.core.util;

import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Maninesan on 2/18/17.
 */
public class Test {
    public static void main(String[] args) {
        AETest ae = new AETest();

//        ae.generateScoreSeries("/home/garth/anomaly_final.jfr","App1_gctime");
    String scorefilePath = "/home/garth/FYP/product-taz/components/org.taz.core/src/main/resources/pZFy8T6B/App1_gctime_ae_score.csv";
        CSVReader reader = null;
        ArrayList<Double> scorelist = new ArrayList<Double>();
        try {
            reader = new CSVReader(new FileReader( scorefilePath));
            String [] nextLine;
            Double score;
            while ((nextLine = reader.readNext()) != null) {
                // nextLine[] is an array of values from the line
                score = Double.parseDouble(nextLine[0]);
//                System.out.println(score) ;
                scorelist.add(score);
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
//for(double a:scorelist){
//            System.out.println(a);
//}
        ae.getAnomalyTimes(scorelist);
}               
}
