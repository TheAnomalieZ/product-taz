package org.taz.core.tests;

import com.opencsv.CSVReader;
import org.taz.core.autoencoder.AE;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 * Created by Maninesan on 2/18/17.
 */
public class AETest {

    @Test
    public void getThreshold() throws Exception {
        AE ae = new AE();
        ArrayList<Double> scorelist  = ae.generateScoreSeries(TestConstants.jfrtestfile,"App1_gctime");
        double t = ae.getThreshold();
        assertNotNull(t);
    }

    @Test
    public void generateScoreSeries() throws Exception {
        AE ae = new AE();
        ArrayList<Double> scorelist  = ae.generateScoreSeries(TestConstants.jfrtestfile,"App1_gctime");
        assertNotNull(scorelist);

    }

    @Test
    public void getAnomalyLabels() throws Exception {
        AE ae = new AE();
        ArrayList<Integer> labellist = ae.getAnomalyLabels(setupTest());
        assertNotNull(labellist);

    }

    @Test
    public void getAnomlayTimes() throws Exception {
        AE ae = new AE();
        ArrayList<Integer> labellist = ae.getAnomalyLabels(setupTest());
        ArrayList<Double[]> times    = ae.getAnomlayTimes(labellist);
        assertNotNull(times);

    }

    public  ArrayList<Double> setupTest(){
        String scorefilePath = TestConstants.anomalyscorefile;
        CSVReader reader = null;
        ArrayList<Double> scorelist = new ArrayList<Double>();
        try {
            reader = new CSVReader(new FileReader( scorefilePath));
            String [] nextLine;
            Double score;
            while ((nextLine = reader.readNext()) != null) {
                // nextLine[] is an array of values from the line
                score = Double.parseDouble(nextLine[0]);
                scorelist.add(score);
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        scorelist.add(36.2);

        return scorelist;

    }

}