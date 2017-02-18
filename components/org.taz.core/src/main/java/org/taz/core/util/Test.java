package org.taz.core.util;

import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by Maninesan on 2/17/17.
 */
public class Test {
    public static void main(String[] args) {
        AETest test = new AETest();
//        test.generateScoreSeries("/home/garth/anomaly_final.jfr");
        List<Integer> list  = test.getAnomalyTimes("/home/garth/FYP/product-taz/components/org.taz.core/src/main/resources/quhu92yl/gctime_ae_score.csv");
        for(int d:list){
            System.out.println(d);
        }
}
}
